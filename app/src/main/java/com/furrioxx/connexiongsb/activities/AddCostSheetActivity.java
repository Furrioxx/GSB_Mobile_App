package com.furrioxx.connexiongsb.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import com.furrioxx.connexiongsb.R;
import com.furrioxx.connexiongsb.entity.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;

public class AddCostSheetActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private User user;
    private Switch switchTransportInput;
    private TextView kmInputTextview, InputMontantAutreTextview, InputFileTransportAutreTextview;
    private Button InputFileTransportAutre, autreFileBtn;
    private EditText kmInput, InputMontantAutre;
    private ImageView otherFileImageView, transportFileImageView;
    private static final int PICK_IMAGE_REQUEST_OTHER = 1;
    private static final int PICK_IMAGE_REQUEST_TRANSPORT = 2;
    private final String TAG = "AddCostSheet";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cost_sheet);

        //transport
        switchTransportInput = findViewById(R.id.switchTransportInput);

        kmInputTextview = findViewById(R.id.kmInputTextview);
        InputMontantAutreTextview = findViewById(R.id.InputMontantTransportAutreTextview);
        InputFileTransportAutreTextview = findViewById(R.id.InputFileTransportAutreTextview);

        kmInput = findViewById(R.id.kmInput);
        InputMontantAutre = findViewById(R.id.InputMontantTransportAutre);
        InputFileTransportAutre = findViewById(R.id.InputFileTransportAutre);

        transportFileImageView = findViewById(R.id.transportFileImageView);

        //autre
        autreFileBtn = findViewById(R.id.autreFileBtn);
        otherFileImageView = findViewById(R.id.otherFileImageView);

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
                    InputMontantAutre.setVisibility(View.VISIBLE);
                    InputFileTransportAutreTextview.setVisibility(View.VISIBLE);
                    InputFileTransportAutre.setVisibility(View.VISIBLE);

                    kmInputTextview.setVisibility(View.GONE);
                    kmInput.setVisibility(View.GONE);

                }else{
                    switchTransportInput.setText(R.string.switch_car_text);

                    InputMontantAutreTextview.setVisibility(View.GONE);
                    InputMontantAutre.setVisibility(View.GONE);
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //récupération de l'image de la partie 'Autre'
        if(requestCode == PICK_IMAGE_REQUEST_OTHER && resultCode == RESULT_OK && data != null && data.getData() != null){
            try{
                Uri imageUri = data.getData();
                Log.d(TAG,imageUri.toString());
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                otherFileImageView.setVisibility(View.VISIBLE);
                otherFileImageView.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }

        }
        //récupération de l'image de la partie 'Transport Autre'
        if(requestCode == PICK_IMAGE_REQUEST_TRANSPORT && resultCode == RESULT_OK && data != null && data.getData() != null){
            try{
                Uri imageUri = data.getData();
                Log.d(TAG,imageUri.toString());
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                transportFileImageView.setVisibility(View.VISIBLE);
                transportFileImageView.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }

        }
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
}