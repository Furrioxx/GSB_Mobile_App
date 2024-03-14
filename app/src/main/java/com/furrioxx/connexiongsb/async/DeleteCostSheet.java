package com.furrioxx.connexiongsb.async;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.furrioxx.connexiongsb.activities.DashboardVisitorActivity;
import com.furrioxx.connexiongsb.entity.User;
import com.furrioxx.connexiongsb.utils.NetworkUtils;

public class DeleteCostSheet extends AsyncTask<String, Void, String> {
    private Context context;
    private User user;

    public DeleteCostSheet(Context activityContext, User userConnected){
        this.context = activityContext;
        this.user = userConnected;
    }
    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtils.deleteCostSheet(strings[0], strings[1], strings[2]);
    }

    @Override
    protected void onPostExecute(String s) {
        Toast.makeText(context, "La fiche de frais à été supprimé", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(context, DashboardVisitorActivity.class);
        intent.putExtra("user", user);
        //ferme toute les activités dans la pile et empeche donc le retour apres déconnexion
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        super.onPostExecute(s);
    }
}
