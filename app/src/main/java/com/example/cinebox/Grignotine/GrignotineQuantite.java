/****************************************
 * Fichier : GrignotineQuantite.java
 * Auteur : Hicham Abekiri
 * Fonctionnalité : Classe permettant d'enregistrer des grignotines avec leurs quantités pour l'achat
 * Date : 23/05/2024
 *
 * Vérification :
 * Date     Nom     Approuvé
 * =========================================================
 *
 *
 * Historique de modifications :
 * Date     Nom     Description
 * =========================================================
 * 23/05/2024  Hicham  Création de la classe
 * 25/05/2024   Arthur  Ajout loadFromJSON()
 * ****************************************/

package com.example.cinebox.Grignotine;

import com.example.cinebox.Panier.Panier;

import org.json.JSONObject;

public class GrignotineQuantite
{

    /**
     * ID de l'ensemble grignotine et quantité pour la BD
     */
    private int id;

    /**
     * Grignotine commandée
     */
    private Grignotine grignotine;

    /**
     * Quantité commandée
     */
    private int quantite;

    /**
     * Crée un nouvel ensemble grignotine et quantité
     * @param grignotine Grignotine commandée
     * @param quantite Quantité commandée
     */
    public GrignotineQuantite(Grignotine grignotine, int quantite)
    {
        this.grignotine = grignotine;
        this.quantite = quantite;
        this.id = Panier.Snack_PanierList.size();
    }

    public GrignotineQuantite() {
        this.id = 0;
        this.grignotine = null;
        this.quantite = 0;
    }

    public Grignotine getGrignotine() {
        return grignotine;
    }

    public void setGrignotine(Grignotine grignotine) {
        this.grignotine = grignotine;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public int getId()
    {
        return id;
    }

    public double getPrixQte()
    {
        return grignotine.getPrix_vente() * quantite;
    }

    public static GrignotineQuantite loadFromJSON(JSONObject jsonObject) {
        GrignotineQuantite grignotineQuantite = new GrignotineQuantite();
        grignotineQuantite.grignotine = Grignotine.loadFromJSON(jsonObject.optJSONObject("grignotine"));
        grignotineQuantite.quantite = jsonObject.optInt("quantite");
        return grignotineQuantite;
    }
}
