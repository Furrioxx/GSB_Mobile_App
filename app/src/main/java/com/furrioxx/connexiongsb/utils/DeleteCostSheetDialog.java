package com.furrioxx.connexiongsb.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import com.furrioxx.connexiongsb.R;
import com.furrioxx.connexiongsb.async.DeleteCostSheet;
import com.furrioxx.connexiongsb.entity.User;

public class DeleteCostSheetDialog extends DialogFragment {

    private User user;
    private String idFicheFrais;
    private Context context;
    public DeleteCostSheetDialog(User userConnected, String idFicheFrais, Context activityContext){
        this.user = userConnected;
        this.idFicheFrais = idFicheFrais;
        this.context = activityContext;
    }
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction.
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.diaog_delete_cost_sheet_title)
                .setPositiveButton(R.string.validate, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //user valide la supression de la fiche de frais
                        String[] params = {user.getMail(), user.getToken(), idFicheFrais};
                        new DeleteCostSheet(context, user).execute(params);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancels the dialog.
                    }
                });
        // Create the AlertDialog object and return it.
        return builder.create();
    }
}
