package com.furrioxx.connexiongsb.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.furrioxx.connexiongsb.R;
import com.furrioxx.connexiongsb.async.GetCost;
import com.furrioxx.connexiongsb.async.GetCostSheet;
import com.furrioxx.connexiongsb.async.GetPreciseCost;
import com.furrioxx.connexiongsb.async.UpdateCost;
import com.furrioxx.connexiongsb.entity.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;

public class UpdateCostActivity extends AppCompatActivity {

    private User user;
    private String TAG = "CostDetail";
    private String idFrais, idFicheFrais, imagePath;
    private Uri imageURI;
    private BottomNavigationView bottomNavigationView;
    private EditText libelleInput, montantInput, timingInput;
    private ImageView justifImageView;
    private Button updateJustifBtn, updateCostBtn;
    private static final int PICK_IMAGE_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_cost);

        libelleInput = findViewById(R.id.libelleInput);
        montantInput = findViewById(R.id.montantInput);
        timingInput = findViewById(R.id.timingInput);
        justifImageView = findViewById(R.id.justifImageView);
        updateJustifBtn = findViewById(R.id.updateJustifBtn);
        updateCostBtn = findViewById(R.id.updateCostBtn);

        Intent intent = getIntent();
        if (intent != null){
            //récupération de l'utilisateur
            user = intent.getParcelableExtra("user");
            if (user != null){
                idFrais = intent.getStringExtra(GetCost.ID_FRAIS);
                idFicheFrais = intent.getStringExtra(GetCost.ID_FICHE_FRAIS);
                initialize();
            }
        }
    }

    private void initialize(){

        this.setNavigation();

        String[] params = {user.getMail(), user.getToken(), idFrais.toString()};
        new GetPreciseCost(libelleInput, montantInput, timingInput, justifImageView, updateJustifBtn).execute(params);

        updateJustifBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent selectFile = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(selectFile, PICK_IMAGE_REQUEST);
            }
        });

        updateCostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendUpdateCost();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //récupération de l'image de la partie 'Autre'
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            try{
                imageURI = data.getData();
                imagePath = getPathFromURI(imageURI);
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageURI);
                justifImageView.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }

        }
    }

    private String getPathFromURI(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        String filePath = null;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                filePath = cursor.getString(columnIndex);
            }
            cursor.close();
        }
        return filePath;
    }

    private void sendUpdateCost(){

        String libelle = libelleInput.getText().toString();
        String montant = montantInput.getText().toString();
        String timing = timingInput.getText().toString();
        //le path de link Justif (imagePath) + idFrais + idFicheFrais

        String[] params = {user.getMail(), user.getToken(),user.getId().toString(), libelle, montant, timing, idFrais, idFicheFrais, imagePath};
        new UpdateCost(user, idFicheFrais, this).execute(params);
    }

    private void setNavigation(){
        //bottom navigation
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        //set wich item is selected
        bottomNavigationView.setSelectedItemId(R.id.home);

        //création des listener de la navigation
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();
                if (itemId == R.id.add) {
                    startActivity(new Intent(getApplicationContext(), AddCostSheetActivity.class).putExtra("user", user));
                    overridePendingTransition(0,0);
                    return true;
                } else if (itemId == R.id.home) {
                    startActivity(new Intent(getApplicationContext(), DashboardVisitorActivity.class).putExtra("user", user));
                    overridePendingTransition(0,0);
                    return true;
                } else if (itemId == R.id.profil) {
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class).putExtra("user", user));
                    overridePendingTransition(0,0);
                    return true;
                }
                return false;
            }
        });
    }
}