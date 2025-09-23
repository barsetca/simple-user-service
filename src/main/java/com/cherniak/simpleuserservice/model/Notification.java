package com.cherniak.simpleuserservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "notifications")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "notification_seq", sequenceName = "notification_seq", allocationSize = 100)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank
    @Length(max = 100)
    private String title;

    @NotBlank
    @Length(max = 10_000)
    private String message;

    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserNotification> userNotifications;

    @Column(name = "created_at")
    @CreationTimestamp
    private Instant createdAt;

    public Notification(String title, String message, Set<UserNotification> userNotifications) {
        this.title = title;
        this.message = message;
        this.userNotifications = userNotifications;
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
