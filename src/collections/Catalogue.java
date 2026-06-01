package com.ecommerce.collections;

import com.ecommerce.models.produits.Produit;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Catalogue - gestion des produits avec toutes les Collections Java
 * Applique : List, Set, Map, Queue, Comparator
 */
public class Catalogue {

    // LIST : stockage ordonné des produits (peut contenir des doublons)
    private List<Produit> listeProduits;

    // SET : produits uniques (pas de doublons basés sur référence)
    private Set<Produit> produitUniques;

    // MAP : accès rapide par référence
    private Map<String, Produit> indexParReference;

    // MAP : groupement par catégorie
    private Map<String, List<Produit>> indexParCategorie;

    // QUEUE : file d'attente des commandes à traiter
    private Queue<String> fileAttenteCommandes;

    public Catalogue() {
        this.listeProduits       = new ArrayList<>();
        this.produitUniques      = new HashSet<>();
        this.indexParReference   = new HashMap<>();
        this.indexParCategorie   = new TreeMap<>(); // TreeMap = trié par clé alphabétiquement
        this.fileAttenteCommandes = new LinkedList<>();
    }

    // ─────────────────────────────────────────────
    // CRUD sur la List
    // ─────────────────────────────────────────────

    /** Ajouter un produit */
    public boolean ajouterProduit(Produit p) {
        // Vérifier unicité via Set
        if (!produitUniques.add(p)) {
            System.out.println("  ✘ Produit déjà existant (référence en double) : " + p.getReference());
            return false;
        }
        listeProduits.add(p);
        indexParReference.put(p.getReference(), p);

        // Grouper par catégorie dans la Map
        indexParCategorie
            .computeIfAbsent(p.getCategorie(), k -> new ArrayList<>())
            .add(p);

        System.out.println("  ✔ Produit ajouté au catalogue : " + p.getNom());
        return true;
    }

    /** Modifier un produit par sa référence */
    public boolean modifierProduit(String reference, String nouveauNom, double nouveauPrix) {
        Produit p = indexParReference.get(reference);
        if (p == null) {
            System.out.println("  ✘ Produit introuvable : " + reference);
            return false;
        }
        p.setNom(nouveauNom);
        p.setPrix(nouveauPrix);
        System.out.printf("  ✔ Produit modifié : %s → %s (%.2f TND)%n", reference, nouveauNom, nouveauPrix);
        return true;
    }

    /** Supprimer un produit par référence */
    public boolean supprimerProduit(String reference) {
        Produit p = indexParReference.remove(reference);
        if (p == null) return false;
        listeProduits.remove(p);
        produitUniques.remove(p);
        List<Produit> cat = indexParCategorie.get(p.getCategorie());
        if (cat != null) cat.remove(p);
        System.out.println("  ✔ Produit supprimé : " + reference);
        return true;
    }

    /** Supprimer tous les produits dont le prix final est 0 (ex: alimentaire expiré) */
    public int supprimerProduitsInvalides() {
        List<Produit> aSupprimer = listeProduits.stream()
                .filter(p -> p.calculerPrixFinal() <= 0)
                .collect(Collectors.toList());
        aSupprimer.forEach(p -> supprimerProduit(p.getReference()));
        return aSupprimer.size();
    }

    // ─────────────────────────────────────────────
    // Recherche (Map)
    // ─────────────────────────────────────────────

    /** Rechercher par référence (O(1) grâce à HashMap) */
    public Produit rechercherParReference(String reference) {
        return indexParReference.get(reference);
    }

    /** Rechercher par nom (partiel, insensible à la casse) */
    public List<Produit> rechercherParNom(String motCle) {
        String mc = motCle.toLowerCase();
        return listeProduits.stream()
                .filter(p -> p.getNom().toLowerCase().contains(mc))
                .collect(Collectors.toList());
    }

    /** Obtenir les produits d'une catégorie */
    public List<Produit> getParCategorie(String categorie) {
        return indexParCategorie.getOrDefault(categorie, new ArrayList<>());
    }

    /** Filtrer par prix max */
    public List<Produit> filtrerParPrixMax(double prixMax) {
        return listeProduits.stream()
                .filter(p -> p.calculerPrixFinal() <= prixMax)
                .collect(Collectors.toList());
    }

    // ─────────────────────────────────────────────
    // Tri
    // ─────────────────────────────────────────────

    /** Trier par prix croissant (utilise Comparable) */
    public List<Produit> trierParPrix() {
        List<Produit> tri = new ArrayList<>(listeProduits);
        Collections.sort(tri);
        return tri;
    }

    /** Trier par prix décroissant */
    public List<Produit> trierParPrixDesc() {
        List<Produit> tri = new ArrayList<>(listeProduits);
        tri.sort(Comparator.comparingDouble(Produit::calculerPrixFinal).reversed());
        return tri;
    }

    /** Trier par nom alphabétique */
    public List<Produit> trierParNom() {
        List<Produit> tri = new ArrayList<>(listeProduits);
        tri.sort(Comparator.comparing(Produit::getNom, String.CASE_INSENSITIVE_ORDER));
        return tri;
    }

    // ─────────────────────────────────────────────
    // Statistiques
    // ─────────────────────────────────────────────

    public int getNombreProduits() { return listeProduits.size(); }

    public Produit getProduitPlusCher() {
        return listeProduits.stream()
                .max(Comparator.comparingDouble(Produit::calculerPrixFinal))
                .orElse(null);
    }

    public Produit getProduitMoinsCher() {
        return listeProduits.stream()
                .min(Comparator.comparingDouble(Produit::calculerPrixFinal))
                .orElse(null);
    }

    public double getPrixMoyen() {
        return listeProduits.stream()
                .mapToDouble(Produit::calculerPrixFinal)
                .average()
                .orElse(0);
    }

    // ─────────────────────────────────────────────
    // File d'attente (Queue)
    // ─────────────────────────────────────────────

    public void ajouterCommandeFile(String numeroCommande) {
        fileAttenteCommandes.offer(numeroCommande);
        System.out.println("  📥 Commande ajoutée à la file : " + numeroCommande);
    }

    public String traiterProchainCommande() {
        String commande = fileAttenteCommandes.poll();
        if (commande != null) {
            System.out.println("  ⚙️  Traitement de la commande : " + commande);
        } else {
            System.out.println("  ℹ️  File d'attente vide.");
        }
        return commande;
    }

    public int getTailleFile() { return fileAttenteCommandes.size(); }

    // ─────────────────────────────────────────────
    // Affichage
    // ─────────────────────────────────────────────

    public void afficherTout() {
        if (listeProduits.isEmpty()) {
            System.out.println("  Catalogue vide.");
            return;
        }
        listeProduits.forEach(p -> System.out.println("  " + p));
    }

    public void afficherParCategorie() {
        indexParCategorie.forEach((categorie, produits) -> {
            System.out.println("  📦 " + categorie + " (" + produits.size() + " produit(s)) :");
            produits.forEach(p -> System.out.println("     • " + p));
        });
    }

    // Getters
    public List<Produit> getListeProduits() { return listeProduits; }
    public Set<Produit> getProduitUniques() { return produitUniques; }
    public Map<String, Produit> getIndexParReference() { return indexParReference; }
    public Map<String, List<Produit>> getIndexParCategorie() { return indexParCategorie; }
    public Queue<String> getFileAttenteCommandes() { return fileAttenteCommandes; }
}
