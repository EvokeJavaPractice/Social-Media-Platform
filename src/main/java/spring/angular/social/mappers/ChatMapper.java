package spring.angular.social.mappers;

import org.mapstruct.Mapper;
import spring.angular.social.dto.ChatDto;
import spring.angular.social.entity.Chat;

@Mapper(componentModel = "spring")
public interface ChatMapper {
    Chat toEntity(ChatDto chatDto);
    ChatDto toDto(Chat chat);
}
