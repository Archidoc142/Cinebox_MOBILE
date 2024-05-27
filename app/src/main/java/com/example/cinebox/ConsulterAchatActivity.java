/****************************************
 * Fichier : ConsulterAchatActivity.java
 * Auteur : Arthur Andrianjafisolo
 * Fonctionnalité : Affichage des détails de chaque facture
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
 *
 * ****************************************/

package com.example.cinebox;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class ConsulterAchatActivity extends AppCompatActivity implements RecyclerViewInterface, View.OnClickListener {
    RecyclerView recyclerViewFilm, recyclerViewSnack;
    private Integer[] idBillet = new Integer[0];
    private String[] nomMovie = new String[0];
    private String[] categorieMovie = new String[0];
    private Integer[] qteMovie = new Integer[0];
    private double[] prixUnitaireMovie = new double[0];

    private Integer[] idSnacks = new Integer[0];
    private String[] nomSnacks = new String[0];
    private String[] categorieSnacks = new String[0];
    private Integer[] qteSnacks = new Integer[0];
    private double[] prixUnitaireSnacks = new double[0];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter_achat);

        TextView datefacture = (TextView) findViewById(R.id.detailTitleDate);
        TextView tpsfacture = (TextView) findViewById(R.id.tpsFacture);
        TextView tvqfacture = (TextView) findViewById(R.id.tvqFacture);
        TextView totalfacture = (TextView) findViewById(R.id.totalFacture);

        recyclerViewFilm = (RecyclerView) findViewById(R.id.recycleBillet);
        recyclerViewSnack = (RecyclerView) findViewById(R.id.recycleSnack);

        // Récupère l'Intent et vérifie s'il n'est pas null
        Intent intent = getIntent();
        if (intent != null) {
            int idAchat = intent.getIntExtra("idAchat", -1);
            if (idAchat != -1) {
                Achat achat = Achat.HistoriqueAchats.get(idAchat);

                tpsfacture.setText(achat.getTps()+"$");
                tvqfacture.setText(achat.getTvq()+"$");
                totalfacture.setText(achat.getMontantFinal()+"$");

                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy/MM/dd");
                Date date = null;
                try {
                    date = inputFormat.parse(achat.getDate());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                String formattedDate = null;
                // Extraire data
                formattedDate = outputFormat.format(date);

                datefacture.setText(formattedDate);
                if (achat != null) {
                    if (achat.getBilletsAchat() != null) {
                        int sizeBillets = achat.getBilletsAchat().size();

                        idBillet = new Integer[sizeBillets];
                        nomMovie = new String[sizeBillets];
                        categorieMovie = new String[sizeBillets];
                        qteMovie = new Integer[sizeBillets];
                        prixUnitaireMovie = new double[sizeBillets];

                        for (int i = 0; i < sizeBillets; i++) {
                            if (!Arrays.asList(idBillet).contains(achat.getBilletsAchat().get(i).getId())) {
                                nomMovie[i] = achat.getBilletsAchat().get(i).getSeance().getFilm().getTitre();
                                categorieMovie[i] = achat.getBilletsAchat().get(i).getTarif().getCategorie();
                                qteMovie[i] = 1;
                                prixUnitaireMovie[i] = achat.getBilletsAchat().get(i).getMontant();
                            } else {
                                qteMovie[i]++;
                            }
                        }
                    }


                    if (achat.getGrignotinesAchat() != null) {
                        int sizeSnacks = achat.getGrignotinesAchat().size();

                        idSnacks = new Integer[sizeSnacks];
                        nomSnacks = new String[sizeSnacks];
                        categorieSnacks = new String[sizeSnacks];
                        qteSnacks = new Integer[sizeSnacks];
                        prixUnitaireSnacks = new double[sizeSnacks];

                        for (int i = 0; i < sizeSnacks; i++) {
                            if (!Arrays.asList(idSnacks).contains(achat.getGrignotinesAchat().get(i).getId())) {
                                nomSnacks[i] = achat.getGrignotinesAchat().get(i).getGrignotine().getMarque();
                                categorieSnacks[i] = achat.getGrignotinesAchat().get(i).getGrignotine().getFormat();
                                qteSnacks[i] = 1;
                                prixUnitaireSnacks[i] = achat.getGrignotinesAchat().get(i).getGrignotine().getPrix_vente();
                            } else {
                                qteSnacks[i]++;
                            }
                        }
                    }
                }
            } else {
                // Gestion de l'erreur : idAchat invalide
                Log.e("ConsulterAchatActivity", "Invalid 'idAchat' received.");
            }
        } else {
            // Gestion de l'erreur : Intent est null
            Log.e("ConsulterAchatActivity", "Intent is null.");
        }

        ItemsAchatAdapter myAdapterFilm = new ItemsAchatAdapter(this, nomMovie, categorieMovie, qteMovie, prixUnitaireMovie, this);
        ItemsAchatAdapter myAdapterSnack = new ItemsAchatAdapter(this, nomSnacks, categorieSnacks, qteSnacks, prixUnitaireSnacks, this);

        recyclerViewFilm.setAdapter(myAdapterFilm);
        recyclerViewSnack.setAdapter(myAdapterSnack);

        recyclerViewFilm.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewSnack.setLayoutManager(new LinearLayoutManager(this));

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
            if (Utilisateur.getInstance().getImage() != null)
                imageUser.setImageBitmap(Utilisateur.getInstance().getImage());
            else
                imageUser.setImageResource(R.drawable.profil_image);
        } else {
            //listNav.setVisibility(View.INVISIBLE);
            cartNav.setVisibility(View.INVISIBLE);
        }

        connexion.setOnClickListener(this);
        films.setOnClickListener(this);
        grignotines.setOnClickListener(this);
        tarifs.setOnClickListener(this);
        listNav.setOnClickListener(this);
        mainTitle.setOnClickListener(this);
        cartNav.setOnClickListener(this);
        imageUser.setOnClickListener(this);
    }

    @Override
    public void onItemClick(int position) {
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.filmsNav) {
            Intent intent = new Intent(ConsulterAchatActivity.this, FilmsActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.grignotinesNav) {
            Intent intent = new Intent(ConsulterAchatActivity.this, GrignotinesActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.tarifsNav) {
            Intent intent = new Intent(ConsulterAchatActivity.this, TarifsActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.cartNav) {
            Intent intent = new Intent(ConsulterAchatActivity.this, PanierActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.mainTitle) {
            Intent intent = new Intent(ConsulterAchatActivity.this, AccueilActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.imageProfil) {
            Intent intent = new Intent(ConsulterAchatActivity.this, CompteActivity.class);
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
                if (Utilisateur.getInstance() != null) {
                    Utilisateur.logOutUser(this);

                    View nav = findViewById(R.id.nav);
                    TextView connexion = nav.findViewById(R.id.connexionNav);
                    connexion.setText("Se connecter");
                    finish();
                }

                Intent intent = new Intent(ConsulterAchatActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }
    }
}
