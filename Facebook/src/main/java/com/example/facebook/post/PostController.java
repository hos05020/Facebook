package com.example.facebook.post;

import static com.example.facebook.common.ApiResult.OK;
import static java.util.stream.Collectors.toList;

import com.example.facebook.common.ApiResult;
import com.example.facebook.common.Id;
import com.example.facebook.exception.NotFoundException;
import com.example.facebook.page.Pageable;
import com.example.facebook.post.comment.CommentDto;
import com.example.facebook.post.comment.CommentRequest;
import com.example.facebook.post.comment.CommentService;
import com.example.facebook.security.JwtAuthentication;
import com.example.facebook.user.User;
import java.util.List;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class PostController {

    private final PostService postService;

    private final CommentService commentService;

    public PostController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @PostMapping(path = "post")
    public ApiResult<PostDto> posting(
        @AuthenticationPrincipal JwtAuthentication authentication,
        @RequestBody PostingRequest request
    ) {
        return OK(
            new PostDto(
                postService.write(authentication.id, request))
        );
    }

    @GetMapping("user/{userId}/post/{postId}")
    public ApiResult<PostDto> getOne(@PathVariable Long userId, @PathVariable Long postId,
        @AuthenticationPrincipal JwtAuthentication authentication) {
        return OK(postService.findById(Id.of(Post.class, postId), Id.of(User.class, userId),
            authentication.id).map(
            PostDto::new).orElseThrow(() -> new NotFoundException(Post.class, postId)));
    }

    @GetMapping(path = "user/{userId}/post/list")
    public ApiResult<List<PostDto>> posts(
        @AuthenticationPrincipal JwtAuthentication authentication,
        @PathVariable Long userId, Pageable pageable
    ) {
        return OK(
            postService.findAll(Id.of(User.class, userId), authentication.id, pageable.offset(),
                pageable.limit())
        );
    }

    @PatchMapping(path = "user/{userId}/post/{postId}/like")
    public ApiResult<PostDto> like(
        @AuthenticationPrincipal JwtAuthentication authentication, @PathVariable Long userId,
        @PathVariable Long postId
    ) {
        return OK(
            postService.like(Id.of(Post.class, postId), Id.of(User.class, userId),
                    authentication.id)
                .map(PostDto::new)
                .orElseThrow(() -> new NotFoundException(Post.class, Id.of(Post.class, postId),
                    Id.of(User.class, userId)))
        );
    }


    @PostMapping(path = "user/{userId}/post/{postId}/comment")
    public ApiResult<CommentDto> comment(
        @AuthenticationPrincipal JwtAuthentication authentication, @PathVariable Long userId,
        @PathVariable Long postId, @RequestBody CommentRequest request) {
        return OK(
            new CommentDto(
                commentService.write(
                    Id.of(Post.class, postId),
                    Id.of(User.class, userId),
                    authentication.id,
                    request)
            )
        );
    }

    @GetMapping(path = "user/{userId}/post/{postId}/comment/list")
    public ApiResult<List<CommentDto>> comments(
        @AuthenticationPrincipal JwtAuthentication authentication, @PathVariable Long userId,
        @PathVariable Long postId) {
        return OK(
            commentService.findAll(Id.of(Post.class, postId), Id.of(User.class, userId),
                    authentication.id)
        );
    }
}
