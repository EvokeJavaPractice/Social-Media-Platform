package spring.angular.social.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import spring.angular.social.entity.FriendConnection;
import spring.angular.social.entity.User;
import spring.angular.social.service.FriendConnectionService;
import spring.angular.social.service.UserService;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/friendConnections")
public class FriendConnectionController {
    private final FriendConnectionService friendConnectionService;
    private final UserService userService;

    @Autowired
    public FriendConnectionController(FriendConnectionService friendConnectionService, UserService userService) {
        this.friendConnectionService = friendConnectionService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<FriendConnection> createFriendConnection(@RequestParam("userId") Long userId,
                                                                   @RequestParam("friendId") Long friendId) {
        Optional<User> optionalUser = userService.findById(userId);
        Optional<User> optionalFriend = userService.findById(friendId);
        
        if (optionalUser.isEmpty() || optionalFriend.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        User user = optionalUser.get();
        User friend = optionalFriend.get();
        
        FriendConnection connection = friendConnectionService.createFriendConnection(user, friend);
        return ResponseEntity.ok(connection);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFriendConnection(@PathVariable Long id) {
        FriendConnection friendConnection = friendConnectionService.findById(id);
        
        if (friendConnection == null) {
            return ResponseEntity.notFound().build();
        }
        
        friendConnectionService.deleteFriendConnection(friendConnection);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/users/{userId}/friends/count")
    public ResponseEntity<Long> getFriendCount(@PathVariable Long userId) {
        Long friendCount = friendConnectionService.getFriendCount(userId);
        return ResponseEntity.ok(friendCount);
    }
    
    @GetMapping("/users/{userId}/friends")
    public ResponseEntity<List<String>> getFriends(@PathVariable Long userId) {
        List<String> friendNames = friendConnectionService.getFriendNames(userId);
        return ResponseEntity.ok(friendNames);
    }

    @GetMapping("/users/{userId}/friends/{friendId}")
    public boolean isFriend(@PathVariable int userId, @PathVariable int friendId) {
        return friendConnectionService.isFriend(userId, friendId);
    }
}
