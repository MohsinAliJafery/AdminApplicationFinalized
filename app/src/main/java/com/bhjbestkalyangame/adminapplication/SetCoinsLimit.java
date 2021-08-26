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

public class SetCoinsLimit extends AppCompatActivity {

    Button Approve;
    EditText CoinsLimit;
    FirebaseDatabase mDatabase;
    DatabaseReference mReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_coins_limit);

        CoinsLimit = findViewById(R.id.coin_limit);
        Approve = findViewById(R.id.approve);
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("coins");

        Approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int Coins = Integer.parseInt(CoinsLimit.getText().toString());

                mReference.child("limit").setValue(Coins).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(SetCoinsLimit.this, "Successfully Updated", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SetCoinsLimit.this, KalyanMatkaKing.class);
                        startActivity(intent);
                        finish();
                    }
                });

            }
        });

    }
}