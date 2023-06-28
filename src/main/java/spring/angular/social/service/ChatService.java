package spring.angular.social.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.angular.social.entity.Chat;
import spring.angular.social.entity.ChatMessage;
import spring.angular.social.entity.Notification;
import spring.angular.social.exception.ChatNotFoundException;
import spring.angular.social.exception.MessageNotFoundException;
import spring.angular.social.repository.ChatMessageRepository;
import spring.angular.social.repository.ChatRepository;

@Service
public class ChatService {
    private final ChatRepository chatRepository;
    private final ChatMessageRepository chatMessageRepository;
    
    @Autowired
    private NotificationService notificationService;

    public ChatService(ChatRepository chatRepository, ChatMessageRepository chatMessageRepository) {
        this.chatRepository = chatRepository;
        this.chatMessageRepository = chatMessageRepository;
    }

    public ChatService(ChatRepository chatRepository, ChatMessageRepository chatMessageRepository,
			NotificationService notificationService) {
		this.chatRepository = chatRepository;
		this.chatMessageRepository = chatMessageRepository;
		this.notificationService = notificationService;
				
	}

	public Chat createChat(Chat chat) {
        Chat cht= chatRepository.save(chat);
        Notification notification = new Notification();
        notification.setUser(chat.getUser());
        notification.setMessage("You received a new Chat request");
        notification.setCreatedAt(LocalDateTime.now());

        chat.setNotification(notification);

        notificationService.createNotification(notification);
        return cht;
    }

    public List<ChatMessage> getChatMessages(Long chatId) {
        return chatMessageRepository.findByChatIdOrderByTimestamp(chatId);
    }

    public ChatMessage sendChatMessage(Long chatId, ChatMessage message) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ChatNotFoundException("Chat not found with id: " + chatId));

        message.setChat(chat);
        message.setTimestamp(LocalDateTime.now());
        
        Notification notification = new Notification();
        notification.setUser(message.getChat().getUser());
        notification.setMessage("You received a new Message");
        notification.setCreatedAt(LocalDateTime.now());

        message.setNotification(notification);

        notificationService.createNotification(notification);

        return chatMessageRepository.save(message);
    }

	public void deleteChatMessage(Long chatId, Long messageId) {
		 ChatMessage chatMessage = chatMessageRepository.findById(messageId)
	                .orElseThrow(() -> new ChatNotFoundException("Chat message not found"));

	        if (!chatMessage.getChat().getId().equals(chatId)) {
	            throw new MessageNotFoundException("Chat message does not belong to the specified chat");
	        }

	        chatMessageRepository.delete(chatMessage);
		
	}
}

