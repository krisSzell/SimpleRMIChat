/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmichat.core.Models;

import java.io.Serializable;
import rmichat.client.IClientComms;

/**
 *
 * @author Krzysztof
 */
public class User implements Serializable {
    private final int _id;
    private final String _name;
    private final IClientComms _clientComms;
    
    public User(int id, String name, IClientComms comms) {
        _id = id;
        _name = name;
        _clientComms = comms;
    }
    
    public int getId(){
        return _id;
    }
    
    public String getName() {
        return _name;
    }
    
    public IClientComms getRemoteInterface() {
        return _clientComms;
    }
}
