package com.example.facebook.user;


import static com.google.common.base.Preconditions.checkArgument;

public class JoinResult {

    private final String apiToken;

    private final UserDto user;

    public JoinResult(String apiToken, UserDto user) {
        checkArgument(apiToken!=null,"apiToken must be provided");
        checkArgument(user!=null,"user must be provided");
        this.apiToken = apiToken;
        this.user = user;
    }
}
