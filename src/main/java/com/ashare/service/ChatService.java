package com.ashare.service;

import com.ashare.dto.ChatDTO;
import com.ashare.dto.MessageDTO;
import com.ashare.model.Message;
import com.ashare.model.User;
import com.ashare.repository.MessageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final MessageRepository messageRepository;
    private final UserService userService;

    public List<ChatDTO> getUserChats(User currentUser) {
        return messageRepository.findChatsForUser(currentUser.getId())
                .stream()
                .map(row -> new ChatDTO(
                        (Long) row[0],       // otherUserId
                        (String) row[1],     // otherUsername
                        (String) row[2],     // lastMessage
                        row[3].toString()    // lastMessageTime
                ))
                .toList();
    }

    public List<MessageDTO> getConversation(User currentUser, Long otherUserId) {
        return messageRepository.findConversation(currentUser.getId(), otherUserId)
                .stream()
                .map(m -> new MessageDTO(
                        m.getId(),
                        m.getSender().getId(),
                        m.getRecipient().getId(),
                        m.getContent(),
                        m.getTimestamp()
                ))
                .toList();
    }

    @Transactional
    public void sendMessage(User sender, Long recipientId, String content) {
        User recipient = userService.getById(recipientId);
        Message message = new Message();
        message.setSender(sender);
        message.setRecipient(recipient);
        message.setContent(content);
        messageRepository.save(message);
    }
}

