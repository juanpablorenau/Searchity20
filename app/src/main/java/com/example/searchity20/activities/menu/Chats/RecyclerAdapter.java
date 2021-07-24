package com.example.searchity20.activities.menu.Chats;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.searchity20.model.User;
import com.example.searchity20.R;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<UsersViewHolder> {

    private ArrayList<User> friends;

    public RecyclerAdapter(ArrayList<User> friends){
        this.friends = friends;
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_chat,parent,false);
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
        //holder.circleImageView_friendChat.setImageURI(friends.get(position).getProfile_picture());
        holder.textView_name_friendChat.setText(friends.get(position).getName());
        holder.textView_university_friendChat.setText(friends.get(position).getUniversity());
        holder.textView_degree_friendChat.setText(friends.get(position).getDegree());
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }
}
