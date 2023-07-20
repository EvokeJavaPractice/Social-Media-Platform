package spring.angular.social.serviceTest;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import spring.angular.social.entity.Chat;
import spring.angular.social.entity.ChatMessage;
import spring.angular.social.entity.Notification;
import spring.angular.social.exception.ChatNotFoundException;
import spring.angular.social.exception.MessageNotFoundException;
import spring.angular.social.repository.ChatMessageRepository;
import spring.angular.social.repository.ChatRepository;
import spring.angular.social.service.ChatService;
import spring.angular.social.service.NotificationService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ChatServiceTest {

    @MockBean
    private ChatRepository chatRepository;

    @MockBean
    private ChatMessageRepository chatMessageRepository;

    @MockBean
    private NotificationService notificationService;

    @MockBean
    private ChatService chatService;

    @Test
    public void testCreateChat() {
        Chat chat = new Chat();
        Chat savedChat = new Chat();
        Notification notification = new Notification();
        notification.setUser(chat.getUser());
        notification.setMessage("You received a new Chat request");
        notification.setCreatedAt(LocalDateTime.now());

        savedChat.setNotification(notification);

        when(chatRepository.save(chat)).thenReturn(savedChat);
        doNothing().when(notificationService).createNotification(notification);

        Chat createdChat = chatService.createChat(chat);

        assertNotNull(createdChat);
        assertEquals(savedChat, createdChat);
        assertEquals(notification, createdChat.getNotification());

        verify(chatRepository, times(1)).save(chat);
        verify(notificationService, times(1)).createNotification(notification);
    }

    @Test
    public void testGetChatMessages() {
        Long chatId = 1L;
        List<ChatMessage> messages = new ArrayList<>();
        when(chatMessageRepository.findByChatIdOrderByTimestamp(chatId)).thenReturn(messages);

         List<ChatMessage> retrievedMessages = chatService.getChatMessages(chatId);

        assertNotNull(retrievedMessages);
        assertEquals(messages, retrievedMessages);

        verify(chatMessageRepository, times(1)).findByChatIdOrderByTimestamp(chatId);
    }

    @Test
    public void testSendChatMessage() {
        Long chatId = 1L;
        Chat chat = new Chat();
        chat.setId(chatId);

        ChatMessage message = new ChatMessage();
        message.setChat(chat);

        ChatMessage savedMessage = new ChatMessage();
        Notification notification = new Notification();
        notification.setUser(chat.getUser());
        notification.setMessage("You received a new Message");
        notification.setCreatedAt(LocalDateTime.now());

        savedMessage.setNotification(notification);

        when(chatRepository.findById(chatId)).thenReturn(Optional.of(chat));
        when(chatMessageRepository.save(message)).thenReturn(savedMessage);
        doNothing().when(notificationService).createNotification(notification);

        ChatMessage sentMessage = chatService.sendChatMessage(chatId, message);

        assertNotNull(sentMessage);
        assertEquals(savedMessage, sentMessage);
        assertEquals(notification, sentMessage.getNotification());

        verify(chatRepository, times(1)).findById(chatId);
        verify(chatMessageRepository, times(1)).save(message);
        verify(notificationService, times(1)).createNotification(notification);
    }

    @Test
    public void testDeleteChatMessage() {
        Long chatId = 1L;
        Long messageId = 1L;

        ChatMessage chatMessage = new ChatMessage();
        Chat chat = new Chat();
        chat.setId(chatId);
        chatMessage.setChat(chat);

        when(chatMessageRepository.findById(messageId)).thenReturn(Optional.of(chatMessage));
        doNothing().when(chatMessageRepository).delete(chatMessage);


        assertDoesNotThrow(() -> chatService.deleteChatMessage(chatId, messageId));
        verify(chatMessageRepository, times(1)).findById(messageId);
        verify(chatMessageRepository, times(1)).delete(chatMessage);
    }

    @Test
    public void testDeleteChatMessage_ChatNotFound() {
        Long chatId = 1L;
        Long messageId = 1L;

        when(chatMessageRepository.findById(messageId)).thenReturn(Optional.empty());
        assertThrows(ChatNotFoundException.class, () -> chatService.deleteChatMessage(chatId, messageId));
        verify(chatMessageRepository, times(1)).findById(messageId);
        verify(chatMessageRepository, never()).delete(any());
    }

    @Test
    public void testDeleteChatMessage_MessageNotFound() {
        Long chatId = 1L;
        Long messageId = 1L;

        ChatMessage chatMessage = new ChatMessage();
        Chat chat = new Chat();
        chat.setId(2L);
        chatMessage.setChat(chat);

        when(chatMessageRepository.findById(messageId)).thenReturn(Optional.of(chatMessage));

        assertThrows(MessageNotFoundException.class, () -> chatService.deleteChatMessage(chatId, messageId));
        verify(chatMessageRepository, times(1)).findById(messageId);
        verify(chatMessageRepository, never()).delete(any());
    }
}

