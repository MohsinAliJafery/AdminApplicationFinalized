package com.bhjbestkalyangame.adminapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;

import android.widget.GridView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class KalyanMatkaResults extends AppCompatActivity {

    String mFrom;
    Map<String, String> Numbers;
    FirebaseDatabase mDatabase;
    DatabaseReference mReference;
    List<String> Values;
    TextView Title, mDate;;

    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("EEE, MMM d");
        date = dateFormat.format(calendar.getTime());

        mDate = findViewById(R.id.today_date);
        mDate.setText(date);

        Title = findViewById(R.id.title);

        Intent mIntent = getIntent();
        mFrom = mIntent.getStringExtra("mFrom");



        Numbers = new HashMap<String, String>();
        mDatabase = FirebaseDatabase.getInstance();
        if(mFrom.equals("SingleNew") || mFrom.equals("JodiNew") || mFrom.equals("PanelNew")){
            mReference = mDatabase.getReference("current_super_numbers").child(date).child(mFrom);
            Title.setText(mFrom);
        }else if(mFrom.equals("SingleOpenKalyan") || mFrom.equals("SingleCloseKalyan") || mFrom.equals("JodiKalyan") || mFrom.equals("PanelKalyan")){
            mReference = mDatabase.getReference("kalyan_matka_super_numbers").child(date).child(mFrom);
        }else if(mFrom.equals("special_game")){
            mReference = mDatabase.getReference("special_game").child(date).child(mFrom);
            Title.setText("Special Game");
        }else if(mFrom.equals("Rajdhani")) {
            mReference = mDatabase.getReference("rajdhani").child(date).child(mFrom);
            Title.setText("Rajdhani Night");
        } else{
            mReference = mDatabase.getReference("kalyan_night_super_numbers").child(date).child(mFrom);

        }

        if(mFrom.equals("SingleOpenKalyan")){
            Title.setText("Kalyan Single Open");
        }else if(mFrom.equals("SingleCloseKalyan")){
            Title.setText("Kalyan Single Close");
        }else if(mFrom.equals("JodiKalyan")){
            Title.setText("Kalyan Jodi");
        }else if(mFrom.equals("PanelKalyan")){
            Title.setText("Kalyan Panel");
        }else if(mFrom.equals("SingleOpenNight")){
            Title.setText("Kalyan Night Single Open");
        }else if(mFrom.equals("SingleCloseNight")){
            Title.setText("Kalyan Night Single Close");
        }else if(mFrom.equals("JodiNight")){
            Title.setText("Kalyan Night Jodi");
        }else if(mFrom.equals("PanelNight")){
            Title.setText("Kalyan Night Panel");
        }


        mReference.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Numbers = (HashMap<String, String>) snapshot.getValue();
                SortedSet<String> values = new TreeSet<String>(Numbers.values());

                Values =  new ArrayList<>();
                Values.addAll(values);

                populateGrid(Values);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void populateGrid(List<String> values) {
        GridView mGridView = findViewById(R.id.gridview_success);

            int i = 3;
            mGridView.setNumColumns(i);

        mGridView.setAdapter(new SuperNoAdapter(this, values));
    }

}
