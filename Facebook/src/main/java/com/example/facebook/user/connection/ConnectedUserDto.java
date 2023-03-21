package com.example.facebook.user.connection;

import static java.util.Optional.ofNullable;
import static org.springframework.beans.BeanUtils.copyProperties;


import com.example.facebook.user.Email;
import java.time.LocalDateTime;
import java.util.Optional;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ConnectedUserDto {

  private Long seq;

  private String name;

  private Email email;

  private Optional<String> profileImageUrl;

  private LocalDateTime grantedAt;

  public ConnectedUserDto(ConnectedUser source) {
    copyProperties(source, this);
  }

  public Long getSeq() {
    return seq;
  }

  public void setSeq(Long seq) {
    this.seq = seq;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Email getEmail() {
    return email;
  }

  public void setEmail(Email email) {
    this.email = email;
  }

  public Optional<String> getProfileImageUrl() {
    return profileImageUrl;
  }

  public void setProfileImageUrl(Optional<String> profileImageUrl) {
    this.profileImageUrl = profileImageUrl;
  }

  public LocalDateTime getGrantedAt() {
    return grantedAt;
  }

  public void setGrantedAt(LocalDateTime grantedAt) {
    this.grantedAt = grantedAt;
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