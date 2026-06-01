package com.ecommerce.services;

import com.ecommerce.models.Client;
import com.ecommerce.models.commandes.Commande;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service de gestion des commandes et des clients
 */
public class GestionCommandes {

    // Map clients : id → Client
    private Map<Integer, Client> clients = new HashMap<>();

    // Liste de toutes les commandes
    private List<Commande> toutesCommandes = new ArrayList<>();

    // ─── Clients ──────────────────────────────
    public void ajouterClient(Client c) {
        clients.put(c.getId(), c);
        System.out.println("  ✔ Client enregistré : " + c.getNom());
    }

    public Client getClient(int id) {
        return clients.get(id);
    }

    public Collection<Client> getTousClients() {
        return clients.values();
    }

    // ─── Commandes ────────────────────────────
    public void enregistrerCommande(Client client, Commande commande) {
        client.ajouterCommande(commande);
        toutesCommandes.add(commande);
        System.out.printf("  ✔ Commande %s enregistrée pour %s%n",
                commande.getNumeroCommande(), client.getNom());
    }

    public List<Commande> getToutesCommandes() {
        return toutesCommandes;
    }

    // ─── Statistiques ─────────────────────────
    public double getTotalVentes() {
        return toutesCommandes.stream()
                .filter(c -> c.getStatutPaiement().equals("PAYÉ"))
                .mapToDouble(Commande::calculerTotal)
                .sum();
    }

    public long getNombreCommandesPayees() {
        return toutesCommandes.stream()
                .filter(c -> c.getStatutPaiement().equals("PAYÉ"))
                .count();
    }

    public Client getMeilleurClient() {
        return clients.values().stream()
                .max(Comparator.comparingDouble(Client::getTotalAchats))
                .orElse(null);
    }

    public void afficherStatistiques() {
        System.out.println("═══════════════════════════════════");
        System.out.println("        STATISTIQUES VENTES        ");
        System.out.println("═══════════════════════════════════");
        System.out.printf("  Total commandes     : %d%n", toutesCommandes.size());
        System.out.printf("  Commandes payées    : %d%n", getNombreCommandesPayees());
        System.out.printf("  Total des ventes    : %.2f TND%n", getTotalVentes());

        Client top = getMeilleurClient();
        if (top != null) {
            System.out.printf("  Meilleur client     : %s (%.2f TND)%n",
                    top.getNom(), top.getTotalAchats());
        }

        System.out.println("\n  Détail par client :");
        clients.values().stream()
                .sorted(Comparator.comparingDouble(Client::getTotalAchats).reversed())
                .forEach(c -> System.out.printf("    • %-20s %d commande(s)  %.2f TND%n",
                        c.getNom(), c.getNombreCommandes(), c.getTotalAchats()));
    }
}
