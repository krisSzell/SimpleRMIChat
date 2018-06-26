/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmichat.core.Models;

/**
 *
 * @author Krzysztof
 */
public class PrivateMessage extends Message {
    
    private final String _recipientName;
    
    public PrivateMessage(User author, String recipientName, String content) {
        super(author, content);
        _recipientName = recipientName;
    }
    
    public String getRecipientName() {
        return _recipientName;
    }
}
