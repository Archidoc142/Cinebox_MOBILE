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
 * 26/05/2024   Arthur  Ajout loadFromJSON
 * ****************************************/

package com.example.cinebox;

import org.json.JSONObject;

public class Billet
{
    /**
     * L'ID du prochain billet à créer, initialisé depuis l'API
     */
    private static int nextBilletId = 0;

    private int id;
    private double montant;
    private Tarif tarif;
    private Seance seance;

    /**
     * Constructeur d'un billet
     *
     * @param id ID du billet
     * @param montant Montant du billet (hors taxes)
     * @param tarifId ID du tarif du billet (enfant, étudiant, adulte...)
     * @param seanceId ID de la séance
     */
    public Billet(int id, double montant, int tarifId, int seanceId)
    {
        this.id = id;
        this.montant = montant;
        this.tarif = Tarif.TarifOnArrayList.get(tarifId - 1);
        this.seance = Seance.seancesArrayList.get(seanceId - 1);
    }

    public static int getNextBilletId() {
        return nextBilletId;
    }

    public static void setNextBilletId(int nextBilletId) {
        Billet.nextBilletId = nextBilletId;
    }

    public static void incrementNextBilletId(int value) {
        nextBilletId += value;
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

    public double getMontant() {
        return montant;
    }

    /**
     *
     * @param jsonObject
     * @return
     */
    public static Billet loadFromJSON(JSONObject jsonObject) {
        int id = jsonObject.optInt("id_billet");
        double montant = jsonObject.optDouble("montant_achat");
        String type_billet = jsonObject.optString("type_billet");
        String seanceStr = jsonObject.optString("seance");
        String film = jsonObject.optString("film");
//        String dateHeureAchat = jsonObject.optString("date_heure_achat");     Champs inutil, car on utilise seulement la date de facturation

        int tarifId = Tarif.getIdFromCategorie(type_billet);
        int seanceId = Seance.getIdFromDateAndTitre(seanceStr, film);

        return new Billet(id, montant, tarifId, seanceId);
    }
}
