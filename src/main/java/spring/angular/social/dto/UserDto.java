package spring.angular.social.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Setter
@Getter
public class UserDto {

    private Long id;

    private String emailId;

    private String username;

    private String password;
}
