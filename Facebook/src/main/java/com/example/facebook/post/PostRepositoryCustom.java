package com.example.facebook.post;

import com.example.facebook.common.Id;
import com.example.facebook.user.User;
import java.util.List;
import java.util.Optional;

public interface PostRepositoryCustom {
    Optional<Post> findById(Id<Post, Long> postId, Id<User, Long> writerId, Id<User, Long> userId);
    List<PostDto> findAll(Id<User,Long> writerId,Id<User,Long> userId,long offset,int limit);

}
