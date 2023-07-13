package spring.angular.social.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.angular.social.entity.Chat;
import spring.angular.social.entity.ChatMessage;
import spring.angular.social.service.ChatService;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping
    public ResponseEntity<Chat> createChat(@RequestBody Chat chat) {
        Chat createdChat = chatService.createChat(chat);
        return ResponseEntity.ok(createdChat);
    }

    @GetMapping("/{chatId}/messages")
    public ResponseEntity<List<ChatMessage>> getChatMessages(@PathVariable Long chatId) {
        List<ChatMessage> messages = chatService.getChatMessages(chatId);
        return ResponseEntity.ok(messages);
    }

    @PostMapping("/{chatId}/messages")
    public ResponseEntity<ChatMessage> sendChatMessage(@PathVariable Long chatId, @RequestBody ChatMessage message) {
        ChatMessage sentMessage = chatService.sendChatMessage(chatId, message);
        return ResponseEntity.ok(sentMessage);
    }

    @DeleteMapping("/{chatId}/messages/{messageId}")
    public ResponseEntity<String> deleteChatMessage(@PathVariable("chatId") Long chatId, @PathVariable("messageId") Long messageId) {
        chatService.deleteChatMessage(chatId, messageId);
        return new ResponseEntity<>("Chat message deleted successfully", HttpStatus.OK);
    }
}

