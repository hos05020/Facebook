package com.example.facebookapiserver.security;

import static com.google.common.base.Preconditions.checkArgument;

import com.example.facebook.user.User;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class AuthenticationResult {


    private final String apiToken;

    private final User user;

    public AuthenticationResult(String apiToken, User user) {
        checkArgument(apiToken != null, "apiToken must be provided.");
        checkArgument(user != null, "user must be provided.");

        this.apiToken = apiToken;
        this.user = user;
    }

    public String getApiToken() {
        return apiToken;
    }

    public User getUser() {
        return user;
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("apiToken", apiToken)
            .append("user", user)
            .toString();
    }

}
