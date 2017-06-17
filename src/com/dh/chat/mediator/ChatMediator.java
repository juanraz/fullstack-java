package com.dh.chat.mediator;

import com.dh.chat.model.Group;
import com.dh.chat.model.Message;
import com.dh.chat.model.User;

import java.util.List;

/**
 * Created by Juan Zapata on 6/16/2017.
 */
public interface ChatMediator {
    public void signUp(User... users);
    public void signUp(User user);
    public void signIn(User... users);
    public void signIn(String userName, String password);
    public void logOut(User user);
    public boolean loggedUser(User user);
    public boolean findUserNameIn(String userName, List<User> users);
    public User getUserIn(String userName, List<User> users);
    public void messageToUser(User to, Message message);
    public void messageToGroup(Group to, Message message);
    public Group findGroup(String name);
    public Group findGroup(long id);
    public User findUser(String userName);
    public User findUser(long id);
    public void listUsers(String type);
    public void accessGroup(User user, Group group);
    public void accessGroup(User user, Group group,String password);
    public void createGroup(User user, Group group);
}
