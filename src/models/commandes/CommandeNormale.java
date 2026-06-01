package com.ecommerce.models.commandes;

/**
 * CommandeNormale - aucun frais supplémentaire
 * Applique : Héritage, Polymorphisme, Override
 */
public class CommandeNormale extends Commande {

    public CommandeNormale(String numeroCommande, String clientNom) {
        super(numeroCommande, clientNom);
    }

    /**
     * Total = somme des prix finaux des produits (pas de frais supplémentaires)
     */
    @Override
    public double calculerTotal() {
        return getListeProduits().stream()
                .mapToDouble(p -> p.calculerPrixFinal())
                .sum();
    }

    @Override
    public String getTypeLivraison() {
        return "LIVRAISON NORMALE (3-5 jours)";
    }
}
