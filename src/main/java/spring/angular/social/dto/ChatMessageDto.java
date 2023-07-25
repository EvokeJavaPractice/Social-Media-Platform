package spring.angular.social.dto;

import lombok.*;
import spring.angular.social.entity.Chat;
import spring.angular.social.entity.Notification;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class ChatMessageDto {
    private Long id;

    private Chat chat;

    private String sender;

    private String receiver;

    private String messageContent;

    private LocalDateTime timestamp;

    private Notification notification;
}
