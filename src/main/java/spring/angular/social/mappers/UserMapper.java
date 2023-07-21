package spring.angular.social.mappers;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import spring.angular.social.dto.UserDto;
import spring.angular.social.entity.User;

import java.util.List;


@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDto userDto);
    @Named("user")
    UserDto toDto(User user);
    @IterableMapping(qualifiedByName = "user")
    List<UserDto> toDto(List<User> users);

}
