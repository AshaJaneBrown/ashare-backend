package com.ashare.dto;

import com.ashare.model.Message;
import com.ashare.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
    private Long id;
    private Long senderId;
    private Long recipientId;
    private String content;
    private LocalDateTime timestamp;
}
