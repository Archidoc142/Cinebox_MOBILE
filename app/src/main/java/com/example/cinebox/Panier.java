package com.example.cinebox;

import android.content.Context;

import java.util.ArrayList;

public class Panier {
    public static ArrayList<Billet> Billet_PanierList = new ArrayList<Billet>();
    public static ArrayList<GrignotineQuantite> Snack_PanierList = new ArrayList<GrignotineQuantite>();

    private static double TPS = 0.05;
    private static double TVQ = 0.09975;

    public static double getTotal() {
        double total = 0;

        for(Billet b: Panier.Billet_PanierList) {
            total += b.getMontant();
        }

        for(GrignotineQuantite g: Panier.Snack_PanierList) {
            total += g.getGrignotine().getPrix_vente();
        }

        return total;
    }

    public static double getTPS() {
        return (getTotal() * TPS);
    }

    public static double getTVQ() {
        return (getTotal() * TVQ);
    }

    public static double getTotalFinal() {
        return  getTotal() * (TPS + TVQ);
    }

    public static void payerPanier(Context context)
    {
        Achat achat = new Achat(context);
        achat.envoyerAchat(context);

        Billet_PanierList.clear();
        Snack_PanierList.clear();

        //faire un Toast "paiement effectué"
    }

    public static void viderPanier() {
        Snack_PanierList.clear();
        Billet_PanierList.clear();
    }

    public static boolean isEmpty()
    {
        return Billet_PanierList.isEmpty() && Snack_PanierList.isEmpty();
    }


}
