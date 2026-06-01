package com.ecommerce.models.produits;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * ProduitAlimentaire - hérite de Produit
 * Applique : Héritage, Polymorphisme, Override
 */
public class ProduitAlimentaire extends Produit {

    private LocalDate dateExpiration;

    public ProduitAlimentaire(String reference, String nom, double prix, LocalDate dateExpiration) {
        super(reference, nom, prix);
        this.dateExpiration = dateExpiration;
    }

    public LocalDate getDateExpiration() { return dateExpiration; }
    public void setDateExpiration(LocalDate dateExpiration) { this.dateExpiration = dateExpiration; }

    /**
     * Prix final = remise si le produit expire dans moins de 7 jours (-20%)
     * sinon prix normal
     */
    @Override
    public double calculerPrixFinal() {
        long joursRestants = ChronoUnit.DAYS.between(LocalDate.now(), dateExpiration);
        if (joursRestants < 0) {
            return 0; // produit expiré, ne peut pas être vendu
        } else if (joursRestants <= 7) {
            return getPrix() * 0.80; // remise 20%
        }
        return getPrix();
    }

    @Override
    public String getCategorie() {
        return "Alimentaire";
    }

    public boolean estExpire() {
        return LocalDate.now().isAfter(dateExpiration);
    }

    public long getJoursRestants() {
        return ChronoUnit.DAYS.between(LocalDate.now(), dateExpiration);
    }

    @Override
    public String toString() {
        String statut = estExpire() ? "EXPIRÉ" : getJoursRestants() + " jour(s) restants";
        return super.toString() + String.format(" | Expiration: %s (%s)", dateExpiration, statut);
    }
}
