package com.example.facebookapiserver.event.listener;
import com.example.facebookapiserver.event.UserJoinedEvent;
import com.example.facebookapiserver.message.UserJoinedMessage;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

public class JoinEventListener implements AutoCloseable {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Value("${spring.kafka.topic.user-joined}")
  private String userJoinedTopic;

  private final EventBus eventBus;

  private final KafkaTemplate<String, UserJoinedMessage> kafkaTemplate;

  public JoinEventListener(EventBus eventBus,  KafkaTemplate<String, UserJoinedMessage> kafkaTemplate) {
    this.eventBus = eventBus;
    this.kafkaTemplate = kafkaTemplate;

    eventBus.register(this);
  }

  @Subscribe
  public void handleJoinEvent(UserJoinedEvent event) {
    UserJoinedMessage userJoinedMessage = new UserJoinedMessage(event);
    log.info("try to send push userJoinedmessage:{}",userJoinedMessage);
    this.kafkaTemplate.send(userJoinedTopic,userJoinedMessage);
  }

  @Override
  public void close() throws Exception {
    eventBus.unregister(this);
  }

}