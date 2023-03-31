package com.example.facebook.message;

import static java.time.LocalDateTime.now;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

import java.time.LocalDateTime;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class PushSubscribedMessage {

    private Long seq;

    private String notificationEndPoint;

    private String publicKey;

    private String auth;

    private Long userId;

    private LocalDateTime createAt;

    protected PushSubscribedMessage(){}

    public PushSubscribedMessage(Long seq, String notificationEndPoint,String publicKey, String auth,Long userId){
        this.seq = seq;
        this.notificationEndPoint = notificationEndPoint;
        this.publicKey = publicKey;
        this.auth = auth;
        this.userId = userId;
        this.createAt =defaultIfNull(createAt, now());
    }


    public Long getSeq() {
        return seq;
    }

    public String getNotificationEndPoint() {
        return notificationEndPoint;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getAuth() {
        return auth;
    }

    public Long getUserId() {
        return userId;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("seq", seq)
            .append("notificationEndPoint", notificationEndPoint)
            .append("publicKey", publicKey)
            .append("password", "[PROTECTED]")
            .append("auth", auth)
            .append("userId", userId)
            .append("createAt", createAt)
            .toString();
    }


    public static final class PushSubscribedMessageBuilder{
        private Long seq;

        private String notificationEndPoint;

        private String publicKey;

        private String auth;

        private Long userId;

        private LocalDateTime createAt;

        public PushSubscribedMessageBuilder() {
        }

        public PushSubscribedMessageBuilder(PushSubscribedMessage subscription){
            this.seq = subscription.seq;
            this.notificationEndPoint = subscription.notificationEndPoint;
            this.publicKey = subscription.publicKey;
            this.auth = subscription.auth;
            this.userId = subscription.userId;
            this.createAt = subscription.createAt;

        }

        public PushSubscribedMessageBuilder seq(Long seq){
            this.seq = seq;
            return this;
        }

        public PushSubscribedMessageBuilder notificationEndPoint(String notificationEndPoint){
            this.notificationEndPoint = notificationEndPoint;
            return this;
        }

        public PushSubscribedMessageBuilder publicKey(String publicKey){
            this.publicKey = publicKey;
            return this;
        }

        public PushSubscribedMessageBuilder auth(String auth){
            this.auth = auth;
            return this;
        }

        public PushSubscribedMessageBuilder userId(Long userId){
            this.userId = userId;
            return this;
        }

        public PushSubscribedMessageBuilder createAt(LocalDateTime createAt){
            this.createAt = createAt;
            return this;
        }


        public PushSubscribedMessage build(){
           PushSubscribedMessage subscription =new PushSubscribedMessage(seq,notificationEndPoint,publicKey,auth,userId);
            subscription.createAt = this.createAt;
           return subscription;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("seq", seq)
                .append("notificationEndPoint", notificationEndPoint)
                .append("publicKey", publicKey)
                .append("password", "[PROTECTED]")
                .append("auth", auth)
                .append("userId", userId)
                .append("createAt", createAt)
                .toString();
        }







    }
}
