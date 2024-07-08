/**
 * Chat module for handling chat functionality between users and admins.
 * @type {{init: init}}
 */
const chat = (function() {
    let stompClient = null;
    let chatActive = false;
    let requestPending = false;
    let currentChatPartner = '';
    let username = '';
    let isAdmin = false;

    // DOM element constants
    const connectBtn = document.getElementById('connectBtn');
    const userContainer = document.getElementById('userContainer');
    const adminContainer = document.getElementById('adminContainer');
    const requestSupportBtn = document.getElementById('requestSupportBtn');
    const acceptSupportBtn = document.getElementById('acceptSupportBtn');
    const chatContainer = document.getElementById('chatContainer');
    const endChatBtn = document.getElementById('endChatBtn');
    const sendMessageBtn = document.getElementById('sendMessageBtn');
    const messageInput = document.getElementById('messageInput');
    const messages = document.getElementById('messages');
    const chatStatus = document.getElementById('chatStatus');
    const requestStatus = document.getElementById('requestStatus');
    const supportQueue = document.getElementById('supportQueue');

    const USERNAME_REQUIRED = "Please enter a username before connecting.";
    const CANNOT_SEND_MESSAGE = "Cannot send message. Conditions:";
    const LEAVE_CHAT = "Leave Chat";
    const RECEIVED_MESSAGE = "Received message:";
    const SUPPORT_CHAT_ENDED = "The support chat has ended.";
    const SUPPORT_CHAT_ACCEPTED = "Support chat accepted.";
    const SUPPORT_REQUEST_PENDING = "Your support request is pending.";
    const SUPPORT_CHAT_STARTED = "You are now in a support chat with user:";
    const ALREADY_HAVE_SUPPORT_REQUEST = "You already have a pending or active support request.";
    const CURRENT_CHAT_PARTNER_SET_TO = "Current chat partner set to:";
    const RECEIVED_ADMIN_QUEUE_UPDATE = "Received admin queue update:";

    /**
     * Connects to the WebSocket server and initializes the chat functionality.
     */
    const connect = () => {
        if (!username && !isAdmin)
            username = document.getElementById('username').value;

        if (username || isAdmin) {
            const socket = new SockJS('/chat-websocket');
            stompClient = Stomp.over(socket);
            stompClient.connect({}, (frame) => {
                console.log('Connected: ' + frame);

                if (isAdmin) {
                    adminContainer.classList.remove('d-none');
                    stompClient.send("/app/chat.adminConnected", {});
                    stompClient.subscribe('/topic/adminQueue', (message) => {
                        console.log(RECEIVED_ADMIN_QUEUE_UPDATE, message.body);
                        updateSupportQueue(JSON.parse(message.body));
                    });
                } else {
                    userContainer.classList.remove('d-none');
                    document.getElementById('requestSupportBtn').classList.remove('d-none');
                }

                stompClient.subscribe('/queue/' + username, (message) => {
                    handleIncomingMessage(JSON.parse(message.body));
                });

                connectBtn.classList.add('d-none');
            });
        } else
            alert(USERNAME_REQUIRED);
    }

    /**
     * Sends a request for support to the admin.
     */
    const requestSupport = () => {
        if (!requestPending && !chatActive) {
            stompClient.send("/app/chat.requestSupport", {}, JSON.stringify({sender: username}));
            requestPending = true;
            requestSupportBtn.disabled = true;
        }
    }

    /**
     * Accepts a support request from a user.
     */
    const acceptSupport = () => {
        stompClient.send("/app/chat.acceptSupport", {}, JSON.stringify({sender: username}));
        chatContainer.classList.remove('d-none');
        adminContainer.classList.add('d-none');
    }

    /**
     * Sends a message to the current chat partner.
     */
    const sendMessage = () => {
        const messageContent = messageInput.value;
        if (messageContent && stompClient && chatActive) {
            const chatMessage = {
                sender: username,
                content: messageContent,
                receiver: currentChatPartner
            };
            console.log("Sending message:", chatMessage);
            stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
            showMessage(username, messageContent, true);
            messageInput.value = '';
        } else {
            console.log(CANNOT_SEND_MESSAGE, {
                messageContent: !!messageContent,
                stompClient: !!stompClient,
                chatActive: chatActive
            });
        }
    }

    /**
     * Ends the current chat session.
     */
    const endChat = () => {
        if (chatActive) {
            stompClient.send("/app/chat.endSupport", {}, JSON.stringify({sender: username}));
            chatActive = false;
            endChatBtn.textContent = LEAVE_CHAT;
            sendMessageBtn.disabled = true;
            messageInput.disabled = true;
        } else {
            chatContainer.classList.add('d-none');
            if (isAdmin)
                adminContainer.classList.remove('d-none');
            else {
                userContainer.classList.remove('d-none');
                requestStatus.innerHTML = '';
                requestSupportBtn.disabled = false;
            }
            messages.innerHTML = '';
            currentChatPartner = '';
        }
        chatStatus.innerHTML = '';
    }

    /**
     * Handles incoming messages from the WebSocket server.
     * @param message
     */
    const handleIncomingMessage = (message) => {
        console.log(RECEIVED_MESSAGE, message);
        if (message.sender === "System") {
            if (message.content === SUPPORT_CHAT_ENDED) {
                chatActive = false;
                requestPending = false;
                endChatBtn.textContent = LEAVE_CHAT;
                sendMessageBtn.disabled = true;
                messageInput.disabled = true;
                if (!isAdmin) {
                    requestSupportBtn.disabled = false;
                    if (requestStatus) {
                        requestStatus.innerHTML = '';
                    }
                }
            } else if (message.content.startsWith(SUPPORT_REQUEST_PENDING)) {
                showRequestStatus(message.content);
            } else if (message.content === ALREADY_HAVE_SUPPORT_REQUEST) {
                showRequestStatus(message.content);
            } else if (message.content.startsWith(SUPPORT_CHAT_ACCEPTED) ||
                message.content.startsWith(SUPPORT_CHAT_STARTED)) {
                chatActive = true;
                requestPending = false;
                endChatBtn.textContent = 'End Chat';
                sendMessageBtn.disabled = false;
                messageInput.disabled = false;
                currentChatPartner = isAdmin ? message.content.split("user: ")[1] : "admin";
                console.log(CURRENT_CHAT_PARTNER_SET_TO, currentChatPartner);
                if (requestStatus) {
                    requestStatus.innerHTML = '';
                }
                chatContainer.classList.remove('d-none');
                if (isAdmin) {
                    adminContainer.classList.add('d-none');
                } else {
                    userContainer.classList.add('d-none');
                }
            }
            showStatus(message.content);
            showMessage("System", message.content, false);
        } else {
            showMessage(message.sender, message.content, false);
        }
    }


    /**
     * Displays a message in the chat window.
     * @param sender
     * @param content
     * @param isSent
     */
    const showMessage = (sender, content, isSent) => {
        const messageElement = document.createElement('div');
        messageElement.className = `message ${isSent ? 'message-sent' : 'message-received'} ${sender === 'System' ? 'message-system' : ''}`;
        messageElement.innerHTML = `<strong>${sender}:</strong> ${content}`;
        messages.appendChild(messageElement);
        messages.scrollTop = messages.scrollHeight;
    }

    /**
     * Displays a status message in the chat window.
     * @param status
     */
    const showStatus = (status) => {
        chatStatus.innerHTML = `<div class="alert alert-info">${status}</div>`;
    }

    /**
     * Displays a status message for the support request.
     * @param status
     */
    const showRequestStatus = (status) => {
        requestStatus.innerHTML = `<div class="alert alert-info">${status}</div>`;
    }

    /**
     * Updates the support queue with the current list of users waiting for support.
     * @param queue
     */
    const updateSupportQueue = (queue) => {
        supportQueue.innerHTML = '';
        queue.forEach(user => {
            const li = document.createElement('li');
            li.className = 'list-group-item';
            li.textContent = user;
            supportQueue.appendChild(li);
        });
        acceptSupportBtn.disabled = queue.length === 0;
    }

    /**
     * Initializes the chat module with the user's username and role.
     * @param userUsername
     * @param userIsAdmin
     */
    const init = (userUsername, userIsAdmin) => {
        username = userUsername;
        isAdmin = userIsAdmin;

        if (messageInput) {
            messageInput.addEventListener('keypress', (e) => {
                if (e.key === 'Enter')
                    sendMessage();
            });
        }

        /**
         * Disconnect the user from the chat when the window is closed or refreshed.
         */
        window.addEventListener('beforeunload', (event) => {
            if (chatActive)
                stompClient.send("/app/chat.endSupport", {}, JSON.stringify({sender: username}));
            else if (requestPending)
                stompClient.send("/app/chat.cancelRequest", {}, JSON.stringify({sender: username}));
        });

        if (connectBtn) connectBtn.addEventListener('click', connect);
        if (requestSupportBtn) requestSupportBtn.addEventListener('click', requestSupport);
        if (acceptSupportBtn) acceptSupportBtn.addEventListener('click', acceptSupport);
        if (sendMessageBtn) sendMessageBtn.addEventListener('click', sendMessage);
        if (endChatBtn) endChatBtn.addEventListener('click', endChat);

        // Initialize UI based on user role
        if (isAdmin)
            if (adminContainer) adminContainer.classList.remove('d-none');
            else
            if (userContainer) userContainer.classList.remove('d-none');
    }

    // Expose connect function globally for use in HTML onclick attribute
    window.connect = connect;

    return {
        init: init
    };
})();

/**
 * Initialize the chat module when the DOM content is loaded.
 */
document.addEventListener('DOMContentLoaded', () => {
    const username = document.getElementById('usernameInput').value;
    const isAdmin = document.getElementById('isAdminInput').value === 'true';
    chat.init(username, isAdmin);
});
