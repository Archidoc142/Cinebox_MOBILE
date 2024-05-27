/****************************************
 * Fichier : Tarif
 * Auteur : Amélie Bergeron
 * Fonctionnalité : N/A
 * Date : 14 mai 2024
 * Vérification :
 * Date Nom Approuvé
 * =========================================================
 *
 * Historique de modifications :
 * Date     Nom     Description
 * =========================================================
 * 14 mai 2024, Amélie Bergeron, Création de la classe
 * 26/05/2024   Arthur  Ajout getIdFromCategorie()
 *****************************************/

package com.example.cinebox;

import android.util.Log;

import java.util.ArrayList;

public class Tarif {

    /**
     * Liste statique contenant les tarifs du cinéma
     */
    public static ArrayList<Tarif> TarifOnArrayList = new ArrayList<Tarif>();

    private int id;
    private String categorie;
    private double prix;
    private String description;

    /**
     * Constructeur d'un tarif
     *
     * @param id ID du tarif
     * @param categorie Catégorie du tarif
     * @param prix Prix du tarif (hors taxes)
     * @param description Description du tarif
     */
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

    @Override
    public String toString() {
        return categorie + " - " + String.valueOf(prix) + "$";
    }
}
