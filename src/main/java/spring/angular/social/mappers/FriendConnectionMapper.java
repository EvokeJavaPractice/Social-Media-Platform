package spring.angular.social.mappers;

import org.mapstruct.Mapper;
import spring.angular.social.dto.FriendConnectionDto;
import spring.angular.social.entity.FriendConnection;

@Mapper(componentModel = "spring")
public interface FriendConnectionMapper {

    FriendConnection toEntity(FriendConnectionDto friendConnectionDto);

    FriendConnectionDto toDto(FriendConnection friendConnection);
}
