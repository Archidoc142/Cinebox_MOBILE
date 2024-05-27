/****************************************
 * Fichier : LoginActivity.java
 * Auteur : Hicham Abekiri
 * Fonctionnalité : Connexion au compte - MCc6
 * Date : 18/05/2024
 *
 * Vérification :
 * Date     Nom     Approuvé
 * =========================================================
 *
 *
 * Historique de modifications :
 * Date     Nom     Description
 * =========================================================
 * 18/05/2024   Hicham   Création de l'activité
 *
 * ****************************************/

package com.example.cinebox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener
{
    private EditText userInput;
    private EditText pwdInput;
    private TextView inscriptionTxt;
    private Button loginBtn;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        View nav = findViewById(R.id.nav);

        TextView filmsNav = nav.findViewById(R.id.filmsNav);
        TextView grignotinesNav = nav.findViewById(R.id.grignotinesNav);
        TextView tarifsNav = nav.findViewById(R.id.tarifsNav);
        TextView connexion = nav.findViewById(R.id.connexionNav);
        ImageView imageUser = nav.findViewById(R.id.imageProfil);
        ImageView listNav = nav.findViewById(R.id.listNav);
        ImageView cartNav = nav.findViewById(R.id.cartNav);
        progressBar = findViewById(R.id.progressBar);

        if (Utilisateur.getInstance() != null) {
            connexion.setText("Se déconnecter");
            //imageUser.setImageBitmap(Utilisateur.getInstance().getImage());
        } else {
            imageUser.setVisibility(View.INVISIBLE);
            //listNav.setVisibility(View.INVISIBLE);
            cartNav.setVisibility(View.INVISIBLE);
        }

        filmsNav.setOnClickListener(this);
        connexion.setOnClickListener(this);
        grignotinesNav.setOnClickListener(this);
        tarifsNav.setOnClickListener(this);
        listNav.setOnClickListener(this);

        userInput = findViewById(R.id.username_input);
        pwdInput = findViewById(R.id.password_input);
        loginBtn = findViewById(R.id.login_btn);
        inscriptionTxt = findViewById(R.id.inscriptionTxt);

        loginBtn.setOnClickListener(this);
        inscriptionTxt.setOnClickListener(this);
    }

    public void onClick(View v)
    {
        if(v.getId() == R.id.inscriptionTxt)
        {
            Intent intent = new Intent(LoginActivity.this, InscriptionActivity.class);
            startActivity(intent);
        }
        else if(v.getId() == R.id.login_btn) {
            String username = userInput.getText().toString();
            String pwd = pwdInput.getText().toString();

            if(username.trim().isEmpty() || !username.trim().matches("^([a-z0-9._-]+)@([a-z0-9._-]+)\\.([a-z]{2,6})$"))
            {
                userInput.setError("Veuillez entrer une adresse courriel.");
            }
            else if(pwd.trim().isEmpty())
            {
                pwdInput.setError("Veuillez entrer un mot de passe.");
            }
            else
            {
                loginBtn.setEnabled(false);
                progressBar.setVisibility(View.VISIBLE);
                userInput.setEnabled(false);
                pwdInput.setEnabled(false);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (APIRequests.postLoginUser(username, pwd, LoginActivity.this)) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this, "Bienvenue " + Utilisateur.getInstance().getNom(), Toast.LENGTH_SHORT).show();
                                }
                            });

                            Intent intent = new Intent(LoginActivity.this, AccueilActivity.class);
                            //Intent intent = new Intent(LoginActivity.this, PanierActivity.class);
                            finish();
                            startActivity(intent);
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run()
                                {
                                    Toast.makeText(LoginActivity.this, "Impossible de se connecter.", Toast.LENGTH_SHORT).show();
                                    loginBtn.setEnabled(true);
                                    progressBar.setVisibility(View.INVISIBLE);
                                    userInput.setEnabled(true);
                                    pwdInput.setEnabled(true);
                                }
                            });
                        }
                    }
                }).start();
            }
        }
        else if (v.getId() == R.id.filmsNav)
        {
            Intent intent = new Intent(LoginActivity.this, FilmsActivity.class);
            startActivity(intent);
        }
        else if (v.getId() == R.id.grignotinesNav)
        {
            Intent intent = new Intent(LoginActivity.this, GrignotinesActivity.class);
            startActivity(intent);
        }
        else if (v.getId() == R.id.tarifsNav)
        {
            Intent intent = new Intent(LoginActivity.this, TarifsActivity.class);
            startActivity(intent);
        }
        else if (v.getId() == R.id.listNav)
        {
            LinearLayout nav_elements = findViewById(R.id.nav_elements);

            if (nav_elements.getVisibility() == View.GONE)
                nav_elements.setVisibility(View.VISIBLE);

            else
                nav_elements.setVisibility(View.GONE);
        }
    }
}