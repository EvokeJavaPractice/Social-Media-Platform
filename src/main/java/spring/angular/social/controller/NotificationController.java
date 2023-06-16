package spring.angular.social.controller;

import java.util.List;
import java.util.Optional;

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

import spring.angular.social.entity.Notification;
import spring.angular.social.service.NotificationService;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    public ResponseEntity<Notification> createNotification(@RequestBody Notification notification) {
        Notification createdNotification = notificationService.createNotification(notification);
        return ResponseEntity.ok(createdNotification);
    }

    @GetMapping("/{notificationId}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Long notificationId) {
        Optional<Notification> optionalNotification = notificationService.getNotificationById(notificationId);

        if (optionalNotification.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Notification notification = optionalNotification.get();
        return ResponseEntity.ok(notification);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> getNotificationsByUserId(@PathVariable Long userId) {
        List<Notification> notifications = notificationService.getNotificationsByUserId(userId);
        return ResponseEntity.ok(notifications);
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
    public ResponseEntity<Notification> markNotificationAsRead(@PathVariable Long notificationId) {
        Optional<Notification> optionalNotification = notificationService.getNotificationById(notificationId);

        if (optionalNotification.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Notification notification = optionalNotification.get();
        notification.setRead(true);
        Notification updatedNotification = notificationService.updateNotification(notification);
        return ResponseEntity.ok(updatedNotification);
    }
}
