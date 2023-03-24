package com.example.facebook.post;


import static com.google.common.base.Preconditions.checkArgument;

import com.example.facebook.common.Id;
import com.example.facebook.exception.NotFoundException;
import com.example.facebook.post.like.Like;
import com.example.facebook.post.like.LikeRepository;
import com.example.facebook.user.User;
import com.example.facebook.user.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final LikeRepository likeRepository;


    public PostService(PostRepository postRepository,UserRepository userRepository,LikeRepository likeRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
    }

    @Transactional
    public Post write(Id<User,Long> writerId,PostingRequest postingRequest) {
        return findUser(writerId).map(user -> {
            Post post = new Post(user, postingRequest.getContents());
            return postRepository.save(post);
        }).orElseThrow(()->new NotFoundException(User.class,writerId));
    }

    @Transactional
    public Optional<Post> like(Id<Post, Long> postId, Id<User, Long> writerId, Id<User, Long> userId) {
        return findById(postId, writerId, userId).map(post -> {
            if (!post.isLikesOfMe()) {
                post.incrementAndGetLikes();
                User user = findUser(userId).orElseThrow(() -> new NotFoundException(User.class, userId));
                likeRepository.save(new Like(user,post));
                postRepository.save(post);
            }
            return post;
        });
    }


    @Transactional(readOnly = true)
    public Optional<Post> findById(Id<Post, Long> postId, Id<User, Long> writerId, Id<User, Long> userId) {
        checkArgument(writerId != null, "writerId must be provided.");
        checkArgument(postId != null, "postId must be provided.");
        checkArgument(userId != null, "userId must be provided.");

        return postRepository.findById(postId, writerId, userId);
    }

    private Optional<User> findUser(Id<User,Long> userId){
        return userRepository.findById(userId.value());
    }


    @Transactional(readOnly = true)
    public List<PostDto> findAll(Id<User, Long> writerId, Id<User, Long> userId, long offset, int limit) {
        checkArgument(writerId != null, "writerId must be provided.");
        checkArgument(userId != null, "userId must be provided.");
        return postRepository.findAll(
            writerId,
            userId,
            checkOffset(offset),
            checkLimit(limit)
        );

    }

    private long checkOffset(long offset) {
        return offset < 0 ? 0 : offset;
    }

    private int checkLimit(int limit) {
        return (limit < 1 || limit > 5) ? 5 : limit;
    }
}
