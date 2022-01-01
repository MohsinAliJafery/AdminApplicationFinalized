package com.bhjbestkalyangame.adminapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.bhjbestkalyangame.adminapplication.Fragments.UsersFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class KalyanMatkaKing extends AppCompatActivity {

    private Button PublicInfo, KalyanMatka, KalyanNight, Notify, SetCoins, SucessStories, SpecialGame, FreePackages, Rajdhani, KalyanResults;
    Fragment SelectedFragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalyan_matka_king);

        PublicInfo = findViewById(R.id.public_info);
        KalyanMatka = findViewById(R.id.kalyan_matka);
        KalyanNight = findViewById(R.id.kalyan_night);
        Notify = findViewById(R.id.notify);
        SetCoins = findViewById(R.id.set_coins);
        SucessStories = findViewById(R.id.success_stories);
        SpecialGame = findViewById(R.id.special_game);
        Rajdhani = findViewById(R.id.rajdhani);
        FreePackages = findViewById(R.id.free_packages);
        KalyanResults = findViewById(R.id.kalyan_results);


        BottomNavigationView mBottomNavigation = findViewById(R.id.bottom_navigation);
        mBottomNavigation.setOnNavigationItemSelectedListener(mBottomNavigationListener);

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
                intent.putExtra("mFrom", "Rajdhani");
                startActivity(intent);
            }
        });

        Notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KalyanMatkaKing.this, SendNotificationToUsers.class);
                startActivity(intent);
            }
        });

        SetCoins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KalyanMatkaKing.this, SetCoinsLimit.class);
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

        KalyanResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KalyanMatkaKing.this, PublicInfoResult.class);
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
    private BottomNavigationView.OnNavigationItemSelectedListener mBottomNavigationListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()){

                        case R.id.chat:
                            Intent intent = new Intent(KalyanMatkaKing.this, ChatActivity.class);
                            startActivity(intent);
                            break;

                        case R.id.facebook:
                            startActivity(getOpenFacebookIntent());
                            break;

                            }

                         return true;

                    }


            };

    public Intent getOpenFacebookIntent() {
        try {
            getPackageManager().getPackageInfo("com.facebook.katana", 0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.facebook.com/kalyanbestgame"));
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/kalyanbestgame"));
        }
    }
}