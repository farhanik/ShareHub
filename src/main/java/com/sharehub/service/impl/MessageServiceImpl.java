package com.sharehub.service.impl;

import com.sharehub.model.Message;
import com.sharehub.service.MessageService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService
{
    private Map<Long, Message> messageDatabase = new HashMap<>();
    private Long currentId = 1L;

    @Override
    public Message sendMessage(Message message)
    {
        message.setId(currentId++);
        messageDatabase.put(message.getId(), message);
        return message;
    }

    @Override
    //Retrieves a conversation (list of messages) between two users.
    public List<Message> getConversation(Long senderId, Long receiverId)
    {
        return messageDatabase.values().stream()
                .filter(message ->
                        (message.getSender().getId().equals(senderId) &&
                                message.getReceiver().getId().equals(receiverId)) ||
                                (message.getSender().getId().equals(receiverId) &&
                                        message.getReceiver().getId().equals(senderId)))
                .sorted(Comparator.comparing(Message::getTimestamp))
                .collect(Collectors.toList());
    }

    @Override
    //Retrieves unread messages for a specific user.
    public List<Message> getUnreadMessages(Long userId)
    {
        return messageDatabase.values().stream()
                .filter(message -> message.getReceiver().getId().equals(userId))
                .filter(message -> !message.isRead())
                .collect(Collectors.toList());
    }

    @Override
    public void markMessageAsRead(Long messageId)
    {
        Message message = messageDatabase.get(messageId);
        if (message != null)
        {
            message.setRead(true);
        }
    }

    @Override
    public void deleteMessage(Long messageId) {
        messageDatabase.remove(messageId);
    }
}