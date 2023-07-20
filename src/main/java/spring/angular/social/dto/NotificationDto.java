package spring.angular.social.dto;

import lombok.Getter;
import lombok.Setter;
import spring.angular.social.entity.User;

import java.time.LocalDateTime;

@Setter
@Getter
public class NotificationDto {

    private Long id;

    private User user;

    private String message;

    private boolean read;

    private LocalDateTime createdAt;
}
