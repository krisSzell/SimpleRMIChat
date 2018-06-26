/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmichat.core.Repositories;

import java.util.ArrayList;
import rmichat.core.Models.User;

/**
 *
 * @author Krzysztof
 */
public class InMemoryUsersRepository implements IUsersRepository {

    private final ArrayList<User> _data;
    
    public InMemoryUsersRepository(ArrayList<User> data) {
        _data = data;
    }
    
    @Override
    public User getByUserName(String userName) {
        return _data.stream()
            .filter(user -> user.getName().equals(userName))
            .findFirst()
            .orElse(null);
    }

    @Override
    public ArrayList<User> getAll() {
        return _data;
    }

    @Override
    public void add(User client) {
        _data.add(client);
    }

    @Override
    public void remove(User client) {
        _data.remove(client);
    }
    
}
