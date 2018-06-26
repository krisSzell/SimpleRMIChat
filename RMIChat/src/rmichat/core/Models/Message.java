/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmichat.core.Models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 *
 * @author Krzysztof
 */
public class Message implements Serializable {
    private final User _author;
    private final String _content;
    private final Timestamp _timestamp;
    
    public Message(User author, String content) {
        _author = author;
        _content = content;
        _timestamp = new Timestamp(System.currentTimeMillis());
    }
    
    public User getAuthor() {
        return _author;
    }
    
    public String getContent() {
        return new SimpleDateFormat("[yyyy-MM-dd hh:mm:ss] ").format(_timestamp) + _author.getName() + ": " + _content;
    }
    
    public Timestamp getTimestamp() {
        return _timestamp;
    }
}
