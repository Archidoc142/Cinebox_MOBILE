/****************************************
 * Fichier : SQLiteManager
 * Auteur : Antoine, Arthur, Hicham
 * Fonctionnalité : N/A
 * Date : 14 mai 2024
 * Vérification :
 * Date Nom Approuvé
 * =========================================================
 * Historique de modifications :
 * Date         Nom     Description
 * =========================================================
 * 23/05/2024   Arthur  Save user into DB
 * 26/05/2024   Arthur  Modification populateList() pour populate Historique d'achat en même temps
 * ****************************************/

package com.example.cinebox;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.cinebox.Achat.Achat;
import com.example.cinebox.Billet.Billet;
import com.example.cinebox.Compte.Utilisateur;
import com.example.cinebox.Grignotine.Grignotine;
import com.example.cinebox.Grignotine.GrignotineQuantite;

import java.io.ByteArrayInputStream;

public class SQLiteManager extends SQLiteOpenHelper {

    private static SQLiteManager sqLiteManager;

    private static final String DATABASE_NAME = "cinebox";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_ACHATS = "historique_achats";
    private static final String TABLE_USER = "utilisateur";
    private static final String TABLE_SNACKS = "grignotines";
    private static final String TABLE_BILLETS = "billets";
    private static final String TABLE_ACHAT_SNACK = "achat_grignotine";

    private Context context;

    public SQLiteManager(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public static SQLiteManager instanceOfDatabase(Context context)
    {
        if (sqLiteManager == null)
        {
            sqLiteManager = new SQLiteManager(context);
        }

        return sqLiteManager;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACHATS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        db.execSQL(
                "CREATE TABLE " + TABLE_USER + " (" +
                        "id INTEGER, " +
                        "token" + " TEXT, " +
                        "nom" + " TEXT," +
                        "prenom" + " TEXT," +
                        "nom_utilisateur" + " TEXT," +
                        "courriel" + " TEXT," +
                        "telephone" + " TEXT," +
                        "image" + " BLOB)"
        );

        db.execSQL(
                "CREATE TABLE " + TABLE_ACHATS + " (" +
                        "id INTEGER, " +
                        "date" + " TEXT," +
                        "total_brut" + " REAL," +
                        "tps" + " REAL," +
                        "tvq" + " REAL," +
                        "total_final" + " REAL)"
        );

        db.execSQL(
                "CREATE TABLE " + TABLE_SNACKS + " (" +
                        "id INTEGER, " +
                        "prix_vente REAL, " +
                        "quantite_disponible INTEGER, " +
                        "categorie TEXT, " +
                        "format TEXT, " +
                        "marque TEXT, " +
                        "image BLOB)"
        );

        db.execSQL(
                "CREATE TABLE " + TABLE_BILLETS + " (" +
                        "id INTEGER, " +
                        "montant_achat REAL, " +
                        "type_tarif TEXT, " +
                        "film TEXT, " +
                        "id_achat INTEGER, " +
                        "FOREIGN KEY(id_achat) REFERENCES " + TABLE_USER + "(id) ON DELETE CASCADE)"
        );

        db.execSQL(
                "CREATE TABLE " + TABLE_ACHAT_SNACK + " (" +
                        "id_achat INTEGER, " +
                        "id_grignotine INTEGER, " +
                        "prix_unitaire REAL, " +
                        "quantite INTEGER, " +
                        "FOREIGN KEY(id_achat) REFERENCES " + TABLE_USER + "(id) ON DELETE CASCADE, " +
                        "FOREIGN KEY(id_grignotine) REFERENCES " + TABLE_SNACKS + "(id) )"
        );
    }

    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {/* the cake is lie*/}

    /**
     * Insertion d'un achat à la base de données locale
     * @param achat L'achat à insérer
     */
    public void insertAchat(Achat achat)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("id", achat.getId());
        contentValues.put("date", achat.getDate());
        contentValues.put("total_brut", achat.getmontantBrut());
        contentValues.put("tps", achat.getTps());
        contentValues.put("tvq", achat.getTvq());
        contentValues.put("total_final", achat.getMontantFinal());

        db.insert(TABLE_ACHATS, null, contentValues);
    }

    /**
     * Insertion de l'utilisateur connecté à la base de données locale
     */
    public void insertUserToDB()
    {
        Utilisateur user = Utilisateur.getInstance();

        if(user != null)
        {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues val = new ContentValues();
            val.put("id", user.getId());
            val.put("token", user.getToken());
            val.put("nom", user.getNom());
            val.put("prenom", user.getPrenom());
            val.put("nom_utilisateur", user.getNomUtilisateur());
            val.put("courriel", user.getCourriel());
            val.put("telephone", user.getTelephone());

            db.insert(TABLE_USER, null, val);
        }
    }

    /**
     * Récupérer l'utilisateur de la base de données
     */
    public void getUserFromDB()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor result = db.rawQuery("SELECT * FROM " + TABLE_USER, null);

        if (result.getCount() != 0)
        {
            result.moveToFirst();
            int id = result.getInt(0);
            String token = result.getString(1);
            String nom = result.getString(2);
            String prenom = result.getString(3);
            String nomUtilisateur = result.getString(4);
            String courriel = result.getString(5);
            String telephone = result.getString(6);
            byte[] imageBlob = result.getBlob(7);

            Bitmap bitmap = null;

            if(imageBlob != null)
            {
                ByteArrayInputStream imgStream = new ByteArrayInputStream(imageBlob);
                bitmap = BitmapFactory.decodeStream(imgStream);
            }

            Utilisateur.initUser(context, token, id, nom, prenom, nomUtilisateur, courriel, telephone, bitmap);
        }
    }

    /**
     * Vérifie si un utililsateur existe dans la base de données
     * @return si un utililsateur existe dans la base de données
     */
    public boolean userExistsInDB()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        long rows = DatabaseUtils.queryNumEntries(db, TABLE_USER);

        if(rows == 0)
            return false;

        return true;
    }

    public void clearDB()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_USER);
        db.execSQL("DELETE FROM " + TABLE_ACHATS);
        db.execSQL("DELETE FROM " + TABLE_ACHAT_SNACK);
        db.execSQL("DELETE FROM " + TABLE_BILLETS);
    }

    public void updateHistoriqueAchat(Achat achat) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("id", achat.getId());
        contentValues.put("date", achat.getDate());
        contentValues.put("total_brut", achat.getmontantBrut());
        contentValues.put("tps", achat.getTps());
        contentValues.put("tvq", achat.getTvq());
        contentValues.put("total_final", achat.getMontantFinal());

        sqLiteDatabase.update(TABLE_ACHATS, contentValues, "id" + " =?", new String[]{String.valueOf(achat.getId())});
    }

    // à modifier
    public void updateUtilisateur(Utilisateur utilisateur) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("id", utilisateur.getId());
        contentValues.put("nom", utilisateur.getNom());
        contentValues.put("prenom", utilisateur.getPrenom());
        contentValues.put("nom_utilisateur", utilisateur.getNomUtilisateur());
        contentValues.put("courriel", utilisateur.getCourriel());
        contentValues.put("telephone", utilisateur.getTelephone());
        //contentValues.put("mot_de_passe", utilisateur.getMotDePasse());
        contentValues.put("image", utilisateur.bitmapToArray());

        sqLiteDatabase.update(TABLE_USER, contentValues, "id" + " =?", new String[]{String.valueOf(utilisateur.getId())});
    }

    public void populateLists() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Achat.HistoriqueAchats.clear();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_ACHATS, null)) {
            if (result.getCount() != 0) {
                while (result.moveToNext()) {
                    int id = result.getInt(0);
                    String date = result.getString(1);
                    double montant = result.getDouble(5);
                    Achat achat = new Achat(id, date, montant);
                    Achat.HistoriqueAchats.add(achat);
                }
            }
        }

        getUserFromDB();
    }

    /**
     *  Insertion des snacks dans la base de données
     */
    public void insertSnacks() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_SNACKS);

        for (Grignotine g: Grignotine.GrignotineOnArrayList) {
            ContentValues val = new ContentValues();
            val.put("id", g.getId());
            val.put("prix_vente", g.getPrix_vente());
            val.put("quantite_disponible", g.getQte_disponible());
            val.put("categorie", g.getCategorie());
            val.put("format", g.getFormat());
            val.put("marque", g.getMarque());
            val.put("image", g.getImage());

            db.insert(TABLE_SNACKS, null, val);
        }
    }

    /**
     * Insertion d'un achat à la base de donneés
     * @param achat L'achat à insérer
     */
    public void insertAchatFromPanier(Achat achat)
    {
        insertAchat(achat);

        if(!achat.getBilletsAchat().isEmpty())
        {
            for(Billet b: achat.getBilletsAchat())
            {
                insertBillet(b, achat);
            }
        }

        if(!achat.getGrignotinesAchat().isEmpty())
        {
            for(GrignotineQuantite gq: achat.getGrignotinesAchat())
            {
                insertGrignotineQte(gq, achat);
            }
        }
    }

    /**
     * Annule l'insertion de l'achat dans la BD dans le cas où l'envoi vers l'API n'a pas fonctionné
     * @param achat
     */
    public void deleteAchat(Achat achat)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_ACHATS, "id=" + achat.getId(), null);
        db.delete(TABLE_ACHAT_SNACK, "id_achat=" + achat.getId(), null);
        db.delete(TABLE_BILLETS, "id_achat=" + achat.getId(), null);
    }

    /**
     * Insertion d'un billet à la base de données
     * @param billet Le billet à insérer
     * @param achat  L'achat lié au billet
     */
    public void insertBillet(Billet billet, Achat achat)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("id", billet.getId());

        contentValues.put("id_achat", achat.getId());
        //contentValues.put("seance", billet.getSeance().getId());
        contentValues.put("film", billet.getSeance().getFilm().getId());
        contentValues.put("montant_achat", billet.getMontant());
        contentValues.put("type_tarif", billet.getTarif().getCategorie());

        db.insert(TABLE_BILLETS, null, contentValues);
    }

    /**
     * Insertion d'un achat de grignotine à la base de données
     * @param gq La grignotine et sa quantité
     * @param achat L'achat lié
     */
    public void insertGrignotineQte(GrignotineQuantite gq, Achat achat)
    {
        Grignotine g = gq.getGrignotine();
        int qte = gq.getQuantite();

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("id_achat", achat.getId());
        contentValues.put("id_grignotine", g.getId());
        contentValues.put("prix_unitaire", g.getPrix_vente());
        contentValues.put("quantite", qte);

        db.insert(TABLE_ACHAT_SNACK, null, contentValues);
    }
}
