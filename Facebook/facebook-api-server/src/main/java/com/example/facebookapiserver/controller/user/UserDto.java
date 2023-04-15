package com.example.facebookapiserver.controller.user;

import static org.springframework.beans.BeanUtils.copyProperties;

import com.example.facebookapiserver.domain.user.Email;
import com.example.facebookapiserver.domain.user.User;
import java.time.LocalDateTime;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class UserDto {

    private  Long seq;
    private  String name;

    private Email email;

    private String profileImageUrl;

    private int loginCount;

    private LocalDateTime lastLoginAt;

    private  LocalDateTime createAt;


    public UserDto(User user){
        copyProperties(user,this);

        this.profileImageUrl = user.getProfileImageUrl().orElse(null);
        this.lastLoginAt = user.getLastLoginAt().orElse(null);
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

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public int getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(int loginCount) {
        this.loginCount = loginCount;
    }

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(LocalDateTime lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("seq", seq)
            .append("name", name)
            .append("email", email)
            .append("profileImageUrl", profileImageUrl)
            .append("loginCount", loginCount)
            .append("lastLoginAt", lastLoginAt)
            .append("createAt", createAt)
            .toString();
    }
}
