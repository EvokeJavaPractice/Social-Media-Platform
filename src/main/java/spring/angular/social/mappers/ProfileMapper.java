package spring.angular.social.mappers;

import org.mapstruct.Mapper;
import spring.angular.social.dto.ProfileDto;
import spring.angular.social.entity.Profile;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    Profile toEntity(ProfileDto profileDto);
    ProfileDto toDto(Profile profile);
}
