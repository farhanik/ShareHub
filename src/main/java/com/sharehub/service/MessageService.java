package com.sharehub.service;

import com.sharehub.model.Message;
import java.util.List;

public interface MessageService {
    Message sendMessage(Message message);
    List<Message> getConversation(Long senderId, Long receiverId);
    List<Message> getUnreadMessages(Long userId);
    void markMessageAsRead(Long messageId);
    void deleteMessage(Long messageId);
}