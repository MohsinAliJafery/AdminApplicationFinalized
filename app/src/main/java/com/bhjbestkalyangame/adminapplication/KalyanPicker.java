package com.bhjbestkalyangame.adminapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class KalyanPicker extends AppCompatActivity {

    String mFrom;
    EditText mTotalNumber;
    Button Go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        Intent mIntent = getIntent();
        Bundle mBundle = mIntent.getExtras();
        mFrom = mBundle.getString("mFrom");


        mTotalNumber = findViewById(R.id.total_number);
        Go = findViewById(R.id.go);

        Go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!mTotalNumber.getText().toString().equals("") && !mTotalNumber.getText().toString().equals("0")) {
                    Intent mIntent = new Intent(KalyanPicker.this, KalyanUpdateResults.class);
                    mIntent.putExtra("mTotalNumber", mTotalNumber.getText().toString());
                    mIntent.putExtra("mFrom", mFrom);
                    startActivity(mIntent);
                }else{
                    Toast.makeText(KalyanPicker.this, "Please enter a valid number", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}
