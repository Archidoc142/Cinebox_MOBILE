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

import java.util.ArrayList;

public class Utilisateur
{
    private static Utilisateur instance;

    private int token;
    private int id;
    private String nom;
    private String prenom;
    private String nomUtilisateur;
    private String courriel;
    private String telephone;
    //private String motDePasse;
    private byte[] image;

    private Utilisateur (String token, int id, String nom, String prenom, String nomUtilisateur, String courriel, String telephone)
    {
        this.nom = nom;
        this.prenom = prenom;
        this.nomUtilisateur = nomUtilisateur;
        this.courriel = courriel;
        this.telephone = telephone;
        //this.motDePasse = motDePasse;
        //this.image = image;
    }

    public static void setInstance(String token, int id, String nom, String prenom, String nomUtilisateur, String courriel, String telephone)
    {
        if(instance == null)
            instance = new Utilisateur(token, id, nom, prenom, nomUtilisateur, courriel, telephone);
    }

    public static void logOutUser()
    {
        instance = null;
    }

    public static Utilisateur getInstance()
    {
        return instance;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
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
    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
