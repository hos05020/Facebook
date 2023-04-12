package com.example.facebook.notification;

import com.example.facebook.common.Id;
import com.example.facebook.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

  private final Logger log = LoggerFactory.getLogger(getClass());

  private final PushService pushService;

  private final ObjectMapper objectMapper;

  private final SubscriptionRepository subscriptionRepository;

  public NotificationService(
    PushService pushService,
    ObjectMapper objectMapper,
    SubscriptionRepository subscriptionRepository
  ) {
    this.pushService = pushService;
    this.objectMapper = objectMapper;
    this.subscriptionRepository = subscriptionRepository;
  }


  public Subscription subscribe(Subscription subscription) {
    return subscriptionRepository.save(subscription);
  }

  public PushMessage notifyUser(Id<User, Long> userId, PushMessage message) throws Exception {
    Long targetUserSeq = userId.value();

    Optional<Subscription> maybeSubscription = subscriptionRepository.findByUserSeq(targetUserSeq);

    if (!maybeSubscription.isPresent()) {
      log.info("Can not send message to user {} because not found any subscription", targetUserSeq);
      return null;
    }

    Subscription subscription = maybeSubscription.get();
    log.info("TRY TO SEND {} to SUBSCRIPTION {}", message, subscription);

    Notification notification = new Notification(
      subscription.getNotificationEndPoint(),
      subscription.getPublicKey(),
      subscription.getAuth(),
      objectMapper.writeValueAsBytes(message));

    pushService.send(notification);

    return message;
  }

  public PushMessage notifyAll(PushMessage message) throws Exception {
    List<Subscription> subscriptions = subscriptionRepository.findAll();
    for (Subscription subscription : subscriptions) {
      Notification notification = new Notification(
        subscription.getNotificationEndPoint(),
        subscription.getPublicKey(),
        subscription.getAuth(),
        objectMapper.writeValueAsBytes(message));
      pushService.send(notification);
    }

    return message;
  }

}