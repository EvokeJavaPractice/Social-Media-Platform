package spring.angular.social.dto;

import lombok.Getter;
import lombok.Setter;
import spring.angular.social.entity.Notification;
import spring.angular.social.entity.Post;
import spring.angular.social.entity.User;


@Setter
@Getter
public class CommentDto {

    private Long id;

    private User user;

    private Post post;

    private String content;

    private Notification notification;

}
