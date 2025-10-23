package com.cherniak.simpleuserservice.repository;

import com.cherniak.simpleuserservice.model.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("SELECT n FROM Notification n JOIN UserNotification un ON n.id = un.notification.id AND (n.sender.username = :username OR un.recipient.username = :username)")
    Page<Notification> findAll(Pageable pageable, @Param("username") String username);
}
