package com.example.ex5springdavidzaydenbergronelian;

import lombok.Getter;
import lombok.Setter;

/**
 * This class represents a chat message.
 */
@Setter
@Getter
public class ChatMessage {
    private String sender;
    private String receiver;
    private String content;
}
