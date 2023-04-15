package com.example.facebookapiserver.repository.post;

import com.example.facebookapiserver.domain.post.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like,Long> {

}
