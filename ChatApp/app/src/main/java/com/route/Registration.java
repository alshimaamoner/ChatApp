package com.route;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.route.Base.BaseActivity;
import com.route.FirebaseUtils.UsersDao;
import com.route.Model.User;
import com.route.chatappg1.R;

public class Registration extends BaseActivity {

    protected TextInputLayout username;
    protected TextInputLayout email;
    protected TextInputLayout password;
    protected Button register;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initView();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sName= username.getEditText().getText().toString();
                String sEmail = email.getEditText().getText().toString();
                String spassword=password.getEditText().getText().toString();

                user=new User();
                user.setEmail(sEmail);
                user.setPassword(spassword);
                user.setUsername(sName);

                showProgressBar(R.string.loading);
                UsersDao.InsertUser(user, onSuccessListener,onFailureListener );

            }
        });


    }

    OnSuccessListener onSuccessListener=new OnSuccessListener() {
        @Override
        public void onSuccess(Object o) {
            hideProgressBar();
            DataHolder.currentUser=user;
            showConfirmationMessage(R.string.success, R.string.User_Registered_successfully, R.string.ok
                    , new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            startActivity(new Intent(activity,HomeActivity.class));
                            finish();
                            //Save User Credentials
                        }
                    })

            ;
        }
    };
    OnFailureListener onFailureListener=new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            hideProgressBar();
            showMessage(getString(R.string.error),e.getMessage(),getString(R.string.ok));

        }
    };
    private void initView() {
        username = (TextInputLayout) findViewById(R.id.user_ame);
        email = (TextInputLayout) findViewById(R.id.email);
        password = (TextInputLayout) findViewById(R.id.password);
        register = (Button) findViewById(R.id.register);
    }
}
