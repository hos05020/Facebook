package com.example.facebookapiserver.repository.post;

import com.example.facebookapiserver.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long>,PostRepositoryCustom {

}
