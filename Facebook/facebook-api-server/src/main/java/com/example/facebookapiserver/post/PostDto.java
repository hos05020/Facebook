package com.example.facebookapiserver.post;

import static org.springframework.beans.BeanUtils.copyProperties;

import com.example.facebook.user.User;
import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class PostDto {
    private  Long seq;

    private String contents;

    private int likes;

    private boolean likesOfMe;
    private int comments;

    private  Writer writer;

    private  LocalDateTime createAt;

    @QueryProjection
    public PostDto(Post post,User user,boolean likesOfMe) {
        this.seq = post.getSeq();
        this.contents = post.getContents();
        this.likes = post.getLikes();
        this.likesOfMe = likesOfMe;
        this.comments = post.getComments();
        this.writer = new Writer(user.getEmail(),user.getName());
        this.createAt = post.getCreateAt();
    }

    public PostDto(Post source) {
        copyProperties(source, this);
        this.writer = source.getWriter().orElse(null);
    }

    public Long getSeq() {
        return seq;
    }


    public String getContents() {
        return contents;
    }

    public int getLikes() {
        return likes;
    }

    public int getComments() {
        return comments;
    }

    public Writer getWriter() {
        return writer;
    }

    public boolean isLikesOfMe() {
        return likesOfMe;
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

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setLikesOfMe(boolean likesOfMe) {
        this.likesOfMe = likesOfMe;
    }

    public void setComments(int comments) {
        this.comments = comments;
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
            .append("likes", likes)
            .append("likesOfMe",likesOfMe)
            .append("comments", comments)
            .append("writer", writer)
            .append("createAt", createAt)
            .toString();
    }
}
