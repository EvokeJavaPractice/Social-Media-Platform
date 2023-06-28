package spring.angular.social.controllerTest;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import spring.angular.social.controller.ChatController;
import spring.angular.social.entity.Chat;
import spring.angular.social.entity.ChatMessage;
import spring.angular.social.service.ChatService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ChatControllerTest {

    private final ChatService chatService = mock(ChatService.class);
    private final ChatController chatController = new ChatController(chatService);

    @Test
    public void testCreateChat() {
    	
        Chat chat = new Chat();
        Chat createdChat = new Chat();
        Mockito.when(chatService.createChat(chat)).thenReturn(createdChat);

        ResponseEntity<Chat> response = chatController.createChat(chat);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(createdChat, response.getBody());
    }

    @Test
    public void testGetChatMessages() {
        
        Long chatId = 1L;
        List<ChatMessage> messages = new ArrayList<>();
        Mockito.when(chatService.getChatMessages(chatId)).thenReturn(messages);

        ResponseEntity<List<ChatMessage>> response = chatController.getChatMessages(chatId);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(messages, response.getBody());
    }

    @Test
    public void testSendChatMessage() {
       
        Long chatId = 1L;
        ChatMessage message = new ChatMessage();
        ChatMessage sentMessage = new ChatMessage();
        Mockito.when(chatService.sendChatMessage(chatId, message)).thenReturn(sentMessage);

        ResponseEntity<ChatMessage> response = chatController.sendChatMessage(chatId, message);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sentMessage, response.getBody());
    }

    @Test
    public void testDeleteChatMessage() {
       
        Long chatId = 1L;
        Long messageId = 1L;

        ResponseEntity<String> response = chatController.deleteChatMessage(chatId, messageId);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Chat message deleted successfully", response.getBody());
        verify(chatService, times(1)).deleteChatMessage(chatId, messageId);
    }
}
