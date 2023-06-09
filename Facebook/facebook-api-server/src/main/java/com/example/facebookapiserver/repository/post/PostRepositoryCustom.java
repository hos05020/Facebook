package com.example.facebookapiserver.repository.post;

import com.example.facebookapiserver.domain.common.Id;
import com.example.facebookapiserver.controller.post.PostDto;
import com.example.facebookapiserver.domain.post.Post;
import com.example.facebookapiserver.domain.user.User;
import java.util.List;
import java.util.Optional;

public interface PostRepositoryCustom {
    Optional<Post> findById(Id<Post, Long> postId, Id<User, Long> writerId, Id<User, Long> userId);
    List<PostDto> findAll(Id<User,Long> writerId,Id<User,Long> userId,long offset,int limit);

    Optional<User> findWriter(Id<Post,Long> postId);

    void update(Post post);

}
