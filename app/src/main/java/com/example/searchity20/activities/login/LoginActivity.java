package com.example.searchity20.activities.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.searchity20.activities.menu.MenuActivity;
import com.example.searchity20.R;
import com.example.searchity20.helpers.Tools;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    //LOGIN ACTIVITY
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private  LoginAdapter adapter;

    //LOGIN TAB
    private EditText email;
    private EditText password;

    //Firebase
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mAuth = FirebaseAuth.getInstance();

        setTabs();
    }

    ///////////////////////////////INICIALIZAR LOGIN ACTIVITY///////////////////////////////////////

    private void setTabs(){
        tabLayout = findViewById(R.id.tab_layout_login);
        viewPager = findViewById(R.id.viewPager_login);
        adapter = new LoginAdapter(getSupportFragmentManager(),getLifecycle());

        adapter.addFragment(new LoginTab());
        adapter.addFragment(new SignUpTab());

        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch(position){
                    case 0:
                        tab.setText("Iniciar Sesión");
                        break;
                    case 1:
                        tab.setText("Registrase");
                        break;
                }
            }
        }).attach();
    }

    ////////////////////////////////MOVIMIENTO POR ACTIVIDADES//////////////////////////////////////

    private void goToMenu() {
        Intent menuActivityIntent = new Intent(this, MenuActivity.class);
        //Esta línea hace que cuando estes en el menu y le des atrás no te direccione al signUp
        menuActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(menuActivityIntent);
    }

    private void goToVerifyEmail() {
        Intent verifyEmailActivityIntent = new Intent(this, VerifyEmailActivity.class);
        //Esta línea hace que cuando estes en el menu y le des atrás no te direccione al signUp
        verifyEmailActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(verifyEmailActivityIntent);
    }

    public void goToBachillerSignUp(View v) {
        Intent bachillerActivity = new Intent(this, PrecollegeActivity.class);
        startActivity(bachillerActivity);
    }

    public void goToCollegeSignUp(View v) {
        Intent collegeActivity = new Intent(this, CollegeActivity.class);
        startActivity(collegeActivity);
    }

    public void goToGraduatedSignUp(View v) {
        Intent graduatedActivity = new Intent(this, GraduatedActivity.class);
        startActivity(graduatedActivity);
    }

    //////////////////////////////////INICIO LOGIN//////////////////////////////////////////////////

    public void checkLogin(View v){
        email = findViewById(R.id.editText_email_login);
        password = findViewById(R.id.editText_password_login);

        if(email.getText().toString().isEmpty()){
            Toast.makeText(this, "El campo email está vacío", Toast.LENGTH_LONG).show();
        }else if(password.getText().toString().isEmpty()){
            Toast.makeText(this, "El campo contraseña está vacío",Toast.LENGTH_LONG).show();
        }else{
            login(email.getText().toString(),password.getText().toString());
        }
    }


    private void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("Éxito", "Iniciar Sesión");
                            user = FirebaseAuth.getInstance().getCurrentUser();
                            if(user.isEmailVerified()){goToMenu();}
                            else{goToVerifyEmail();}
                        } else {
                            Log.w("Error", "Iniciar Sesión", task.getException());
                            Tools.showAlert(LoginActivity.this,"Error",task.getException().getMessage());
                        }
                    }
                });
    }

    ////////////////////////////////////CAMBIAR CONTRASEÑA//////////////////////////////////////////

    public void resetPassword(View v){
        email = findViewById(R.id.editText_email_login);
        String email_pass = email.getText().toString();
        if(email_pass.length() == 0){
            Toast.makeText(this, "Indique su email", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Se le ha enviado un correo", Toast.LENGTH_LONG).show();
            mAuth.sendPasswordResetEmail(email_pass).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d("Éxito", "Restablecer contraseña");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w("Error", "Restablecer contraseña", e);
                    Tools.showAlert(LoginActivity.this,"Error",e.getMessage());
                }
            });
        }
    }
}

