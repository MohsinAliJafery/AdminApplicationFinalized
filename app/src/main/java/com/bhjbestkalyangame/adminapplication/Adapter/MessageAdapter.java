package com.bhjbestkalyangame.adminapplication.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bhjbestkalyangame.adminapplication.Model.Chat;

import com.bhjbestkalyangame.adminapplication.R;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;
    
    private Context mContext;
    private List<Chat> mChat;
    private String mImageUrl, mAdmin;


    public MessageAdapter(Context mContext, List<Chat> mChat, String mImageUrl, String mAdmin) {
        this.mContext = mContext;
        this.mChat = mChat;
        this.mImageUrl = mImageUrl;
        this.mAdmin = mAdmin;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false);
            return new ViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, parent, false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Chat chat = mChat.get(position);
        holder.ShowMessage.setText(chat.getMessage());

        if(!chat.getSender().equals(mAdmin)){
            Glide.with(mContext).load(mImageUrl).into(holder.ProfileImage);
        }


        if (position == mChat.size() - 1) {
            if (chat.isSeen()) {
                holder.TextSeen.setText("Seen");
            } else {
                holder.TextSeen.setText("Delivered");
            }
        } else {
            holder.TextSeen.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView ShowMessage;
        public ImageView ProfileImage;
        public TextView TextSeen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ShowMessage = itemView.findViewById(R.id.show_message);
            ProfileImage = itemView.findViewById(R.id.profile_image);
            TextSeen = itemView.findViewById(R.id.text_seen);
        }


    }

    @Override
    public int getItemViewType(int position) {

        if (mChat.get(position).getSender().equals(mAdmin)) {
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }

    }

}
