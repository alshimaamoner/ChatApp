package com.route;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.route.Adapters.ChatThreadAdapter;
import com.route.Base.BaseActivity;
import com.route.FirebaseUtils.MessagesDao;
import com.route.Model.ChatRoom;
import com.route.Model.Message;
import com.route.chatappg1.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatThreadActivity extends BaseActivity implements View.OnClickListener {

    public static ChatRoom chatRoom;
    protected RecyclerView recyclerView;
    protected EditText message;
    protected ImageButton send;

    ChatThreadAdapter adapter;
    LinearLayoutManager layoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_chat_thread);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initView();
        recyclerView=findViewById(R.id.recycler_view);
        layoutManager=new LinearLayoutManager(activity);
        layoutManager.setStackFromEnd(true);
        adapter=new ChatThreadAdapter(null);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        MessagesDao.getMessagesByRoomId(chatRoom.getId())
                .addChildEventListener(messagesEventListner);

    }
   ChildEventListener messagesEventListner= new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot,
                @Nullable String s) {

            Message message =dataSnapshot.getValue(Message.class);
            if(message!=null)
                Log.e("message",message.getContent());

            adapter.AddMessage(message);

        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MessagesDao.getMessagesByRoomId(chatRoom.getId())
                .removeEventListener(messagesEventListner);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.send) {
            String content= message.getText().toString();
            Message message=new Message();
            message.setContent(content);
            message.setRoomId(chatRoom.getId());
            message.setSenderId(DataHolder.currentUser.getId());
            message.setSenderName(DataHolder.currentUser.getUsername());
            SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            String currentTimeStamp = s.format(new Date());
            message.setTimeStamp(currentTimeStamp);
            MessagesDao.AddMessage(message,onSuccessListener,onFailureListener);

        }
    }

    OnSuccessListener onSuccessListener=new OnSuccessListener() {
        @Override
        public void onSuccess(Object o) {
            message.setText(null);
        }
    };
    OnFailureListener onFailureListener=new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Toast.makeText(activity, R.string.try_again_later, Toast.LENGTH_SHORT).show();
        }
    };

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        message = (EditText) findViewById(R.id.message);
        send = (ImageButton) findViewById(R.id.send);
        send.setOnClickListener(ChatThreadActivity.this);
    }
}
