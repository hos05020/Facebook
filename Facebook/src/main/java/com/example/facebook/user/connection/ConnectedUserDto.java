package com.example.facebook.user.connection;

import static java.util.Optional.ofNullable;


import com.example.facebook.user.Email;
import com.example.facebook.user.User;
import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ConnectedUserDto {

  private Long seq;

  private String name;

  private Email email;

  private String profileImageUrl;

  private LocalDateTime grantedAt;


  @QueryProjection
  public ConnectedUserDto(Connections connections,User user){
    this.seq = user.getSeq();
    this.name = user.getName();
    this.email = user.getEmail();
    this.profileImageUrl = user.getProfileImageUrl().orElse(null);
    this.grantedAt = connections.getGrantedAt();
  }

  public Long getSeq() {
    return seq;
  }

  public String getName() {
    return name;
  }

  public Email getEmail() {
    return email;
  }


  public Optional<String> getProfileImageUrl() {
    return ofNullable(profileImageUrl);
  }


  public LocalDateTime getGrantedAt() {
    return grantedAt;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ConnectedUserDto that = (ConnectedUserDto) o;
    return Objects.equals(getSeq(), that.getSeq());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getSeq());
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
      .append("seq", seq)
      .append("name", name)
      .append("email", email)
      .append("profileImageUrl",profileImageUrl)
      .append("grantedAt", grantedAt)
      .toString();
  }

}