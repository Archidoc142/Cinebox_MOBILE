/****************************************
 * Fichier : AccueilAdapter.java
 * Auteur : Hicham Abekiri
 * Fonctionnalité : Page d'accueil (MAcc0)
 * Date : 17 mai 2024
 *
 * Vérification :
 * Date     Nom     Approuvé
 * =========================================================
 *
 *
 * Historique de modifications :
 * Date     Nom     Description
 * =========================================================
 * 17/05/2024   Hicham  Création de l'adapter pour l'activité Accueil
 *
 * ****************************************/

package com.example.cinebox;

import android.content.Context;

public class AccueilAdapter
{
    public class FilmsAdapterAccueil extends FilmsAdapter
    {
        public FilmsAdapterAccueil(Context context)
        {
            super(context);
        }

        @Override
        public int getItemCount()
        {
            int size = Film.FilmOnArrayList.size();
            return (size > 4 ? 4 : size);
        }
    }

    public class GrignotinesAdapterAccueil extends GrignotinesAdapter
    {
        public GrignotinesAdapterAccueil(Context context)
        {
            super(context);
        }

        @Override
        public int getItemCount()
        {
            int size = Grignotine.GrignotineOnArrayList.size();
            return (size > 4 ? 4 : size);
        }
    }
}
