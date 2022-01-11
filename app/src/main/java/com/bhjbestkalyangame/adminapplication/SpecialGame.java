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

public class SpecialGame extends AppCompatActivity {

    EditText Title, Type, NameOfGame;
    Button Go, SpecialGame;
    Switch Display;
    FirebaseDatabase mDatabase;
    DatabaseReference mReference, SpecialGameInfoReference;
    String mFrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_game);

        Title = findViewById(R.id.title);
        NameOfGame = findViewById(R.id.name_of_game);
        Type = findViewById(R.id.type);
        Go = findViewById(R.id.go);
        SpecialGame = findViewById(R.id.special_game);
        Display = findViewById(R.id.display);

        Intent PrevIntent = getIntent();
        mFrom = PrevIntent.getStringExtra("mFrom");

        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("special_game");

        Go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = Title.getText().toString();
                String type = Type.getText().toString();
                String name = NameOfGame.getText().toString();

                String display;

                if(Display.isChecked()){
                    display = "1";
                }else{
                    display = "0";
                }

                HashMap<String, String > hashMap = new HashMap<>();
                hashMap.put("title", title);
                hashMap.put("type", type);
                hashMap.put("display", display);
                hashMap.put("name", name);


                mReference.child("info").setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(SpecialGame.this, "Updated!", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

        SpecialGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SpecialGame.this, KalyanPicker.class);
                intent.putExtra("mFrom", "special_game");
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        SpecialGameInfoReference = mDatabase.getReference("special_game").child("info");
        SpecialGameInfoReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                SpecialGameInfo specialGameInfo = snapshot.getValue(SpecialGameInfo.class);
                String SpecialGameTitle = specialGameInfo.getTitle();
                String SpecialGameSubTitle = specialGameInfo.getType();
                String SpecialGameVisibility = specialGameInfo.getDisplay();
                String GameName = specialGameInfo.getName();

                if(SpecialGameVisibility.equals("1")){
                    Display.setChecked(true);
                }else{
                    Display.setChecked(false);
                }

                Title.setText(SpecialGameTitle);
                Type.setText(SpecialGameSubTitle);
                NameOfGame.setHint(GameName);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}