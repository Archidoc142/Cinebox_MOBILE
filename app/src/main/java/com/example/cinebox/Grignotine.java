/****************************************
 * Fichier : Grignotine
 * Auteur : Antoine Auger
 * Fonctionnalité : N/A
 * Date : 16 mai 2024
 * Vérification :
 * Date Nom Approuvé
 * =========================================================
 * Historique de modifications :
 * Date Nom Description
 * 25/05/2024   Arthur  Ajout loadFromJSON()
 * =========================================================****************************************/

package com.example.cinebox;

import org.json.JSONObject;

import java.util.ArrayList;

public class Grignotine {
    /**
     * Liste de tous les grignotines
     */
    public static ArrayList<Grignotine> GrignotineOnArrayList = new ArrayList<Grignotine>();

    private int id;
    private String marque;
    private String categorie;
    private String format;
    private double prix_vente;
    private int qte_disponible;
    private String image;

    public Grignotine() {
        this.id = 0;
        this.marque = null;
        this.categorie = null;
        this.format = null;
        this.prix_vente = 0;
        this.qte_disponible = 0;
        this.image = null;
    }

    public Grignotine(int id, String marque, String categorie, String format, double prix_vente, int qte_disponible, String image) {
        this.id = id;
        this.marque = marque;
        this.categorie = categorie;
        this.format = format;
        this.prix_vente = prix_vente;
        this.qte_disponible = qte_disponible;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public double getPrix_vente() {
        return prix_vente;
    }

    public void setPrix_vente(double prix_vente) {
        this.prix_vente = prix_vente;
    }

    public int getQte_disponible() {
        return qte_disponible;
    }

    public void setQte_disponible(int qte_disponible) {
        this.qte_disponible = qte_disponible;
    }

    public void removeOne()
    {
        this.qte_disponible--;
    }

    public void addOne()
    {
        this.qte_disponible++;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @param jsonObject object JSON contenant les informations de d'une grignotine
     * @return Grignotine object from a JSON object
     */
    public static Grignotine loadFromJSON(JSONObject jsonObject) {
        Grignotine grignotine = new Grignotine();
        grignotine.id = jsonObject.optInt("id");
        grignotine.marque = jsonObject.optString("marque");
        grignotine.categorie = jsonObject.optString("categorie");
        grignotine.format = jsonObject.optString("format");
        grignotine.prix_vente = jsonObject.optDouble("prix_vente");
        grignotine.qte_disponible = jsonObject.optInt("qte_disponible");
        grignotine.image = jsonObject.optString("image");
        return grignotine;
    }
}
