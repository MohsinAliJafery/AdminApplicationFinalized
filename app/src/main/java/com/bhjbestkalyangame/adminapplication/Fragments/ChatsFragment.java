package com.bhjbestkalyangame.adminapplication.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.bhjbestkalyangame.adminapplication.Model.Chatlist;
import com.bhjbestkalyangame.adminapplication.Model.User;
import com.bhjbestkalyangame.adminapplication.R;

import java.util.ArrayList;
import java.util.List;


public class ChatsFragment extends Fragment {

    private RecyclerView mRecyclerView;

    private UserAdapter mUserAdapter;
    private List<User> mUsers;

    FirebaseUser mFirebaseUser;
    DatabaseReference mDatabaseReference;

    private List<Chatlist> mUsersList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chats, container, false);

        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        mUsersList = new ArrayList<>();
        mUsers = new ArrayList<>();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Chatlist").child(mFirebaseUser.getUid());
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsersList.clear();
                for(DataSnapshot mSnapshot: snapshot.getChildren()){

                            Chatlist chatlist = mSnapshot.getValue(Chatlist.class);
                            mUsersList.add(chatlist);
                            Log.d("Tu", "onDataChange: " + chatlist.getId());

                }
                Toast.makeText(getContext(), ""+mUsersList.size(), Toast.LENGTH_SHORT).show();
                chatList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }




    private void chatList() {

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Users");
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsers.clear();
                for(DataSnapshot mSnapshot: snapshot.getChildren()){
                    User user = mSnapshot.getValue(User.class);



//                    for(Iterator<Chatlist> mIterator = mUsersList.iterator(); mIterator.hasNext();){
//
//                        Chatlist mlist = mIterator.next();
//                        if(user.getID().equals(mlist.getId())){
//                            mUsers.add(user);
//                        }
//
//                    }

                    for(Chatlist hatlist: mUsersList){

                        if(user.getID().equals(hatlist.getId())) {
                            mUsers.add(user);
                        }
                    }

                }

                mUserAdapter = new UserAdapter(getContext(), mUsers, true);
                mRecyclerView.setAdapter(mUserAdapter);

            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}