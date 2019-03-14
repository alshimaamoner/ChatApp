package com.route.FirebaseUtils;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.route.Model.Message;

/**
 * Created by Mohamed Nabil Mohamed (Nobel) on 2/22/2019.
 * byte code SA
 * m.nabil.fci2015@gmail.com
 */
public class MessagesDao {

    public static void AddMessage(Message message, OnSuccessListener onSuccessListener,
                                  OnFailureListener onFailureListener){
        DatabaseReference messageNode=MyDataBase.getMessagesBranch()
                .push();
        message.setId(messageNode.getKey());
        messageNode.setValue(message)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);

    }


    public static Query getMessagesByRoomId(String roomId){
        return MyDataBase.getMessagesBranch()
                .orderByChild("roomId")
                .equalTo(roomId);
    }
}
