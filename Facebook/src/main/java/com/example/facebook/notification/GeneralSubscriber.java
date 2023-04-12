package com.example.facebook.notification;

import com.example.facebook.common.Id;
import com.example.facebook.post.comment.CommentCreatedMessage;
import com.example.facebook.user.User;
import com.example.facebook.user.UserDto;
import com.example.facebook.user.UserJoinedMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
public class GeneralSubscriber {

  private final NotificationService notificationService;

  GeneralSubscriber(NotificationService notificationService) {
    this.notificationService = notificationService;
  }

  @KafkaListener(topics = "${spring.kafka.topic.subscription-request}", containerFactory = "kafkaListenerContainerSubscriptionFactory")
  @SendTo
  public PushSubscribedMessage handleSubscriptionRequestTopic(PushSubscribedMessage pushSubscribedMessage) {
    Subscription subscribe = notificationService.subscribe(Subscription.of(pushSubscribedMessage));
    return new PushSubscribedMessage(
      subscribe.getSeq(), subscribe.getNotificationEndPoint(), subscribe.getPublicKey(), subscribe.getAuth(), subscribe.getUserId().value());
  }

  @KafkaListener(topics = "${spring.kafka.topic.comment-created}",
    containerFactory = "kafkaListenerContainerPushMessageFactory")
  public void onCommentCreated(CommentCreatedMessage commentCreatedMessage) throws Exception {
    UserDto postWriter = commentCreatedMessage.getPostWriter();
    // 자기 자신에게는 안보낸다.
    if (postWriter.getSeq().equals(commentCreatedMessage.getUserId())) {
      return;
    }

    PushMessage pushMessage = new PushMessage(
      commentCreatedMessage.getCommentWriter().getName().orElse("아무개") + "이 댓글을 달았어요.",
      "/",
      "한번 확인해 보세요."
    );

    Long targetUserSeq = postWriter.getSeq();
    notificationService.notifyUser(Id.of(User.class, targetUserSeq), pushMessage);
  }

  @KafkaListener(topics = "${spring.kafka.topic.user-joined}",
    containerFactory = "kafkaListenerContainerPushMessageFactory")
  public void onUserJoined(UserJoinedMessage userJoinedMessage) throws Exception {
    String name = userJoinedMessage.getName();
    Long userId = userJoinedMessage.getUserId();

    PushMessage message = new PushMessage(
      name + " Joined!",
      "/friends/" + userId,
      "Please send welcome message"
    );

    notificationService.notifyAll(message);
  }

}
