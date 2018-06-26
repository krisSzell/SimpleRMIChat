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
public interface IUsersRepository {
    User getByUserName(String userName);
    ArrayList<User> getAll();
    void add(User client);
    void remove(User client);
}
