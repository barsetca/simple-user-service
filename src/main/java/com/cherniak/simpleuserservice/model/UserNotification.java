package com.cherniak.simpleuserservice.model;

import com.cherniak.simpleuserservice.model.enums.NotificationState;
import com.cherniak.simpleuserservice.model.enums.UserNotificationRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "user_notifications", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "notification_id", "role"})
})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "user_not_seq", sequenceName = "user_not_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserNotificationRole role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_id", nullable = false)
    private Notification notification;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationState state;

    @Column(name = "sent_at")
    private Instant sentAt;
    @Column(name = "read_at")
    private Instant readAt;

    public UserNotification(User user, UserNotificationRole role, Notification notification) {
        this.user = user;
        this.role = role;
        this.notification = notification;
    }
}
