package com.cherniak.simpleuserservice.repository;

import com.cherniak.simpleuserservice.model.UserNotification;
import com.cherniak.simpleuserservice.model.enums.NotificationState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface UserNotificationRepository extends JpaRepository<UserNotification, Long> {

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE UserNotification un SET un.state= :state, un.modifiedAt= :modifiedAt WHERE un.notification.id= :notificationId AND un.recipient.username= :username")
    void setState(@Param("state") NotificationState state, @Param("modifiedAt") Instant modifiedAt, @Param("notificationId") Long notificationId, @Param("username") String username);
}
