package com.furrioxx.connexiongsb.async;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.furrioxx.connexiongsb.utils.NetworkUtils;

import org.json.JSONObject;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;

public class GetPreciseCost extends AsyncTask<String, Void, String> {

    private String TAG = "getPreciseCost";
    private final WeakReference<EditText> libelleEditText, montantEditText, timingEditText;
    private final WeakReference<ImageView> justifImageView;
    private final WeakReference<Button> updateJustifBtn;
    private String WEB_BASE_PATH = "https://tom-pelud.fr/gsb/";

    public GetPreciseCost(EditText libelleInput, EditText montantInput, EditText timingInput, ImageView imgView, Button btn){
        libelleEditText = new WeakReference<>(libelleInput);
        montantEditText = new WeakReference<>(montantInput);
        timingEditText = new WeakReference<>(timingInput);
        justifImageView = new WeakReference<>(imgView);
        updateJustifBtn = new WeakReference<>(btn);
    }
    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtils.getPreciseCost(strings[0], strings[1], strings[2]);
    }

    @Override
    protected void onPostExecute(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONObject cost = jsonObject.getJSONObject("data");
            int i = 0;
            String idCost = null;
            String libelle = null;
            String montant = null;
            String refund_montant = null;
            String timing = null;
            String dateligne = null;
            String idFicheFrais = null;
            String linkJustif = null;
            String statu = null;
            String httpStatut = null;

            try{
                httpStatut = jsonObject.getString("status");
                idCost = cost.getString("id");
                libelle = cost.getString("libelle");
                montant = cost.getString("montant");
                refund_montant = cost.getString("refund_montant");
                timing = cost.getString("timing");
                dateligne = cost.getString("dateligne");
                idFicheFrais = cost.getString("idFicheFrais");
                statu = cost.getString("statu");
                linkJustif = cost.getString("linkJustif");
                if(!linkJustif.equals("")){
                    linkJustif = WEB_BASE_PATH + cost.getString("linkJustif").substring(3); //pour enlever "../" du chemin
                }

            }catch (Exception e) {
                e.printStackTrace();
            }

            Log.d(TAG, jsonObject.toString());

            libelleEditText.get().setText(libelle);
            montantEditText.get().setText(montant);
            timingEditText.get().setText(timing);

            //gestion de l'affichage en fonction des frais
            if(libelle.equals("transport (train)")){
                libelleEditText.get().setEnabled(false);
                timingEditText.get().setEnabled(false);
                justifImageView.get().setVisibility(View.VISIBLE);
                updateJustifBtn.get().setVisibility(View.VISIBLE);
                new DownloadImageTask().execute(linkJustif);
            } else if (libelle.equals("transport (voiture)")) {
                libelleEditText.get().setEnabled(false);
                montantEditText.get().setEnabled(false);
            }else if (libelle.equals("restauration")) {
                libelleEditText.get().setEnabled(false);
            }else if (libelle.equals("logement")) {
                libelleEditText.get().setEnabled(false);
            }else {
                //le frais est "autre"
                timingEditText.get().setEnabled(false);
                if(!linkJustif.equals("")){
                    justifImageView.get().setVisibility(View.VISIBLE);
                    updateJustifBtn.get().setVisibility(View.VISIBLE);
                    new DownloadImageTask().execute(linkJustif);
                }else{
                    justifImageView.get().setVisibility(View.VISIBLE);
                    updateJustifBtn.get().setVisibility(View.VISIBLE);
                }

            }

        } catch (Exception e) {

        }


        super.onPostExecute(s);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... urls) {
            String urlDisplay = urls[0];
            Bitmap bitmap = null;
            try {
                InputStream in = new URL(urlDisplay).openStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            justifImageView.get().setImageBitmap(result);
        }
    }
}
