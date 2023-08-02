package spring.angular.social.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class AllDetails {
    private String userName;
    private String postContent;
    private String postComment;

}
