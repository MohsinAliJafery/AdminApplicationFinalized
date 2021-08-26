package com.bhjbestkalyangame.adminapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class KalyanMatkaKing extends AppCompatActivity {
    
    private Button PublicInfo, KalyanMatka, KalyanNight, CurrentApp, SetCoins, SucessStories, SpecialGame, FreePackages;
    private Button Rajdhani;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalyan_matka_king);

        PublicInfo = findViewById(R.id.public_info);
        KalyanMatka = findViewById(R.id.kalyan_matka);
        KalyanNight = findViewById(R.id.kalyan_night);
        CurrentApp = findViewById(R.id.current_app);
        SetCoins = findViewById(R.id.set_coins);
        SucessStories = findViewById(R.id.success_stories);
        SpecialGame = findViewById(R.id.special_game);
        Rajdhani = findViewById(R.id.rajdhani);
        FreePackages = findViewById(R.id.free_packages);

        KalyanMatka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KalyanMatkaKing.this, KalyanMatkaGame.class);
                intent.putExtra("mFrom", "KalyanMatka");
                startActivity(intent);
            }
        });

        KalyanNight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KalyanMatkaKing.this, KalyanNightGame.class);
                intent.putExtra("mFrom", "KalyanNight");
                startActivity(intent);
            }
        });

        Rajdhani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KalyanMatkaKing.this, KalyanPicker.class);
                intent.putExtra("mFrom", "Rajdhani Night");
                startActivity(intent);
            }
        });

        CurrentApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KalyanMatkaKing.this, KalyanMatkaInterface.class);
                intent.putExtra("mFrom", "CurrentApp");
                startActivity(intent);
            }
        });

        SetCoins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KalyanMatkaKing.this, SetCoinsLimit.class);
                intent.putExtra("mFrom", "CurrentApp");
                startActivity(intent);
            }
        });

        PublicInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KalyanMatkaKing.this, PublicInformation.class);
                intent.putExtra("mFrom", "public_info");
                startActivity(intent);
            }
        });


        SpecialGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KalyanMatkaKing.this, SpecialGame.class);
                intent.putExtra("mFrom", "special_game");
                startActivity(intent);
            }
        });

        FreePackages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KalyanMatkaKing.this, FreePackages.class);
                startActivity(intent);
            }
        });

        SucessStories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KalyanMatkaKing.this, SuccessStory.class);
                startActivity(intent);
            }
        });



    }
}