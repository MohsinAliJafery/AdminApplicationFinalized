package com.bhjbestkalyangame.adminapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class KalyanMatkaGame extends AppCompatActivity {

    private Button SingleOpenKalyan, SingleCloseKalyan, JodiKalyan, PanelKalyan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalyan_matka_game);

        SingleOpenKalyan = findViewById(R.id.single_open);
        SingleCloseKalyan = findViewById(R.id.single_close);
        JodiKalyan = findViewById(R.id.jodi);
        PanelKalyan = findViewById(R.id.panel);

        SingleOpenKalyan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KalyanMatkaGame.this, KalyanPicker.class);
                intent.putExtra("mFrom", "SingleOpenKalyan");
                startActivity(intent);

            }
        });

        SingleCloseKalyan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KalyanMatkaGame.this, KalyanPicker.class);
                intent.putExtra("mFrom", "SingleCloseKalyan");
                startActivity(intent);

            }
        });

        JodiKalyan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KalyanMatkaGame.this, KalyanPicker.class);
                intent.putExtra("mFrom", "JodiKalyan");
                startActivity(intent);

            }
        });

        PanelKalyan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KalyanMatkaGame.this, KalyanPicker.class);
                intent.putExtra("mFrom", "PanelKalyan");
                startActivity(intent);

            }
        });


    }
}