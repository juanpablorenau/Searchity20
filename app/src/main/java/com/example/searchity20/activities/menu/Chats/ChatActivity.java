package com.example.searchity20.activities.menu.Chats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.searchity20.activities.menu.MenuActivity;
import com.example.searchity20.activities.menu.Profile.ProfileActivity;
import com.example.searchity20.helpers.Tools;
import com.example.searchity20.model.User;
import com.example.searchity20.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private ImageView imageView_toolbar;
    private TextView textView_toolbar;
    private RecyclerView recyclerView_chats;

    private ArrayList<User> chat_users;

    //Firebase
    private FirebaseStorage storage;
    private RecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);

        textView_toolbar = findViewById(R.id.textView_toolbar);
        imageView_toolbar = findViewById(R.id.imageView_toolbar);
        recyclerView_chats = findViewById(R.id.recyclerView_chats);

        storage = FirebaseStorage.getInstance();

        setToolBar();
        setRecycler();

    }

    ///////////////////////////////INICIALIZAR COMPONENTES//////////////////////////////////////////

    private void setToolBar(){
        textView_toolbar.setText("Chats");
        imageView_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menuActivityIntent = new Intent(ChatActivity.this, MenuActivity.class);
                startActivity(menuActivityIntent);
            }
        });
    }

    private void setRecycler(){
        recyclerView_chats.setHasFixedSize(true);
        recyclerView_chats.setLayoutManager(new LinearLayoutManager(this));
        chat_users = new ArrayList<>();
        recyclerAdapter = new RecyclerAdapter(chat_users);
        recyclerView_chats.setAdapter(recyclerAdapter);

        recyclerAdapter.notifyDataSetChanged();
    }
}