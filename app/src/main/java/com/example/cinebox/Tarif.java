/****************************************
 * Fichier : Tarif
 * Auteur : Amélie
 * Fonctionnalité : N/A
 * Date : 20 mai 2024
 * Vérification :
 * Date Nom Approuvé
 * =========================================================
 * Historique de modifications :
 * Date Nom Description
 * =========================================================****************************************/

package com.example.cinebox;

import java.util.ArrayList;

public class Tarif {
    public static ArrayList<Tarif> TarifOnArrayList = new ArrayList<Tarif>();

    private int id;
    private String categorie;
    private double prix;
    private String description;

    public Tarif(int id, String categorie, double prix, String description) {
        this.id = id;
        this.categorie = categorie;
        this.prix = prix;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
