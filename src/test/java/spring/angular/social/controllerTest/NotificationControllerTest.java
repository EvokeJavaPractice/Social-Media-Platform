package spring.angular.social.controllerTest;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import spring.angular.social.controller.NotificationController;
import spring.angular.social.dto.NotificationDto;
import spring.angular.social.entity.Notification;
import spring.angular.social.service.NotificationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class NotificationControllerTest {

    private final NotificationService notificationService = mock(NotificationService.class);
    private final NotificationController notificationController = new NotificationController();

    @Test
    public void testCreateNotification() {
    	
    	Notification notification = new Notification();
        when(notificationService.createNotification(notification)).thenReturn(notification);

        NotificationDto notificationDto=new NotificationDto();
        
        ResponseEntity<NotificationDto> expectedResult = notificationController.createNotification(notificationDto);
        
        assertEquals(expectedResult.getStatusCode(),HttpStatus.OK);
        assertEquals(expectedResult.getBody(),notification); 
        
    }
    
    @Test
    public void testGetNotificationById_success() {
    	
    	Long notificationId = 1L;
    	
    	Notification notification = new Notification();
    	
    	when(notificationService.getNotificationById(notificationId)).thenReturn(Optional.of(notification));
    	
    	ResponseEntity<NotificationDto> expectedResult = notificationController.getNotificationById(notificationId);
    	
    	assertEquals(expectedResult.getStatusCode(),HttpStatus.OK);
        assertEquals(expectedResult.getBody(),notification); 
    }
    
    @Test
    public void testGetNotificationById_failure() {
    	
    	Long notificationId = 1L;
    	
    	
    	when(notificationService.getNotificationById(notificationId)).thenReturn(Optional.empty());
    	
    	ResponseEntity<NotificationDto> expectedResult = notificationController.getNotificationById(notificationId);
    	
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
    	
    	ResponseEntity<List<NotificationDto>> expectedResult = notificationController.getNotificationsByUserId(userId);
    	
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
        
        ResponseEntity<NotificationDto> expectedResult =
        		notificationController.markNotificationAsRead(notificationId);
       
        assertEquals(expectedResult.getStatusCode(),HttpStatus.OK);
        assertEquals(expectedResult.getBody(),notification);
        
    }
    
    @Test
    public void testMarkNotificationAsRead_failure() {
    	
    	Long notificationId=1L;
        
        when(notificationService.getNotificationById(notificationId)).thenReturn(Optional.empty());
        
        ResponseEntity<NotificationDto> expectedResult =
        		notificationController.markNotificationAsRead(notificationId);
     
        assertEquals(expectedResult.getStatusCode(),HttpStatus.NOT_FOUND);
        
        
    }
    

   
}
