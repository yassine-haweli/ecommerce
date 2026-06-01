package com.ecommerce.models.produits;

/**
 * ProduitElectronique - hérite de Produit
 * Applique : Héritage, Polymorphisme, Override
 */
public class ProduitElectronique extends Produit {

    private int garantie; // en années

    public ProduitElectronique(String reference, String nom, double prix, int garantie) {
        super(reference, nom, prix); // appel constructeur parent
        this.garantie = garantie;
    }

    public int getGarantie() { return garantie; }
    public void setGarantie(int garantie) { this.garantie = garantie; }

    /**
     * Prix final = prix de base + 5% de frais techniques par année de garantie
     */
    @Override
    public double calculerPrixFinal() {
        return getPrix() + (getPrix() * 0.05 * garantie);
    }

    @Override
    public String getCategorie() {
        return "Électronique";
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Garantie: %d an(s)", garantie);
    }
}
