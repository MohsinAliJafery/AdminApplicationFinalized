package com.bhjbestkalyangame.adminapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class KalyanMatkaInterface extends AppCompatActivity {
     private TextView dateTimeDisplay;
     private Calendar calendar;
     private SimpleDateFormat dateFormat;
     private  String date;
     private Button Single, Jodi, Panel, PublicInfo, SingleNew, JodiNew, PanelNew, KalayanResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalyan_work);

        Single = findViewById(R.id.single);
        Jodi =findViewById(R.id.jodi);
        Panel = findViewById(R.id.panel);
        PublicInfo = findViewById(R.id.public_info);
        SingleNew = findViewById(R.id.single_new);
        JodiNew = findViewById(R.id.jodi_new);
        PanelNew = findViewById(R.id.panel_new);
        KalayanResult = findViewById(R.id.kalyan_result);

        Single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KalyanMatkaInterface.this, KalyanPicker.class);
                intent.putExtra("mFrom", "Single");
                startActivity(intent);

            }
        });
        Jodi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KalyanMatkaInterface.this, KalyanPicker.class);
                intent.putExtra("mFrom","Jodi");
                startActivity(intent);

            }
        });
        Panel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KalyanMatkaInterface.this, KalyanPicker.class);
                intent.putExtra("mFrom","Panel");
                startActivity(intent);

            }
        });

        SingleNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KalyanMatkaInterface.this, KalyanPicker.class);
                intent.putExtra("mFrom", "SingleNew");
                startActivity(intent);

            }
        });

        JodiNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KalyanMatkaInterface.this, KalyanPicker.class);
                intent.putExtra("mFrom", "JodiNew");
                startActivity(intent);

            }
        });

        PanelNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KalyanMatkaInterface.this, KalyanPicker.class);
                intent.putExtra("mFrom", "PanelNew");
                startActivity(intent);

            }
        });

        PublicInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KalyanMatkaInterface.this, PublicInformation.class);
                intent.putExtra("mFrom", "public_info");
                startActivity(intent);
            }
        });

        KalayanResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KalyanMatkaInterface.this, PublicInfoResult.class);
                intent.putExtra("mFrom", "kalyan_result");
                startActivity(intent);
            }
        });
    }

}