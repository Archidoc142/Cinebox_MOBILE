/****************************************
 * Fichier : Billet.java
 * Auteur : Arthur Andrianjafisolo
 * Fonctionnalité : Object billet permettant de stocker les info de chaque billet vendu pour détail dans ConsulterAchatActivity
 * Date : 19/05/2023
 *
 * Vérification :
 * Date     Nom     Approuvé
 * =========================================================
 *
 *
 * Historique de modifications :
 * Date     Nom     Description
 * =========================================================
 *
 * ****************************************/

package com.example.cinebox;

public class Billet
{
    private int id;
    private float montant;
    private Tarif tarif;
    private Seance seance;
    private Achat achat;

    public Tarif getTarif() {
        return tarif;
    }

    public Seance getSeance() {
        return seance;
    }

    public int getId() {
        return id;
    }

    public float getMontant() {
        return montant;
    }

    public Achat getAchat() {
        return achat;
    }
}
