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

package com.example.cinebox.Compte;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cinebox.APIRequests;
import com.example.cinebox.R;

import org.json.JSONObject;

public class InscriptionActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    private static final int REQUEST_GPS = 1;
    LocationManager locationManager;
    Bitmap bmp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        // Nav
        View nav = findViewById(R.id.nav);

        TextView connexion = nav.findViewById(R.id.connexionNav);
        ImageView listNav = nav.findViewById(R.id.listNav);
        ImageView cartNav = nav.findViewById(R.id.cartNav);
        ImageView imageUser = nav.findViewById(R.id.imageProfil);

        if (Utilisateur.getInstance() != null) {
            connexion.setText("Se déconnecter");
            //imageUser.setImageBitmap(Utilisateur.getInstance().getImage());
        } else {
            imageUser.setVisibility(View.INVISIBLE);
            //listNav.setVisibility(View.INVISIBLE);
            cartNav.setVisibility(View.INVISIBLE);
        }

        connexion.setOnClickListener(this);

        Button inscriptionBtn = findViewById(R.id.inscriptionBtn);
        Button camera = findViewById(R.id.camera);

        inscriptionBtn.setOnClickListener(this);
        camera.setOnClickListener(this);
    }

    private boolean isFormValid()
    {
        EditText nom = findViewById(R.id.nom);
        EditText prenom = findViewById(R.id.prenom);
        EditText username = findViewById(R.id.username);
        EditText courriel = findViewById(R.id.courriel);
        EditText telephone = findViewById(R.id.telephone);
        EditText mot_de_passe = findViewById(R.id.mot_de_passe);
        EditText confirmation = findViewById(R.id.confirmation);

        // Validation nomUtilisateur
        String nomUtilisateurPattern = "^[a-z0-9._-]+";
        if (username.getText().toString().trim().isEmpty()) {
            username.setError("Veuillez entrer un nom d'utilisateur.");
            return false;
        } else if (!username.getText().toString().trim().matches(nomUtilisateurPattern)) {
            username.setError("Le format du nom d'utilisateur entré est invalide.");
            return false;
        } else if (username.getText().toString().trim().length() > 24) {
            username.setError("Le nom d'utilisateur ne peut pas dépasser 24 caractères.");
            return false;
        }
        else
        {
            // Validation prenomUser
            String nomPrenomPattern = "^[A-ZÀ-Ü][a-zà-ù-]+$";
            if (prenom.getText().toString().trim().isEmpty()) {
                prenom.setError("Veuillez entrer un prénom.");
                return false;
            } else if (!prenom.getText().toString().trim().matches(nomPrenomPattern)) {
                prenom.setError("Le format du prénom entré est invalide.");
                return false;
            } else if (prenom.getText().toString().trim().length() > 128) {
                prenom.setError("Le prénom ne peut pas dépasser 128 caractères.");
                return false;
            }

            // Validation nomUser
            if (nom.getText().toString().trim().isEmpty()) {
                nom.setError("Veuillez entrer un nom de famille.");
                return false;
            } else if (!nom.getText().toString().trim().matches(nomPrenomPattern)) {
                nom.setError("Le format du nom de famille entré est invalide.");
                return false;
            } else if (nom.getText().toString().trim().length() > 128) {
                nom.setError("Le nom de famille ne peut pas dépasser 128 caractères.");
                return false;
            }
            else
            {
                // Validation courrielUser
                String emailPattern = "^([a-z0-9._-]+)@([a-z0-9._-]+)\\.([a-z]{2,6})$";
                if (courriel.getText().toString().trim().isEmpty()) {
                    courriel.setError("Veuillez entrer un courriel.");
                    return false;
                } else if (!courriel.getText().toString().trim().matches(emailPattern)) {
                    courriel.setError("Le format du courriel entré est invalide.");
                    return false;
                }

                else
                {
                    // Validation phoneUser
                    String phonePattern = "^([0-9\\s\\-\\+\\(\\)]*)$";
                    if (telephone.getText().toString().trim().isEmpty()) {
                        telephone.setError("Veuillez entrer un numéro de téléphone.");
                        return false;
                    } else if (!telephone.getText().toString().trim().matches(phonePattern)) {
                        telephone.setError("Le numéro de téléphone ne respecte pas le format attendu.");
                        return false;
                    } else if (telephone.getText().toString().trim().length() < 10) {
                        telephone.setError("Le numéro de téléphone doit comporter au moins 10 caractères.");
                        return false;
                    }
                    else
                    {
                        if (mot_de_passe.getText().toString().trim().isEmpty() || confirmation.getText().toString().trim().isEmpty()  ) {
                            mot_de_passe.setError("Veuillez entrer un mot de passe.");
                            return false;
                        } else if (!mot_de_passe.getText().toString().equals(confirmation.getText().toString())) {
                            mot_de_passe.setError("Mots de passe différents.");
                            return false;
                        } else if (mot_de_passe.getText().toString().trim().length() < 8) {
                            mot_de_passe.setError("Le mot de passe au moins 10 caractères.");
                            return false;
                        }
                        else
                        {
                            return true;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.camera) {
            askCameraPermissions();

        } else if (v.getId() == R.id.inscriptionBtn) {

            EditText nom = findViewById(R.id.nom);
            EditText prenom = findViewById(R.id.prenom);
            EditText username = findViewById(R.id.username);
            EditText courriel = findViewById(R.id.courriel);
            EditText telephone = findViewById(R.id.telephone);
            EditText mot_de_passe = findViewById(R.id.mot_de_passe);

            if(isFormValid())
            {
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

                            if(response)
                                APIRequests.postLoginUser(courriel.getText().toString(), mot_de_passe.getText().toString(), InscriptionActivity.this);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run()
                                {
                                    if (response) {
                                        Toast.makeText(InscriptionActivity.this, "Inscription effectuée", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(InscriptionActivity.this, LoginActivity.class);
                                        finish();
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        Toast.makeText(InscriptionActivity.this, "Erreur lors de l'inscription.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }).start();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                getLocalisation();
            }


            /*
            if (!mot_de_passe.getText().toString().isEmpty() &&
                    !confirmation.getText().toString().isEmpty() &&
                    !nom.getText().toString().isEmpty() &&
                    !prenom.getText().toString().isEmpty() &&
                    !username.getText().toString().isEmpty() &&
                    !courriel.getText().toString().isEmpty() &&
                    !telephone.getText().toString().isEmpty()) {



                if (mot_de_passe.getText().toString().equals(confirmation.getText().toString())) {

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

                                if(response)
                                    APIRequests.postLoginUser(courriel.getText().toString(), mot_de_passe.getText().toString(), InscriptionActivity.this);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run()
                                    {
                                        if (response) {
                                            Toast.makeText(InscriptionActivity.this, "Inscription effectuée", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(InscriptionActivity.this, LoginActivity.class);
                                            finish();
                                            startActivity(intent);
                                        }
                                        else
                                        {
                                            Toast.makeText(InscriptionActivity.this, "Erreur lors de l'inscription.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }).start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    getLocalisation();
                } else {
                    Toast.makeText(InscriptionActivity.this, "Not the same password", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(InscriptionActivity.this, "Un ou plusieurs champs vide!!!", Toast.LENGTH_SHORT).show();
            }*/



        } else if (v.getId() == R.id.connexionNav) {
            if (Utilisateur.getInstance() != null) {
                if (Utilisateur.getInstance() != null) {
                    Utilisateur.logOutUser(this);

                    View nav = findViewById(R.id.nav);
                    TextView connexion = nav.findViewById(R.id.connexionNav);
                    connexion.setText("Se connecter");
                    finish();
                }

                Intent intent = new Intent(InscriptionActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }
    }

    private void getLocalisation() {
        //PERMISSION DU GPS -----------
        ActivityCompat.requestPermissions(this, new String[]
                {android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_GPS);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //vérif si le GPS est actif ou non
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // activer le gps
            activerGPS();
        }

        //Vérif permissions à nouveau
        if(ActivityCompat.checkSelfPermission(InscriptionActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(InscriptionActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]
                    {android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_GPS);
        }
        else {
            Location locationGps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location locationNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location locationPassive = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if(locationGps != null) {
                double Slat = locationGps.getLatitude();
                double Slon = locationGps.getLongitude();
                Toast.makeText(this, Slat + " x " + Slon, Toast.LENGTH_SHORT).show();
            }
            else if(locationNetwork != null) {
                double Slat = locationNetwork.getLatitude();
                double Slon = locationNetwork.getLongitude();
                Toast.makeText(this, Slat + " x " + Slon, Toast.LENGTH_SHORT).show();
            }
            else if(locationPassive != null) {
                double Slat = locationPassive.getLatitude();
                double Slon = locationPassive.getLongitude();
                Toast.makeText(this, Slat + " x " + Slon, Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "Location introuvable", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void activerGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Activer le GPS").setCancelable(false).setPositiveButton("OUI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("ANNULER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
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
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE) {
            bmp = (Bitmap) data.getExtras().get("data");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults.length < 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Camera Permission is Required to use Camera", Toast.LENGTH_SHORT).show();
            }
        }
    }
}