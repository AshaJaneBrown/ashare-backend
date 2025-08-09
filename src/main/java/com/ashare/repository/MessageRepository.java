package com.ashare.repository;

import com.ashare.model.Message;
import com.ashare.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("""
    SELECT 
        CASE WHEN m.sender.id = :userId THEN m.recipient.id ELSE m.sender.id END AS otherUserId,
        CASE WHEN m.sender.id = :userId THEN m.recipient.username ELSE m.sender.username END AS otherUsername,
        m.content AS lastMessage,
        m.timestamp AS lastMessageTime
    FROM Message m
    WHERE m.timestamp IN (
        SELECT MAX(m2.timestamp)
        FROM Message m2
        WHERE m2.sender.id = :userId OR m2.recipient.id = :userId
        GROUP BY 
            CASE WHEN m2.sender.id = :userId THEN m2.recipient.id ELSE m2.sender.id END
    )
    ORDER BY lastMessageTime DESC
""")
    List<Object[]> findChatsForUser(@Param("userId") Long userId);

    @Query("""
    SELECT m FROM Message m
    WHERE (m.sender.id = :userId AND m.recipient.id = :otherUserId)
       OR (m.sender.id = :otherUserId AND m.recipient.id = :userId)
    ORDER BY m.timestamp ASC
""")
    List<Message> findConversation(@Param("userId") Long userId, @Param("otherUserId") Long otherUserId);

}
