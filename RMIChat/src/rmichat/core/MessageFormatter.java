package rmichat.core;

import rmichat.core.Models.PrivateMessage;

/**
 *
 * @author Krzysztof
 */
public class MessageFormatter {
    public String userJoinedGlobalChat(String userName) {
        return userName + " has joined the global chat!";
    }
    
    public String userLeftGlobalChat(String userName) {
        return userName + " has left the global chat!";
    }
    
    public String getWithPrivateMarkForAuthor(PrivateMessage message) {
        return message.getContent() + " (private with: " + message.getRecipientName() + ")";
    }
    
    public String getWithPrivateMarkForRecipient(PrivateMessage message) {
        return message.getContent() + " (private with: " + message.getAuthor().getName() + ")";
    }
}
