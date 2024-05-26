/****************************************
 * Fichier : GrignotineQuantite.java
 * Auteur : ????
 * Fonctionnalité : ????
 * Date : ???
 * Vérification :
 * Date     Nom     Approuvé
 * =========================================================
 *
 * Historique de modifications :
 * Date     Nom     Description
 * =========================================================
 * 25/05/2024   Arthur  Ajout loadFromJSON()
 * ****************************************/

package com.example.cinebox;

import org.json.JSONObject;

import java.util.ArrayList;

public class GrignotineQuantite
{
    //public static ArrayList<GrignotineQuantite> grignotineQuantiteList = new ArrayList<>();
    private int id;
    private Grignotine grignotine;
    private int quantite;

    public GrignotineQuantite() {
        this.id = 0;
        this.grignotine = null;
        this.quantite = 0;
    }

    public GrignotineQuantite(Grignotine grignotine, int quantite)
    {
        this.grignotine = grignotine;
        this.quantite = quantite;
        this.id = Panier.Snack_PanierList.size();
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
