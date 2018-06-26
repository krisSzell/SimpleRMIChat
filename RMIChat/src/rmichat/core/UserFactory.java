/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmichat.core;

import rmichat.client.IClientComms;
import rmichat.core.Models.User;

/**
 *
 * @author Krzysztof
 */
public class UserFactory {
    private static int _currentId = 0; 
    
    public User getNew(String name, IClientComms remoteInterface) {
        return new User(++_currentId, name, remoteInterface);
    }
}
