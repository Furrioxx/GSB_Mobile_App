package com.furrioxx.connexiongsb.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.furrioxx.connexiongsb.async.Login;
import com.furrioxx.connexiongsb.R;

public class MainActivity extends AppCompatActivity {
    private final Context context = this;


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
                String[] param = {mailInput.getText().toString(), passwordInput.getText().toString()};
                new Login(context, errorLoginMessage).execute(param);
            }
        });
    }
}