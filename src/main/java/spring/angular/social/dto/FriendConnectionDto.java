package spring.angular.social.dto;

import lombok.Getter;
import lombok.Setter;
import spring.angular.social.entity.Notification;
import spring.angular.social.entity.User;
@Setter
@Getter
public class FriendConnectionDto {

    private Long id;

    private User user;

    private User friend;

    private boolean isFriends;

    private Notification notification;
}
