package spring.angular.social.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.angular.social.dto.NotificationDto;
import spring.angular.social.entity.Notification;
import spring.angular.social.mappers.NotificationMapper;
import spring.angular.social.service.NotificationService;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private  NotificationService notificationService;

    @Autowired
    private NotificationMapper mapper;

	@PostMapping
    public ResponseEntity<NotificationDto> createNotification(@RequestBody NotificationDto notificationDto) {
        Notification createdNotification = notificationService.createNotification(mapper.toEntity(notificationDto));
        return ResponseEntity.ok(mapper.toDto(createdNotification));
    }

    @GetMapping("/{notificationId}")
    public ResponseEntity<NotificationDto> getNotificationById(@PathVariable Long notificationId) {
        Optional<Notification> optionalNotification = notificationService.getNotificationById(notificationId);

        if (optionalNotification.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Notification notification = optionalNotification.get();
        return ResponseEntity.ok(mapper.toDto(notification));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationDto>> getNotificationsByUserId(@PathVariable Long userId) {
        List<Notification> notifications = notificationService.getNotificationsByUserId(userId);
        return ResponseEntity.ok(mapper.toDto(notifications));
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long notificationId) {
        boolean deleted = notificationService.deleteNotificationById(notificationId);

        if (deleted) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
    
    @PutMapping("/{notificationId}/read")
    public ResponseEntity<NotificationDto> markNotificationAsRead(@PathVariable Long notificationId) {
        Optional<Notification> optionalNotification = notificationService.getNotificationById(notificationId);

        if (optionalNotification.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Notification notification = optionalNotification.get();
        notification.setRead(true);
        Notification updatedNotification = notificationService.updateNotification(notification);
        return ResponseEntity.ok(mapper.toDto(updatedNotification));
    }
}
