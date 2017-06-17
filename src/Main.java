import com.dh.chat.mediator.ChatMediator;
import com.dh.chat.mediator.ChatMediatorImpl;
import com.dh.chat.model.Group;
import com.dh.chat.model.Message;
import com.dh.chat.model.User;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Juan Zapata on 5/25/2017.
 */
public class Main {
    public static void main(String[] args) {
        ChatMediator chat = new ChatMediatorImpl();

        User u1 = new User(1, "Juan","Zapata","jzapatamercado@gmail.com"
                ,new Date("30/03/1981"),"jzapata","password",true);
        User u2 = new User(2, "Mario","Zapata","mzapata@gmail.com"
                ,new Date("01/03/1979"),"mzapata","password",false);
        User u3 = new User(3, "Cristina","Mercado","cristina.mercado@gmail.com"
                ,new Date("12/12/1974"),"cmercado","password",false);
        User u4 = new User(4, "Maria","Mercado","maria.mercado@gmail.com"
                ,new Date("23/08/1976"),"mmercado","password",false);
        User u5 = new User(5, "Juan","Repeated","juan.again@gmail.com"
                ,new Date("23/08/1976"),"jzapata","password",false);
        User u6 = new User(6, "Ramon","Mercado","ramon@gmail.com"
                ,new Date("23/08/1976"),"rzapata","password",false);

        Group gPrivate = new Group(1, "#FullStack","FS123",u1,true,
                new ArrayList<User>(){{add(u1);add(u2);}});

        Group gPublic = new Group(2, "#General",null ,u1,false,
                new ArrayList<User>(){{add(u3);add(u5);}});

        //log in with no registered user.
        chat.signIn(u1);

        //register multiple users
        chat.signUp(u1, u2, u3,u5);

        //login users
        chat.signIn(u1,u2);

        //send message to not registered user
        chat.messageToUser(u4,new Message(1,u1.getUserName(),"Hi, are you there?"));

        //message to existent user
        chat.messageToUser(u2,new Message(1,u1.getUserName(),"Hi, are you there?"));
        chat.messageToUser(u1,new Message(1,u2.getUserName(),"Hey, yes I'm here, I want to create a private group #FullStack"));

        //create a private group without privileges.
        chat.createGroup(u2, new Group(1, "#FullStack","FS123",u2,true,null));

        chat.messageToUser(u1,new Message(1,u2.getUserName(),"I'm not able to create the group as private"));
        chat.messageToUser(u2,new Message(1,u1.getUserName(),"Don't worry I can do it"));

        //create a private group, adding people
        chat.createGroup(u1, gPrivate);

        chat.signUp(u4);

        //message without to login
        chat.messageToUser(u1,new Message(1,u4.getUserName(),"Hey I just got registered"));
        chat.signIn(u4);
        chat.messageToUser(u1,new Message(1,u4.getUserName(),"Hey I just get registered, sorry I was trying to send messages without to login"));
        chat.messageToUser(u4,new Message(1,u1.getUserName(),"Join us to #FullStack group"));

        //join to group without proper password
        chat.accessGroup(u4,gPrivate);

        //create a public group.
        chat.createGroup(u4, gPublic);

        //try to join to different group,
        chat.accessGroup(u4, gPublic);
        chat.messageToUser(u1,new Message(1,u4.getUserName(),"Juan, I can't access #FullStack, it is requesting me some password, but I just created #General"));
        chat.messageToUser(u4,new Message(1,u1.getUserName(),"Sorry I forgot to tell you password is: FS123"));
        chat.messageToUser(u1,new Message(1,u4.getUserName(),"Oh ok!, Let me try"));
        //Access with wrong password
        chat.accessGroup(u4, gPrivate,"ES123");
        //Access with proper password
        chat.accessGroup(u4, gPrivate,"FS123");
        chat.messageToGroup(gPrivate,new Message(1,u4.getUserName(),"Finally I got connected to #FullStack"));
        chat.messageToGroup(gPrivate,new Message(1,u2.getUserName(),"Hey welcome Maria!"));

        chat.signUp(u6);
        chat.signIn(u6);
        //trying to send messages to group were user does not belong.
        chat.messageToGroup(gPrivate,new Message(1,u6.getUserName(),"Hello?"));
        chat.messageToGroup(gPublic,new Message(1,u6.getUserName(),"Hello?"));
        //access public group
        chat.accessGroup(u6, gPublic);
        chat.messageToGroup(gPublic,new Message(1,u6.getUserName(),"Hello?, i think it should be working now."));





    }
}

