package com.furrioxx.connexiongsb.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.furrioxx.connexiongsb.async.Login;
import com.furrioxx.connexiongsb.R;

public class MainActivity extends AppCompatActivity {
    private final Context context = this;
    private static final int REQUEST_STORAGE_PERMISSION = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginBtn = findViewById(R.id.loginBtn);
        EditText mailInput = findViewById(R.id.mailInput);
        EditText passwordInput = findViewById(R.id.passwordInput);
        TextView errorLoginMessage = findViewById(R.id.errorLoginTextView);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mailText = mailInput.getText().toString();
                String passwordText = passwordInput.getText().toString();
                if(mailText.equals("")){
                    Toast.makeText(getApplicationContext(), "Le champs mail ne peut être vide", Toast.LENGTH_LONG).show();
                } else if (passwordText.equals("")) {
                    Toast.makeText(getApplicationContext(), "Le champs mot de passe ne peut être vide", Toast.LENGTH_LONG).show();
                }else{
                    String[] param = {mailText, passwordText};
                    new Login(context, errorLoginMessage).execute(param);
                }

            }
        });

        // Vérifiez si la permission est déjà accordée
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Demandez la permission
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_STORAGE_PERMISSION);
        }
    }
}