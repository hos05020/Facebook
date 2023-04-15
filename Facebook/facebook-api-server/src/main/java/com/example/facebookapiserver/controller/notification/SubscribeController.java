package com.example.facebookapiserver.controller.notification;
import static com.example.facebookapiserver.controller.ApiResult.OK;

import com.example.facebookapiserver.controller.ApiResult;
import com.example.facebookapiserver.message.PushSubscribedMessage;
import com.example.facebookapiserver.message.PushSubscribedMessage.PushSubscribedMessageBuilder;
import com.example.facebookapiserver.repository.notification.Subscription;
import com.example.facebookapiserver.security.JwtAuthentication;
import java.util.concurrent.ExecutionException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class SubscribeController {

  private static final Logger logger = LoggerFactory.getLogger(SubscribeController.class);


  @Value("${spring.kafka.topic.subscription-request}")
  private String requestTopic;

  @Value("${spring.kafka.topic.subscription-reply}")
  private String requestReplyTopic;


  private final ReplyingKafkaTemplate<String, PushSubscribedMessage,PushSubscribedMessage> kafkaTemplate;

  public SubscribeController(ReplyingKafkaTemplate<String,PushSubscribedMessage,PushSubscribedMessage> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  @PostMapping("/subscribe")
  public ApiResult<PushSubscribedMessage> subscribe(
    @AuthenticationPrincipal JwtAuthentication authentication,
    @RequestBody Subscription subscription
  ) throws ExecutionException, InterruptedException {
    PushSubscribedMessageBuilder subscriptionBuilder = new PushSubscribedMessageBuilder();
    subscriptionBuilder.notificationEndPoint(subscription.getNotificationEndPoint());
    subscriptionBuilder.publicKey(subscription.getPublicKey());
    subscriptionBuilder.auth(subscription.getAuth());
    subscriptionBuilder.userId(subscription.getUserId().value());

    //카프카에게 보내는 메세지 타입
    ProducerRecord<String,PushSubscribedMessage> record = new ProducerRecord<>(requestTopic,subscriptionBuilder.build());

    record.headers().add(KafkaHeaders.REPLY_TOPIC,requestReplyTopic.getBytes());//메시지 응답이 올것이라고 알림

    RequestReplyFuture<String, PushSubscribedMessage, PushSubscribedMessage> sendAndReceive = kafkaTemplate.sendAndReceive(
        record);

    ConsumerRecord<String, PushSubscribedMessage> consumerRecord = sendAndReceive.get();//Consumer에게 응답을 받음

    logger.info("success to subscribe {}",consumerRecord.value());

//    Subscription subscribe = notificationService.subscribe(subscriptionBuilder.build());
    //todo 카프카를 이용해 처리(Push 구독)를 보장
//    Subscription subscription = subscriptionBuilder.build();
//    Subscription result =replyingKafkaTemplate.send(subscription);

    return OK(consumerRecord.value());
  }

}