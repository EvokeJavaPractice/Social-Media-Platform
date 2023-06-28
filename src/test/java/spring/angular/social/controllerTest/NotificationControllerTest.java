package spring.angular.social.controllerTest;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import spring.angular.social.controller.NotificationController;
import spring.angular.social.entity.Notification;
import spring.angular.social.service.NotificationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class NotificationControllerTest {

    private final NotificationService notificationService = mock(NotificationService.class);
    private final NotificationController notificationController = new NotificationController(notificationService);

    @Test
    public void testCreateNotification() {
    	
    	Notification notification = new Notification();
        when(notificationService.createNotification(notification)).thenReturn(notification);
        
        ResponseEntity<Notification> expectedResult = notificationController.createNotification(notification);
        
        assertEquals(expectedResult.getStatusCode(),HttpStatus.OK);
        assertEquals(expectedResult.getBody(),notification); 
        
    }
    
    @Test
    public void testGetNotificationById_success() {
    	
    	Long notificationId = 1L;
    	
    	Notification notification = new Notification();
    	
    	when(notificationService.getNotificationById(notificationId)).thenReturn(Optional.of(notification));
    	
    	ResponseEntity<Notification> expectedResult = notificationController.getNotificationById(notificationId);
    	
    	assertEquals(expectedResult.getStatusCode(),HttpStatus.OK);
        assertEquals(expectedResult.getBody(),notification); 
    }
    
    @Test
    public void testGetNotificationById_failure() {
    	
    	Long notificationId = 1L;
    	
    	
    	when(notificationService.getNotificationById(notificationId)).thenReturn(Optional.empty());
    	
    	ResponseEntity<Notification> expectedResult = notificationController.getNotificationById(notificationId);
    	
    	assertEquals(expectedResult.getStatusCode(),HttpStatus.NOT_FOUND);
        assertEquals(expectedResult.getBody(),null); 
    }
    
    @Test
    public void testGetNotificationsByUserId() {
    	
    	Long userId = 1L;
    	Notification n1 = new Notification();
    	Notification n2 = new Notification();
    	List<Notification> notifications = new ArrayList<Notification>();
    	
    	notifications.add(n1);
    	notifications.add(n2);
    	
    	when(notificationService.getNotificationsByUserId(userId)).thenReturn(notifications);
    	
    	ResponseEntity<List<Notification>> expectedResult = notificationController.getNotificationsByUserId(userId);
    	
    	assertEquals(expectedResult.getStatusCode(),HttpStatus.OK);
        assertEquals(expectedResult.getBody(),notifications); 
    	
    }
    
    @Test
    public void testDeleteNotification_success() {
    	
    	Long notificationId = 1L;
        boolean deleted = true;
        
        when(notificationService.deleteNotificationById(notificationId)).thenReturn(deleted);
        
        ResponseEntity<Void> expectedResult = notificationController.deleteNotification(notificationId);
        		
        assertEquals(expectedResult.getStatusCode(),HttpStatus.NO_CONTENT);
        assertEquals(expectedResult.getBody(),null); 
    	
    }
    
    @Test
    public void testDeleteNotification_failure() {
    	
    	Long notificationId = 1L;
        boolean deleted = false;
        
        when(notificationService.deleteNotificationById(notificationId)).thenReturn(deleted);
        
        ResponseEntity<Void> expectedResult = notificationController.deleteNotification(notificationId);
        		
        assertEquals(expectedResult.getStatusCode(),HttpStatus.NOT_FOUND);
        
    }
    
    @Test
    public void testMarkNotificationAsRead_success() {
    	
    	Long notificationId=1L;
        Notification notification = new Notification();
        
        when(notificationService.getNotificationById(notificationId)).thenReturn(Optional.of(notification));
        
        when(notificationService.updateNotification(notification)).thenReturn(notification);
        
        ResponseEntity<Notification> expectedResult = 
        		notificationController.markNotificationAsRead(notificationId);
       
        assertEquals(expectedResult.getStatusCode(),HttpStatus.OK);
        assertEquals(expectedResult.getBody(),notification);
        
    }
    
    @Test
    public void testMarkNotificationAsRead_failure() {
    	
    	Long notificationId=1L;
        
        when(notificationService.getNotificationById(notificationId)).thenReturn(Optional.empty());
        
        ResponseEntity<Notification> expectedResult = 
        		notificationController.markNotificationAsRead(notificationId);
     
        assertEquals(expectedResult.getStatusCode(),HttpStatus.NOT_FOUND);
        
        
    }
    

   
}
