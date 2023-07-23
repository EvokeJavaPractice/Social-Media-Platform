package spring.angular.social.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.angular.social.dto.FriendConnectionDto;
import spring.angular.social.entity.FriendConnection;
import spring.angular.social.entity.User;
import spring.angular.social.mappers.FriendConnectionMapper;
import spring.angular.social.service.FriendConnectionService;
import spring.angular.social.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/friendConnections")
public class FriendConnectionController {

    @Autowired
    private FriendConnectionService friendConnectionService;
    @Autowired
    private UserService userService;

    @Autowired
    private FriendConnectionMapper mapper;


    
    @PostMapping
    public ResponseEntity<FriendConnectionDto> createFriendConnection(@RequestParam("userId") Long userId,
                                                                      @RequestParam("friendId") Long friendId) {
       
        
        FriendConnection connection = friendConnectionService.createFriendConnection(userId, friendId);
        return ResponseEntity.ok(mapper.toDto(connection));
    }


    @DeleteMapping
    public ResponseEntity<?> deleteFriendConnection(@RequestParam("userId") Long userId,
            										@RequestParam("friendId") Long friendId) {
    	 Optional<User> optionalUser = userService.findById(userId);
         Optional<User> optionalFriend = userService.findById(friendId);
         
        
         if (optionalUser.isEmpty() || optionalFriend.isEmpty()) {
             return ResponseEntity.notFound().build();
         }
         Optional<FriendConnection> optionalFriendConnection = userService.findFriendConnectionByUsers(userId, friendId);

         if (optionalFriendConnection.isEmpty()) {
             return ResponseEntity.notFound().build();
         }

         FriendConnection friendConnection = optionalFriendConnection.get();

         // Perform any necessary operations with the friend connection

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
    @PostMapping("/friendRequest/{notificationId}")
    public ResponseEntity<String> acceptFriendRequest(@PathVariable Long notificationId) {

        return new ResponseEntity<String>(friendConnectionService.acceptFriendRequest(notificationId),HttpStatus.OK);
    }
    @DeleteMapping("/{notificationId}")
    public ResponseEntity<?> declineFriendRequest(@PathVariable Long notificationId ){
    	friendConnectionService.declineFriendRequest(notificationId);
		return ResponseEntity.ok(HttpStatus.OK);

    }
}
