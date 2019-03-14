package com.route.FirebaseUtils;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.route.Model.User;

/**
 * Created by Mohamed Nabil Mohamed (Nobel) on 2/22/2019.
 * byte code SA
 * m.nabil.fci2015@gmail.com
 */
public class UsersDao {


    public static void InsertUser(User user, OnSuccessListener onSuccessListener
    , OnFailureListener onFailureListener){

        DatabaseReference userNode=MyDataBase.getUsersBranch().push();
        user.setId(userNode.getKey());
        userNode.setValue(user)
        .addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener);
    }
}
