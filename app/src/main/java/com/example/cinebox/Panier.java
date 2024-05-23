package com.example.cinebox;

import java.util.ArrayList;

public class Panier {
    public static ArrayList<Billet> Billet_PanierList = new ArrayList<Billet>();

    /* TODO: Remplacer Grignotine par GrignotineQuantite et adapter le code
             (pour conserver les quantités des snacks pour l'API)

                            vvvvvvvvvv                               */
    public static ArrayList<Grignotine> Snack_PanierList = new ArrayList<Grignotine>();

    public static double getTotal() {
        double total = 0;

        for(Billet b: Panier.Billet_PanierList) {
            //total += b.getMontant_achat();
        }

        for(Grignotine g: Panier.Snack_PanierList) {
            total += g.getPrix_vente();
        }

        return total;
    }

    public static void payerPanier() {
        //ajouter à la BD
        //vider les listes
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
