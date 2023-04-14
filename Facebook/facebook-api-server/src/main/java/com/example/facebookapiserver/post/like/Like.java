package com.example.facebookapiserver.post.like;


import static java.time.LocalDateTime.now;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

import com.example.facebook.post.Post;
import com.example.facebook.user.User;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "likes")
public class Like {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @ManyToOne
    @JoinColumn(name = "user_seq")
    private User user;


    @ManyToOne
    @JoinColumn(name = "post_seq")
    private Post post;


    @Column(name = "create_at")
    private LocalDateTime createAt;



    protected Like(){}


    public Like(User user,Post post){
        this(null,user,post,null);
    }

    public Like(Long seq, User user, Post post, LocalDateTime createAt) {
        this.seq = seq;
        this.user = user;
        this.post = post;
        this.createAt = defaultIfNull(createAt, now());
    }
}
