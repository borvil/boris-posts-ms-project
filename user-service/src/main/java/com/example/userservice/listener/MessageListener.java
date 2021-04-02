package com.example.userservice.listener;

import com.example.userservice.entities.Comment;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageListener {
    @KafkaListener(topics = "comment_topic", groupId = "comment_group",containerFactory = "commentListener")
    void listener(List<Comment> data) {
        data.forEach(o-> System.out.println(o.getId()+":"+o.getPostId()+":"+o.getUserId()+":"+o.getComment()));
        //System.out.println(data);
    }
}