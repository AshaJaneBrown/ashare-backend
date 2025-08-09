package com.ashare.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatDTO {
    private Long id;
    private String username;
    private String lastMessage;
    private String lastMessageTime;
}
