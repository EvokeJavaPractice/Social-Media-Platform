package spring.angular.social.dto;

import lombok.Getter;
import lombok.Setter;
import spring.angular.social.entity.Notification;
import spring.angular.social.entity.User;

import javax.persistence.*;

@Setter
@Getter
public class ChatDto {

    private Long id;
    private User user;
    private User friend;
    private Notification notification;

}
