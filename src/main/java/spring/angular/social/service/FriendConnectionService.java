package spring.angular.social.service;

import java.time.LocalDateTime;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.angular.social.entity.FriendConnection;
import spring.angular.social.entity.Notification;
import spring.angular.social.entity.User;
import spring.angular.social.repository.FriendConnectionRepository;

@Service
public class FriendConnectionService {
    private final FriendConnectionRepository friendConnectionRepository;
    

    @Autowired
    private NotificationService notificationService;
    
    @Autowired
    private UserService userService;

    private final Map<Integer, List<Integer>> friendConnections = new HashMap<>();
    @Autowired
    public FriendConnectionService(FriendConnectionRepository friendConnectionRepository) {
        this.friendConnectionRepository = friendConnectionRepository;
    }

	/*
	 * public FriendConnection createFriendConnection(User user, User friend) {
	 * FriendConnection connection = new FriendConnection();
	 * connection.setUser(user); connection.setFriend(friend);
	 * 
	 * Notification notification = new Notification();
	 * notification.setUser(connection.getUser());
	 * notification.setMessage("You connected with a new freind... "+
	 * connection.getFriend().getUsername());
	 * notification.setCreatedAt(LocalDateTime.now());
	 * 
	 * connection.setNotification(notification);
	 * 
	 * notificationService.createNotification(notification); return
	 * friendConnectionRepository.save(connection); }
	 * 
	 * public void deleteFriendConnection(FriendConnection friendConnection) {
	 * friendConnectionRepository.delete(friendConnection); }
	 */
    
 public FriendConnection createFriendConnection(Long userId, Long friendId) {
    	
    	

        Optional<FriendConnection> optionalFriendConnection = userService.findFriendConnectionByUsers(userId, friendId);
       
        if(optionalFriendConnection.isPresent()) {
        	throw new RuntimeException("you both are already friends");
        }
        else
        {
        	 Optional<User> optionalUser = userService.findById(userId);
             Optional<User> optionalFriend = userService.findById(friendId);
             
             User user = optionalUser.get();
             User friend = optionalFriend.get();
         	
             FriendConnection connection = new FriendConnection();
             connection.setUser(user);
             connection.setFriend(friend);
             
             Notification notification = new Notification();
             notification.setUser(connection.getUser());
             notification.setMessage("You connected with a new freind... "+ connection.getFriend().getUsername());
             notification.setCreatedAt(LocalDateTime.now());

             connection.setNotification(notification);

             notificationService.createNotification(notification);

             
             return friendConnectionRepository.save(connection);
        }
        

    }

    public void deleteFriendConnection(FriendConnection friendConnection) {
        friendConnectionRepository.delete(friendConnection);
    }

    public FriendConnection findById(Long id) {
        Optional<FriendConnection> friendConnection = friendConnectionRepository.findById(id);
        return friendConnection.orElse(null);
    }

	public Long getFriendCount(Long userId) {
		return friendConnectionRepository.countByUserId(userId);
	}

	public List<String> getFriendNames(Long userId) {
	    List<FriendConnection> connections = friendConnectionRepository.findByUserId(userId);
	    List<String> friendNames = new ArrayList<>();
	    for (FriendConnection connection : connections) {
	        friendNames.add(connection.getFriend().getUsername());
	    }
	    return friendNames;
	}

    public boolean isFriend(int userId, int friendId) {
        List<Integer> friends = friendConnections.get(userId);
        return friends != null && friends.contains(friendId);
    }

	public void setFriendConnections(Map<Integer, List<Integer>> friendConnections) {
		
		
	}

	public void setNotificationService(NotificationService notificationService) {
		this.notificationService = notificationService;
		
	}
}
