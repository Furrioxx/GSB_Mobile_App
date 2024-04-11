package com.furrioxx.connexiongsb.async;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.DialogFragment;

import com.furrioxx.connexiongsb.R;
import com.furrioxx.connexiongsb.activities.CostDetailActivity;
import com.furrioxx.connexiongsb.activities.DashboardVisitorActivity;
import com.furrioxx.connexiongsb.entity.User;
import com.furrioxx.connexiongsb.utils.DeleteCostSheetDialog;
import com.furrioxx.connexiongsb.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class GetCostSheet extends AsyncTask<String, Void, String> {
    private static final String TAG = "MyAsyncTask";
    public static final String ID_FICHE_FRAIS = "com.furrioxx.connexiongsb.extra.ID_FICHE_FRAIS";
    private Context context;
    private User user;
    private final WeakReference<LinearLayout> linearLayoutCostSheet;

    public GetCostSheet(Context activityContext, LinearLayout layout, User userConnected){
        this.context = activityContext;
        this.user = userConnected;
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
                    String[] datas = {montant_total, refund_total, statue, idFicheFrais};
                    if( idFicheFrais != null){
                        // i % 2 pour avoir un tableau stripped
                        if(i % 2 == 0){
                            addRowToLayout(datas, true);
                        }else addRowToLayout(datas, false);

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
        // data.length -1 car on ne veut pas afficher l'id de la fiche de frais
        while(i < datas.length -1){
            TextView newColumn = new TextView(context);
            newColumn.setText(datas[i]);
            // Création des paramètres avec un poids
            LinearLayout.LayoutParams columnLayoutParams = new LinearLayout.LayoutParams(
                    // Width
                    0,
                    // Height
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    // weight
                    2.5f
            );
            newColumn.setLayoutParams(columnLayoutParams);
            newColumn.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            newRow.addView(newColumn);
            i++;
        }
        ImageView deleteButton = new ImageView(context);

        //ajout du bouton supprimé si la fiche n'est pas encore traité
        if(datas[2].equals("NT")){
            deleteButton.setImageResource(R.drawable.delete);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogFragment dialog = new DeleteCostSheetDialog(user, datas[datas.length -1], context);
                    dialog.show(((AppCompatActivity) context).getSupportFragmentManager(), "game");
                }
            });
            newRow.addView(deleteButton);
        }

        newRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CostDetailActivity.class);
                intent.putExtra(ID_FICHE_FRAIS, datas[datas.length -1]); //data.length - 1 correspond a idFicheFrais
                intent.putExtra("user", user);
                context.startActivity(intent);
            }
        });

        //possibilité de supprimer avec un clik prolongé seulement si la fiche est non traité
        if(datas[2].equals("NT")) {
            newRow.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    DialogFragment dialog = new DeleteCostSheetDialog(user, datas[datas.length - 1], context);
                    dialog.show(((AppCompatActivity) context).getSupportFragmentManager(), "game");
                    return false;
                }
            });
        }

        //ajout de la ligne au linear layout de l'activity
        linearLayoutCostSheet.get().addView(newRow);
    }
}