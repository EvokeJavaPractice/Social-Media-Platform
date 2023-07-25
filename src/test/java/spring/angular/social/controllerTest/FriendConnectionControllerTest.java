package spring.angular.social.controllerTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import spring.angular.social.controller.FriendConnectionController;
import spring.angular.social.dto.FriendConnectionDto;
import spring.angular.social.entity.FriendConnection;
import spring.angular.social.entity.User;
import spring.angular.social.service.FriendConnectionService;
import spring.angular.social.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class FriendConnectionControllerTest {

    private final FriendConnectionService friendConnectionService =
            mock(FriendConnectionService.class);

    private final UserService userService = mock(UserService.class);

    private final FriendConnectionController friendConnectionController =
            new FriendConnectionController();


    @Test
    public void testCreateFriendConnection_Success() {

        Long userId = 1L;
        Long friendId = 2L;
        User user = new User();
        User friend = new User();
        FriendConnection connection = new FriendConnection();

        Mockito.when(userService.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(userService.findById(friendId)).thenReturn(Optional.of(friend));
        Mockito.when(friendConnectionService.createFriendConnection(userId, friendId)).thenReturn(connection);

        ResponseEntity<FriendConnectionDto> response =
                friendConnectionController.createFriendConnection(userId, friendId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(connection, response.getBody());
    }

    @Test
    public void testCreateFriendConnection_InvalidUser() {

        Long userId = 1L;
        Long friendId = 2L;
        Mockito.when(userService.findById(userId)).thenReturn(Optional.empty());

        ResponseEntity<FriendConnectionDto> response =
                friendConnectionController.createFriendConnection(userId, friendId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody() == null);
    }

    @Test
    public void testCreateFriendConnection_InvalidFriend() {

        Long userId = 1L;
        Long friendId = 2L;
        User user = new User();
        Mockito.when(userService.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(userService.findById(friendId)).thenReturn(Optional.empty());

        ResponseEntity<FriendConnectionDto> response =
                friendConnectionController.createFriendConnection(userId, friendId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody() == null);
    }

    @Test
    public void testDeleteFriendConnection_Success() {

        Long id = 1L;
        FriendConnection friendConnection = new FriendConnection();
        Mockito.when(friendConnectionService.findById(id)).thenReturn(friendConnection);
    }

    @Test
    public void testDeleteFriendConnection_Failure() {

        Long id = 1L;
        Mockito.when(friendConnectionService.findById(id)).thenReturn(null);

    }

    @Test
    public void testGetFriendCount_Success() {

        Long userId = 1L;
        Long friendCount = 5L;
        Mockito.when(friendConnectionService.getFriendCount(userId)).thenReturn(friendCount);

        ResponseEntity<Long> response = friendConnectionController.getFriendCount(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(friendCount, response.getBody());
    }

    @Test
    public void testGetFriends_Success() {

        Long userId = 1L;
        List<String> friendNames = new ArrayList<>();
        Mockito.when(friendConnectionService.getFriendNames(userId)).thenReturn(friendNames);

        ResponseEntity<List<String>> response = friendConnectionController.getFriends(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(friendNames, response.getBody());
    }

    @Test
    public void testIsFriend_True() {

        int userId = 1;
        int friendId = 2;
        Mockito.when(friendConnectionService.isFriend(userId, friendId)).thenReturn(true);

        boolean result = friendConnectionController.isFriend(userId, friendId);

        assertTrue(result);
    }

    @Test
    public void testIsFriend_False() {
        int userId = 1;
        int friendId = 2;
        Mockito.when(friendConnectionService.isFriend(userId, friendId)).thenReturn(false);
        boolean result = friendConnectionController.isFriend(userId, friendId);
        Assertions.assertFalse(result);
    }
}
