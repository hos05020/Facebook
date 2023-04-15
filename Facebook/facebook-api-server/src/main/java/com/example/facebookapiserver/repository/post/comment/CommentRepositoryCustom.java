package com.example.facebookapiserver.repository.post.comment;

import com.example.facebookapiserver.controller.post.CommentDto;
import com.example.facebookapiserver.domain.common.Id;
import com.example.facebookapiserver.domain.post.Post;
import java.util.List;

public interface CommentRepositoryCustom {

    List<CommentDto> findAll(Id<Post,Long> postId);

}
