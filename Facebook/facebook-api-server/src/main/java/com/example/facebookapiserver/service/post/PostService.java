package com.example.facebookapiserver.service.post;


import static com.google.common.base.Preconditions.checkArgument;

import com.example.facebookapiserver.domain.common.Id;
import com.example.facebookapiserver.controller.post.PostDto;
import com.example.facebookapiserver.controller.post.PostingRequest;
import com.example.facebookapiserver.domain.post.Like;
import com.example.facebookapiserver.domain.post.Post;
import com.example.facebookapiserver.exception.NotFoundException;
import com.example.facebookapiserver.repository.post.LikeRepository;
import com.example.facebookapiserver.domain.user.User;
import com.example.facebookapiserver.repository.post.PostRepository;
import com.example.facebookapiserver.repository.user.UserRepository;
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
    public Post write(Id<User,Long> writerId, PostingRequest postingRequest) {
        User user = findUser(writerId);
        Post post =  new Post(user,postingRequest.getContents());
        return postRepository.save(post);
    }

    @Transactional
    public Optional<Post> like(Id<Post, Long> postId, Id<User, Long> writerId, Id<User, Long> userId) {
        return findById(postId, writerId, userId).map(post -> {
            if (!post.isLikesOfMe()) {
                post.incrementAndGetLikes();
                User user = findUser(userId);
                likeRepository.save(new Like(user,post));
                update(post);
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

    private User findUser(Id<User,Long> userId){
        return userRepository.findById(userId.value()).orElseThrow(()->new NotFoundException(User.class,userId));
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

    @Transactional(readOnly = true)
    public Optional<User> findWriter(Id<Post, Long> postId) {
        checkArgument(postId!=null,"postId must be provided");
        return postRepository.findWriter(postId);
    }

    private long checkOffset(long offset) {
        return offset < 0 ? 0 : offset;
    }

    private int checkLimit(int limit) {
        return (limit < 1 || limit > 5) ? 5 : limit;
    }

    private void update(Post post) {
        postRepository.update(post);
    }


}
