/****************************************
 * Fichier : CompteActivity.java
 * Auteur : Arthur Andrianjafisolo
 * Fonctionnalité : Fonctionnement global de la page affichant les information du compte utilisateur
 * Date : 18/05/2023
 *
 * Vérification :
 * Date     Nom     Approuvé
 * =========================================================
 *
 *
 * Historique de modifications :
 * Date     Nom     Description
 * =========================================================
 * 19/05/2023   Arthur  Test récupération des données depuis BD Infructueux
 * 21/05/2023   Arthur  Ajout fonctionnement de la caméra et modification des champs
 *
 * ****************************************/

package com.example.cinebox;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class CompteActivity extends AppCompatActivity implements RecyclerViewInterface {
    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    private Bitmap image_data;
    private ImageView avatar;
    TextView nomUtilisateur;
    EditText nomUtilisateurEdit;
    TextView prenomUser;
    EditText prenomUserEdit;
    TextView nomUser;
    EditText nomUserEdit;
    TextView courrielUser;
    EditText courrielUserEdit;
    TextView phoneUser;
    EditText phoneUserEdit;
    ImageView editButton;
    ImageView skipEditButton;
    ImageView saveEditButton;
    private Integer[] id = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    private String[] date = {"2023/01/01", "2023/02/01", "2023/03/01", "2023/04/01", "2023/05/01", "2023/06/01", "2023/01/01", "2023/02/01", "2023/03/01", "2023/04/01", "2023/05/01", "2023/06/01"};
    private double[] montant = {10.5, 20.0, 15.75, 30.0, 25.5, 50.0, 10.5, 20.0, 15.75, 30.0, 25.5, 50.0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compte);

//        Utilisateur user = Utilisateur.getInstance();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerAchats);
        nomUtilisateur = (TextView) findViewById(R.id.nomUtilisateur);
        nomUtilisateurEdit = (EditText) findViewById(R.id.nomUtilisateurEdit);
        prenomUser = (TextView) findViewById(R.id.prenomUser);
        prenomUserEdit = (EditText) findViewById(R.id.prenomUserEdit);
        nomUser = (TextView) findViewById(R.id.nomUser);
        nomUserEdit = (EditText) findViewById(R.id.nomUserEdit);
        courrielUser = (TextView) findViewById(R.id.courrielUser);
        courrielUserEdit = (EditText) findViewById(R.id.courrielUserEdit);
        phoneUser = (TextView) findViewById(R.id.phoneUser);
        phoneUserEdit = (EditText) findViewById(R.id.phoneUserEdit);

        ImageView cameraButton = (ImageView) findViewById(R.id.cameraButton);

        editButton = (ImageView) findViewById(R.id.editButton);
        skipEditButton = (ImageView) findViewById(R.id.skipEdit);
        saveEditButton = (ImageView) findViewById(R.id.saveEdit);

        avatar = (ImageView) findViewById(R.id.avatar);

        HistoriqueAchatAdapter myAdapter = new HistoriqueAchatAdapter(this, id, date, montant, this);

        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /*nomUtilisateur.setText(user.getNomUtilisateur());
        prenomUser.setText(user.getPrenom());
        nomUser.setText(user.getNom());
        courrielUser.setText(user.getCourriel());
        phoneUser.setText(user.getTelephone());*/

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askCameraPermissions();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEdit();
            }
        });

        skipEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showText();
            }
        });

        saveEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Save Data in DB
            }
        });
    }

    public void askCameraPermissions() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        } else {
            openCamera();
        }
    }
    public void openCamera() {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, CAMERA_REQUEST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == RESULT_OK && data != null) {
                // Retrieve the image from the intent data
                image_data = (Bitmap) data.getExtras().get("data");
                avatar.setImageBitmap(image_data);
            } else {
                Toast.makeText(this, "Failed to capture image", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grandResults) {
        if (requestCode == CAMERA_PERM_CODE) {
            if (grandResults.length < 0 && grandResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Camera Permission is required to use Camera", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grandResults);
    }
    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(CompteActivity.this, ConsulterAchatActivity.class);

        //intent.inputExtra....

        startActivity(intent);
    }

    public void showEdit(){
        editButton.setVisibility(View.GONE);
        skipEditButton.setVisibility(View.VISIBLE);
        saveEditButton.setVisibility(View.VISIBLE);

        nomUtilisateur.setVisibility(View.GONE);
        nomUtilisateurEdit.setVisibility(View.VISIBLE);
        prenomUser.setVisibility(View.GONE);
        prenomUserEdit.setVisibility(View.VISIBLE);
        nomUser.setVisibility(View.GONE);
        nomUserEdit.setVisibility(View.VISIBLE);
        courrielUser.setVisibility(View.GONE);
        courrielUserEdit.setVisibility(View.VISIBLE);
        phoneUser.setVisibility(View.GONE);
        phoneUserEdit.setVisibility(View.VISIBLE);
    }

    public void showText(){
        editButton.setVisibility(View.VISIBLE);
        skipEditButton.setVisibility(View.GONE);
        saveEditButton.setVisibility(View.GONE);

        nomUtilisateur.setVisibility(View.VISIBLE);
        nomUtilisateurEdit.setVisibility(View.GONE);
        prenomUser.setVisibility(View.VISIBLE);
        prenomUserEdit.setVisibility(View.GONE);
        nomUser.setVisibility(View.VISIBLE);
        nomUserEdit.setVisibility(View.GONE);
        courrielUser.setVisibility(View.VISIBLE);
        courrielUserEdit.setVisibility(View.GONE);
        phoneUser.setVisibility(View.VISIBLE);
        phoneUserEdit.setVisibility(View.GONE);
    }
}
