package com.furrioxx.connexiongsb.async;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.TextView;

import com.furrioxx.connexiongsb.R;
import com.furrioxx.connexiongsb.activities.Dashboard;
import com.furrioxx.connexiongsb.entity.User;
import com.furrioxx.connexiongsb.utils.NetworkUtils;

import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class Login extends AsyncTask<String, Void, String> {

    private Context context;
    private WeakReference<TextView> errorTextView;

    public Login(Context context, TextView errorLoginTextView){
        this.context = context;
        errorTextView = new WeakReference<>(errorLoginTextView);
    }

    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtils.login(strings[0], strings[1]);
    }

    @Override
    protected void onPostExecute(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONObject data = jsonObject.getJSONObject("0");
            int i = 0;
            Integer id = null;
            String mail = null;
            String surname = null;
            String name = null;
            String adress = null;
            String ville = null;
            String cp = null;
            String statut = null;
            String ppLink = null;
            String token = null;
            String httpStatut = null;

            try{
                httpStatut = jsonObject.getString("status");
                id = data.getInt("id");
                surname = data.getString("surname");
                name = data.getString("name");
                mail = data.getString("login");
                adress = data.getString("adress");
                ville = data.getString("ville");
                cp = data.getString("cp");
                statut = data.getString("statut");
                ppLink = data.getString("ppLink");
                token = jsonObject.getString("token");
            }catch (Exception e) {
                e.printStackTrace();
            }
            if(httpStatut != "400"){
                User user = new User(id, surname, name, mail, adress, cp, ville, statut, ppLink, token);

                //redirection vers activity Dashboard
                Intent intent = new Intent(context, Dashboard.class);
                intent.putExtra("user", user);
                context.startActivity(intent);
            }
            //Le mot de passe ou l'identifiant est incorrect
        } catch (Exception e) {
            e.printStackTrace();
            errorTextView.get().setText(R.string.error_login_message);
        }

        super.onPostExecute(s);
    }
}
