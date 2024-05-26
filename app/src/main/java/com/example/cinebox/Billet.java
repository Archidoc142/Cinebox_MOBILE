/****************************************
 * Fichier : Billet.java
 * Auteur : Arthur Andrianjafisolo, Hicham Abekiri
 * Fonctionnalité : Object billet permettant de stocker les info de chaque billet vendu pour détail dans ConsulterAchatActivity
 * Date : 19/05/2024
 *
 * Vérification :
 * Date     Nom     Approuvé
 * =========================================================
 *
 *
 * Historique de modifications :
 * Date     Nom     Description
 * =========================================================
 * 25/05/2024  Antoine,Hicham   Modifications pour l'envoi de l'achat
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

    public Billet(int id, float montant, Tarif tarif, Seance seance, Achat achat)
    {
        this.id = id;
        this.montant = montant;
        this.tarif = tarif;
        this.seance = seance;
        this.achat = achat;
    }

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
