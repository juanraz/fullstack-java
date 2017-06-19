package com.dh.chat.mediator;

import com.dh.chat.Messages;
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
            System.out.println(Messages.USER_IS_NOT_REGISTERED(userName));
        }else{
            if(password.equals(userR.getPassword())){
                chat.getLoggedUsers().add(userR);
                System.out.println(Messages.USER_JUST_LOGGED_IN(userR));

            }else{
                System.out.println(Messages.USER_PASSWORD_DOES_NOT_MATCH);
            }
        }
    }

    @Override
    public void logOut(User user) {
        List<User> lUsers = chat.getLoggedUsers();

        User u = getUserIn(user.getUserName(),lUsers);
        if(u!=null){
            lUsers.remove(u);

            System.out.println(Messages.USER_HAS_LEFT(u));
        }else{
            System.out.println(Messages.USER_NOT_LOGGED_IN(user));

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
            System.out.println(Messages.NO_LOGIN_NO_MESSAGE);
        }else{
            if(fUser!=null){
                System.out.println("FROM "+message.getUserName()+" TO "+to+": "+message.getContent());
            }else{
                System.out.println(Messages.NO_USER_NO_MESSAGE(to));
            }
        }
    }

    @Override
    public void messageToGroup(Group to, Message message) {
        Group fGroup = findGroup(to.getName());
        User u = findUser(message.getUserName());
        if(u.getPremium()||(!u.getPremium()&& fGroup.getPrivate())){
            System.out.println(Messages.USER_CANT_SEND_MESSAGE_TO_PRIVATE_GROUP(u));
        }else{
            if(fGroup!=null){
                if(!findUserNameIn(message.getUserName(),fGroup.getUsers())){
                    System.out.println(Messages.USER_DOES_NOT_BELONG_TO(message.getUserName(), to));
                }else{
                    System.out.println("FROM: "+getUserIn(message.getUserName(),chat.registeredUsers)+" TO: " +fGroup+" "+message.getContent());
                }
            }else{
                System.out.println(Messages.GROUP_DOES_NOT_EXISTS(to));
            }
        }
    }

    @Override
    public Group findGroup(User user, String name){
        Group fGroup = findGroup(name);

        if(fGroup!=null){
            if(fGroup.getPrivate()&&!user.getPremium()){
                if(!fGroup.getVisible()){
                    System.out.println(name+ Messages.NOT_FOUND);
                    return null;
                }else{
                    System.out.println(fGroup+Messages.HAS_BEEN_FOUND);
                }
            }else {
                System.out.println(fGroup+Messages.HAS_BEEN_FOUND);
            }
        }else{
            System.out.println(name+ Messages.NOT_FOUND);
        }

        return fGroup;
    }

    private Group findGroup(String name) {
        List<Group> groups = this.chat.getGroups();
        for(Group g: groups){
            if(g.getName()==name){
                return g;
            }
        }
        return null;
    }

    @Override
    public Group findGroup(User user, long id){
        Group fGroup = findGroup(id);
        if(fGroup==null){
            System.out.println("Group not found.");
        }else{
            if(fGroup.getPrivate()&&!user.getPremium()){
                System.out.println("Group not found.");
                return null;
            }else if(fGroup!=null){
                System.out.println(fGroup+" has been found");
            }else{
                System.out.println("Group with id: "+id+" not found");
            }
        }
        return fGroup;
    }

    public Group findGroup(long id) {
        List<Group> rGroups = this.chat.getGroups();
        for(Group g : rGroups){
            if(id==g.getId()){
                return g;
            }
        }
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
        List<User> us = chat.getRegisteredUsers();
        int i = 0;
        boolean isPremiumFilter = (type.equals("Premium"));
        for(User u:us){
            if(u.getPremium()==isPremiumFilter){
                System.out.println(u);
                i++;
            }
        }
        System.out.println(type+ " users found: "+i);
    }

    @Override
    public void listGroups(User user) {
        List<Group> gs = chat.getGroups();
        int i = 0;
        for(Group g:gs){
            if(g.getPrivate()&&user.getPremium()){
                System.out.println(g);
                i++;
            }else if(!g.getPrivate()){
                System.out.println(g);
                i++;
            }
        }
        System.out.println("Groups found: "+i);

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
    public void setGroupVisibility(User user, Group group,boolean visibility) {
        if(!group.getPrivate()){
            System.out.println("Public groups are always visible");
        }else{
            if(group.getAdmin().getUserName().equals(user.getUserName())){
                group.setVisible(visibility);
                System.out.println("Group is now "+(visibility?"visible.":"invisible."));
            }else{
                System.out.println(user+" is not "+ group+" owner.");
            }
        }
    }

    @Override
    public void accessGroup(User user, Group group, String password) {
        Group fGroup = findGroup(group.getName());
        if(!user.getPremium()){
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
        }else{
            System.out.println(user+". You are not premium user, please upgrade to access private groups.");
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
