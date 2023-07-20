package spring.angular.social.mappers;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.web.bind.annotation.SessionAttributes;
import spring.angular.social.dto.PostDto;
import spring.angular.social.entity.Post;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {
    Post toEntity(PostDto postDto);
    @Named("post")
    PostDto toDto(Post post);
    @IterableMapping(qualifiedByName = "post")
    List<PostDto> toDto(List<Post> post);

}
