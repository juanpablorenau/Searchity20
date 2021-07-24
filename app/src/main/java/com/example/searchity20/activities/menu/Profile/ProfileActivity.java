package com.example.searchity20.activities.menu.Profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.searchity20.activities.menu.MenuActivity;
import com.example.searchity20.R;
import com.example.searchity20.helpers.Tools;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    //Layout
    private ImageView imageView_toolbar;
    private TextView textView_toolbar;
    private CircleImageView circleImageView_profile;
    private TextView textView_name_profile;
    private TextView textView_degree_profile;
    private TextView textView_university_profile;
    private Button button_changePhoto_profile;

    //Firebase
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private FirebaseUser user;

    private static final int GALLERY_INTENT = 1;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textView_toolbar = findViewById(R.id.textView_toolbar);
        imageView_toolbar = findViewById(R.id.imageView_toolbar);
        circleImageView_profile = findViewById(R.id.circleImageView_profile);
        textView_name_profile = findViewById(R.id.textView_name_profile);
        textView_degree_profile = findViewById(R.id.textView_degree_profile);
        textView_university_profile = findViewById(R.id.textView_university_profile);
        button_changePhoto_profile = findViewById(R.id.button_changePhoto_profile);

        user = FirebaseAuth.getInstance().getCurrentUser();
        storage = FirebaseStorage.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference();

        setToolBar();
        setProfile();
    }

    ///////////////////////////////INICIALIZAR COMPONENTES//////////////////////////////////////////

    private void setToolBar(){
        textView_toolbar.setText("Perfil");
        imageView_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menuActivityIntent = new Intent(ProfileActivity.this, MenuActivity.class);
                startActivity(menuActivityIntent);
            }
        });
    }

    private void setProfile() {

    }

    /////////////////////////////////////CAMBIAR FOTO///////////////////////////////////////////////

    public void changePicture(View v) {
        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
        openGalleryIntent.setType("image/*");
        openGalleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(openGalleryIntent,"SELECT IMAGE"),GALLERY_INTENT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_INTENT && resultCode == RESULT_OK){
            Uri image = data.getData();
            CropImage.activity(image).setAspectRatio(1,1).start(this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                progressDialog = new ProgressDialog(ProfileActivity.this);
                progressDialog.setTitle("Cambiar Foto");
                progressDialog.setMessage("Se está actualizando la foto...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                //Guardar la imagen en el storage
                Uri picture = result.getUri();
                circleImageView_profile.setImageURI(picture);

                StorageReference filePath = storageRef.child("users").child(user.getUid());
                filePath.putFile(picture).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.cancel();
                        Log.d("Éxito", "change profile picture");
                        //Actualizar el campo imagen del perfil
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Error", "change profile picture", e);
                        Tools.showAlert(ProfileActivity.this, "Error", e.getMessage());
                    }
                });
            }else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.w("Error", "Selecting image", error);
                Tools.showAlert(ProfileActivity.this, "Error", error.getMessage());
            }
        }
    }

}