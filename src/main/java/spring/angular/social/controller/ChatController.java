package spring.angular.social.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.angular.social.entity.Chat;
import spring.angular.social.entity.ChatMessage;
import spring.angular.social.service.ChatService;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

    @Autowired
    private  ChatService chatService;

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
    public ResponseEntity<ChatMessage> sendChatMessage(
            @PathVariable Long chatId,
            @RequestBody ChatMessage message
    ) {
        ChatMessage sentMessage = chatService.sendChatMessage(chatId, message);
        return ResponseEntity.ok(sentMessage);
    }
    
    @DeleteMapping("/{chatId}/messages/{messageId}")
    public ResponseEntity<String> deleteChatMessage(@PathVariable("chatId") Long chatId, @PathVariable("messageId") Long messageId) {
    	chatService.deleteChatMessage(chatId, messageId);
        return new ResponseEntity<>("Chat message deleted successfully", HttpStatus.OK);
    }
}

