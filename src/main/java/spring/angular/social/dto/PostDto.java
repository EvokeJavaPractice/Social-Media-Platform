package spring.angular.social.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.angular.social.entity.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

	private long id;
	private String content;
	private User user;
}
