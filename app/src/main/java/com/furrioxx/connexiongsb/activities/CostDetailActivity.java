package com.furrioxx.connexiongsb.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.furrioxx.connexiongsb.R;
import com.furrioxx.connexiongsb.async.GetCost;
import com.furrioxx.connexiongsb.async.GetCostSheet;
import com.furrioxx.connexiongsb.entity.User;

public class CostDetailActivity extends AppCompatActivity {

    private User user;
    private TextView titleCostDetail,titleTotalCostTv, titleRefundCostTv, titleStateCostSheetTv;
    private LinearLayout linearLayoutCostDetail;
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
        String idFicheFrais = intent.getStringExtra(GetCostSheet.ID_FICHE_FRAIS);
        user = intent.getParcelableExtra("user");

        titleCostDetail.append(idFicheFrais);

        String[] param = {user.getMail() , user.getToken(), idFicheFrais};
        new GetCost(this, linearLayoutCostDetail ,titleTotalCostTv, titleRefundCostTv, titleStateCostSheetTv).execute(param);
    }
}