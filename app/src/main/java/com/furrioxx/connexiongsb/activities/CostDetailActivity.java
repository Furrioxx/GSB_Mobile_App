package com.furrioxx.connexiongsb.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.furrioxx.connexiongsb.R;
import com.furrioxx.connexiongsb.async.GetCost;
import com.furrioxx.connexiongsb.async.GetCostSheet;
import com.furrioxx.connexiongsb.entity.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CostDetailActivity extends AppCompatActivity {

    private User user;
    private String idFicheFrais;
    private TextView titleCostDetail,titleTotalCostTv, titleRefundCostTv, titleStateCostSheetTv;
    private LinearLayout linearLayoutCostDetail;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost_detail);

        linearLayoutCostDetail = findViewById(R.id.linearLayoutCostDetail);
        titleCostDetail = findViewById(R.id.titleCostDetail);
        titleTotalCostTv = findViewById(R.id.titleTotalCost);
        titleRefundCostTv = findViewById(R.id.titleRefundTotal);
        titleStateCostSheetTv = findViewById(R.id.titleStateCostSheet);

        Intent intent = getIntent();
        if (intent != null){
            //récupération de l'utilisateur
            user = intent.getParcelableExtra("user");
            if (user != null){
                idFicheFrais = intent.getStringExtra(GetCostSheet.ID_FICHE_FRAIS);
                titleCostDetail.append(idFicheFrais);
                initialize();
            }
        }

    }
    private void initialize(){
        this.setNavigation();

        String[] param = {user.getMail() , user.getToken(), idFicheFrais};
        new GetCost(this, linearLayoutCostDetail ,titleTotalCostTv, titleRefundCostTv, titleStateCostSheetTv, user).execute(param);
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
                    startActivity(new Intent(getApplicationContext(), DashboardVisitorActivity.class).putExtra("user", user));
                    overridePendingTransition(0,0);
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