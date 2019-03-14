package com.route;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.route.Adapters.RoomsAdapter;
import com.route.Base.BaseActivity;
import com.route.FirebaseUtils.MyDataBase;
import com.route.Model.ChatRoom;
import com.route.chatappg1.R;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RoomsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView=findViewById(R.id.recycler_view);
        adapter=new RoomsAdapter(null);
        layoutManager=new LinearLayoutManager(activity);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        adapter.setOnItemClickListener(new RoomsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos, ChatRoom room) {
                ChatThreadActivity.chatRoom=room;
                startActivity(new Intent(activity,ChatThreadActivity.class));
            }
        });

        FloatingActionButton fab =  findViewById(R.id.fab);
        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity,AddRoomActivity.class));
               }
        });


        MyDataBase.getRoomsBranch()
                .addValueEventListener(valueEventListener);
    }

  ValueEventListener valueEventListener=  new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            List<ChatRoom> rooms=new ArrayList<>();
            for (DataSnapshot item :dataSnapshot.getChildren()){
                ChatRoom room=item.getValue(ChatRoom.class);
                rooms.add(room);
            }
            adapter.ChangeData(rooms);

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            showMessage(getString(R.string.error),databaseError.getMessage()
                    ,getString(R.string.ok));
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyDataBase.getMessagesBranch().removeEventListener(valueEventListener);
    }
}
