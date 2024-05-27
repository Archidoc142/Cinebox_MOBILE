/****************************************
 * Fichier : Panier
 * Auteur : Amélie Bergeron
 * Fonctionnalité : N/A
 * Date : 14 mai 2024
 * Vérification :
 * Date Nom Approuvé
 * =========================================================
 * Historique de modifications :
 * Date Nom Description
 * =========================================================****************************************/

package com.example.cinebox.Panier;

import android.content.Context;

import com.example.cinebox.Achat.Achat;
import com.example.cinebox.Achat.AchatUnsuccessfulException;
import com.example.cinebox.Billet.Billet;
import com.example.cinebox.Grignotine.GrignotineQuantite;

import java.util.ArrayList;

public class Panier {

    /**
     * Tous les billets du panier
     */
    public static ArrayList<Billet> Billet_PanierList = new ArrayList<Billet>();

    /**
     * Toutes les grignotines du panier avec leurs quantités
     */
    public static ArrayList<GrignotineQuantite> Snack_PanierList = new ArrayList<GrignotineQuantite>();

    private static final double TPS = 0.05;
    private static final double TVQ = 0.09975;

    /**
     * Calcule le total des produits du panier (hors taxes)
     * @return Le total des produits du panier (hors taxes)
     */
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

    /**
     * Envoyer l'achat à la BD et à l'API
     * @param context Contexte de l'application
     * @throws AchatUnsuccessfulException Si l'achat ne fonctionne pas
     */
    public static void payerPanier(Context context) throws AchatUnsuccessfulException
    {
        Achat achat = new Achat(true);
        achat.envoyerAchat(context);
    }

    public static void viderPanier() {
        Snack_PanierList.clear();
        Billet_PanierList.clear();
    }

    /**
     * Si le panier est vide
     * @return si le panier est vide
     */
    public static boolean isEmpty()
    {
        return Billet_PanierList.isEmpty() && Snack_PanierList.isEmpty();
    }

    /**
     * Supprime une grignotine avec sa quantité du panier
     * @param id ID de la grignotine
     */
    public static void removeGrignotine(int id)
    {
        for (GrignotineQuantite gq: Snack_PanierList)
        {
            if(gq.getId() == id)
            {
                Snack_PanierList.remove(gq);
                gq.getGrignotine().addOne();
                break;
            }
        }
    }

    /**
     * Supprime un billet du panier
     * @param id ID du billet
     */
    public static void removeBillet(int id)
    {
        for (Billet b: Billet_PanierList)
        {
            if(b.getId() == id)
            {
                Billet_PanierList.remove(b);
                break;
            }
        }
    }
}
