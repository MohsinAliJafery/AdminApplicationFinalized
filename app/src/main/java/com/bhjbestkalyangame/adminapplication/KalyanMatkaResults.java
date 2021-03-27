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

        Title.setText(mFrom + " Kalyan");

        Numbers = new HashMap<String, String>();
        mDatabase = FirebaseDatabase.getInstance();
        if(mFrom.equals("SingleNew") || mFrom.equals("JodiNew") || mFrom.equals("PanelNew")){
            mReference = mDatabase.getReference("current_super_numbers").child(date).child(mFrom);
        }else{
            mReference = mDatabase.getReference("current_lucky_numbers").child(mFrom);
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
        int i = 2;
        if(mFrom.equals("Panel") || mFrom.equals("PanelNew")) {
            mGridView.setNumColumns(i);

        }else{
            i = 3;
            mGridView.setNumColumns(i);
        }

        mGridView.setAdapter(new SuperNoAdapter(this, values));
    }

}
