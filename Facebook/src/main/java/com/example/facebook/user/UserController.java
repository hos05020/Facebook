package com.example.facebook.user;

import static com.example.facebook.common.ApiResult.OK;
import static com.example.facebook.common.AttachedFile.toAttachedFile;
import static java.util.Optional.ofNullable;
import static java.util.concurrent.CompletableFuture.supplyAsync;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.example.facebook.aws.S3Client;
import com.example.facebook.common.ApiResult;
import com.example.facebook.common.AttachedFile;
import com.example.facebook.common.Id;
import com.example.facebook.exception.NotFoundException;
import com.example.facebook.security.Jwt;
import com.example.facebook.security.JwtAuthentication;
import com.example.facebook.user.connection.ConnectedUserDto;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final Jwt jwt;

    private final UserService userService;

    private final S3Client s3Client;

    public UserController(Jwt jwt,UserService userService,S3Client s3Client) {
        this.jwt = jwt;
        this.userService = userService;
        this.s3Client = s3Client;
    }

    public Optional<String> uploadProfileImage(AttachedFile profileFile){
        String profileImageUrl = null;
        if (profileFile != null) {
            String key = profileFile.randomName("profiles", "jpeg");
            try {
                profileImageUrl = s3Client.upload(profileFile.inputStream(), profileFile.length(), key, profileFile.getContentType(), null);
            } catch (AmazonS3Exception e) {
                log.warn("Amazon S3 error (key: {}): {}", key, e.getMessage(), e);
            }
        }
        return ofNullable(profileImageUrl);
    }


    @GetMapping("user/exists")
    public ApiResult<Boolean> checkEmail(@RequestBody Map<String,String> request){
        Email email = new Email(request.get("address"));
        return OK(userService.findByEmail(email).isPresent());
    }


    @PostMapping(path = "user/join",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResult<JoinResult> join(@ModelAttribute JoinRequest joinRequest, @RequestPart(required = false)
        MultipartFile file){
        User user = userService.join(joinRequest.getName(), new Email(joinRequest.getPrincipal()),
            joinRequest.getCredentials());

        toAttachedFile(file).ifPresent(attachedFile ->
            supplyAsync(()->
                uploadProfileImage(attachedFile)).thenAccept(opt->
                opt.ifPresent(profileImageUrl->
                    userService.updateProfileImage(Id.of(User.class,user.getSeq()),profileImageUrl))));

        String apiToken = user.newApiToken(jwt, new String[]{Role.USER.value()});
        return OK(
            new JoinResult(apiToken, new UserDto(user))
        );

    }

    @GetMapping("user/me")
    public ApiResult<UserDto> me(@AuthenticationPrincipal JwtAuthentication authentication) {
        return OK(
            userService.findById(authentication.id)
                .map(UserDto::new)
                .orElseThrow(() -> new NotFoundException(User.class, authentication.id))
        );
    }

    @GetMapping("user/connections")
    public ApiResult<List<ConnectedUserDto>> connections(@AuthenticationPrincipal JwtAuthentication authentication){
        return OK(userService.findAllConnectedUser(authentication.id));
    }


}
