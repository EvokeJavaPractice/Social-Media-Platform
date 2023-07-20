package spring.angular.social.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.angular.social.dto.ChatDto;
import spring.angular.social.dto.ChatMessageDto;
import spring.angular.social.entity.Chat;
import spring.angular.social.entity.ChatMessage;
import spring.angular.social.mappers.ChatMapper;
import spring.angular.social.mappers.ChatMessageMapper;
import spring.angular.social.service.ChatService;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private ChatMapper mapper;

    @Autowired
    private ChatMessageMapper messageMapper;

    @PostMapping
    public ResponseEntity<ChatDto> createChat(@RequestBody Chat chat) {
        Chat createdChat = chatService.createChat(chat);
        return ResponseEntity.ok(mapper.toDto(createdChat));
    }

    @GetMapping("/{chatId}/messages")
    public ResponseEntity<List<ChatMessageDto>> getChatMessages(@PathVariable Long chatId) {
        List<ChatMessage> messages = chatService.getChatMessages(chatId);
        return ResponseEntity.ok(messageMapper.toDto(messages));
    }

    @PostMapping("/{chatId}/messages")
    public ResponseEntity<ChatMessageDto> sendChatMessage(@PathVariable Long chatId, @RequestBody ChatMessage message) {
        ChatMessage sentMessage = chatService.sendChatMessage(chatId, message);
        return ResponseEntity.ok(messageMapper.toDto(sentMessage));
    }

    @DeleteMapping("/{chatId}/messages/{messageId}")
    public ResponseEntity<String> deleteChatMessage(@PathVariable("chatId") Long chatId, @PathVariable("messageId") Long messageId) {
        chatService.deleteChatMessage(chatId, messageId);
        return new ResponseEntity<>("Chat message deleted successfully", HttpStatus.OK);
    }
}

