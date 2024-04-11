package com.furrioxx.connexiongsb.async;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.furrioxx.connexiongsb.activities.CostDetailActivity;
import com.furrioxx.connexiongsb.activities.DashboardVisitorActivity;
import com.furrioxx.connexiongsb.entity.User;
import com.furrioxx.connexiongsb.utils.NetworkUtils;

public class UpdateCost extends AsyncTask <String, Void, String> {

    private String TAG = "UpdateCost";
    private User user;
    private String idFicheFrais;
    private Context context;

    public UpdateCost(User currentUser, String idCostSheet, Context ctx){
        this.user = currentUser;
        this.idFicheFrais = idCostSheet;
        this.context = ctx;
    }
    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtils.updateCost(strings[0], strings[1], strings[2], strings[3], strings[4], strings[5], strings[6], strings[7], strings[8]);
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d(TAG, s);
        Intent intent = new Intent(context, CostDetailActivity.class);
        intent.putExtra("user", user);
        intent.putExtra("idFicheFrais", idFicheFrais);
        context.startActivity(intent);

        super.onPostExecute(s);
    }
}
