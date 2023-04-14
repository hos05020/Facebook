package com.example.facebookapiserver.user.connection;

import com.example.facebook.user.User;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
@Table(name = "connections")
public class Connections {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User userSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_seq")
    private User targetSeq;

    @Column(name = "granted_at")
    private LocalDateTime grantedAt;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    protected Connections(){}

    public Long getSeq() {
        return seq;
    }

    public User getUserSeq() {
        return userSeq;
    }

    public User getTargetSeq() {
        return targetSeq;
    }

    public LocalDateTime getGrantedAt() {
        return grantedAt;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("seq", seq)
            .append("userSeq", userSeq)
            .append("targetSeq", targetSeq)
            .append("grantedAt", grantedAt)
            .append("createAt", createAt)
            .toString();
    }
}
