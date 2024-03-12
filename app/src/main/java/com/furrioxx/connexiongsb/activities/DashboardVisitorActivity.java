package com.furrioxx.connexiongsb.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.furrioxx.connexiongsb.R;
import com.furrioxx.connexiongsb.async.GetCostSheet;
import com.furrioxx.connexiongsb.entity.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DashboardVisitorActivity extends AppCompatActivity {

    private TextView titleNameDashboard;
    private User user;
    private LinearLayout costSheetLinearLayout;
    private FloatingActionButton addCostSheetButton;
    private Context context;
    private BottomNavigationView bottomNavigationView;
    private final int MENU_ADD = R.id.add;
    private final int MENU_HOME = R.id.home;
    private final int MENU_PROFILE = R.id.profil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_visitor);

        titleNameDashboard = findViewById(R.id.titleNameDashboard);
        costSheetLinearLayout = findViewById(R.id.linearLayoutCostSheet);
        addCostSheetButton = findViewById(R.id.addCostSheetButton);
        context = this;

        Intent intent = getIntent();
        if (intent != null){
            //récupération de l'utilisateur
            user = intent.getParcelableExtra("user");
            if (user != null){
                initialize();
                addCostSheetButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intentAddCostSheet = new Intent(context, AddCostSheetActivity.class);
                        intentAddCostSheet.putExtra("user", user);
                        context.startActivity(intentAddCostSheet);
                    }
                });
            }
        }
    }
    protected void initialize(){
        this.setNavigation();

        //affiche le nom et prénom de l'utilisateur connecté
        String displayName = " " + user.getName() + " " + user.getSurname();
        titleNameDashboard.append(displayName);

        String[] param = {user.getMail() , user.getToken(), user.getId().toString(), "all"};
        new GetCostSheet(this, costSheetLinearLayout, user).execute(param);
    }

    private void setNavigation(){
        //bottom navigation
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        //set wich item is selected
        bottomNavigationView.setSelectedItemId(R.id.home);

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
                    return true;
                } else if (itemId == R.id.profil) {
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class).putExtra("user", user));
                    overridePendingTransition(0,0);
                    return true;
                }
                return false;
            }
        });
    }
}