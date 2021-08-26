package com.bhjbestkalyangame.adminapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class KalyanNightGame extends AppCompatActivity {

    private Button SingleOpenNight, SingleCloseNight, JodiNight, PanelNight;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalyan_night_game);

        SingleOpenNight = findViewById(R.id.single_open_night);
        SingleCloseNight = findViewById(R.id.single_close_night);
        JodiNight = findViewById(R.id.jodi_night);
        PanelNight = findViewById(R.id.panel_night);

        SingleOpenNight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(KalyanNightGame.this, KalyanPicker.class);
                intent.putExtra("mFrom", "SingleOpenNight");
                startActivity(intent);

            }
        });

        SingleCloseNight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(KalyanNightGame.this, KalyanPicker.class);
                intent.putExtra("mFrom", "SingleCloseNight");
                startActivity(intent);

            }
        });

        JodiNight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(KalyanNightGame.this, KalyanPicker.class);
                intent.putExtra("mFrom", "JodiNight");
                startActivity(intent);

            }
        });

        PanelNight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(KalyanNightGame.this, KalyanPicker.class);
                intent.putExtra("mFrom", "PanelNight");
                startActivity(intent);

            }
        });

    }
}