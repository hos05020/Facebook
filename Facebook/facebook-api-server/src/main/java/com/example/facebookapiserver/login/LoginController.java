package com.example.facebookapiserver.login;

import static com.example.facebook.common.ApiResult.*;

import com.example.facebook.common.ApiResult;
import com.example.facebook.exception.UnauthorizedException;
import com.example.facebook.security.AuthenticationRequest;
import com.example.facebook.security.AuthenticationResult;
import com.example.facebook.security.JwtAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class LoginController {

    private final AuthenticationManager authenticationManager;


    public LoginController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    public  ApiResult<AuthenticationResultDto> login(@RequestBody AuthenticationRequest loginRequest){
        try {
            JwtAuthenticationToken authToken = new JwtAuthenticationToken(
                loginRequest.getPrincipal(), loginRequest.getCredentials());

            Authentication authentication = authenticationManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return OK(
                new AuthenticationResultDto((AuthenticationResult) authentication.getDetails())
            );
        }catch (AuthenticationException e){
            throw new UnauthorizedException(e.getMessage());
        }
    }
}
