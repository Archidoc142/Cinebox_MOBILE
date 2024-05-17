/****************************************
 * Fichier : SQLiteManager
 * Auteur : Antoine Auger
 * Fonctionnalité : N/A
 * Date : 14 mai 2024
 * Vérification :
 * Date Nom Approuvé
 * =========================================================
 * Historique de modifications :
 * Date Nom Description
 * =========================================================****************************************/

package com.example.cinebox;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.sql.Blob;

public class SQLiteManager extends SQLiteOpenHelper {

    private static SQLiteManager sqLiteManager;

    private static final String DATABASE_NAME = "cinebox";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME1 = "historique_achats";
    private static final String TABLE_NAME2 = "utilisateur";

    public SQLiteManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SQLiteManager instanceOfDatabase(Context context) {
        if (sqLiteManager == null) {
            sqLiteManager = new SQLiteManager(context);
        }

        return sqLiteManager;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);

        db.execSQL(
                "CREATE TABLE " + TABLE_NAME1 + " (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "date" + " TEXT," +
                        "montant" + " REAL)"
        );

        db.execSQL(
                "CREATE TABLE " + TABLE_NAME2 + " (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "nom" + " TEXT," +
                        "prenom" + " TEXT," +
                        "nom_utilisateur" + " TEXT," +
                        "courriel" + " TEXT," +
                        "telephone" + " TEXT," +
                        "mot_de_passe" + " TEXT," +
                        "image" + " BLOB)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // the cake is lie
    }

    public void ajouterHistoriqueAchat(HistoriqueAchat historiqueAchat, SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("date", historiqueAchat.getDate());
        contentValues.put("montant", historiqueAchat.getMontant());

        db.insert(TABLE_NAME1, null, contentValues);
    }

    public void ajouterUtilisateur(Utilisateur utilisateur, SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("nom", utilisateur.getNom());
        contentValues.put("prenom", utilisateur.getPrenom());
        contentValues.put("nom_utilisateur", utilisateur.getNom_utilisateur());
        contentValues.put("courriel", utilisateur.getCourriel());
        contentValues.put("telephone", utilisateur.getTelephone());
        contentValues.put("mot_de_passe", utilisateur.getMot_de_passe());
        contentValues.put("image", utilisateur.getImage());

        db.insert(TABLE_NAME2, null, contentValues);
    }

    public void updateHistoriqueAchat(HistoriqueAchat historiqueAchat) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("id", historiqueAchat.getId());
        contentValues.put("date", historiqueAchat.getDate());
        contentValues.put("montant", historiqueAchat.getMontant());

        sqLiteDatabase.update(TABLE_NAME1, contentValues, "id" + " =?", new String[]{String.valueOf(historiqueAchat.getId())});
    }

    public void updateUtilisateur(Utilisateur utilisateur) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("id", utilisateur.getId());
        contentValues.put("nom", utilisateur.getNom());
        contentValues.put("prenom", utilisateur.getPrenom());
        contentValues.put("nom_utilisateur", utilisateur.getNom_utilisateur());
        contentValues.put("courriel", utilisateur.getCourriel());
        contentValues.put("telephone", utilisateur.getTelephone());
        contentValues.put("mot_de_passe", utilisateur.getMot_de_passe());
        contentValues.put("image", utilisateur.getImage());

        sqLiteDatabase.update(TABLE_NAME2, contentValues, "id" + " =?", new String[]{String.valueOf(utilisateur.getId())});
    }

    public void populateLists() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        HistoriqueAchat.HistoriqueAchatOnArrayList.clear();
        Utilisateur.UtilisateurOnArrayList.clear();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME1, null)) {
            if (result.getCount() != 0) {
                while (result.moveToNext()) {
                    String date = result.getString(1);
                    float montant = result.getFloat(2);
                    HistoriqueAchat historiqueAchat = new HistoriqueAchat(date, montant);
                    HistoriqueAchat.HistoriqueAchatOnArrayList.add(historiqueAchat);
                }
            }
        }

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME2, null)) {
            if (result.getCount() != 0) {
                while (result.moveToNext()) {
                    String nom = result.getString(1);
                    String prenom = result.getString(2);
                    String nom_utilisateur = result.getString(3);
                    String courriel = result.getString(4);
                    String telephone = result.getString(5);
                    String mot_de_passe = result.getString(6);
                    byte[] image = result.getBlob(7);
                    Utilisateur utilisateur = new Utilisateur(nom, prenom, nom_utilisateur, courriel, telephone, mot_de_passe, image);
                    Utilisateur.UtilisateurOnArrayList.add(utilisateur);
                }
            }
        }
    }
}
