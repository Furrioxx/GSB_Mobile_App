package com.furrioxx.connexiongsb.async;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.furrioxx.connexiongsb.activities.CostDetailActivity;
import com.furrioxx.connexiongsb.activities.DashboardVisitorActivity;
import com.furrioxx.connexiongsb.entity.User;
import com.furrioxx.connexiongsb.utils.NetworkUtils;

public class AddCostSheet extends AsyncTask<String, Void, String> {

    private final String TAG = "addCostSheet";

    private User user;
    private String idFicheFrais;
    private Context context;

    public AddCostSheet(User currentUser, Context ctx){
        this.user = currentUser;
        this.context = ctx;
    }
    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtils.addCostSheet(
                strings[0],
                strings[1],
                strings[2],
                strings[3],
                strings[4],
                strings[5],
                strings[6],
                strings[7],
                strings[8],
                strings[9],
                strings[10],
                strings[11],
                strings[12],
                strings[13],
                strings[14]
        );
    }

    @Override
    protected void onPostExecute(String s) {
        //faire la redirection apres ajout de fiche de frais
        Log.d(TAG, s);

        Intent intent = new Intent(context, DashboardVisitorActivity.class);
        intent.putExtra("user", user);
        context.startActivity(intent);

        super.onPostExecute(s);
    }
}
