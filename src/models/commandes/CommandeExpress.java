package com.ecommerce.models.commandes;

/**
 * CommandeExpress - avec frais de livraison fixes
 * Applique : Héritage, Polymorphisme, Override
 */
public class CommandeExpress extends Commande {

    private static final double FRAIS_LIVRAISON = 15.0; // frais fixes en TND

    public CommandeExpress(String numeroCommande, String clientNom) {
        super(numeroCommande, clientNom);
    }

    /**
     * Total = somme des prix finaux + frais de livraison express
     */
    @Override
    public double calculerTotal() {
        double sousTotal = getListeProduits().stream()
                .mapToDouble(p -> p.calculerPrixFinal())
                .sum();
        return sousTotal + FRAIS_LIVRAISON;
    }

    @Override
    public String getTypeLivraison() {
        return "LIVRAISON EXPRESS (24h) +15.00 TND";
    }

    public static double getFraisLivraison() {
        return FRAIS_LIVRAISON;
    }
}
