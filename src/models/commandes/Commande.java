package com.ecommerce.models.commandes;

import com.ecommerce.interfaces.Payable;
import com.ecommerce.models.produits.Produit;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstraite Commande
 * Applique : Abstraction, Interface Payable, Encapsulation
 */
public abstract class Commande implements Payable {

    private String numeroCommande;
    private List<Produit> listeProduits;
    private LocalDateTime dateCommande;
    private String statutPaiement;
    private String clientNom;

    public Commande(String numeroCommande, String clientNom) {
        this.numeroCommande = numeroCommande;
        this.clientNom = clientNom;
        this.listeProduits = new ArrayList<>();
        this.dateCommande = LocalDateTime.now();
        this.statutPaiement = "EN_ATTENTE";
    }

    // Ajouter un produit à la commande
    public void ajouterProduit(Produit produit) {
        listeProduits.add(produit);
        System.out.println("  ✔ Produit ajouté : " + produit.getNom());
    }

    // Supprimer un produit de la commande
    public boolean supprimerProduit(String reference) {
        return listeProduits.removeIf(p -> p.getReference().equals(reference));
    }

    // Méthode abstraite - chaque type de commande calcule son total différemment
    public abstract double calculerTotal();

    // Retourne le type de livraison
    public abstract String getTypeLivraison();

    // Implémentation de Payable
    @Override
    public boolean payer(double montant) {
        double total = calculerTotal();
        if (montant >= total) {
            this.statutPaiement = "PAYÉ";
            double monnaie = montant - total;
            System.out.printf("  ✔ Paiement accepté pour commande %s%n", numeroCommande);
            if (monnaie > 0) {
                System.out.printf("  💰 Monnaie rendue : %.2f TND%n", monnaie);
            }
            return true;
        } else {
            System.out.printf("  ✘ Montant insuffisant. Total: %.2f TND, Reçu: %.2f TND%n",
                    total, montant);
            return false;
        }
    }

    @Override
    public double getMontantTotal() {
        return calculerTotal();
    }

    @Override
    public String getStatutPaiement() {
        return statutPaiement;
    }

    // Getters
    public String getNumeroCommande() { return numeroCommande; }
    public List<Produit> getListeProduits() { return listeProduits; }
    public LocalDateTime getDateCommande() { return dateCommande; }
    public String getClientNom() { return clientNom; }
    public void setStatutPaiement(String statut) { this.statutPaiement = statut; }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("┌─ Commande N°%s [%s]%n", numeroCommande, getTypeLivraison()));
        sb.append(String.format("│  Client       : %s%n", clientNom));
        sb.append(String.format("│  Date         : %s%n", dateCommande.format(fmt)));
        sb.append(String.format("│  Produits (%d):%n", listeProduits.size()));
        for (Produit p : listeProduits) {
            sb.append(String.format("│    - %s → %.2f TND%n", p.getNom(), p.calculerPrixFinal()));
        }
        sb.append(String.format("│  Total        : %.2f TND%n", calculerTotal()));
        sb.append(String.format("└─ Statut Paiement : %s%n", statutPaiement));
        return sb.toString();
    }
}
