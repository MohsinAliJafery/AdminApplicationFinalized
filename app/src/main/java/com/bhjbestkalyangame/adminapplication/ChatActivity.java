package com.bhjbestkalyangame.adminapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.bhjbestkalyangame.adminapplication.Fragments.UsersFragment;

public class ChatActivity extends AppCompatActivity {
    Fragment SelectedFragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        SelectedFragment = new UsersFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragments_container, SelectedFragment).commit();

    }
}