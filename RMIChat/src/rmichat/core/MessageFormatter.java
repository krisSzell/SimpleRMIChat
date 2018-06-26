/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmichat.core;

/**
 *
 * @author Krzysztof
 */
public class MessageFormatter {
    private final TimeStampProvider _timeStampProvider;
    
    public MessageFormatter(TimeStampProvider timeStampProvider) {
        _timeStampProvider = timeStampProvider;
    }
    
    public String includeTimeStampAndUserName(String message, String userName) {
        return _timeStampProvider.getCurrent() + " " + userName + ": " + message;
    }
    
    public String userJoinedGlobalChat(String userName) {
        return userName + " has joined the global chat!";
    }
    
    public String userLeftGlobalChat(String userName) {
        return userName + " has left the global chat!";
    }
}
