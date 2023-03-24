package com.example.facebook.post.comment;

import static org.springframework.beans.BeanUtils.copyProperties;

import com.example.facebook.post.Post;
import com.example.facebook.post.Writer;
import com.example.facebook.user.User;
import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class CommentDto {

    private Long seq;

    private String contents;
    private Writer writer;

    private LocalDateTime createAt;

    @QueryProjection
    public CommentDto(Comment comment,User user){
        this.seq = comment.getSeq();
        this.contents = comment.getContents();
        this.writer = new Writer(user.getEmail(),user.getName());
        this.createAt = comment.getCreateAt();
    }

    public CommentDto(Comment source){
        copyProperties(source,this);
        this.writer = source.getWriter().orElse(null);
    }

    public Long getSeq() {
        return seq;
    }

    public String getContents() {
        return contents;
    }


    public Writer getWriter() {
        return writer;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setWriter(Writer writer) {
        this.writer = writer;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("seq", seq)
            .append("contents", contents)
            .append("writer", writer)
            .append("createAt", createAt)
            .toString();
    }
}
