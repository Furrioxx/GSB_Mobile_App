package com.furrioxx.connexiongsb.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.furrioxx.connexiongsb.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class GetCostSheet extends AsyncTask<String, Void, String> {
    private static final String TAG = "MyAsyncTask";
    private Context context;
    private WeakReference<LinearLayout> linearLayoutCostSheet;

    public GetCostSheet(Context activityContext, LinearLayout layout){
        this.context = activityContext;
        linearLayoutCostSheet = new WeakReference<>(layout);
    }

    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtils.getCostSheet(strings[0], strings[1], strings[2], strings[3]);
    }

    @Override
    protected void onPostExecute(String s) {
        try {
            Log.d(TAG, "Réponse de l'API : " + s);
            JSONObject jsonObject = new JSONObject(s);
            JSONArray itemsArray = jsonObject.getJSONArray("data");
            int i = 0;
            String idFicheFrais = null;
            String montant_total = null;
            String refund_total = null;
            String idUserValidation = null;
            String statue = null;

            while (i < itemsArray.length()) {
                JSONObject costSheet = itemsArray.getJSONObject(i);
                try {
                    idFicheFrais = costSheet.getString("idFicheFrais");
                    montant_total = costSheet.getString("montant_total") + " €";
                    refund_total = costSheet.getString("refund_total") + " €";
                    idUserValidation = costSheet.getString("idUserValidation");
                    statue  = costSheet.getString("statue");
                    String[] datas = {idFicheFrais, montant_total, refund_total, idUserValidation, statue};
                    if( idFicheFrais != null){
                        addRowToLayout(datas);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onPostExecute(s);
    }

    private void addRowToLayout(String[] datas){
        //creation du linearLayout horizontal -> une ligne
        LinearLayout newRow = new LinearLayout(context);
        newRow.setOrientation(LinearLayout.HORIZONTAL);
        //ajout des parametres de la ligne
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                //width
                ViewGroup.LayoutParams.MATCH_PARENT,
                //height
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        newRow.setLayoutParams(layoutParams);


        //ajout des colonnes a chaque ligne
        int i = 0;
        while(i < datas.length){
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

        //ajout de la ligne au linear layout de l'activity
        linearLayoutCostSheet.get().addView(newRow);
    }
}