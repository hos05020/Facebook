package com.example.facebookapiserver.domain.post;

import static com.google.common.base.Preconditions.checkArgument;
import static java.time.LocalDateTime.now;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import com.example.facebookapiserver.domain.user.User;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;


    @ManyToOne
    @JoinColumn(name = "user_seq")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_seq")
    private Post post;

    @Column(name = "contents")
    private String contents;

    @Transient
    private Writer writer;

    @Column(name = "create_at")
    private LocalDateTime createAt;


    protected Comment(){}

    public Comment(User user,Post post,String contents){
        this(null,user,post,contents,null);
    }


    public Comment(Long seq, User user, Post post, String contents, LocalDateTime createAt) {
        checkArgument(user != null, "user must be provided.");
        checkArgument(post != null, "post must be provided.");
        checkArgument(isNotEmpty(contents), "contents must be provided.");
        checkArgument(
            contents.length() >= 4 && contents.length() <= 500,
            "comment contents length must be between 4 and 500 characters."
        );
        this.seq = seq;
        this.user = user;
        this.post = post;
        this.contents = contents;
        this.writer = new Writer(user.getEmail(),user.getName());
        this.createAt = defaultIfNull(createAt, now());
    }

    public Long getSeq() {
        return seq;
    }

    public User getUser() {
        return user;
    }

    public Post getPost() {
        return post;
    }

    public String getContents() {
        return contents;
    }

    public Optional<Writer> getWriter() {
        return Optional.ofNullable(writer);
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(seq, comment.seq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seq);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("seq", seq)
            .append("userId", user.getSeq())
            .append("postId", post.getSeq())
            .append("contents", contents)
            .append("writer", writer)
            .append("createAt", createAt)
            .toString();
    }
}
