/**
 * @author Antoine, Hicham, Arthur
 * @version 1.0
 * 
 * Cette classe représente l'utilisateur connecté dans l'application.
 */

/****************************************
 * Fichier : Utilisateur
 * Auteur : Antoine Auger, Hicham Abekiri, Arthur
 * Fonctionnalité : N/A
 * Date : 14 mai 2024
 *
 * Vérification :
 * Date Nom Approuvé
 * =========================================================
 *
 * Historique de modifications :  
 * Date         Nom     Description
 * =========================================================
 * 14/05/2024   Hicham  Création de la classe + système d'authentification
 * 23/05/2024   Arthur  Ajout fonction bitmapToArray() pour sauvegarder l'image dans la BD as Blob
 * ****************************************/

package com.example.cinebox;

import android.content.Context;
import android.graphics.Bitmap;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 *
 */
public class Utilisateur
{
    /**
     * Instance de l'utilisateur connecté
     */
    private static Utilisateur instance;

    private String token;
    private int id;
    private String nom;
    private String prenom;
    private String nomUtilisateur;
    private String courriel;
    private String telephone;
    private Bitmap image;

    /**
     *  Constructeur de l'utilisateur, utilisé seulement dans la méthode setInstance
     * @param token Token de l'utilisateur
     * @param id ID de l'utilsiateur
     * @param nom Nom de famille de l'utilisateur
     * @param prenom Prénom de l'utilisateur
     * @param nomUtilisateur Nom du compté utilisateur 
     * @param courriel Courriel de l'utilisateur
     * @param telephone Numéro de téléphone de l'utilisateur
     * @param image Photo de l'utilisateur
     */
    public Utilisateur (String token, int id, String nom, String prenom, String nomUtilisateur, String courriel, String telephone, Bitmap image)
    {
        this.nom = nom;
        this.prenom = prenom;
        this.nomUtilisateur = nomUtilisateur;
        this.courriel = courriel;
        this.telephone = telephone;
        this.token = token;
        this.id = id;
        this.image = image;
    }
    
    /** Initialise une session utilisateur dans l'application et insère l'utilisateur dans la BD 
     * @param context Contexte de l'application
     * @param token Token de l'utilisateur
     * @param id ID de l'utilsiateur
     * @param nom Nom de famille de l'utilisateur
     * @param prenom Prénom de l'utilisateur
     * @param nomUtilisateur Nom du compté utilisateur 
     * @param courriel Courriel de l'utilisateur
     * @param telephone Numéro de téléphone de l'utilisateur
     * @param image Photo de l'utilisateur
     */
    public static void initUser(Context context, String token, int id, String nom, String prenom, String nomUtilisateur, String courriel, String telephone, Bitmap image)
    {
        setInstance(token, id, nom, prenom, nomUtilisateur, courriel, telephone, image);

        SQLiteManager sql = SQLiteManager.instanceOfDatabase(context);

        if(!sql.userExistsInDB())
            addUserToDB(context);
    }

    /**
     * Définit la nouvelle instance de l'utilisateur connecté
     * @param token Token de l'utilisateur
     * @param id ID de l'utilsiateur
     * @param nom Nom de famille de l'utilisateur
     * @param prenom Prénom de l'utilisateur
     * @param nomUtilisateur Nom du compté utilisateur 
     * @param courriel Courriel de l'utilisateur
     * @param telephone Numéro de téléphone de l'utilisateur
     * @param image Photo de l'utilisateur
     */
    private static void setInstance(String token, int id, String nom, String prenom, String nomUtilisateur, String courriel, String telephone, Bitmap image)
    {
        if(instance == null)
        {
            instance = new Utilisateur(token, id, nom, prenom, nomUtilisateur, courriel, telephone, image);
        }
    }

    /**
     * Détermine si l'utilisateur s'est déjà connecté à l'application avant 
     * en récupérant son token de la BD locale et en vérifiant sa validité avec l'API.
     *
     * @param context Contexte de l'application
     * @return true si le token dans la BD est encore valide, false si ce n'est pas le cas
     */
    public static boolean loggedIn(Context context)
    {
        SQLiteManager sql = SQLiteManager.instanceOfDatabase(context);

        if(sql.userExistsInDB())
        {
            APIRequests.TokenValidRunnable tokenValidRunnable = new APIRequests.TokenValidRunnable();

            Thread thread = new Thread(tokenValidRunnable);
            thread.start();

            try
            {
                thread.join();
            }
            catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }

            if(!tokenValidRunnable.isValid())
                logOutUser(context);

            return tokenValidRunnable.isValid();
        }
        else
        {
            logOutUser(context);
            return false;
        }

    }

    /**
     * Met fin à la session et déconnecte l'utilisateur connecté de l'application
     * @param context Contexte de l'application
     */
    public static void logOutUser(Context context)
    {
        instance = null;

        SQLiteManager sql = SQLiteManager.instanceOfDatabase(context);
        sql.clearDB();
    }

    /**
     * Insère l'utilisateur connecté à la BD locale
     * @param context Contexte de l'application
     */
    public static void addUserToDB(Context context)
    {
        SQLiteManager sql = SQLiteManager.instanceOfDatabase(context);
        sql.insertUserToDB();
    }

    /**
     * Récupérer l'utilisateur connecté
     * @return objet Utilisateur représentant l'utilisateur connecté
     */
    public static Utilisateur getInstance()
    {
        return instance;
    }

    /**
     * Récupérer le token d'un utilisateur
     * @return le token de l'utilisateur
     */
    public String getToken() {
        return token;
    }

    /**
     * Définir le token d'un utilisateur
     * @param token Le token à définir
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Récupérer le nom d'utilisateur
     * @return le nom d'utilisateur
     */
    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    /**
     * Définir le nom d'utilisateur
     * @param nomUtilisateur Le nom d'utilisateur à définir
     */
    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    /**
     * Récupérer l'ID d'un utilisateur
     * @return l'ID de l'utilisateur
     */
    public int getId() {
        return id;
    }

    /**
     * Définir l'ID de l'utilisateur
     * @param id l'ID de l'utilisateur
     */
    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getCourriel() {
        return courriel;
    }

    public void setCourriel(String courriel) {
        this.courriel = courriel;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    /**
     * Convertit l'image de l'utilisateur à un byte array
     * @return byte array de l'image
     */
    public byte[] bitmapToArray() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        this.image.compress(Bitmap.CompressFormat.PNG, 100, stream);

        byte[] image_data = stream.toByteArray();

        return image_data;
    }

    /**
     * Retourne un utilisateur au format JSON
     * @return Objet JSON de l'utilisateur
     */
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id_client", this.id);
            jsonObject.put("nom_famille", this.nom);
            jsonObject.put("prenom", this.prenom);
            jsonObject.put("nom_utilisateur", this.nomUtilisateur);
            jsonObject.put("email", this.courriel);
            jsonObject.put("telephone", this.telephone);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
