package com.furrioxx.connexiongsb.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.furrioxx.connexiongsb.entity.User;
import com.furrioxx.connexiongsb.utils.NetworkUtils;

public class ChangePassword extends AsyncTask <String, Void, String> {

    private User user;
    private Context context;
    private String TAG = "ChangePassword";

    public ChangePassword(User currentUser, Context ctx){
        this.user = currentUser;
        this.context = ctx;
    }
    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtils.changePassword(strings[0], strings[1], strings[2], strings[3], strings[4], strings[5]);
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d(TAG, s);
        super.onPostExecute(s);
    }
}
