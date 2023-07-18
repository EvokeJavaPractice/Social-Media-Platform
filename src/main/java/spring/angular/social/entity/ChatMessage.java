package spring.angular.social.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Table
@Entity
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    private String sender;
    
    private String receiver;
    
    private String messageContent;
    
    private LocalDateTime timestamp;
    
    @OneToOne
    private Notification notification;


}
