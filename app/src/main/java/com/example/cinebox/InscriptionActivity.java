/****************************************
 * Fichier : Inscription
 * Auteur : Antoine Auger
 * Fonctionnalité : Cc1 / MCc5
 * Date : 14 mai 2024
 * Vérification :
 * Date Nom Approuvé
 * =========================================================
 * Historique de modifications :
 * Date Nom Description
 * =========================================================****************************************/

package com.example.cinebox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONObject;

public class InscriptionActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    Bitmap bmp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        // Nav
        View nav = findViewById(R.id.nav);

        TextView films = nav.findViewById(R.id.filmsNav);
        TextView grignotines = nav.findViewById(R.id.grignotinesNav);
        TextView tarifs = nav.findViewById(R.id.tarifsNav);
        TextView connexion = nav.findViewById(R.id.connexionNav);
        ImageView imageUser = nav.findViewById(R.id.imageInstanceFilm);
        ImageView listNav = nav.findViewById(R.id.listNav);
        ImageView cartNav = nav.findViewById(R.id.cartNav);

        if (Utilisateur.getInstance() != null) {
            connexion.setText("Se déconnecter");
            imageUser.setImageBitmap(Utilisateur.getInstance().getImage());
        } else {
            imageUser.setVisibility(View.INVISIBLE); // Juste pour inscription
            listNav.setVisibility(View.INVISIBLE);
            cartNav.setVisibility(View.INVISIBLE);
        }

        connexion.setOnClickListener(this);
        films.setOnClickListener(this);
        grignotines.setOnClickListener(this);
        tarifs.setOnClickListener(this);
        listNav.setOnClickListener(this);

        Button inscriptionBtn = findViewById(R.id.inscriptionBtn);
        Button camera = findViewById(R.id.camera);

        inscriptionBtn.setOnClickListener(this);
        camera.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.camera) {
            askCameraPermissions();
        } else if (v.getId() == R.id.inscriptionBtn) {

            EditText mot_de_passe = findViewById(R.id.mot_de_passe);
            EditText confirmation = findViewById(R.id.confirmation);

            if (mot_de_passe.getText().toString().equals(confirmation.getText().toString())) {
                EditText nom = findViewById(R.id.nom);
                EditText prenom = findViewById(R.id.prenom);
                EditText username = findViewById(R.id.username);
                EditText courriel = findViewById(R.id.courriel);
                EditText telephone = findViewById(R.id.telephone);

                JSONObject data = new JSONObject();
                try {
                    data.put("nom_utilisateur", username.getText().toString());
                    data.put("nom_famille", nom.getText().toString());
                    data.put("prenom", prenom.getText().toString());
                    data.put("courriel", courriel.getText().toString());
                    data.put("telephone", telephone.getText().toString());
                    data.put("mdp", mot_de_passe.getText().toString());

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            boolean response = APIRequests.addUser(data);
                            System.out.println("Response: " + response);

                            if (response) {
                                APIRequests.postLoginUser(courriel.getText().toString(), mot_de_passe.getText().toString(), InscriptionActivity.this);
                            }
                        }
                    }).start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(InscriptionActivity.this, "Not the same password", Toast.LENGTH_SHORT).show();
            }

        } else if (v.getId() == R.id.filmsNav) {
            Intent intent = new Intent(InscriptionActivity.this, FilmsActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.grignotinesNav) {
            Intent intent = new Intent(InscriptionActivity.this, GrignotinesActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.tarifsNav) {
            Intent intent = new Intent(InscriptionActivity.this, TarifsActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.listNav) {
            LinearLayout nav_elements = findViewById(R.id.nav_elements);
            if (nav_elements.getVisibility() == View.GONE) {
                nav_elements.setVisibility(View.VISIBLE);
            } else {
                nav_elements.setVisibility(View.GONE);
            }
        } else if (v.getId() == R.id.connexionNav) {
            if (Utilisateur.getInstance() != null) {
                Utilisateur.logOutUser(this);

                View nav = findViewById(R.id.nav);
                TextView connexion = nav.findViewById(R.id.connexionNav);
                connexion.setText("Se connecter");
            } else {
                Intent intent = new Intent(InscriptionActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }
    }

    private void askCameraPermissions() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        } else {
            openCamera();
        }
    }

    private void openCamera() {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, CAMERA_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CAMERA_REQUEST_CODE) {
            bmp = (Bitmap) data.getExtras().get("data");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults.length < 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            }
            else {
                Toast.makeText(this, "Camera Permission is Required to use Camera", Toast.LENGTH_SHORT).show();
            }
        }
    }
}