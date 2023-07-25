package spring.angular.social.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Table
@Entity
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "friend_id")
    private User friend;

    @OneToOne(fetch = FetchType.LAZY)
    private Notification notification;
}
