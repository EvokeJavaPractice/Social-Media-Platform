package spring.angular.social.serviceTest;


import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import org.springframework.boot.test.mock.mockito.MockBean;
import spring.angular.social.entity.FriendConnection;
import spring.angular.social.entity.Notification;
import spring.angular.social.entity.User;
import spring.angular.social.repository.FriendConnectionRepository;
import spring.angular.social.service.FriendConnectionService;
import spring.angular.social.service.NotificationService;

public class FriendConnectionServiceTest {

    @MockBean
    private FriendConnectionService friendConnectionService;

    @MockBean
    private FriendConnectionRepository friendConnectionRepository;

    @MockBean
    private NotificationService notificationService;

    @Before("")
    public void setup() {

        MockitoAnnotations.initMocks(this);
        friendConnectionService = new FriendConnectionService(friendConnectionRepository);
        friendConnectionService.setNotificationService(notificationService);
        final Map<Integer, List<Integer>> friendConnections = new HashMap<>();
    }

    @Test
    public void testCreateFriendConnection() {

        User user = new User();
        User friend = new User();
        FriendConnection friendConnection = new FriendConnection();
        friendConnection.setUser(user);
        friendConnection.setFriend(friend);
        LocalDateTime now = LocalDateTime.now();

        when(friendConnectionRepository.save(friendConnection)).thenReturn(friendConnection);
        FriendConnection result = friendConnectionService.createFriendConnection(user.getId(), friend.getId());
        verify(notificationService, times(1)).createNotification(any(Notification.class));
        assertEquals(friendConnection, result);
        Notification notification = friendConnection.getNotification();
        assertNotNull(notification);
        assertEquals(friendConnection.getUser(), notification.getUser());
        assertEquals("You connected with a new freind... " + friendConnection.getFriend().getUsername(), notification.getMessage());
        assertNotNull(notification.getCreatedAt());
    }

    @Test
    public void testDeleteFriendConnection() {

        FriendConnection friendConnection = new FriendConnection();
        friendConnectionService.deleteFriendConnection(friendConnection);
        verify(friendConnectionRepository, times(1)).delete(friendConnection);
    }

    @Test
    public void testFindById() {

        Long id = 1L;
        FriendConnection friendConnection = new FriendConnection();
        friendConnection.setId(id);
        when(friendConnectionRepository.findById(id)).thenReturn(Optional.of(friendConnection));
        FriendConnection result = friendConnectionService.findById(id);
        verify(friendConnectionRepository, times(1)).findById(id);
        assertEquals(friendConnection, result);
    }

    @Test
    public void testFindById_FriendConnectionNotFound() {

        Long id = 1L;
        when(friendConnectionRepository.findById(id)).thenReturn(Optional.empty());
        FriendConnection result = friendConnectionService.findById(id);
        verify(friendConnectionRepository, times(1)).findById(id);
        assertNull(result);
    }

    @Test
    public void testGetFriendCount() {

        Long userId = 1L;
        Long friendCount = 5L;

        when(friendConnectionRepository.countByUserId(userId)).thenReturn(friendCount);
        Long result = friendConnectionService.getFriendCount(userId);
        verify(friendConnectionRepository, times(1)).countByUserId(userId);
        assertEquals(friendCount, result);
    }

    @Test
    public void testGetFriendNames() {

        Long userId = 1L;
        List<FriendConnection> connections = new ArrayList<>();
        User friend1 = new User();
        friend1.setUsername("Friend1");
        User friend2 = new User();
        friend2.setUsername("Friend2");
        connections.add(createFriendConnection(userId, friend1));
        connections.add(createFriendConnection(userId, friend2));

        when(friendConnectionRepository.findByUserId(userId)).thenReturn(connections);
        List<String> result = friendConnectionService.getFriendNames(userId);
        verify(friendConnectionRepository, times(1)).findByUserId(userId);

        assertEquals(2, result.size());
        assertTrue(result.contains("Friend1"));
        assertTrue(result.contains("Friend2"));
    }

    @Test
    public void testIsFriend_ExistingFriendConnection() {

        int userId = 1;
        int friendId = 2;
        List<Integer> friends = new ArrayList<>();
        friends.add(friendId);
        Map<Integer, List<Integer>> friendConnections = new HashMap<>();
        friendConnections.put(userId, friends);

        friendConnectionService.setFriendConnections(friendConnections);
        boolean result = friendConnectionService.isFriend(userId, friendId);
        assertTrue(result);
    }

    @Test
    public void testIsFriend_NonExistingFriendConnection() {
        int userId = 1;
        int friendId = 2;
        Map<Integer, List<Integer>> friendConnections = new HashMap<>();

        friendConnectionService.setFriendConnections(friendConnections);
        boolean result = friendConnectionService.isFriend(userId, friendId);

        assertFalse(result);
    }

    private FriendConnection createFriendConnection(Long userId, User friend) {
        FriendConnection connection = new FriendConnection();
        connection.setFriend(friend);
        return connection;
    }
}
