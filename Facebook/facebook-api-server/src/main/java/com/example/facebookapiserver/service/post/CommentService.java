package com.example.facebookapiserver.service.post;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Collections.emptyList;

import com.example.facebookapiserver.domain.common.Id;
import com.example.facebookapiserver.controller.post.CommentDto;
import com.example.facebookapiserver.controller.post.CommentRequest;
import com.example.facebookapiserver.domain.post.Comment;
import com.example.facebookapiserver.event.CommentCreatedEvent;
import com.example.facebookapiserver.exception.NotFoundException;
import com.example.facebookapiserver.domain.post.Post;
import com.example.facebookapiserver.repository.post.comment.CommentRepository;
import com.example.facebookapiserver.repository.post.PostRepository;
import com.example.facebookapiserver.domain.user.User;
import com.example.facebookapiserver.repository.user.UserRepository;
import com.google.common.eventbus.EventBus;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final CommentRepository commentRepository;

    private final EventBus eventBus;

    public CommentService(PostRepository postRepository, UserRepository userRepository,
        CommentRepository commentRepository,EventBus eventBus) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.eventBus = eventBus;
    }

    @Transactional
    public Comment write(Id<Post, Long> postId, Id<User, Long> postWriterId, Id<User, Long> userId, CommentRequest comment) {
        checkArgument(comment != null, "comment must be provided.");
        checkArgument(postId != null, "postId must be provided.");
        checkArgument(postWriterId != null, "postId must be provided.");
        checkArgument(userId != null, "userId must be provided.");

        return findPost(postId, postWriterId, userId)
            .map(post -> {
                post.incrementAndGetComments();
                postRepository.update(post);
                User user = findUser(userId);
                Comment inserted = commentRepository.save(comment.newComment(user,post));
                eventBus.post(new CommentCreatedEvent(inserted));
                return inserted;
            })
            .orElseThrow(() -> new NotFoundException(Post.class, postId, userId));
    }

    @Transactional(readOnly = true)
    public List<CommentDto> findAll(Id<Post, Long> postId, Id<User, Long> postWriterId, Id<User, Long> userId) {
        return findPost(postId, postWriterId, userId)
            .map(post -> commentRepository.findAll(postId))
            .orElse(emptyList());
    }


    private Optional<Post> findPost(Id<Post, Long> postId, Id<User, Long> postWriterId, Id<User, Long> userId) {
        checkArgument(postId != null, "postId must be provided.");
        checkArgument(postWriterId != null, "postWriterId must be provided.");
        checkArgument(userId != null, "userId must be provided.");

        return postRepository.findById(postId, postWriterId, userId);
    }

    private User findUser(Id<User,Long> userId){
        return userRepository.findById(userId.value()).orElseThrow(()->new NotFoundException(User.class,userId));
    }

}
