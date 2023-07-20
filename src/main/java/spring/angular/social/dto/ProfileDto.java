package spring.angular.social.dto;

import lombok.Getter;
import lombok.Setter;
import spring.angular.social.entity.User;

@Setter
@Getter
public class ProfileDto {

    private Long id;

    private User user;

    private String fullName;

    private String bio;

    private String profileImage;
}
