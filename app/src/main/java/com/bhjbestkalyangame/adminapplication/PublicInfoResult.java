package com.bhjbestkalyangame.adminapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PublicInfoResult extends AppCompatActivity {

    EditText Kalyan, KalyanNight;
    Button Send;
    String SKalyan, SKalyanNight;
    
    FirebaseDatabase mDatabase;
    DatabaseReference mReference;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_info_result);
        
        Kalyan = findViewById(R.id.kalyan);
        KalyanNight = findViewById(R.id.kalyan_night);
        Send = findViewById(R.id.send_kalyan_result);
        
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("kalyan");

        
        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SKalyan = Kalyan.getText().toString();
                SKalyanNight = KalyanNight.getText().toString();
                
                mReference.child("kalyan_result").setValue(SKalyan).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(PublicInfoResult.this, "Kalyan Result Updated!", Toast.LENGTH_SHORT).show();
                    }
                });
                
                mReference.child("kalyan_night_result").setValue(SKalyanNight).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(PublicInfoResult.this, "Kalyan Night Updated!", Toast.LENGTH_SHORT).show();
                    }
                });
                
            }
        });
        
    }
}