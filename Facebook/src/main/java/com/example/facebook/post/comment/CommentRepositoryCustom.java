package com.example.facebook.post.comment;

import com.example.facebook.common.Id;
import com.example.facebook.post.Post;
import java.util.List;

public interface CommentRepositoryCustom {

    List<CommentDto> findAll(Id<Post,Long> postId);

}
