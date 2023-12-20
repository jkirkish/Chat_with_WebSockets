package com.coderscampus.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.coderscampus.chat.ChatMessage;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {

    List<ChatMessage> findByChatId(String s);

}
