package com.example.searchity20.activities.menu.Settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.searchity20.activities.menu.MenuActivity;
import com.example.searchity20.R;

public class SettingsActivity extends AppCompatActivity {

    private ImageView imageView_toolbar;
    private TextView textView_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        textView_toolbar = findViewById(R.id.textView_toolbar);
        imageView_toolbar = findViewById(R.id.imageView_toolbar);

        setToolBar();
    }

    ///////////////////////////////INICIALIZAR COMPONENTES//////////////////////////////////////////

    private void setToolBar(){
        textView_toolbar.setText("Ajustes");
        imageView_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menuActivityIntent = new Intent(SettingsActivity.this, MenuActivity.class);
                startActivity(menuActivityIntent);
            }
        });
    }

}