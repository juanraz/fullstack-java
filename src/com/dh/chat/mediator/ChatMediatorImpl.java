package com.dh.chat.mediator;

import com.dh.chat.model.Group;
import com.dh.chat.model.Message;
import com.dh.chat.model.User;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juan Zapata on 6/16/2017.
 */
public class ChatMediatorImpl implements ChatMediator
{
    Chat chat;

    public ChatMediatorImpl() {
        this.chat = new Chat();
    }

    @Override
    public void createGroup(User user, Group group) {
        if(!group.getPrivate()
        ||(group.getPrivate()&&user.getPremium())){
                chat.getGroups().add(group);
                System.out.println(group+ "has been created by:\n\t"+user);
                for(User u: group.getUsers()){
                    if(!u.getUserName().equals(user.getUserName())){
                        System.out.println("\t"+ u +" has been added to "+group);
                    }
                }
        }else{
            System.out.println(user+" is not premium user, it is not able to create "+group);
        }
    }

    @Override
    public void signUp(User... users) {
        for(User user:users){
            this.signUp(user);
        }
    }

    @Override
    public void signIn(User... users) {
        for(User user:users){
            this.signIn(user.getUserName(),user.getPassword());
        }
    }

    @Override
    public User getUserIn(String userName, List<User> users) {
        for(User u : users){
            if(u.getUserName().equals(userName)){
                return u;
            }
        }
        return null;
    }

    @Override
    public boolean findUserNameIn(String userName, List<User> users) {
        for(User u : users){
            if(u.getUserName().equals(userName)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void signUp(User user) {

        if(!user.getUserName().isEmpty()
                &&!user.getPassword().isEmpty()){

            List<User> rUsers = this.chat.getRegisteredUsers();
            if(findUserNameIn(user.getUserName(),rUsers)){
                System.out.println("Error: " + user.getUserName()+" already exists. "+user+" has not been registered." );
            }else{
                rUsers.add(user);
                this.chat.setRegisteredUsers(rUsers);
                System.out.println(user+" has been registered");

            }
        }else{
            System.out.println("User name and password are required.");
        }
    }

    @Override
    public void signIn(String userName, String password) {

        User userR = this.findUser(userName);

        if(chat.getRegisteredUsers().size()<1||userR==null){
            System.out.println("Warning!: "+userName+" is not registered.");
        }else{
            if(password.equals(userR.getPassword())){
                chat.getLoggedUsers().add(userR);
                System.out.println(userR+" just logged in.");
            }else{
                System.out.println("Error: user password do not match. Try again!");
            }
        }
    }

    @Override
    public void logOut(User user) {
        List<User> lUsers = chat.getLoggedUsers();

        User u = getUserIn(user.getUserName(),lUsers);
        if(u!=null){
            lUsers.remove(u);
            System.out.println(u+" has left.");
        }else{
            System.out.println(user+ " is not logged in.");
        }
    }

    @Override
    public boolean loggedUser(User user) {
        List<User> lUsers = chat.getLoggedUsers();
        for(User u: lUsers){
            if(u.getUserName().equals(user.getUserName())){
                return true;
            }
        }
        return false;
    }

    @Override
    public void messageToUser(User to, Message message) {
        User fUser = findUser(to.getUserName());

        if(!findUserNameIn(message.getUserName(),chat.getLoggedUsers())){
            System.out.println("Sorry you are not logged in. Message was not sent.");
        }else{
            if(fUser!=null){
                System.out.println("FROM "+message.getUserName()+" TO "+to+": "+message.getContent());
            }else{
                System.out.println(to+" was not found, message was not sent.");
            }
        }
    }

    @Override
    public void messageToGroup(Group to, Message message) {
        Group fGroup = findGroup(to.getName());

        if(fGroup!=null){
            if(!findUserNameIn(message.getUserName(),fGroup.getUsers())){
                System.out.println(message.getUserName()+" does not belong to "+to+", please join first.");
            }else{
                System.out.println("FROM: "+getUserIn(message.getUserName(),chat.registeredUsers)+" TO: " +fGroup+" "+message.getContent());
            }
        }else{
            System.out.println("Group "+to+" does not exists.");
        }

    }

    @Override
    public Group findGroup(String name) {
        List<Group> groups = this.chat.getGroups();
        for(Group g: groups){
            if(g.getName()==name){
                return g;
            }
        }
        return null;
    }

    @Override
    public Group findGroup(long id) {
        return null;
    }

    @Override
    public User findUser(String userName) {
        List<User> registeredUsers = this.chat.getRegisteredUsers();
        User fUser = getUserIn(userName,registeredUsers);
        if(fUser==null){
            System.out.println(userName +" not registered.");
        }
        return fUser;
    }

    @Override
    public User findUser(long id) {

        List<User> registeredUsers = this.chat.getRegisteredUsers();

        for(User user : registeredUsers){
            if(user.getId()==id){
                return user;
            }
        }
        return null;
    }

    @Override
    public void listUsers(String type) {

    }

    @Override
    public void accessGroup(User user, Group group) {
        Group fGroup = findGroup(group.getName());
        if(fGroup.getPrivate()){
            System.out.println("Group is private, please provide the access password.");
        }else{
            fGroup.getUsers().add(user);
            System.out.println(user+" just joined to "+group);
        }
    }

    @Override
    public void accessGroup(User user, Group group, String password) {
        Group fGroup = findGroup(group.getName());
        if(!group.getPrivate()){
            chat.getGroups().add(group);
            System.out.println(user+" just joined to "+group);
        }else{
            if(group.getPassword().equals(password)){
                fGroup.getUsers().add(user);
                System.out.println(user+" just joined to "+group);
            }else{
                System.out.println("Wrong password for "+group+" please try again.");
            }
        }
    }

    private class Chat{
        private List<Group> groups;
        private List<User>  registeredUsers;
        private List<User> loggedUsers;

        public Chat() {
            this.groups             = new ArrayList<Group>();
            this.registeredUsers    = new ArrayList<User>();
            this.loggedUsers        = new ArrayList<User>();
        }

        public List<Group> getGroups() {
            return groups;
        }

        public void setGroups(List<Group> groups) {
            this.groups = groups;
        }

        public List<User> getRegisteredUsers() {
            return registeredUsers;
        }

        public void setRegisteredUsers(List<User> registeredUsers) {
            this.registeredUsers = registeredUsers;
        }

        public List<User> getLoggedUsers() {
            return loggedUsers;
        }

        public void setLoggedUsers(List<User> loggedUsers) {
            this.loggedUsers = loggedUsers;
        }
    }
}
