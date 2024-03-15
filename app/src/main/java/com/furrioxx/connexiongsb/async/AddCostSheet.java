package com.furrioxx.connexiongsb.async;

import android.os.AsyncTask;
import android.util.Log;

import com.furrioxx.connexiongsb.utils.NetworkUtils;

public class AddCostSheet extends AsyncTask<String, Void, String> {

    private final String TAG = "addCostSheet";
    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtils.addCostSheet(strings[0], strings[1], strings[2]);
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d(TAG, s);
        super.onPostExecute(s);
    }
}
