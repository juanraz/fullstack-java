package com.dh.chat;

import com.dh.chat.model.Group;
import com.dh.chat.model.User;

/**
 * Created by Juan Zapata on 6/18/2017.
 */
public final class Messages {

    public static String NOT_FOUND = " not found.";
    public static String HAS_BEEN_FOUND = " has been found.";
    public static String NO_LOGIN_NO_MESSAGE = "Sorry you are not logged in. Message was not sent.";
    public static String USER_PASSWORD_DOES_NOT_MATCH = "Error: user password do not match. Try again!";

    public static String USER_IS_NOT_REGISTERED(String userName){
        return     "Warning!: "+userName+" is not registered."
                ;
    }


    public static String USER_JUST_LOGGED_IN(User user){
        return user+" just logged in.";
    }

    public static String GROUP_DOES_NOT_EXISTS(Group g){
        return "Group "+g+" does not exists.";
    }
    public static String USER_DOES_NOT_BELONG_TO(String u, Group g){
        return u+" does not belong to "+g+", please join first.";
    }

    public static String USER_CANT_SEND_MESSAGE_TO_PRIVATE_GROUP(User u){
        return "Error: "+u+" is not able to sent messaged to private groups.";
    }

    public static String NO_USER_NO_MESSAGE(User to){
        return to+" was not found, message was not sent.";
    }

    public static String USER_NOT_LOGGED_IN(User user){
        return user+ " is not logged in.";
    }

    public static String USER_HAS_LEFT(User u){
        return u+" has left.";
    }

}
