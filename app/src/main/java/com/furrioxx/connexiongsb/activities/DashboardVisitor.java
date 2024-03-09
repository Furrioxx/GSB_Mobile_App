package com.furrioxx.connexiongsb.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.furrioxx.connexiongsb.R;
import com.furrioxx.connexiongsb.async.GetCostSheet;
import com.furrioxx.connexiongsb.entity.User;

public class DashboardVisitor extends AppCompatActivity {

    private TextView titleNameDashboard;
    private User user;
    private LinearLayout costSheetLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        titleNameDashboard = findViewById(R.id.titleNameDashboard);
        costSheetLinearLayout = findViewById(R.id.linearLayoutCostSheet);


        Intent intent = getIntent();
        if (intent != null){
            //récupération de l'utilisateur
            user = intent.getParcelableExtra("user");
            if (user != null){
                initialize();
            }
        }
    }
    protected void initialize(){
        //affiche le nom et prénom de l'utilisateur connecté
        String displayName = " " + user.getName() + " " + user.getSurname();
        titleNameDashboard.append(displayName);

        String[] param = {user.getMail() , user.getToken(), user.getId().toString(), "all"};
        new GetCostSheet(this, costSheetLinearLayout).execute(param);
    }
}