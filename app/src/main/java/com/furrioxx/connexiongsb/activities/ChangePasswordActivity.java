package com.furrioxx.connexiongsb.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.furrioxx.connexiongsb.R;
import com.furrioxx.connexiongsb.async.ChangePassword;
import com.furrioxx.connexiongsb.entity.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ChangePasswordActivity extends AppCompatActivity {

    private User user;
    private BottomNavigationView bottomNavigationView;
    private EditText currentPasswordInput, newPasswordInput, reNewPasswordInput;
    private Button changePasswordButton;
    private Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        Intent intent = getIntent();
        if (intent != null){
            //récupération de l'utilisateur
            user = intent.getParcelableExtra("user");
            if (user != null){
                changePasswordButton = findViewById(R.id.changePasswordButton);
                currentPasswordInput = findViewById(R.id.currentPassword);
                newPasswordInput = findViewById(R.id.newPassword);
                reNewPasswordInput = findViewById(R.id.reNewPassword);
                initialize();
            }
        }
    }

    private void initialize(){
        this.setNavigation();
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentPassword = currentPasswordInput.getText().toString();
                String newPassword = newPasswordInput.getText().toString();
                String reNewPassword = reNewPasswordInput.getText().toString();

                if(!currentPassword.equals("") && !newPassword.equals("") && !reNewPassword.equals("")){
                    if(newPassword.equals(reNewPassword)){
                        //les nouveaux mots de passe sont identiques
                        String[] params = {user.getMail(), user.getId().toString(), user.getToken(), currentPassword, newPassword, reNewPassword};
                        new ChangePassword(user, context).execute(params);
                    }else{
                        Toast.makeText(context, "Les nouveaux mots de passe ne sont pas identique", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(context, "Les champs ne sont pas tous remplis", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void setNavigation(){
        //bottom navigation
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        //set wich item is selected
        bottomNavigationView.setSelectedItemId(R.id.profil);

        //création des listener de la navigation
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();
                if (itemId == R.id.add) {
                    startActivity(new Intent(getApplicationContext(), AddCostSheetActivity.class).putExtra("user", user));
                    overridePendingTransition(0,0);
                    return true;
                } else if (itemId == R.id.home) {
                    startActivity(new Intent(getApplicationContext(), DashboardVisitorActivity.class).putExtra("user", user));
                    overridePendingTransition(0,0);
                    return true;
                } else if (itemId == R.id.profil) {
                    return true;
                }
                return false;
            }
        });
    }
}