package spring.angular.social.mappers;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import spring.angular.social.dto.NotificationDto;
import spring.angular.social.entity.Notification;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    Notification toEntity(NotificationDto notificationDto);
    @Named("notification")
    NotificationDto toDto(Notification notification);
    @IterableMapping(qualifiedByName = "notification")
    List<NotificationDto> toDto(List<Notification> notification);
}
