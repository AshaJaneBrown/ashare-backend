package com.ashare.controller;

import com.ashare.dto.ChatDTO;
import com.ashare.dto.MessageDTO;
import com.ashare.model.User;
import com.ashare.service.ChatService;
import com.ashare.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final UserService userService;

    // Список чатів користувача
    @GetMapping
    public List<ChatDTO> getUserChats(Principal principal) {
        User currentUser = userService.getByEmail(principal.getName());
        return chatService.getUserChats(currentUser);
    }

    // Історія повідомлень з користувачем
    @GetMapping("/{userId}/messages")
    public List<MessageDTO> getMessagesWithUser(@PathVariable Long userId, Principal principal) {
        User currentUser = userService.getByEmail(principal.getName());
        return chatService.getConversation(currentUser, userId);
    }

    // Надіслати повідомлення
    @PostMapping("/{userId}/messages")
    public void sendMessageToUser(@PathVariable Long userId, @RequestBody String content, Principal principal) {
        User sender = userService.getByEmail(principal.getName());
        chatService.sendMessage(sender, userId, content);
    }
}

