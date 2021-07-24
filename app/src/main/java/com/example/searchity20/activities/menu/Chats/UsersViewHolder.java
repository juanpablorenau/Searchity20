package com.example.searchity20.activities.menu.Chats;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.searchity20.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersViewHolder extends RecyclerView.ViewHolder {

    private View view;
    public CircleImageView circleImageView_friendChat;
    public TextView textView_name_friendChat;
    public TextView textView_university_friendChat;
    public TextView textView_degree_friendChat;


    public UsersViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
        circleImageView_friendChat = itemView.findViewById(R.id.circleImageView_friendChat);
        textView_name_friendChat = itemView.findViewById(R.id.textView_name_friendChat);
        textView_university_friendChat = itemView.findViewById(R.id.textView_university_friendChat);
        textView_degree_friendChat = itemView.findViewById(R.id.textView_degree_friendChat);
    }
}
