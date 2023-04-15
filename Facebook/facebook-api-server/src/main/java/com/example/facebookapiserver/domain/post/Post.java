package com.example.facebookapiserver.domain.post;

import static com.google.common.base.Preconditions.checkArgument;
import static java.time.LocalDateTime.now;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import com.example.facebookapiserver.domain.user.User;
import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User user;

    @Column(name = "contents")
    private String contents;

    @Column(name = "like_count")
    private int likes;

    @Transient
    private boolean likesOfMe;

    @Column(name = "comment_count")
    private int comments;

    @Transient
    private  Writer writer;
    @Column(name = "create_at")
    private  LocalDateTime createAt;

    protected Post(){}

    public Post(User user,String contents){
        this(null,user,contents,0,false,0,new Writer(user.getEmail(),user.getName()),null);
    }

    @QueryProjection
    public Post(Post post, User user,boolean likesOfMe) {
        this(post.seq,user,post.getContents(),post.likes,likesOfMe,post.getComments(),new Writer(user.getEmail(),user.getName()),post.getCreateAt());
    }

    public Post(Long seq, User user, String contents, int likes,boolean likesOfMe ,int comments,Writer writer,LocalDateTime createAt) {
        checkArgument(user.getSeq() != null,"user must be provided");
        checkArgument(isNotEmpty(contents), "contents must be provided.");
        checkArgument(
            contents.length() >= 4 && contents.length() <= 500,
            "post contents length must be between 4 and 500 characters."
        );
        this.seq = seq;
        this.user = user;
        this.contents = contents;
        this.likes = likes;
        this.likesOfMe = likesOfMe;
        this.comments = comments;
        this.writer = writer;
        this.createAt = defaultIfNull(createAt, now());;
    }

    public int incrementAndGetLikes() {
        likesOfMe = true;
        return ++likes;
    }

    public int incrementAndGetComments() {
        return ++comments;
    }


    public Long getSeq() {
        return seq;
    }

    public User getUser() {
        return user;
    }

    public String getContents() {
        return contents;
    }

    public int getLikes() {
        return likes;
    }

    public boolean isLikesOfMe() {
        return likesOfMe;
    }

    public int getComments() {
        return comments;
    }

    public Optional<Writer> getWriter() {
        return ofNullable(writer);
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(seq, post.seq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seq);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("seq", seq)
            .append("user", user.getSeq())
            .append("contents", contents)
            .append("likes", likes)
            .append("likesOfMe", likesOfMe)
            .append("comments", comments)
            .append("writer", writer)
            .append("createAt", createAt)
            .toString();
    }
}
