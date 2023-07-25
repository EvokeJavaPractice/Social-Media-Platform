package spring.angular.social.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import spring.angular.social.entity.Notification;
import spring.angular.social.entity.Post;
import spring.angular.social.entity.User;
@Getter
@Setter
@ToString
public class PostLikeDto {

    private Long id;

    private User user;

    private Post post;

    private Notification notification;

}
