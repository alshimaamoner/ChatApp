package com.route.FirebaseUtils;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.route.Model.ChatRoom;

/**
 * Created by Mohamed Nabil Mohamed (Nobel) on 2/22/2019.
 * byte code SA
 * m.nabil.fci2015@gmail.com
 */
public class ChatRoomsDao {

    public static void AddRoom(ChatRoom chatRoom,
                               OnSuccessListener onSuccessListener,
                               OnFailureListener onFailureListener){
        DatabaseReference roomNode=MyDataBase.getRoomsBranch()
                .push();
        chatRoom.setId(roomNode.getKey());

        roomNode.setValue(chatRoom)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }
}
