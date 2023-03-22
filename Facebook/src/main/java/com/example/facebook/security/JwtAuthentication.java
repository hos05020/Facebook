package com.example.facebook.security;

import static com.google.common.base.Preconditions.checkArgument;

import com.example.facebook.common.Id;
import com.example.facebook.user.Email;
import com.example.facebook.user.User;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class JwtAuthentication {

    public final Id<User,Long> id;

    public final String name;

    public final Email email;

    public JwtAuthentication(Long id, String name, Email email) {
        checkArgument(id != null, "id must be provided.");
        checkArgument(name != null, "name must be provided.");
        checkArgument(email != null, "email must be provided.");
        this.id = Id.of(User.class,id);
        this.name = name;
        this.email = email;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("id", id)
            .append("email", email)
            .toString();
    }
}
