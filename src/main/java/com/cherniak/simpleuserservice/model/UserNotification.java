package com.cherniak.simpleuserservice.model;

import com.cherniak.simpleuserservice.model.enums.NotificationState;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "user_notifications", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"recipient_id", "notification_id"})
})
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "user_not_seq", sequenceName = "user_not_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id")
    @EqualsAndHashCode.Include
    private User recipient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_id", nullable = false)
    private Notification notification;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationState state;

    @Column(name = "modified_at")
    private Instant modifiedAt;

    public UserNotification(User recipient, Notification notification) {
        this.recipient = recipient;
        this.notification = notification;
    }
}
