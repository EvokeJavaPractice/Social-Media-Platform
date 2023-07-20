package spring.angular.social.mappers;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import spring.angular.social.dto.PostLikeDto;
import spring.angular.social.entity.PostLike;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostLIkeMapper {

    PostLike toEntity(PostLikeDto postLikeDto);
    @Named("postLike")
    PostLikeDto toDto(PostLike postLike);
    @IterableMapping(qualifiedByName ="postLike")
    List<PostLikeDto> toDto(List<PostLike> postLike);
}
