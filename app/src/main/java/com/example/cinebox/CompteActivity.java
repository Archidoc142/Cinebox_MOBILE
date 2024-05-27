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
 * Date         Nom     Description
 * =========================================================
 * 19/05/2023   Arthur  Test récupération des données depuis BD Infructueux
 * 21/05/2023   Arthur  Ajout fonctionnement de la caméra et modification des champs
 * 22/05/2023   Arthur  Ajout notification après modification information compte
 * 23/05/2024   Arthur  Save modification into DB
 * 24/05/2024   Arthur  Début sauvgearde vers API
 * 25/05/2024   Arthur  Fin sauvegarde vers API + Ajout Validation de champs avant sauvegarde + Début Ajout loadFromJSON
 * 26/05/2024   Arthur  AJout Achat + infos insertIntoBD + Affichage historique d'achat from DB
 * ****************************************/

package com.example.cinebox;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CompteActivity extends AppCompatActivity implements RecyclerViewInterface, View.OnClickListener {
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
    ImageView saveIntoAPI;

    Utilisateur user;
    private static final String CHANNEL_ID = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compte);

        View nav = findViewById(R.id.nav);

        TextView films = nav.findViewById(R.id.filmsNav);
        TextView grignotines = nav.findViewById(R.id.grignotinesNav);
        TextView tarifs = nav.findViewById(R.id.tarifsNav);
        TextView connexion = nav.findViewById(R.id.connexionNav);
        ImageView imageUser = nav.findViewById(R.id.imageProfil);
        ImageView listNav = nav.findViewById(R.id.listNav);
        ImageView cartNav = nav.findViewById(R.id.cartNav);
        TextView mainTitle = nav.findViewById(R.id.mainTitle);

        if (Utilisateur.getInstance() != null) {
            connexion.setText("Se déconnecter");
            if(Utilisateur.getInstance().getImage() != null)
                imageUser.setImageBitmap(Utilisateur.getInstance().getImage());
            else
                imageUser.setImageResource(R.drawable.profil_image);
        } else {
            listNav.setVisibility(View.INVISIBLE);
            cartNav.setVisibility(View.INVISIBLE);
        }

        connexion.setOnClickListener(this);
        films.setOnClickListener(this);
        grignotines.setOnClickListener(this);
        tarifs.setOnClickListener(this);
        listNav.setOnClickListener(this);
        mainTitle.setOnClickListener(this);
        cartNav.setOnClickListener(this);

/*================================================GESTION INFORMATION COMPTE============================================================================*/
        user = Utilisateur.getInstance();

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
        saveIntoAPI = (ImageView) findViewById(R.id.saveIntoAPI);

        avatar = (ImageView) findViewById(R.id.avatar);

        //Si il y à une image dan DB alors on l'affiche comme avatar
        if(user.getImage() != null)
            avatar.setImageBitmap(user.getImage());
        else
            avatar.setImageResource(R.drawable.profil_image);

        fillTextView();
        fillEditText();
/*================================================GESTION HISTORIQUE ACHAT ET ENVOI INFO COMPTE VERS API============================================================================*/
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Récupération des achats depuis l'API
                    APIRequests.getAchats(user.getToken(), CompteActivity.this);
                    insertAchatsToDB(CompteActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(CompteActivity.this, "Erreur lors de la récupération des achats.", Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int size = Achat.HistoriqueAchats.size();
                        Integer[] ids = new Integer[size];
                        String[] dates = new String[size];
                        double[] montants = new double[size];

                        for (int i = 0; i < size; i++) {
                            Achat achat = Achat.HistoriqueAchats.get(i);
                            ids[i] = achat.getId();

                            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
                            Date date = null;
                            try {
                                date = inputFormat.parse(achat.getDate());
                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }
                            String formattedDate = null;
                            // Extraire data
                            formattedDate = outputFormat.format(date);
                            dates[i] = formattedDate;
                            montants[i] = achat.getMontantFinal();
                        }

                        HistoriqueAchatAdapter myAdapter = new HistoriqueAchatAdapter(CompteActivity.this, ids, dates, montants, CompteActivity.this);
                        recyclerView.setAdapter(myAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(CompteActivity.this));
                        saveIntoAPI.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        boolean response = APIRequests.postUserUpdate();
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (response) {
                                                    Toast.makeText(CompteActivity.this, "Envoi vers l'API à fonctionné!", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(CompteActivity.this, "Échec de l'envoi vers l'API!", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                }).start();
                            }
                        });
                    }
                });
            }
        }).start();
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
                Context context = view.getContext();

                showText();

                //Ce bout de code permet de "refresh" la page sans transition pour afficher les modifications
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
                //TODO: clean each editText ?
                makeNotification(context, "Statut des informations du compte", "Les modifications apporté n'ont été annulé");

            }
        });

        saveEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateEditext()) {
                    Context context = view.getContext();

                    user.setNomUtilisateur(nomUtilisateurEdit.getText().toString());
                    user.setPrenom(prenomUserEdit.getText().toString());
                    user.setNom(nomUserEdit.getText().toString());
                    user.setCourriel(courrielUserEdit.getText().toString());
                    user.setTelephone(phoneUserEdit.getText().toString());

                    saveToDB();

                    //Ce bout de code permet de "refresh" la page sans transition pour afficher les modifications
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);

                    makeNotification(context, "Statut des informations du compte", "Vos information de compte on bien été sauvegardé");
                } else {
                    Toast.makeText(CompteActivity.this, "Certains champs sont invalides", Toast.LENGTH_SHORT).show();
                }
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
                user.setImage(image_data);

                avatar.setImageBitmap(user.getImage());

                saveToDB();
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.filmsNav) {
            Intent intent = new Intent(CompteActivity.this, FilmsActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.grignotinesNav) {
            Intent intent = new Intent(CompteActivity.this, GrignotinesActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.tarifsNav) {
            Intent intent = new Intent(CompteActivity.this, TarifsActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.cartNav) {
            Intent intent = new Intent(CompteActivity.this, PanierActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.mainTitle) {
            Intent intent = new Intent(CompteActivity.this, AccueilActivity.class);
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
                Intent intent = new Intent(CompteActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }
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

    public void makeNotification(Context context, String titleNotif, String contentNotif) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.profil_image)
                .setContentTitle(titleNotif)
                .setContentText(contentNotif)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        //Nécéssaire pour afficher la notification
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(0, builder.build());

    }

    public void saveToDB() {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);

        sqLiteManager.updateUtilisateur(user);
    }

    public void fillEditText() {
        nomUtilisateurEdit.setText(user.getNomUtilisateur());
        prenomUserEdit.setText(user.getPrenom());
        nomUserEdit.setText(user.getNom());
        courrielUserEdit.setText(user.getCourriel());
        phoneUserEdit.setText(user.getTelephone());
    }
    public void fillTextView() {
        nomUtilisateur.setText(user.getNomUtilisateur());
        prenomUser.setText(user.getPrenom());
        nomUser.setText(user.getNom());
        courrielUser.setText(user.getCourriel());
        phoneUser.setText(user.getTelephone());     //TODO: voir pour afficher le format suivant si temps suffisant: "(123) 456 7890"
    }

    public boolean validateEditext() {
        boolean formInputValid = true;

        // Validation nomUtilisateur
        String nomUtilisateurPattern = "^[a-z0-9._-]+";
        if (nomUtilisateurEdit.getText().toString().trim().isEmpty()) {
            nomUtilisateurEdit.setError("Veuillez entrer un nom d'utilisateur.");
            formInputValid = false;
        } else if (!nomUtilisateurEdit.getText().toString().trim().matches(nomUtilisateurPattern)) {
            nomUtilisateurEdit.setError("Le format du nom d'utilisateur entré est invalide.");
            formInputValid = false;
        } else if (nomUtilisateurEdit.getText().toString().trim().length() > 24) {
            nomUtilisateurEdit.setError("Le nom d'utilisateur ne peut pas dépasser 24 caractères.");
            formInputValid = false;
        }

        // Validation prenomUser
        String nomPrenomPattern = "^[A-ZÀ-Ü][a-zà-ù-]+$";
        if (prenomUserEdit.getText().toString().trim().isEmpty()) {
            prenomUserEdit.setError("Veuillez entrer un prénom.");
            formInputValid = false;
        } else if (!prenomUserEdit.getText().toString().trim().matches(nomPrenomPattern)) {
            prenomUserEdit.setError("Le format du prénom entré est invalide.");
            formInputValid = false;
        } else if (prenomUserEdit.getText().toString().trim().length() > 128) {
            prenomUserEdit.setError("Le prénom ne peut pas dépasser 128 caractères.");
            formInputValid = false;
        }

        // Validation nomUser
        if (nomUserEdit.getText().toString().trim().isEmpty()) {
            nomUserEdit.setError("Veuillez entrer un nom de famille.");
            formInputValid = false;
        } else if (!nomUserEdit.getText().toString().trim().matches(nomPrenomPattern)) {
            nomUserEdit.setError("Le format du nom de famille entré est invalide.");
            formInputValid = false;
        } else if (nomUserEdit.getText().toString().trim().length() > 128) {
            nomUserEdit.setError("Le nom de famille ne peut pas dépasser 128 caractères.");
            formInputValid = false;
        }

        // Validation courrielUser
        String emailPattern = "^([a-z0-9._-]+)@([a-z0-9._-]+)\\.([a-z]{2,6})$";
        if (courrielUserEdit.getText().toString().trim().isEmpty()) {
            courrielUserEdit.setError("Veuillez entrer un courriel.");
            formInputValid = false;
        } else if (!courrielUserEdit.getText().toString().trim().matches(emailPattern)) {
            courrielUserEdit.setError("Le format du courriel entré est invalide.");
            formInputValid = false;
        }

        // Validation phoneUser
        String phonePattern = "^([0-9\\s\\-\\+\\(\\)]*)$";
        if (phoneUserEdit.getText().toString().trim().isEmpty()) {
            phoneUserEdit.setError("Veuillez entrer un numéro de téléphone.");
            formInputValid = false;
        } else if (!phoneUserEdit.getText().toString().trim().matches(phonePattern)) {
            phoneUserEdit.setError("Le numéro de téléphone ne respecte pas le format attendu.");
            formInputValid = false;
        } else if (phoneUserEdit.getText().toString().trim().length() < 10) {
            phoneUserEdit.setError("Le numéro de téléphone doit comporter au moins 10 caractères.");
            formInputValid = false;
        }

        return formInputValid;
    }

    private void insertAchatsToDB(Context context) {
        for (Achat achat : Achat.HistoriqueAchats) {
            SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(context);
            sqLiteManager.insertAchat(achat);

            if (achat.getBilletsAchat() != null) {
                for (Billet billet : achat.getBilletsAchat()) {
                    sqLiteManager.insertBillet(billet, achat);
                }
            }

            if (achat.getGrignotinesAchat() != null) {
                for (GrignotineQuantite grignotine : achat.getGrignotinesAchat()) {
                    sqLiteManager.insertGrignotineQte(grignotine, achat);
                }
            }
        }
    }
}
