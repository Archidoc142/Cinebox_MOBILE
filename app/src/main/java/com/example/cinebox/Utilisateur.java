/****************************************
 * Fichier : Utilisateur
 * Auteur : Antoine Auger, Hicham Abekiri
 * Fonctionnalité : N/A
 * Date : 14 mai 2024
 * Vérification :
 * Date Nom Approuvé
 * =========================================================
 * Historique de modifications :
 * Date Nom Description
 * =========================================================****************************************/

package com.example.cinebox;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;

public class Utilisateur
{
    private static Utilisateur instance;

    private String token;
    private int id;
    private String nom;
    private String prenom;
    private String nomUtilisateur;
    private String courriel;
    private String telephone;
    private Bitmap image;

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

    public static void initUser(Context context, String token, int id, String nom, String prenom, String nomUtilisateur, String courriel, String telephone, Bitmap image)
    {
        setInstance(token, id, nom, prenom, nomUtilisateur, courriel, telephone, image);

        SQLiteManager sql = SQLiteManager.instanceOfDatabase(context);

        if(!sql.userExistsInDB())
            addUserToDB(context);
    }

    private static void setInstance(String token, int id, String nom, String prenom, String nomUtilisateur, String courriel, String telephone, Bitmap image)
    {
        if(instance == null)
        {
            instance = new Utilisateur(token, id, nom, prenom, nomUtilisateur, courriel, telephone, image);
        }
    }

    public static boolean loggedIn(Context context)
    {
        SQLiteManager sql = SQLiteManager.instanceOfDatabase(context);

        if(sql.userExistsInDB())
        {
            APIRequests api = new APIRequests();
            APIRequests.TokenValidRunnable tokenValidRunnable = api.new TokenValidRunnable();

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

    public static void logOutUser(Context context)
    {
        instance = null;

        SQLiteManager sql = SQLiteManager.instanceOfDatabase(context);
        sql.clearUserDB();
    }

    public static void addUserToDB(Context context)
    {
        SQLiteManager sql = SQLiteManager.instanceOfDatabase(context);
        sql.insertUserToDB();
    }

    public static Utilisateur getInstance()
    {
        return instance;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    public int getId() {
        return id;
    }

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
/*
    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
*/
    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
