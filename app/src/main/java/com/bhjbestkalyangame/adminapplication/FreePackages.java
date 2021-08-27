package com.bhjbestkalyangame.adminapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class FreePackages extends AppCompatActivity {

    Button Go;
    Switch SpecialGameFreeWithSub, KalyanMatkaFreeOrPaid;
    FirebaseDatabase mDatabase;
    DatabaseReference mReference, SpecialGameFreeWithSubRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_packages);

        Go = findViewById(R.id.go);
        SpecialGameFreeWithSub = findViewById(R.id.special_game_free_with_subscription);
        KalyanMatkaFreeOrPaid = findViewById(R.id.kalyan_matka_paid_or_free);

        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("free");
        SpecialGameFreeWithSubRef = mDatabase.getReference("free_things_with_subscription");

        Go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer specialGameFree, kalyanMatkaFree;

                if (SpecialGameFreeWithSub.isChecked()) {
                    specialGameFree = 1;
                } else {
                    specialGameFree = 0;
                }

                if (KalyanMatkaFreeOrPaid.isChecked()) {
                    kalyanMatkaFree = 1;
                } else {
                    kalyanMatkaFree = 0;
                }


                mReference.child("kalyan_matka").setValue(kalyanMatkaFree).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(FreePackages.this, "Updated!", Toast.LENGTH_SHORT).show();
                    }
                });

                SpecialGameFreeWithSubRef.child("special_game").setValue(specialGameFree).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(FreePackages.this, "Updated!", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        mReference.child("kalyan_matka").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

               Integer kalyan_matka = snapshot.getValue(Integer.class);

                if (kalyan_matka == 1) {
                    KalyanMatkaFreeOrPaid.setChecked(true);
                } else {
                    KalyanMatkaFreeOrPaid.setChecked(false);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        SpecialGameFreeWithSubRef.child("special_game").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Integer special_game = snapshot.getValue(Integer.class);

                if (special_game == 1) {
                    SpecialGameFreeWithSub.setChecked(true);
                } else {
                    SpecialGameFreeWithSub.setChecked(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}
