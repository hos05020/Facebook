package com.example.facebook.login;


import static org.springframework.beans.BeanUtils.copyProperties;

import com.example.facebook.security.AuthenticationResult;
import com.example.facebook.user.UserDto;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class AuthenticationResultDto {

  private String apiToken;

  private UserDto user;

  public AuthenticationResultDto(AuthenticationResult source) {
    copyProperties(source, this);

    this.user = new UserDto(source.getUser());
  }

  public String getApiToken() {
    return apiToken;
  }

  public void setApiToken(String apiToken) {
    this.apiToken = apiToken;
  }

  public UserDto getUser() {
    return user;
  }

  public void setUser(UserDto user) {
    this.user = user;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
      .append("apiToken", apiToken)
      .append("user", user)
      .toString();
  }

}