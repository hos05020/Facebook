package com.example.facebookapiserver.message;
import com.example.facebookapiserver.event.CommentCreatedEvent;
import com.example.facebookapiserver.domain.post.Writer;
import com.example.facebookapiserver.controller.user.UserDto;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class CommentCreatedMessage {


    private UserDto postWriter;
    private  Long postId;

    private  Long userId;

    private Writer commentWriter;

    private  Long commentId;


    public CommentCreatedMessage() {
    }

    public CommentCreatedMessage(UserDto postWriter, CommentCreatedEvent event) {
        this.postWriter = postWriter;
        this.postId = event.getPostId().value();
        this.userId = event.getUserId().value();
        this.commentWriter = event.getCommentWriter();
        this.commentId = event.getCommentId().value();
    }

    public UserDto getPostWriter() {
        return postWriter;
    }

    public Long getPostId() {
        return postId;
    }

    public Long getUserId() {
        return userId;
    }

    public Writer getCommentWriter() {
        return commentWriter;
    }

    public Long getCommentId() {
        return commentId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("UserDto",postWriter)
            .append("postId", postId)
            .append("userId", userId)
            .append("commentWriter", commentWriter)
            .append("commentId", commentId)
            .toString();
    }
}
