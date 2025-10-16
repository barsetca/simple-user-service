package com.cherniak.simpleuserservice.model;

import com.cherniak.simpleuserservice.model.enums.NotificationState;
import com.cherniak.simpleuserservice.model.enums.UserNotificationRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "notification_seq", sequenceName = "notification_seq", allocationSize = 100)
    private Long id;

    @NotBlank
    @Length(max = 100)
    private String title;

    @NotBlank
    @Length(max = 2_000)
    private String message;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    @EqualsAndHashCode.Include
    private User sender;

    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserNotification> userNotifications;

    @Column(name = "created_at")
    @CreationTimestamp
    @EqualsAndHashCode.Include
    private Instant createdAt;

    public Notification(String title, String message, Set<UserNotification> userNotifications) {
        this.title = title;
        this.message = message;
        this.userNotifications = userNotifications;
    }

    public void addUserNotification(User user, UserNotificationRole userNotificationRole) {
        if (this.userNotifications == null) {
            this.userNotifications = new HashSet<>();
        }
        UserNotification userNotification = new UserNotification(user, userNotificationRole, this);
        userNotification.setState(NotificationState.SENT);
        userNotifications.add(userNotification);
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
