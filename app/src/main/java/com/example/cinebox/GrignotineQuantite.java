package com.example.cinebox;

import java.util.ArrayList;

public class GrignotineQuantite
{
    //public static ArrayList<GrignotineQuantite> grignotineQuantiteList = new ArrayList<>();
    private int id;
    private Grignotine grignotine;
    private int quantite;

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
}
