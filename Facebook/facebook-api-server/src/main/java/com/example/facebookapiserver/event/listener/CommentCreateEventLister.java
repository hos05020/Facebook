package com.example.facebookapiserver.event.listener;
import com.example.facebookapiserver.domain.common.Id;
import com.example.facebookapiserver.event.CommentCreatedEvent;
import com.example.facebookapiserver.domain.post.Post;
import com.example.facebookapiserver.service.post.PostService;
import com.example.facebookapiserver.message.CommentCreatedMessage;
import com.example.facebookapiserver.domain.user.User;
import com.example.facebookapiserver.controller.user.UserDto;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

public class CommentCreateEventLister implements AutoCloseable{

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Value("${spring.kafka.topic.comment-created}")
    private String commentCreateTopic;

    private final PostService postService;

    private final KafkaTemplate<String, CommentCreatedMessage> kafkaTemplate;

    private final EventBus eventBus;


    public CommentCreateEventLister(EventBus eventBus,PostService postService,KafkaTemplate<String,CommentCreatedMessage> kafkaTemplate) {
        this.eventBus = eventBus;
        this.postService = postService;
        this.kafkaTemplate = kafkaTemplate;
        eventBus.register(this);
    }

    @Subscribe
    public void handleCommentCreateEvent(CommentCreatedEvent event){

        Id<Post, Long> postId = event.getPostId();
        User postWriter = postService.findWriter(postId)
            .orElseThrow(() -> new RuntimeException("Can't find Writer for " + event.getPostId()));

       CommentCreatedMessage commentCreatedMessage =new CommentCreatedMessage(new UserDto(postWriter),event);
       log.info("Try to send push commentCreatedMessage:{}",commentCreatedMessage);
       this.kafkaTemplate.send(commentCreateTopic,postWriter.getSeq().toString(),commentCreatedMessage);


    }

    @Override
    public void close() throws Exception {
        eventBus.unregister(this);
    }
}
