package com.example.ex5springdavidzaydenbergronelian.controller;

import com.example.ex5springdavidzaydenbergronelian.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.ex5springdavidzaydenbergronelian.constants.InfoMessages.*;

/**
 * Controller for handling chat requests and messages.
 */
@Controller
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;

    private final Map<String, String> activeSupportChats = new HashMap<>();
    private final Queue<String> supportQueue = new LinkedList<>();
    private final Set<String> pendingRequests = new HashSet<>();
    private final Map<String, Long> requestTimestamps = new HashMap<>();
    private static final long REQUEST_TIMEOUT = 300000; // 5 minutes in milliseconds

    /**
     * Constructor for ChatController.
     * @param messagingTemplate SimpMessagingTemplate for sending messages.
     */
    @Autowired
    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * Handler for the chat page.
     * @param model Model for the chat page.
     * @return The chat page.
     */
    @GetMapping("/chat")
    public String chatPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
            model.addAttribute("username", auth.getName());
            if (auth.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN")))
                model.addAttribute("isAdmin", true);
        }
        return "chat";
    }

    /**
     * Handler for a user requesting support.
     * @param chatMessage ChatMessage containing the user's username.
     */
    @MessageMapping("/chat.requestSupport")
    public void requestSupport(ChatMessage chatMessage) {
        String user = chatMessage.getSender();
        long currentTime = System.currentTimeMillis();

        // Remove expired requests
        supportQueue.removeIf(u -> currentTime - requestTimestamps.getOrDefault(u, 0L) > REQUEST_TIMEOUT);
        pendingRequests.removeIf(u -> currentTime - requestTimestamps.getOrDefault(u, 0L) > REQUEST_TIMEOUT);

        if (!pendingRequests.contains(user) && !activeSupportChats.containsKey(user)) {
            pendingRequests.add(user);
            supportQueue.offer(user);
            requestTimestamps.put(user, currentTime);
            updateAdminQueue();
            sendStatusToUser(user, SUPPORT_REQUEST_PENDING);
        } else
            sendStatusToUser(user, PENDING_OR_ACTIVE_SUPPORT_REQUEST);
    }

    /**
     * Handler for a user cancelling a support request.
     * @param chatMessage ChatMessage containing the user's username.
     */
    @MessageMapping("/chat.cancelRequest")
    public void cancelRequest(ChatMessage chatMessage) {
        String user = chatMessage.getSender();
        pendingRequests.remove(user);
        supportQueue.remove(user);
        requestTimestamps.remove(user);
        updateAdminQueue();
    }

    /**
     * Handler for an admin accepting a support request.
     * @param chatMessage ChatMessage containing the admin's username.
     */
    @MessageMapping("/chat.acceptSupport")
    public void acceptSupport(ChatMessage chatMessage) {
        String admin = chatMessage.getSender();
        String user = supportQueue.poll();
        if (user != null) {
            pendingRequests.remove(user);
            activeSupportChats.put(user, admin);
            activeSupportChats.put(admin, user);

            notifyUserOfAcceptedChat(user, admin);
            notifyAdminOfAcceptedChat(admin, user);
            updateAdminQueue();
        }
    }

    /**
     * Handler for sending a message in a chat.
     * @param chatMessage ChatMessage containing the message content and sender.
     */
    @MessageMapping("/chat.sendMessage")
    public void sendMessage(ChatMessage chatMessage) {
        String sender = chatMessage.getSender();
        String receiver = activeSupportChats.get(sender);

        if (receiver != null)
            messagingTemplate.convertAndSend("/queue/" + receiver, chatMessage);
    }

    /**
     * Handler for ending a support chat.
     * @param chatMessage ChatMessage containing the user's username.
     */
    @MessageMapping("/chat.endSupport")
    public void endSupport(ChatMessage chatMessage) {
        String sender = chatMessage.getSender();
        String receiver = activeSupportChats.remove(sender);
        if (receiver != null) {
            activeSupportChats.remove(receiver);
            pendingRequests.remove(sender);
            pendingRequests.remove(receiver);
            notifyChatEnded(sender);
            notifyChatEnded(receiver);
        }
    }

    /**
     * Handler for an admin connecting to the chat.
     */
    @MessageMapping("/chat.adminConnected")
    public void adminConnected() {
        updateAdminQueue();
    }

    /**
     * Notify a user that the support chat has ended.
     * @param user The user to notify.
     */
    private void notifyChatEnded(String user) {
        ChatMessage message = new ChatMessage();
        message.setSender("System");
        message.setContent(SUPPORT_CHAT_ENDED);
        messagingTemplate.convertAndSend("/queue/" + user, message);
    }

    /**
     * Send a status message to a user.
     * @param user The user to send the status message to.
     * @param status The status message to send.
     */
    private void sendStatusToUser(String user, String status) {
        ChatMessage statusMessage = new ChatMessage();
        statusMessage.setSender("System");
        statusMessage.setContent(status);
        messagingTemplate.convertAndSend("/queue/" + user, statusMessage);
    }

    /**
     * Update the admin queue with the current support queue.
     */
    private void updateAdminQueue() {
        long currentTime = System.currentTimeMillis();
        List<String> queueAsList = supportQueue.stream()
                .filter(u -> currentTime - requestTimestamps.getOrDefault(u, 0L) <= REQUEST_TIMEOUT)
                .collect(Collectors.toList());
        messagingTemplate.convertAndSend("/topic/adminQueue", queueAsList);
    }

    /**
     * Notify a user that their chat request has been accepted.
     * @param user The user to notify.
     * @param admin The admin that accepted the chat.
     */
    private void notifyUserOfAcceptedChat(String user, String admin) {
        ChatMessage message = new ChatMessage();
        message.setSender("System");
        message.setContent(SUPPORT_CHAT_ACCEPTED);
        messagingTemplate.convertAndSend("/queue/" + user, message);
    }

    /**
     * Notify an admin that they are now in a support chat.
     * @param admin The admin to notify.
     * @param user The user that the admin is now in a chat with.
     */
    private void notifyAdminOfAcceptedChat(String admin, String user) {
        ChatMessage message = new ChatMessage();
        message.setSender("System");
        message.setContent(SUPPORT_CHAT_STARTED + user);
        messagingTemplate.convertAndSend("/queue/" + admin, message);
    }
}
