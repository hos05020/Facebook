package com.example.facebookapiserver.controller.post;

import com.example.facebookapiserver.domain.post.Post;
import com.example.facebookapiserver.domain.user.User;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class PostingRequest {

  private String contents;

  protected PostingRequest() {}

  public String getContents() {
    return contents;
  }

  public Post newPost(User writer) {
    return new Post(writer, contents);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
      .append("contents", contents)
      .toString();
  }

}