package com.example.facebook.user.connection;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Optional.ofNullable;

import com.example.facebook.user.Email;
import com.example.facebook.user.User;
import java.time.LocalDateTime;
import java.util.Optional;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ConnectedUser {
    private final Long seq;

    private final String name;

    private final Email email;

    private final String profileImageUrl;

    private final LocalDateTime grantedAt;

    public ConnectedUser(Connections connections){
        this(connections.getTargetSeq().getSeq(),connections.getTargetSeq().getName(),connections.getTargetSeq()
            .getEmail(), connections.getTargetSeq().getProfileImageUrl().orElse(null),connections.getGrantedAt());
    }


    public ConnectedUser(Long seq, String name, Email email, String profileImageUrl, LocalDateTime grantedAt) {
        checkArgument(seq != null, "seq must be provided.");
        checkArgument(name != null, "name must be provided.");
        checkArgument(email != null, "email must be provided.");
        checkArgument(grantedAt != null, "grantedAt must be provided.");

        this.seq = seq;
        this.name = name;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
        this.grantedAt = grantedAt;
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
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("seq", seq)
            .append("name", name)
            .append("email", email)
            .append("profileImageUrl", profileImageUrl)
            .append("grantedAt", grantedAt)
            .toString();
    }
}
