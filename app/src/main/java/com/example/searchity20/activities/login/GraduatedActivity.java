package com.example.searchity20.activities.login;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.searchity20.activities.menu.MenuActivity;
import com.example.searchity20.R;
import com.example.searchity20.helpers.Tools;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GraduatedActivity extends AppCompatActivity {

    //LAYOUT
    private EditText editText_email_gra;
    private EditText editText_password_gra;
    private EditText editText_confirm_pass_gra;
    private EditText editText_name_gra;
    private EditText editText_lastname_gra;
    private CheckBox conditions_gra;
    private TextView textView_birthday_gra;
    private Button button_birthday_gra;
    private Spinner spinnerGender_gra;
    private Spinner spinnerProvinces_gra;
    private Spinner spinnerUniversity_gra;
    private Spinner spinnerDegree_gra;
    private EditText editText_graduationYear_gra;

    //CALENDARIO
    private int year;
    private int month;
    private int day;
    DatePickerDialog datePickerDialog;
    static final int MINIMUM_AGE  = 14;

    //Firebase
    private FirebaseUser user;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graduated);

        editText_email_gra = findViewById(R.id.editText_email_graduated_signUp);
        editText_password_gra  = findViewById(R.id.editText_password_graduated_signUp);
        editText_confirm_pass_gra  = findViewById(R.id.editText_confirmPassword_graduated_signUp);
        editText_name_gra  = findViewById(R.id.editText_name_graduated_signUp);
        editText_lastname_gra  = findViewById(R.id.editText_lastName_graduated_signUp);
        textView_birthday_gra = findViewById(R.id.textView_birthday_graduated_signUp);
        button_birthday_gra = findViewById(R.id.button_birthday_graduated_signUp);
        conditions_gra = findViewById(R.id.checkBox_graduated_signUp);
        editText_graduationYear_gra = findViewById(R.id.editText_graduationYear_graduated_signUp);


        setSpinners();
    }

    ///////////////////////////////INICIALIZAR COMPONENTES//////////////////////////////////////////

    private void setSpinners(){
        spinnerGender_gra = findViewById(R.id.spinner_gender_graduated);
        spinnerProvinces_gra = findViewById(R.id.spinner_province_graduated);
        spinnerUniversity_gra = findViewById(R.id.spinner_university_graduated);
        spinnerDegree_gra = findViewById(R.id.spinner_degree_graduated);

        //SPINNER GÉNERO
        String[] gender = {"Hombre", "Mujer" , "No Binario"};
        ArrayAdapter<String> arrayAdapter_gender = new ArrayAdapter<String>(this,R.layout.spinner_text_view,gender);
        spinnerGender_gra.setAdapter(arrayAdapter_gender);


        //SPINNER PROVINCIA
        String[] provinces = {"Alicante", "Castellón" , "Valencia"};
        ArrayAdapter<String> arrayAdapter_provinces = new ArrayAdapter<String>(this,R.layout.spinner_text_view,provinces);
        spinnerProvinces_gra.setAdapter(arrayAdapter_provinces);

        //SPINNER UNIVERSIDAD
        String[] universities = {"Universidad Politécnica de Valencia"};
        ArrayAdapter<String> arrayAdapter_universities = new ArrayAdapter<String>(this,R.layout.spinner_text_view,universities);
        spinnerUniversity_gra.setAdapter(arrayAdapter_universities);

        //SPINNER GRADO
        String[] degrees = {"Doble grado en Administración y Dirección de Empresas + Ingeniería Informática"};
        ArrayAdapter<String> arrayAdapter_degrees = new ArrayAdapter<String>(this,R.layout.spinner_text_view,degrees);
        spinnerDegree_gra.setAdapter(arrayAdapter_degrees);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setBirthday(View v){
        final Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day =calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(this, R.style.datePickerTheme,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;
                textView_birthday_gra.setText(dayOfMonth+"/"+month+"/"+year);
            }
        },year,month,day);
        datePickerDialog.show();
    }

    ////////////////////////////////MOVIMIENTO POR ACTIVIDADES//////////////////////////////////////

    public void goToLogin(View v) {
        Intent loginActivityIntent = new Intent(this, LoginActivity.class);
        startActivity(loginActivityIntent);
    }

    private void goToMenu() {
        Intent menuActivityIntent = new Intent(this, MenuActivity.class);
        //Esta línea hace que cuando estes en el menu y le des atrás no te direccione al signUp
        menuActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(menuActivityIntent);
    }
    ////////////////////////////////COMPROBACIONES//////////////////////////////////////////////////

    private boolean checkIsEmpty(){
        if(editText_email_gra.getText().toString().isEmpty()){
            Toast.makeText(this, "El campo email está vacío", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(editText_password_gra.getText().toString().isEmpty()){
            Toast.makeText(this, "El campo contraseña está vacío", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(editText_confirm_pass_gra.getText().toString().isEmpty()){
            Toast.makeText(this, "El campo confirmar contraseña está vacío", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(editText_name_gra.getText().toString().isEmpty()){
            Toast.makeText(this, "El campo nombre está vacío", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(editText_lastname_gra.getText().toString().isEmpty()){
            Toast.makeText(this, "El campo apellidos está vacío", Toast.LENGTH_LONG).show();
            return false;
        }else if(textView_birthday_gra.getText().toString().isEmpty()){
            Toast.makeText(this, "El campo fecha de nacimiento está vacío", Toast.LENGTH_LONG).show();
            return false;
        }else if(editText_graduationYear_gra.getText().toString().isEmpty()){
            Toast.makeText(this, "El campo año de graduación está vacío", Toast.LENGTH_LONG).show();
            return false;
        } else if(!conditions_gra.isChecked()) {
            Toast.makeText(this, "Tiene que aceptar las condiciones y políticas", Toast.LENGTH_LONG).show();
            return false;
        }else{ return true;}
    }

    private boolean checkPassword(){
        if(!editText_password_gra.getText().toString().equals(editText_confirm_pass_gra.getText().toString())){
            Toast.makeText(this, "La contraseña no coincide", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean checkYear(){
        String textViewBirthdayText = textView_birthday_gra.getText().toString();
        if(!textViewBirthdayText.isEmpty()){
            int yearOfBirth = 0;
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            try {
                Date birthday = format.parse(textViewBirthdayText);
                yearOfBirth = birthday.getYear() + 1900;
            } catch (ParseException e) { e.printStackTrace();}
            //Date actual
            Date date = new Date();
            ZoneId timeZone = ZoneId.systemDefault();
            int validYear = date.toInstant().atZone(timeZone).getYear() - MINIMUM_AGE;
            if(yearOfBirth > validYear){
                Toast.makeText(getApplicationContext()," Eres demasiado joven" ,Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        }
        else{
            return false;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean checkGraduationYear(){
        String graduationYear_string = editText_graduationYear_gra.getText().toString();
        if(graduationYear_string.length() != 4){
            Toast.makeText(getApplicationContext(),"El campo año de graduación debe de tener 4 números" ,Toast.LENGTH_LONG).show();
            return false;
        }
        int graduationYear_int;
        try{
            graduationYear_int = Integer.parseInt(graduationYear_string);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage() ,Toast.LENGTH_LONG).show();
            return false;
        }
        Date date = new Date();
        ZoneId timeZone = ZoneId.systemDefault();
        int validYear = date.toInstant().atZone(timeZone).getYear() - 10;
        if(graduationYear_int < validYear){
            Toast.makeText(getApplicationContext(),"Lo sentimos, no te puedes registrar. Te graduaste hace más de 10 años" ,Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    ///////////////////////////////////INICIO REGISTRARSE///////////////////////////////////////////

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void graduatedSignUp(View v){

        progressDialog = new ProgressDialog(GraduatedActivity.this);
        progressDialog.setTitle("Registrando Usuario");
        progressDialog.setMessage("Puede tardar unos segundos...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String email_gra = editText_email_gra.getText().toString();
        String password_gra = editText_password_gra.getText().toString();
        String name_gra = editText_name_gra.getText().toString();
        String lastname_gra = editText_lastname_gra.getText().toString();
        String birthday_gra = textView_birthday_gra.getText().toString();
        String gender_gra = (String) spinnerGender_gra.getSelectedItem();
        String province_gra = (String) spinnerProvinces_gra.getSelectedItem();
        String university_gra = (String) spinnerUniversity_gra.getSelectedItem();
        String degree_gra = (String) spinnerDegree_gra.getSelectedItem();
        String graduationYear_gra = editText_graduationYear_gra.getText().toString();

        if(checkIsEmpty() && checkPassword() && checkYear() && checkGraduationYear()){
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email_gra, password_gra)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d("Éxito", "signInWithEmail:success");
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                graduatedDB(email_gra,password_gra);
                                //sendEmail();
                                progressDialog.cancel();
                                goToMenu();
                            } else {
                                progressDialog.cancel();
                                Log.w("Error", "signInWithEmail:failure", task.getException());
                                Tools.showAlert(GraduatedActivity.this,"Error",task.getException().getMessage().toString());
                            }
                        }
                    });
        }

    }

    private void graduatedDB(String email_gra, String password_gra) {
        String name_gra = editText_name_gra.getText().toString();
        String lastname_gra = editText_lastname_gra.getText().toString();
        String birthday_gra = textView_birthday_gra.getText().toString();
        String gender_gra = (String) spinnerGender_gra.getSelectedItem();
        String province_gra = (String) spinnerProvinces_gra.getSelectedItem();
        String university_gra = (String) spinnerUniversity_gra.getSelectedItem();
        String degree_gra = (String) spinnerDegree_gra.getSelectedItem();
        String graduationYear_gra = editText_graduationYear_gra.getText().toString();


    }

    ////////////////////////////////////ENVIAR EMAIL DE VERIFICACIÓN////////////////////////////////

        private void sendEmail(){
            user = FirebaseAuth.getInstance().getCurrentUser();
            user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d("Éxito", "Enviar email");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w("Error", "Enviar email", e);
                    Tools.showAlert(GraduatedActivity.this,"Error",e.getMessage());
                }
            });
        }
    }