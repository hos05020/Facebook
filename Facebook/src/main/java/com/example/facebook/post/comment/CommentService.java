package com.example.facebook.post.comment;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Collections.emptyList;

import com.example.facebook.common.Id;
import com.example.facebook.exception.NotFoundException;
import com.example.facebook.post.Post;
import com.example.facebook.post.PostRepository;
import com.example.facebook.user.User;
import com.example.facebook.user.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final CommentRepository commentRepository;

    public CommentService(PostRepository postRepository, UserRepository userRepository,
        CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    @Transactional
    public Comment write(Id<Post, Long> postId, Id<User, Long> postWriterId, Id<User, Long> userId, CommentRequest comment) {
        checkArgument(comment != null, "comment must be provided.");
//        checkArgument(comment.getPostId().equals(postId), "comment.postId must equals postId");
//        checkArgument(comment.getUserId().equals(userId), "comment.userId must equals userId");

        return findPost(postId, postWriterId, userId)
            .map(post -> {
                post.incrementAndGetComments();
                postRepository.update(post);
                User user = findUser(userId);
                return commentRepository.save(comment.newComment(user,post));
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
