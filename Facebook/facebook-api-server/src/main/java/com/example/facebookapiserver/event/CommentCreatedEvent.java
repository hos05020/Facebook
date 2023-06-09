package com.example.facebookapiserver.event;
import com.example.facebookapiserver.domain.common.Id;
import com.example.facebookapiserver.exception.NotFoundException;
import com.example.facebookapiserver.domain.post.Post;
import com.example.facebookapiserver.domain.post.Writer;
import com.example.facebookapiserver.domain.post.Comment;
import com.example.facebookapiserver.domain.user.User;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class CommentCreatedEvent {
    private final Id<Post,Long> postId;

    private final Id<User,Long> userId;

    private final Writer commentWriter;

    private final Id<Comment,Long> commentId;

    public CommentCreatedEvent(Comment comment) {
        this.postId = Id.of(Post.class,comment.getPost().getSeq());
        this.userId = Id.of(User.class,comment.getUser().getSeq());
        this.commentWriter = comment.getWriter().orElseThrow(()-> new NotFoundException("writer should be exist"));
        this.commentId = Id.of(Comment.class,comment.getSeq());
    }


    public Id<Post, Long> getPostId() {
        return postId;
    }

    public Id<User, Long> getUserId() {
        return userId;
    }

    public Writer getCommentWriter() {
        return commentWriter;
    }

    public Id<Comment, Long> getCommentId() {
        return commentId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("postId", postId)
            .append("userId", userId)
            .append("commentWriter", commentWriter)
            .append("password", "[PROTECTED]")
            .append("commentId", commentId)
            .toString();
    }

}
