package com.ecommerce.models;

import com.ecommerce.models.commandes.Commande;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe Client
 * Applique : Encapsulation
 */
public class Client {

    private int id;
    private String nom;
    private String email;
    private List<Commande> listeCommandes;

    public Client(int id, String nom, String email) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.listeCommandes = new ArrayList<>();
    }

    public void ajouterCommande(Commande commande) {
        listeCommandes.add(commande);
    }

    public double getTotalAchats() {
        return listeCommandes.stream()
                .mapToDouble(Commande::calculerTotal)
                .sum();
    }

    public int getNombreCommandes() {
        return listeCommandes.size();
    }

    // Getters & Setters
    public int getId() { return id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public List<Commande> getListeCommandes() { return listeCommandes; }

    @Override
    public String toString() {
        return String.format("Client[%d] %s <%s> | Commandes: %d | Total achats: %.2f TND",
                id, nom, email, getNombreCommandes(), getTotalAchats());
    }
}
