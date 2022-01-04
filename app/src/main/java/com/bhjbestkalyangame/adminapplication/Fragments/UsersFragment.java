package com.bhjbestkalyangame.adminapplication.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.bhjbestkalyangame.adminapplication.Adapter.UserAdapter;
import com.bhjbestkalyangame.adminapplication.Model.User;
import com.bhjbestkalyangame.adminapplication.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class UsersFragment extends Fragment {

    Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private UserAdapter mUserAdapter;
    private List<User> mUser;
    ContentLoadingProgressBar mProgressBar;
    String  AdminId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_users, container, false);

        mRecyclerView = view.findViewById(R.id.MyRecyclerView);
        mToolbar = view.findViewById(R.id.toolbar);
        AdminId = "VYHYRTfHiscUVIKNz3sN4I1LrWn1";
        mRecyclerView.setHasFixedSize(true);
        mProgressBar = view.findViewById(R.id.progressbar);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setLayoutManager(layoutManager);

        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();

            }
        });

        mUser = new ArrayList<>();
        ReadUsers();
        return view;

    }

    private void ReadUsers() {

            DatabaseReference mReference = FirebaseDatabase.getInstance().getReference("all_users_data");

            mReference.orderByValue().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
            if(snapshot.exists()){
                mUser.clear();
                for (DataSnapshot mSnapshot : snapshot.getChildren()) {
                    User user = mSnapshot.getValue(User.class);

                    if (!(user.getID()).equals(AdminId)) {
                        mUser.add(user);
                    }

                }
                Collections.sort(mUser, new Comparator<User>() {
                    @Override
                    public int compare(User user, User t1) {
                        if (user.getTimestamp() < t1.getTimestamp()) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                });
                mUserAdapter = new UserAdapter(getContext(), mUser, true);
                mRecyclerView.setAdapter(mUserAdapter);
                mProgressBar.setVisibility(View.GONE);
            }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

            });





    }
}


