package com.ecommerce.interfaces;

/**
 * Interface Payable
 * Applique : Interface, Polymorphisme
 */
public interface Payable {

    /**
     * Effectue le paiement du montant donné
     * @param montant montant à payer
     * @return true si le paiement est réussi
     */
    boolean payer(double montant);

    /**
     * Retourne le montant total à payer
     */
    double getMontantTotal();

    /**
     * Retourne le statut du paiement
     */
    String getStatutPaiement();
}
