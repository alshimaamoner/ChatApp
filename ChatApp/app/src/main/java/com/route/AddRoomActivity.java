package com.route;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.route.Base.BaseActivity;
import com.route.FirebaseUtils.ChatRoomsDao;
import com.route.Model.ChatRoom;
import com.route.chatappg1.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddRoomActivity extends BaseActivity implements View.OnClickListener {

    protected TextInputLayout roomName;
    protected TextInputLayout desc;
    protected Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_add_room);
        initView();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.add) {
            String name=roomName.getEditText().getText().toString();
            String details=desc.getEditText().getText().toString();
            SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            String currentTimeStamp = s.format(new Date());
            ChatRoom chatRoom=new ChatRoom(name,details,currentTimeStamp);
            ChatRoomsDao
                    .AddRoom(chatRoom,onAddSuccess,onAddFailure);

        }
    }


    OnSuccessListener onAddSuccess=new OnSuccessListener() {
        @Override
        public void onSuccess(Object o) {
            hideProgressBar();
            showConfirmationMessage(R.string.success,
                    R.string.room_added,
                    R.string.ok
                    , new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                           // startActivity(new Intent(activity,HomeActivity.class));
                            finish();
                            //Save User Credentials
                        }
                    })

            ;

        }
    };

    OnFailureListener onAddFailure=new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            hideProgressBar();
            showMessage(getString(R.string.error),e.getMessage(),getString(R.string.ok));

        }
    };

    private void initView() {
        roomName = (TextInputLayout) findViewById(R.id.roomName);
        desc = (TextInputLayout) findViewById(R.id.desc);
        add = (Button) findViewById(R.id.add);
        add.setOnClickListener(AddRoomActivity.this);
    }
}
