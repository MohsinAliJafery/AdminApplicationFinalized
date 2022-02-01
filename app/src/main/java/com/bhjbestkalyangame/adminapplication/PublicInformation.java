package com.bhjbestkalyangame.adminapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PublicInformation extends AppCompatActivity {

    EditText PublicInfo;
    Button SendBroadcast, ClearChat;
    FirebaseDatabase mDatabase;
    DatabaseReference mReference;
    ConstraintLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_information);

        PublicInfo = findViewById(R.id.public_info_message);
        SendBroadcast = findViewById(R.id.send_broadcast_message);
        ClearChat = findViewById(R.id.clear_chat);
        mLayout = findViewById(R.id.public_information);
        mDatabase = FirebaseDatabase.getInstance();

        ClearChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(mLayout, "Do you really want to clear all Chat?", Snackbar.LENGTH_LONG)
                        .setTextColor(getResources().getColor(R.color.noColor))
                        .setBackgroundTint(getResources().getColor(R.color.colorPrimaryDark))
                        .setAction("Yes", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DatabaseReference mRef = mDatabase.getReference("all_users_data");
                                mRef.removeValue();
                                mRef = mDatabase.getReference("Chats");
                                mRef.removeValue();
                                mRef = mDatabase.getReference("Chatlist");
                                mRef.removeValue();
                                Toast.makeText(PublicInformation.this, "Deletion Successful", Toast.LENGTH_SHORT).show();
                            }

                        })
                        .setActionTextColor(getResources().getColor(R.color.noColor))
                        .show();

            }
        });

        mReference = mDatabase.getReference("public_information");

        mReference.child("message").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String message = snapshot.getValue(String.class);
                PublicInfo.setText(message);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        SendBroadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String message = PublicInfo.getText().toString();
                mReference.child("message").setValue(message).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(PublicInformation.this, "Successfully updated...", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}