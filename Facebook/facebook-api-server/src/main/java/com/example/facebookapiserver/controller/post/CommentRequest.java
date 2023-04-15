package com.example.facebookapiserver.controller.post;

import com.example.facebookapiserver.domain.post.Post;
import com.example.facebookapiserver.domain.post.Comment;
import com.example.facebookapiserver.domain.user.User;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class CommentRequest {


    private String contents;

    public String getContents() {
        return contents;
    }

    public Comment newComment(User user, Post post) {
        return new Comment(user, post,contents);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("contents", contents)
            .toString();
    }

}
