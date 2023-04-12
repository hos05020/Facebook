package com.example.facebook.notification;

import static com.example.facebook.utils.DateTimeUtils.dateTimeOf;
import static com.example.facebook.utils.DateTimeUtils.timestampOf;

import com.example.facebook.common.Id;
import com.example.facebook.user.User;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcSubscriptionRepository implements SubscriptionRepository {

  private final JdbcTemplate jdbcTemplate;

  public JdbcSubscriptionRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Subscription findById(Long seq) {
    List<Subscription> results = jdbcTemplate.query(
      "SELECT * FROM subscriptions WHERE seq = ?",
      mapper,
      seq
    );
    return results.isEmpty() ? null : results.get(0);
  }

  @Override
  public Subscription save(Subscription subscription) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(conn -> {
      PreparedStatement ps = conn.prepareStatement(
        "MERGE INTO subscriptions(seq, user_seq, endpoint, public_key, auth, create_at) KEY (user_seq) " +
          "VALUES (null,?,?,?,?,?)",
        new String[]{"seq"});
      ps.setLong(1, subscription.getUserId().value());
      ps.setString(2, subscription.getNotificationEndPoint());
      ps.setString(3, subscription.getPublicKey());
      ps.setString(4, subscription.getAuth());
      ps.setTimestamp(5, timestampOf(subscription.getCreateAt()));
      return ps;
    }, keyHolder);

    Number key = keyHolder.getKey();
    long generatedSeq = key != null ? key.longValue() : -1;
    return new Subscription.SubscriptionBuilder(subscription)
      .seq(generatedSeq)
      .build();
  }

  @Override
  public Optional<Subscription> findByUserSeq(Long userSeq) {
    List<Subscription> results = jdbcTemplate.query(
      "SELECT * FROM subscriptions WHERE user_seq = ?",
      mapper,
      userSeq
    );
    return Optional.ofNullable(results.get(0));
  }

  @Override
  public List<Subscription> findAll() {
    return jdbcTemplate.query("SELECT * FROM subscriptions", mapper);
  }

  static RowMapper<Subscription> mapper = (rs, rowNum) -> new Subscription.SubscriptionBuilder()
    .seq(rs.getLong("seq"))
    .userId(Id.of(User.class, rs.getLong("user_seq")))
    .notificationEndPoint(rs.getString("endpoint"))
    .auth(rs.getString("auth"))
    .publicKey(rs.getString("public_key"))
    .createAt(dateTimeOf(rs.getTimestamp("create_at")))
    .build();

}