package com.example.searchity20.activities.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.searchity20.activities.menu.MenuActivity;
import com.example.searchity20.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class VerifyEmailActivity extends AppCompatActivity {

    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email);
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    ////////////////////////////////////MOVIMIENTO POR ACTIVIDADES//////////////////////////////////

    public void goToMenu(View v) {
        Intent menuActivityIntent = new Intent(this, MenuActivity.class);
        //Esta línea hace que cuando estes en el menu y le des atrás no te direccione al signUp
        menuActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(menuActivityIntent);
    }

    /////////////////////////////////////BOTÓN ENVIAR CORREO////////////////////////////////////////

    public void sendEmail(View v){
        if(user.isEmailVerified()){
            Toast.makeText(this, "Ya se ha verificado", Toast.LENGTH_LONG).show();
        }else{
            user.sendEmailVerification();
            Toast.makeText(this, "Email enviado", Toast.LENGTH_LONG).show();
        }
    }
}