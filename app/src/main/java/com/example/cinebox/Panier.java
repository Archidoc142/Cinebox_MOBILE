package com.example.cinebox;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
            total += g.getPrixQte();
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
        return  getTotal() + (getTVQ() + getTPS());
    }

    public static void payerPanier(Context context)
    {
        Achat achat = new Achat(context);
        achat.envoyerAchat(context);

        //faire un Toast "paiement effectué"

       // Achat achatPanier = new Achat();


    }

    public static void viderPanier() {
        Snack_PanierList.clear();
        Billet_PanierList.clear();
    }

    public static boolean isEmpty()
    {
        return Billet_PanierList.isEmpty() && Snack_PanierList.isEmpty();
    }

    public static void removeGrignotine(int id)
    {
        for (GrignotineQuantite gq: Snack_PanierList)
        {
            if(gq.getId() == id)
            {
                Snack_PanierList.remove(gq);
                break;
            }
        }
    }


}
