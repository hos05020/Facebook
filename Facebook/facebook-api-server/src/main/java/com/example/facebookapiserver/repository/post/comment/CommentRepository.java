package com.example.facebookapiserver.repository.post.comment;

import com.example.facebookapiserver.domain.post.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long>,CommentRepositoryCustom {

}
