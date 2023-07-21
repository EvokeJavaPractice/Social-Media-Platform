package spring.angular.social.mappers;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import spring.angular.social.dto.ChatDto;
import spring.angular.social.dto.ChatMessageDto;
import spring.angular.social.entity.ChatMessage;

import java.util.List;

@Mapper(componentModel = "spring" )
public interface ChatMessageMapper {

    ChatMessage toEntity(ChatMessageDto chatMessageDto);
    @Named("chatMessage")
    ChatMessageDto toDto(ChatMessage chatMessage);
    @IterableMapping(qualifiedByName = "chatMessage")
    List<ChatMessageDto> toDto(List<ChatMessage> chatMessage);
}
