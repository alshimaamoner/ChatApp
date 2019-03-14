package com.route.FirebaseUtils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Mohamed Nabil Mohamed (Nobel) on 2/22/2019.
 * byte code SA
 * m.nabil.fci2015@gmail.com
 */
public class MyDataBase {

    private static FirebaseDatabase firebaseDatabase;

    public static FirebaseDatabase getInstance(){
        if(firebaseDatabase==null)
            firebaseDatabase= FirebaseDatabase.getInstance();
        return firebaseDatabase;
    }

    final static String Users="users";
    public static DatabaseReference getUsersBranch(){
        return getInstance().getReference(Users);
    }

    final static String Rooms="rooms";
    public static DatabaseReference getRoomsBranch(){
        return getInstance().getReference(Rooms);
    }

    final static String Messages="messages";
    public static DatabaseReference getMessagesBranch(){
        return getInstance().getReference(Messages);
    }



}
