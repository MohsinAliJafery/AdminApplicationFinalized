package com.bhjbestkalyangame.adminapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bhjbestkalyangame.adminapplication.Service.FcmNotificationsSender;
import com.google.firebase.database.core.Context;
import com.google.firebase.messaging.FirebaseMessaging;

public class SendNotificationToUsers extends AppCompatActivity {

    private EditText Title, Description, UserToken;
    private Button SendToUser, Broadcast;
    private String mUserToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notification_to_users);

        Title = findViewById(R.id.title);
        Description = findViewById(R.id.description);
        UserToken = findViewById(R.id.user_token);
        Broadcast = findViewById(R.id.broadcast);
        SendToUser = findViewById(R.id.send_to_user);

        Intent intent = getIntent();
        mUserToken = intent.getStringExtra("Token");
        if(!mUserToken.equals("")){
            UserToken.setText(mUserToken);
            UserToken.setTextColor(getResources().getColor(R.color.colorRed));
            UserToken.setEnabled(false);
            Broadcast.setVisibility(View.GONE);
            SendToUser.setVisibility(View.VISIBLE);
            UserToken.setVisibility(View.VISIBLE);
            Description.setText("You have a new message.");
        }

        FirebaseMessaging.getInstance().subscribeToTopic("all");

        Broadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Broadcast.setEnabled(false);
                if(!Title.getText().toString().isEmpty() && !Description.getText().toString().isEmpty()) {
                    FcmNotificationsSender mFcmNotificationSender = new FcmNotificationsSender("/topics/all",
                            Title.getText().toString(), Description.getText().toString(), getApplicationContext(), SendNotificationToUsers.this);

                    mFcmNotificationSender.SendNotifications();
                    Toast.makeText(SendNotificationToUsers.this, "Broadcast Activated...", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(SendNotificationToUsers.this, "Fields must not be empty.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        SendToUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Title.getText().toString().isEmpty() && !Description.getText().toString().isEmpty() && !SendToUser.getText().toString().isEmpty()) {
                    FcmNotificationsSender mFcmNotificationSender = new FcmNotificationsSender(UserToken.getText().toString(),
                            Title.getText().toString(), Description.getText().toString(), getApplicationContext(), SendNotificationToUsers.this);

                    mFcmNotificationSender.SendNotifications();
                    Toast.makeText(SendNotificationToUsers.this, "Lone Notification Provoked...", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(SendNotificationToUsers.this, "Fields must not be empty.", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}