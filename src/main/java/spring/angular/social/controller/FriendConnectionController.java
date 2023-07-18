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

    @Autowired
    private FriendConnectionService friendConnectionService;
    @Autowired
    private UserService userService;


	/*
	 * @PostMapping public ResponseEntity<FriendConnection>
	 * createFriendConnection(@RequestParam("userId") Long userId,
	 * 
	 * @RequestParam("friendId") Long friendId) { Optional<User> optionalUser =
	 * userService.findById(userId); Optional<User> optionalFriend =
	 * userService.findById(friendId);
	 * 
	 * if (optionalUser.isEmpty() || optionalFriend.isEmpty()) { return
	 * ResponseEntity.notFound().build(); }
	 * 
	 * User user = optionalUser.get(); User friend = optionalFriend.get();
	 * 
	 * FriendConnection connection =
	 * friendConnectionService.createFriendConnection(user, friend); return
	 * ResponseEntity.ok(connection); }
	 * 
	 * 
	 * @DeleteMapping("/{id}") public ResponseEntity<?>
	 * deleteFriendConnection(@PathVariable Long id) { FriendConnection
	 * friendConnection = friendConnectionService.findById(id);
	 * 
	 * if (friendConnection == null) { return ResponseEntity.notFound().build(); }
	 * 
	 * friendConnectionService.deleteFriendConnection(friendConnection); return
	 * ResponseEntity.ok().build(); }
	 */
    
    @PostMapping
    public ResponseEntity<FriendConnection> createFriendConnection(@RequestParam("userId") Long userId,
                                                                   @RequestParam("friendId") Long friendId) {
       
        
        FriendConnection connection = friendConnectionService.createFriendConnection(userId, friendId);
        return ResponseEntity.ok(connection);
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
}
