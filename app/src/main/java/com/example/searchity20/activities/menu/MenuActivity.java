package com.example.searchity20.activities.menu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.searchity20.R;
import com.example.searchity20.activities.login.LoginActivity;
import com.example.searchity20.activities.menu.Chats.ChatActivity;
import com.example.searchity20.activities.menu.Home.HomeFragment;
import com.example.searchity20.activities.menu.Profile.ProfileActivity;
import com.example.searchity20.activities.menu.Settings.SettingsActivity;
import com.example.searchity20.helpers.Tools;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private CircleImageView circleImageView_menu;

    private FirebaseStorage storage;
    private StorageReference storageRef;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        user = FirebaseAuth.getInstance().getCurrentUser();
        storage = FirebaseStorage.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference();


        setMenu();
    }

    ///////////////////////////////INICIALIZAR COMPONENTES//////////////////////////////////////////

    private void setMenu() {
        setSupportActionBar(findViewById(R.id.toolbar_menu));
        drawerLayout = findViewById(R.id.drawer_menu);
        navigationView = findViewById(R.id.navigation_view_menu);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, findViewById(R.id.toolbar_menu), R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerSlideAnimationEnabled(true);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        //Cargar menu_fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frameLayout_menu, new HomeFragment());
        fragmentTransaction.commit();

        //Establecer evento onClick al navigationView
        navigationView.setNavigationItemSelectedListener(this);

        setProfile();
    }

    public void setProfile() {
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.textView_email_menu);
        circleImageView_menu = (CircleImageView) headerView.findViewById(R.id.circleImageView_menu);

        circleImageView_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //Inicializar los textViews


    }

    ////////////////////////////////MOVIMIENTO POR ACTIVIDADES//////////////////////////////////////

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        if (item.getItemId() == R.id.home) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout_menu, new HomeFragment());
            fragmentTransaction.commit();
        }
        if (item.getItemId() == R.id.chats) {
            Intent chatsActivityIntent = new Intent(this, ChatActivity.class);
            startActivity(chatsActivityIntent);
        }
        if (item.getItemId() == R.id.profile) {
            goToProfile();
        }
        if (item.getItemId() == R.id.settings) {
            Intent settingsActivityIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsActivityIntent);
        }

        return false;
    }

    private void goToProfile(){
        Intent profileActivityIntent = new Intent(this, ProfileActivity.class);
        startActivity(profileActivityIntent);
    }

    ////////////////////////////////CERRAR SESIÓN///////////////////////////////////////////////////

    public void signOut(View v) {
        FirebaseAuth.getInstance().signOut();
        Intent loginActivityIntent = new Intent(this, LoginActivity.class);
        startActivity(loginActivityIntent);
    }
}
