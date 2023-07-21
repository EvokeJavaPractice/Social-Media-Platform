package spring.angular.social.mappers;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import spring.angular.social.dto.CommentDto;
import spring.angular.social.entity.Comment;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment toEntity(CommentDto commentDto);
    @Named("comment")
    CommentDto toDto(Comment comment);
    @IterableMapping(qualifiedByName = "comment")
    List<CommentDto> toDto(List<Comment> comment);

}
