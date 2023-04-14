package com.example.facebookapiserver.user;

import com.example.facebook.event.JoinEvent;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class UserJoinedMessage {

    private Long userId;
    private String name;

    public UserJoinedMessage(){}

    public UserJoinedMessage(Long userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public UserJoinedMessage(JoinEvent event) {
        this.userId = event.getUserId().value();
        this.name = event.getName();
    }

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("userId", userId)
            .append("name", name)
            .toString();
    }
}
