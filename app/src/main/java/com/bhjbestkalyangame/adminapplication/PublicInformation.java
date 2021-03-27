package com.bhjbestkalyangame.adminapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PublicInformation extends AppCompatActivity {

    EditText PublicInfo;
    Button SendBroadcast;
    FirebaseDatabase mDatabase;
    DatabaseReference mReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_information);

        PublicInfo = findViewById(R.id.public_info_message);
        SendBroadcast = findViewById(R.id.send_broadcast_message);

        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("public_information");

        SendBroadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String message = PublicInfo.getText().toString();
                mReference.child("message").setValue(message).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(PublicInformation.this, "Broadcast Successful!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(PublicInformation.this, KalyanMatkaInterface.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
    }
}