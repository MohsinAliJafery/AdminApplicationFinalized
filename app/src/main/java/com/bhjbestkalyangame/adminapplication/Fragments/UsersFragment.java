package com.bhjbestkalyangame.adminapplication.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.bhjbestkalyangame.adminapplication.Adapter.UserAdapter;
import com.bhjbestkalyangame.adminapplication.Model.User;
import com.bhjbestkalyangame.adminapplication.R;

import java.util.ArrayList;
import java.util.List;


public class UsersFragment extends Fragment {

    Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private UserAdapter mUserAdapter;
    private List<User> mUser;
    String  AdminId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_child_users, container, false);

        mRecyclerView = view.findViewById(R.id.MyRecyclerView);
        mToolbar = view.findViewById(R.id.toolbar);
        AdminId = "VYHYRTfHiscUVIKNz3sN4I1LrWn1";
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();

            }
        });

        mUser = new ArrayList<>();
        ReadUsers();

//        mSearchUser.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                searchUser(charSequence.toString());
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });

        return view;

    }

//    private void searchUser(String mString) {
//        final FirebaseUser mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//
//        Query mQuery = FirebaseDatabase.getInstance().getReference("Users").orderByChild("Username")
//                .startAt(mString)
//                .endAt(mString+"\uf0ff");
//
//        mQuery.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                mUser.clear();
//
//                for(DataSnapshot mSnapshot: snapshot.getChildren()){
//                    User user = mSnapshot.getValue(User.class);
//
//                    if(!user.getID().equals(mFirebaseUser.getUid())){
//                        mUser.add(user);
//                    }
//                }
//
//                mUserAdapter = new UserAdapter(getContext(), mUser, false);
//                mRecyclerView.setAdapter(mUserAdapter);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//
//
//
//
//    }

    private void ReadUsers() {

//        final FirebaseUser mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference mReference = FirebaseDatabase.getInstance().getReference("all_users_data");

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                    mUser.clear();
                    for (DataSnapshot mSnapshot : snapshot.getChildren()) {
                        User user = mSnapshot.getValue(User.class);

                        if (!(user.getID()).equals(AdminId)) {
                            mUser.add(user);
                        }

                    }

                    mUserAdapter = new UserAdapter(getContext(), mUser, false);
                    mRecyclerView.setAdapter(mUserAdapter);

            }




            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}


//
//public class UsersFragment extends Fragment {
//
//    private RecyclerView mRecyclerView;
//    private UserAdapter mUserAdapter;
//    private List<User> mUser;
//
//    private EditText mSearchUser;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        View view = inflater.inflate(R.layout.fragment_users, container, false);
//
//        mRecyclerView = view.findViewById(R.id.MyRecyclerView);
//        mSearchUser = view.findViewById(R.id.search_user);
//
//        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        mUser = new ArrayList<>();
//        ReadUsers();
//
//        mSearchUser.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                searchUser(charSequence.toString());
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        return view;
//
//    }
//
//    private void searchUser(String mString) {
//        final FirebaseUser mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//
//        Query mQuery = FirebaseDatabase.getInstance().getReference("Users").orderByChild("Username")
//                .startAt(mString)
//                .endAt(mString+"\uf0ff");
//
//        mQuery.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                mUser.clear();
//
//                for(DataSnapshot mSnapshot: snapshot.getChildren()){
//                    User user = mSnapshot.getValue(User.class);
//
//                    if(!user.getID().equals(mFirebaseUser.getUid())){
//                        mUser.add(user);
//                    }
//                }
//
//                mUserAdapter = new UserAdapter(getContext(), mUser, false);
//                mRecyclerView.setAdapter(mUserAdapter);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//
//
//
//
//    }
//
//    private void ReadUsers() {
//
//        final FirebaseUser mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        DatabaseReference mReference = FirebaseDatabase.getInstance().getReference("Users");
//
//        mReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                if (mSearchUser.getText().toString().equals("")) {
//
//                    mUser.clear();
//                    for (DataSnapshot mSnapshot : snapshot.getChildren()) {
//                        User user = mSnapshot.getValue(User.class);
//
//                        if (!user.getID().equals(mFirebaseUser.getUid())) {
//                            mUser.add(user);
//                        }
//
//
//                    }
//
//                    mUserAdapter = new UserAdapter(getContext(), mUser, false);
//                    mRecyclerView.setAdapter(mUserAdapter);
//                }
//            }
//
//
//
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//
//
//    }
//}
