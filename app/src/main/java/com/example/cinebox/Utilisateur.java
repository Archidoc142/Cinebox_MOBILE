package com.example.cinebox;

import java.util.ArrayList;

public class Utilisateur {
    public static ArrayList<Utilisateur> UtilisateurOnArrayList = new ArrayList<Utilisateur>();

    public int id;
    public String nom;
    public String prenom;
    public String nom_utilisateur;
    public String courriel;
    public String telephone;
    public String mot_de_passe;
    private byte[] image;

    public Utilisateur () {
        id = 0;
        nom = null;
        prenom = null;
        nom_utilisateur = null;
        courriel = null;
        telephone = null;
        mot_de_passe = null;
        image = null;
    }

    public Utilisateur (String nom, String prenom, String nom_utilisateur, String courriel, String telephone, String mot_de_passe, byte[] image) {
        this.nom = nom;
        this.prenom = prenom;
        this.nom_utilisateur = nom_utilisateur;
        this.courriel = courriel;
        this.telephone = telephone;
        this.mot_de_passe = mot_de_passe;
        this.image = image;
    }

    public String getNom_utilisateur() {
        return nom_utilisateur;
    }

    public void setNom_utilisateur(String nom_utilisateur) {
        this.nom_utilisateur = nom_utilisateur;
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

    public String getMot_de_passe() {
        return mot_de_passe;
    }

    public void setMot_de_passe(String mot_de_passe) {
        this.mot_de_passe = mot_de_passe;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
