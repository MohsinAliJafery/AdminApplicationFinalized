package com.bhjbestkalyangame.adminapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.bhjbestkalyangame.adminapplication.MessageActivity;
import com.bhjbestkalyangame.adminapplication.Model.Chat;
import com.bhjbestkalyangame.adminapplication.Model.User;
import com.bhjbestkalyangame.adminapplication.R;

import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context mContext;
    private List<User> mUser;
    private boolean isChat;
    private String AdminId;
    private String TheLastMessage;

    public UserAdapter(Context mContext, List<User> mUser, boolean isChat) {
        this.mContext = mContext;
        this.mUser = mUser;
        this.isChat = isChat;
        this.AdminId = "VYHYRTfHiscUVIKNz3sN4I1LrWn1";
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final User user = mUser.get(position);
        holder.Username.setText(user.getUsername().substring(0,1).toUpperCase() + user.getUsername().substring(1).toLowerCase());
        holder.Email.setText(user.getEmail().toLowerCase());
        Glide.with(mContext).load(user.getImageUrl()).into(holder.ProfileImage);

        if(user.getNewMessage().equals("new")){
            holder.newMessage.setVisibility(View.VISIBLE);
        }

        if(isChat){
            lastMessage(user.getID(), holder.LastText);
        }else{
            holder.LastText.setVisibility(View.GONE);
        }

          if(user.getStatus().equals("online")){
                holder.ImgOn.setVisibility(View.VISIBLE);
                holder.ImgOff.setVisibility(View.GONE);
         }else{
               holder.ImgOff.setVisibility(View.VISIBLE);
               holder.ImgOn.setVisibility(View.GONE);
           }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mIntent;
                mIntent  = new Intent(mContext, MessageActivity.class);
                mIntent.putExtra("UserId", user.getID());
                mIntent.putExtra("UserEmail", user.getEmail());
                mIntent.putExtra("UserToken", user.getToken());
                mContext.startActivity(mIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mUser.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView Username, newMessage;
        public EditText Email;
        public CircleImageView ProfileImage;
        private CircleImageView ImgOn, ImgOff;
        private TextView LastText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Username = itemView.findViewById(R.id.Username);
            Email = itemView.findViewById(R.id.email);
            ProfileImage = itemView.findViewById(R.id.ProfileImage);
            ImgOn = itemView.findViewById(R.id.img_on);
            ImgOff = itemView.findViewById(R.id.img_off);
            LastText = itemView.findViewById(R.id.last_text);
            newMessage = itemView.findViewById(R.id.newMessage);
        }

    }

    private void lastMessage(final String userId, final TextView last_message){
        TheLastMessage = "default";

        final FirebaseUser mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference mReference = FirebaseDatabase.getInstance().getReference("Chats");

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot mDataSnapshot : snapshot.getChildren()) {
                        Chat mChat = mDataSnapshot.getValue(Chat.class);
                        if (mChat.getReceiver().equals(AdminId) && mChat.getSender().equals(userId) ||
                                mChat.getReceiver().equals(userId) && mChat.getSender().equals(AdminId)) {
                            TheLastMessage = mChat.getMessage();
                        }
                    }

                    switch (TheLastMessage) {
                        case "default":

                            break;

                        default:
                            last_message.setText(TheLastMessage);
                            break;
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
