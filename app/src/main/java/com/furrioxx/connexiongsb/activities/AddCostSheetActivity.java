package com.furrioxx.connexiongsb.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.furrioxx.connexiongsb.R;
import com.furrioxx.connexiongsb.async.AddCostSheet;
import com.furrioxx.connexiongsb.entity.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;

public class AddCostSheetActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private User user;
    private Switch switchTransportInput;
    private TextView kmInputTextview, InputMontantAutreTextview, InputFileTransportAutreTextview;
    private Button InputFileTransportAutre, autreFileBtn, submitNewCostSheet;
    private EditText beginDateInput, endDateInput ,kmInput,InputMontantTransportAutre,herbergementNumberInput, hebergementPriceInput, alimentationNumberInput,alimentationPriceInput , autreLibelleInput, autreMontantInput;
    private ImageView otherFileImageView, transportFileImageView;
    private Uri imageTransportURI, imageAutreURI;
    private String imageTransportPath, imageAutrePath;
    private static final int PICK_IMAGE_REQUEST_OTHER = 1;
    private static final int PICK_IMAGE_REQUEST_TRANSPORT = 2;
    private final String TAG = "AddCostSheet";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cost_sheet);

        //Dates
        beginDateInput = findViewById(R.id.beginDateInput);
        endDateInput = findViewById(R.id.endDateInput);

        //transport
        switchTransportInput = findViewById(R.id.switchTransportInput);

        kmInputTextview = findViewById(R.id.kmInputTextview);
        InputMontantAutreTextview = findViewById(R.id.InputMontantTransportAutreTextview);
        InputFileTransportAutreTextview = findViewById(R.id.InputFileTransportAutreTextview);

        kmInput = findViewById(R.id.kmInput);
        InputMontantTransportAutre = findViewById(R.id.InputMontantTransportAutre);
        InputFileTransportAutre = findViewById(R.id.InputFileTransportAutre);

        transportFileImageView = findViewById(R.id.transportFileImageView);

        //hébergement
        herbergementNumberInput = findViewById(R.id.herbergementNumberInput);
        hebergementPriceInput = findViewById(R.id.hebergementPriceInput);

        //alimentation
        alimentationNumberInput = findViewById(R.id.alimentationNumberInput);
        alimentationPriceInput = findViewById(R.id.alimentationPriceInput);

        //autre
        autreLibelleInput = findViewById(R.id.autreLibelleInput);
        autreMontantInput = findViewById(R.id.autreMontantInput);

        autreFileBtn = findViewById(R.id.autreFileBtn);
        otherFileImageView = findViewById(R.id.otherFileImageView);

        //bouter envoyer
        submitNewCostSheet = findViewById(R.id.submitNewCostSheet);

        Intent intent = getIntent();
        if (intent != null){
            //récupération de l'utilisateur
            user = intent.getParcelableExtra("user");
            if (user != null){
                initialize();
            }
        }
    }

    private void initialize(){
        this.setNavigation();

        switchTransportInput.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    switchTransportInput.setText(R.string.switch_other_text);

                    InputMontantAutreTextview.setVisibility(View.VISIBLE);
                    InputMontantTransportAutre.setVisibility(View.VISIBLE);
                    InputFileTransportAutreTextview.setVisibility(View.VISIBLE);
                    InputFileTransportAutre.setVisibility(View.VISIBLE);

                    kmInputTextview.setVisibility(View.GONE);
                    kmInput.setVisibility(View.GONE);

                }else{
                    switchTransportInput.setText(R.string.switch_car_text);

                    InputMontantAutreTextview.setVisibility(View.GONE);
                    InputMontantTransportAutre.setVisibility(View.GONE);
                    InputFileTransportAutreTextview.setVisibility(View.GONE);
                    InputFileTransportAutre.setVisibility(View.GONE);

                    kmInputTextview.setVisibility(View.VISIBLE);
                    kmInput.setVisibility(View.VISIBLE);
                }
            }
        });

        //ouvrir la sélection de fichier
        autreFileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent selectFileOther = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(selectFileOther, PICK_IMAGE_REQUEST_OTHER);
            }
        });
        InputFileTransportAutre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent selectFileOther = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(selectFileOther, PICK_IMAGE_REQUEST_TRANSPORT);
            }
        });

        //lorsque le bouton de creation de fiche de frais est cliqué
        submitNewCostSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendCostSheet();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //récupération de l'image de la partie 'Autre'
        if(requestCode == PICK_IMAGE_REQUEST_OTHER && resultCode == RESULT_OK && data != null && data.getData() != null){
            try{
                imageAutreURI = data.getData();
                imageAutrePath = getPathFromURI(imageTransportURI);
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageAutreURI);
                otherFileImageView.setVisibility(View.VISIBLE);
                otherFileImageView.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }

        }
        //récupération de l'image de la partie 'Transport Autre'
        if(requestCode == PICK_IMAGE_REQUEST_TRANSPORT && resultCode == RESULT_OK && data != null && data.getData() != null){
            try{
                imageTransportURI = data.getData();
                imageTransportPath = getPathFromURI(imageTransportURI);
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageTransportURI);
                transportFileImageView.setVisibility(View.VISIBLE);
                transportFileImageView.setImageBitmap(bitmap);
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

    private void setNavigation(){
        //bottom navigation
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        //set wich item is selected
        bottomNavigationView.setSelectedItemId(R.id.add);

        //création des listener de la navigation
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();
                if (itemId == R.id.add) {
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

    private void sendCostSheet(){
        String beginDate = beginDateInput.getText().toString();
        String endDate = endDateInput.getText().toString();

        String kmTransport = kmInput.getText().toString();
        String montantTransport = InputMontantTransportAutre.getText().toString();
        //le fichier image (imageTransportPath)

        String herbergementNumber = herbergementNumberInput.getText().toString();
        String hebergementPrice = hebergementPriceInput.getText().toString();

        String restaurantNumber = alimentationNumberInput.getText().toString();
        String restaurantPrice = alimentationPriceInput.getText().toString();

        String libelleOther = autreMontantInput.getText().toString();
        String priceOther = autreMontantInput.getText().toString();
        //le fichier image (imageAutrePath)



        if(!beginDate.equals("") && !endDate.equals("")){
            String[] params = {user.getMail(), user.getToken(), imageTransportPath, imageAutrePath};
            new AddCostSheet().execute(params);
        }else{
            Toast.makeText(getApplicationContext(), "Vous devez entrez des dates", Toast.LENGTH_LONG).show();
        }
    }
}