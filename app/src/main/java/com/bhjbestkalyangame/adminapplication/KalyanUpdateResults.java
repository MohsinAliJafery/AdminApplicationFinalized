package com.bhjbestkalyangame.adminapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class KalyanUpdateResults extends AppCompatActivity {

    String mFrom;
    int mTotalNumber;
    TextView aDate, Heading;
    LinearLayout LY;
    EditText eT;

    FirebaseDatabase mDatabase;
    DatabaseReference mReference;

    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_result);

        mDatabase = FirebaseDatabase.getInstance();

        Intent mIntent = getIntent();
        Bundle mBundle = mIntent.getExtras();

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("EEE, MMM d");
        date = dateFormat.format(calendar.getTime());

        eT = new EditText(this);
        Heading = findViewById(R.id.heading);
        LY = findViewById(R.id.input_text_linearlayout);

        mFrom = mBundle.getString("mFrom");

        if(mFrom.equals("SingleNew") || mFrom.equals("JodiNew") || mFrom.equals("PanelNew")){
            mReference = mDatabase.getReference("current_super_numbers").child(date);
            Heading.setText("Enter Your Numbers");
        }else if(mFrom.equals("SingleOpenKalyan") || mFrom.equals("SingleCloseKalyan") || mFrom.equals("JodiKalyan")  || mFrom.equals("PanelKalyan")){
            mReference = mDatabase.getReference("kalyan_matka_super_numbers").child(date);
        }else if(mFrom.equals("special_game")){
            mReference = mDatabase.getReference("special_game").child(date);
        }else if(mFrom.equals("Rajdhani")) {
            mReference = mDatabase.getReference("rajdhani").child(date);
        }else {
            mReference = mDatabase.getReference("kalyan_night_super_numbers").child(date);

        }

        mTotalNumber = Integer.valueOf(mBundle.getString("mTotalNumber"));

        if(mFrom.equals("SingleOpenKalyan")){
            Heading.setText("Kalyan Single Open");
        }else if(mFrom.equals("SingleCloseKalyan")){
            Heading.setText("Kalyan Single Close");
        }else if(mFrom.equals("JodiKalyan")){
            Heading.setText("Kalyan Jodi");
        }else if(mFrom.equals("PanelKalyan")){
            Heading.setText("Kalyan Panel");
        }else if(mFrom.equals("SingleOpenNight")){
            Heading.setText("Kalyan Night Single Open");
        }else if(mFrom.equals("SingleCloseNight")){
            Heading.setText("Kalyan Night Single Close");
        }else if(mFrom.equals("JodiNight")){
            Heading.setText("Kalyan Night Jodi");
        }else if(mFrom.equals("PanelNight")){
            Heading.setText("Kalyan Night Panel");
        }else if(mFrom.equals("Rajdhani")){
            Heading.setText("Rajdhani Night");
        }
        

        for(int i = 0; i< mTotalNumber; i++){

            EditText ET = new EditText(this);
            ET.setId(i);
            ET.setHint( (i+1) +": Please enter a number.");
            ET.setBackgroundColor(getResources().getColor(R.color.noColor));
            ET.setTextSize(16);

            ET.setPadding(20,20,20,20);
            LinearLayout.LayoutParams ETParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            ETParams.setMargins(5, 15,5, 15);
            ETParams.gravity = Gravity.CENTER_HORIZONTAL;
            ET.setInputType(InputType.TYPE_CLASS_NUMBER);
            ET.setLayoutParams(ETParams);
            LY.addView(ET);
        }
            int BID = 90;
            Button B = new Button(this);
            B.setId(BID);
            LinearLayout.LayoutParams BParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
            BParams.setMargins(100,50,100,15);
            BParams.gravity = Gravity.CENTER_HORIZONTAL;
            B.setLayoutParams(BParams);
            B.setGravity(Gravity.CENTER);
            B.setText("Publish");
            B.setTextSize(16);
            B.setBackground(getDrawable(R.layout.primary_background_for_button));
        B.setPadding(20,20,20,20);

        B.setTextColor(Color.WHITE);
            LY.addView(B);

            B.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mReference.child(mFrom).removeValue();

                    for(int i=mTotalNumber-1; i>=0; i--) {
                        eT = findViewById(i);
                        mReference.child(mFrom).push().setValue(eT.getText().toString());
                    }

                    HashMap<String, String> hashMap = new HashMap<String, String>();
                    hashMap.put("date", date);
                    mReference = mDatabase.getReference("current_super_numbers").child("date");
                    mReference.setValue(hashMap);

                    Intent IntentSuccess = new Intent(KalyanUpdateResults.this, KalyanMatkaResults.class);
                    IntentSuccess.putExtra("mFrom", mFrom);
                    startActivity(IntentSuccess);
                    finish();
                }
            });




    }
}
