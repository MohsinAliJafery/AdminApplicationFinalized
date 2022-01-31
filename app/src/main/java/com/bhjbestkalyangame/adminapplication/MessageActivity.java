package com.bhjbestkalyangame.adminapplication;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.bhjbestkalyangame.adminapplication.Adapter.MessageAdapter;
import com.bhjbestkalyangame.adminapplication.Model.Chat;
import com.bhjbestkalyangame.adminapplication.Model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity {

    CircleImageView ProfileImage;
    TextView Username;

    Intent mIntent;

    Button SendMessage, CopyEmail, CopyToken;
    EditText TypeAMessage;

    MessageAdapter mMessageAdapter;
    List<Chat> mChat;

    RecyclerView mRecyclerView;

    FirebaseUser mFirebaseUser;
    DatabaseReference mDatabaseReference;

    ValueEventListener mSeenListener;
    private String AdminId;
    private FirebaseDatabase mDatabase;
    String mUserID, mUserEmail, mUserToken;
    boolean notify = false;
    private ValueEventListener mChatListner;

    @SuppressLint("WrongViewCast")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Toolbar mToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");

        mChat = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance();
        SendMessage = findViewById(R.id.send_message);
        TypeAMessage = findViewById(R.id.type_a_message);
        CopyToken = findViewById(R.id.copy_token);
        CopyEmail = findViewById(R.id.copy_email);

        ProfileImage = findViewById(R.id.profile_image);
        Username = findViewById(R.id.Username);
        mIntent = getIntent();

        mRecyclerView = findViewById(R.id.recyclerview_message_activity);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLinearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        SharedPreferences sharedpreferences = getSharedPreferences("RealApp", Context.MODE_PRIVATE);

        AdminId = "VYHYRTfHiscUVIKNz3sN4I1LrWn1";
        mUserID = mIntent.getStringExtra("UserId");
        mUserEmail = mIntent.getStringExtra("UserEmail");
        mUserToken = mIntent.getStringExtra("UserToken");

        // Change Id From ADmin to UserID
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("all_users_data").child(mUserID);

        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    User user = snapshot.getValue(User.class);
                    Username.setText(user.getUsername());

                    Glide.with(getApplicationContext()).load(user.getImageUrl()).into(ProfileImage);
                    readMessage(mUserID, AdminId, user.getImageUrl());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        SendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notify = true;
                String typeAMessage = TypeAMessage.getText().toString();
                if(!typeAMessage.equals("")){
                    sendMessage(AdminId, mUserID, typeAMessage);
                }else{
                    Toast.makeText(MessageActivity.this, "You can't send empty messages", Toast.LENGTH_SHORT).show();
                }
                TypeAMessage.setText("");
            }
        });
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        CopyEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CopyEmail.setText("Got it!");
                CopyEmail.setTextColor(getResources().getColor(R.color.colorGoogle));
                CopyEmail.setBackgroundColor(getResources().getColor(R.color.noColor));
                ClipData clip = ClipData.newPlainText("Email", mUserEmail);
                clipboardManager.setPrimaryClip(clip);
                Intent intent= new Intent(Intent.ACTION_SEND);
                String[] recipients={mUserEmail};
                intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                intent.putExtra(Intent.EXTRA_SUBJECT,"Kalyan Matka King - Important!");
                intent.putExtra(Intent.EXTRA_TEXT,"");
                intent.putExtra(Intent.EXTRA_CC,"mailcc@gmail.com");
                intent.setType("text/html");
                intent.setPackage("com.google.android.gm");
                startActivity(Intent.createChooser(intent, "Send mail"));
            }
        });

        CopyToken.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                CopyToken.setTextColor(getResources().getColor(R.color.colorPrimary));
                CopyToken.setBackgroundColor(getResources().getColor(R.color.noColor));
                CopyToken.setText("Got it!");
                ClipData clip = ClipData.newPlainText("Token", mUserToken);
                clipboardManager.setPrimaryClip(clip);

                Intent intent = new Intent(MessageActivity.this, SendNotificationToUsers.class);
                intent.putExtra("Token", mUserToken);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

    }

    private void sendMessage(String Sender, final String Receiver, String Message){

        DatabaseReference mReference = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> mHashmap = new HashMap<>();
        mHashmap.put("sender", Sender);
        mHashmap.put("receiver", Receiver);
        mHashmap.put("message", Message);
        mHashmap.put("seen", false);

        mReference.child("Chats").push().setValue(mHashmap);

        final DatabaseReference ChatReference = FirebaseDatabase.getInstance().getReference("Chatlist")
                .child(mUserID).child(AdminId);

        ChatReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()){
                    ChatReference.child("id").setValue(AdminId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void readMessage(final String MyID, final String AdminId, final String ImageUrl){

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Chats");
        mChatListner = mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    mChat.clear();
                    for (DataSnapshot mSnapshot : snapshot.getChildren()) {
                        Chat chat = mSnapshot.getValue(Chat.class);
                        if (chat.getReceiver().equals(MyID) && chat.getSender().equals(AdminId)
                                || chat.getReceiver().equals(AdminId) && chat.getSender().equals(MyID)) {
                            mChat.add(chat);
                            Log.d("chat", "yes");
                        }

                        DatabaseReference mTimeStampRef = mDatabase.getReference("all_users_data").child(mUserID);
                        mTimeStampRef.child("newMessage").setValue("old");

                        mMessageAdapter = new MessageAdapter(MessageActivity.this, mChat, ImageUrl, AdminId);
                        mRecyclerView.setAdapter(mMessageAdapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mDatabaseReference.removeEventListener(mChatListner);
    }
}
