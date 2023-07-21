package spring.angular.social.dto;

import lombok.Getter;
import lombok.Setter;
import spring.angular.social.entity.Chat;
import spring.angular.social.entity.Notification;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Getter
@Setter
public class ChatMessageDto {
    private Long id;

    private Chat chat;

    private String sender;

    private String receiver;

    private String messageContent;

    private LocalDateTime timestamp;

    private Notification notification;
}
