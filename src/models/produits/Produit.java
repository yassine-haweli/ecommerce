package com.ecommerce.models.produits;

/**
 * Classe abstraite Produit - représente un produit général
 * Applique : Encapsulation, Abstraction
 */
public abstract class Produit implements Comparable<Produit> {

    // Encapsulation : attributs privés
    private String reference;
    private String nom;
    private double prix;

    // Constructeur
    public Produit(String reference, String nom, double prix) {
        this.reference = reference;
        this.nom = nom;
        this.prix = prix;
    }

    // Getters et Setters
    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }

    // Méthode abstraite pour calculer le prix final (selon le type de produit)
    public abstract double calculerPrixFinal();

    // Méthode abstraite pour obtenir la catégorie
    public abstract String getCategorie();

    // Comparable pour le tri par prix
    @Override
    public int compareTo(Produit autre) {
        return Double.compare(this.calculerPrixFinal(), autre.calculerPrixFinal());
    }

    // equals et hashCode basés sur la référence (pour les Sets)
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Produit)) return false;
        Produit p = (Produit) obj;
        return this.reference.equals(p.reference);
    }

    @Override
    public int hashCode() {
        return reference.hashCode();
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - Prix: %.2f TND (Final: %.2f TND) | %s",
                reference, nom, prix, calculerPrixFinal(), getCategorie());
    }
}
