package com.example.facebook.user;

import static com.google.common.base.Preconditions.checkArgument;
import static java.time.LocalDateTime.now;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import com.example.facebook.security.Jwt;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column(name = "name")
    private String name;

    @Embedded
    private Email email;

    @Column(name = "passwd")
    private String password;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Column(name = "login_count")
    private int loginCount;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    protected User(){}

    public User(String name, Email email, String password) {
        this(name, email, password, null);
    }

    public User(String name, Email email, String password, String profileImageUrl) {
        this(null, name, email, password, profileImageUrl, 0, null, null);
    }

    public User(Long seq, String name, Email email, String password, String profileImageUrl, int loginCount, LocalDateTime lastLoginAt, LocalDateTime createAt) {
        checkArgument(isNotEmpty(name), "name must be provided.");
        checkArgument(
            name.length() >= 1 && name.length() <= 10,
            "name length must be between 1 and 10 characters."
        );
        checkArgument(email != null, "email must be provided.");
        checkArgument(password != null, "password must be provided.");
        checkArgument(
            profileImageUrl == null || profileImageUrl.length() <= 255,
            "profileImageUrl length must be less than 255 characters."
        );

        this.seq = seq;
        this.name = name;
        this.email = email;
        this.password = password;
        this.profileImageUrl = profileImageUrl;
        this.loginCount = loginCount;
        this.lastLoginAt = lastLoginAt;
        this.createAt = defaultIfNull(createAt, now());
    }

    public void updateProfileImage(String profileImageUrl) {
        checkArgument(
            profileImageUrl == null || profileImageUrl.length() <= 255,
            "profileImageUrl length must be less than 255 characters."
        );

        this.profileImageUrl = profileImageUrl;
    }

    public void login(PasswordEncoder passwordEncoder, String credentials) {
        if (!passwordEncoder.matches(credentials,password))
            throw new IllegalArgumentException("Bad Password");
        loginCount++;
        lastLoginAt = now();
    }

    public String newApiToken(Jwt jwt, String[] roles) {
          Jwt.Claims claims  = Jwt.Claims.of(seq,name,email,roles);
          return jwt.newToken(claims);
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

    public String getPassword() {
        return password;
    }

    public Optional<String> getProfileImageUrl() {
        return ofNullable(profileImageUrl);
    }

    public int getLoginCount() {
        return loginCount;
    }

    public Optional<LocalDateTime> getLastLoginAt() {
        return ofNullable(lastLoginAt);
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(seq, user.seq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seq);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("seq", seq)
            .append("name", name)
            .append("email", email)
            .append("password", "[PROTECTED]")
            .append("profileImageUrl", profileImageUrl)
            .append("loginCount", loginCount)
            .append("lastLoginAt", lastLoginAt)
            .append("createAt", createAt)
            .toString();
    }



}
