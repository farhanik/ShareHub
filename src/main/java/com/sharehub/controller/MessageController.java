package com.sharehub.controller;
import java.time.LocalDateTime;
import java.util.Optional;
import com.sharehub.model.Message;
import com.sharehub.model.User;
import com.sharehub.service.MessageService;
import com.sharehub.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = "*")
public class MessageController
{
    private final MessageService messageService;
    private final UserService userService;
    public MessageController(MessageService messageService, UserService userService)
    {
        this.messageService = messageService;
        this.userService = userService;
    }

    @PostMapping("/send")
    public ResponseEntity<ApiResponse<Message>> sendMessage(@RequestBody Message message) {
        System.out.println("Received message: " + message); // Add this line to see the incoming data
        try {
            Long senderId = message.getSenderId();
            Long receiverId = message.getReceiverId();

            System.out.println("Sender ID: " + senderId);
            System.out.println("Receiver ID: " + receiverId);
            // Validate senderId and receiverId
            if (message.getSenderId() == null || message.getReceiverId() == null)
            {
                throw new IllegalArgumentException("Sender ID and Receiver ID are required.");
            }

            // Fetch users and set sender/receiver details
            Optional<User> sender = userService.getUserById(message.getSenderId());
            Optional<User> receiver = userService.getUserById(message.getReceiverId());

            if (sender.isEmpty())
            {
                throw new IllegalArgumentException("Sender not found with ID: " + senderId);
            }

            if (receiver.isEmpty())
            {
                throw new IllegalArgumentException("Receiver not found with ID: " + receiverId);
            }


            message.setSender(sender.get());
            message.setReceiver(receiver.get());

            message.setTimestamp(LocalDateTime.now());
            Message sentMessage = messageService.sendMessage(message);

            return ResponseEntity.ok(new ApiResponse<>(true, "Message sent successfully", sentMessage));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }



    //Retrieves the conversation between two users
    @GetMapping("/conversation")
    public ResponseEntity<ApiResponse<List<Message>>> getConversation(
            @RequestParam Long senderId,
            @RequestParam Long receiverId)
    {
        List<Message> messages = messageService.getConversation(senderId, receiverId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Conversation retrieved", messages));
    }

    @GetMapping("/unread/{userId}")
    public ResponseEntity<ApiResponse<List<Message>>> getUnreadMessages(@PathVariable Long userId)
    {
        List<Message> messages = messageService.getUnreadMessages(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Unread messages retrieved", messages));
    }

    @PutMapping("/{messageId}/read")
    public ResponseEntity<ApiResponse<Void>> markMessageAsRead(@PathVariable Long messageId)
    {
        messageService.markMessageAsRead(messageId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Message marked as read", null));
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<ApiResponse<Void>> deleteMessage(@PathVariable Long messageId)
    {
        messageService.deleteMessage(messageId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Message deleted successfully", null));
    }
}