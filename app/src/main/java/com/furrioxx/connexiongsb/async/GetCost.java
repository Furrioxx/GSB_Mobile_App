package com.furrioxx.connexiongsb.async;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.furrioxx.connexiongsb.R;
import com.furrioxx.connexiongsb.activities.CostDetailActivity;
import com.furrioxx.connexiongsb.activities.DashboardVisitorActivity;
import com.furrioxx.connexiongsb.activities.UpdateCostActivity;
import com.furrioxx.connexiongsb.entity.User;
import com.furrioxx.connexiongsb.utils.DeleteCostSheetDialog;
import com.furrioxx.connexiongsb.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class GetCost extends AsyncTask<String, Void, String> {
    private static final String TAG = "GetCost";
    private Context context;
    public static final String ID_FRAIS = "com.furrioxx.connexiongsb.extra.ID_FRAIS";
    public static final String ID_FICHE_FRAIS = "com.furrioxx.connexiongsb.extra.ID_FICHE_FRAIS";


    private User user;
    private WeakReference<LinearLayout> linearLayoutCost;
    private WeakReference<TextView> totalCostTv, refundCostTv, stateCostSheetTv;

    public GetCost(Context activityContext, LinearLayout layout, TextView totalCost, TextView refundCost, TextView stateCost, User userConnected){
        this.context = activityContext;
        linearLayoutCost = new WeakReference<>(layout);
        totalCostTv = new WeakReference<>(totalCost);
        refundCostTv = new WeakReference<>(refundCost);
        stateCostSheetTv = new WeakReference<>(stateCost);
        this.user = userConnected;
    }

    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtils.getCost(strings[0], strings[1], strings[2]);
    }

    @Override
    protected void onPostExecute(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray itemsArray = jsonObject.getJSONArray("data");
            int i = 0;
            String idCost = null;
            String libelle = null;
            String montant = null;
            String refund_montant = null;
            String status = null;
            String montant_total = null;
            String refund_total = null;
            String linkJustif = null;
            String statue = null;
            String idFicheFrais = null;

            while (i < itemsArray.length()) {
                JSONObject cost = itemsArray.getJSONObject(i);
                Log.d(TAG, "Réponse de l'API getCost : " + cost);
                try {
                    idCost = cost.getString("id");
                    libelle = cost.getString("libelle");
                    montant = cost.getString("montant") + " €";
                    if(cost.getString("refund_montant").equals("null")){
                        refund_montant = "-";
                    }else{
                        refund_montant = cost.getString("refund_montant") + " €";
                    }


                    status  = cost.getString("statue");
                    montant_total = cost.getString("montant_total");
                    refund_total = cost.getString("refund_total");
                    linkJustif = cost.getString("linkJustif");
                    statue = cost.getString("statue");
                    idFicheFrais = cost.getString("idFicheFrais");
                    String[] datas = {libelle, montant, refund_montant, idCost, statue, idFicheFrais};
                    if( idCost != null){

                        // i % 2 pour avoir un tableau stripped
                        addRowToLayout(datas, i % 2 == 0);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                i++;
            }

            totalCostTv.get().append( " " + montant_total + " €");
            refundCostTv.get().append( " " + refund_total + " €");
            stateCostSheetTv.get().append( " " + status);

        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onPostExecute(s);

    }

    private void addRowToLayout(String[] datas, boolean isPair){
        //creation du linearLayout horizontal -> une ligne
        LinearLayout newRow = new LinearLayout(context);
        newRow.setOrientation(LinearLayout.HORIZONTAL);
        //ajout des parametres de la ligne
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                //width
                ViewGroup.LayoutParams.MATCH_PARENT,
                //height
                200
        );
        newRow.setLayoutParams(layoutParams);
        newRow.setGravity(Gravity.CENTER);
        if(isPair){
            newRow.setBackgroundColor(ContextCompat.getColor(context, R.color.gsb_blue_lighter));
        }

        //ajout des colonnes a chaque ligne
        int i = 0;
        // data.length -1 car on ne veut pas afficher l'id du frais
        while(i < datas.length - 3){
            TextView newColumn = new TextView(context);
            newColumn.setText(datas[i]);
            // Création des paramètres avec un poids
            LinearLayout.LayoutParams columnLayoutParams = new LinearLayout.LayoutParams(
                    // Width
                    0,
                    // Height
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    // weight
                    1f
            );
            newColumn.setLayoutParams(columnLayoutParams);
            newRow.addView(newColumn);
            i++;
        }

        //si la fiche de frais est non traité alors on peut modifier les frais
        if(datas[4].equals("NT")){
            newRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, UpdateCostActivity.class);
                    intent.putExtra(ID_FRAIS, datas[datas.length -3]); //data.length - 3 correspond a idFrais
                    intent.putExtra("user", user);
                    intent.putExtra(ID_FICHE_FRAIS, datas[datas.length-1]); //date.length - 1 correspond à idFicheFrais
                    context.startActivity(intent);
                }
            });
        }

        //ajout de la ligne au linear layout de l'activity
        linearLayoutCost.get().addView(newRow);
    }
}