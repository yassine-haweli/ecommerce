package com.ecommerce.main;

import com.ecommerce.collections.Catalogue;
import com.ecommerce.models.Client;
import com.ecommerce.models.commandes.*;
import com.ecommerce.models.produits.*;
import com.ecommerce.services.GestionCommandes;

import java.time.LocalDate;
import java.util.List;

/**
 * Classe principale - Scénario complet de la plateforme E-Commerce
 */
public class Main {

    public static void main(String[] args) {

        separateur("PLATEFORME E-COMMERCE - DÉMARRAGE");

        // ══════════════════════════════════════════════════
        // PARTIE 1 : Création des produits & du catalogue
        // ══════════════════════════════════════════════════
        separateur("PARTIE 1 : CRÉATION DES PRODUITS");

        Catalogue catalogue = new Catalogue();

        // Produits Électroniques
        ProduitElectronique laptop  = new ProduitElectronique("ELEC-001", "Laptop Dell XPS", 2500.00, 2);
        ProduitElectronique phone   = new ProduitElectronique("ELEC-002", "Samsung Galaxy S24", 1800.00, 1);
        ProduitElectronique tablet  = new ProduitElectronique("ELEC-003", "iPad Pro", 3200.00, 1);
        ProduitElectronique casque  = new ProduitElectronique("ELEC-004", "Sony WH-1000XM5", 650.00, 2);
        ProduitElectronique clavier = new ProduitElectronique("ELEC-005", "Clavier Mécanique", 180.00, 1);

        // Produits Alimentaires
        ProduitAlimentaire fromage  = new ProduitAlimentaire("ALIM-001", "Fromage Gouda 500g",  18.50, LocalDate.now().plusDays(30));
        ProduitAlimentaire lait     = new ProduitAlimentaire("ALIM-002", "Lait Bio 1L",          4.20, LocalDate.now().plusDays(5));  // bientôt expiré → -20%
        ProduitAlimentaire pain     = new ProduitAlimentaire("ALIM-003", "Pain de mie Bio",       6.80, LocalDate.now().plusDays(2));  // bientôt expiré → -20%
        ProduitAlimentaire jus      = new ProduitAlimentaire("ALIM-004", "Jus d'orange 1L",       7.50, LocalDate.now().plusDays(60));
        ProduitAlimentaire expire   = new ProduitAlimentaire("ALIM-005", "Yaourt (EXPIRÉ)",        3.00, LocalDate.now().minusDays(2)); // expiré → prix 0

        System.out.println("\n📦 Ajout des produits au catalogue :");
        catalogue.ajouterProduit(laptop);
        catalogue.ajouterProduit(phone);
        catalogue.ajouterProduit(tablet);
        catalogue.ajouterProduit(casque);
        catalogue.ajouterProduit(clavier);
        catalogue.ajouterProduit(fromage);
        catalogue.ajouterProduit(lait);
        catalogue.ajouterProduit(pain);
        catalogue.ajouterProduit(jus);
        catalogue.ajouterProduit(expire);

        // Test doublon (Set)
        System.out.println("\n🔁 Test ajout doublon :");
        catalogue.ajouterProduit(new ProduitElectronique("ELEC-001", "Doublon Laptop", 999.0, 1));

        // Afficher tout le catalogue
        System.out.println("\n📋 Catalogue complet :");
        catalogue.afficherTout();

        // ══════════════════════════════════════════════════
        // PARTIE 2 : Tri & Suppression conditionnelle
        // ══════════════════════════════════════════════════
        separateur("PARTIE 2 : TRI ET SUPPRESSION");

        System.out.println("🔼 Tri par prix croissant :");
        catalogue.trierParPrix().forEach(p ->
                System.out.printf("  %-30s → %.2f TND%n", p.getNom(), p.calculerPrixFinal()));

        System.out.println("\n🔽 Tri par prix décroissant :");
        catalogue.trierParPrixDesc().forEach(p ->
                System.out.printf("  %-30s → %.2f TND%n", p.getNom(), p.calculerPrixFinal()));

        System.out.println("\n🔤 Tri par nom alphabétique :");
        catalogue.trierParNom().forEach(p ->
                System.out.printf("  %s%n", p.getNom()));

        System.out.println("\n🗑️  Suppression des produits invalides (expirés / prix = 0) :");
        int nbSupprimes = catalogue.supprimerProduitsInvalides();
        System.out.println("  → " + nbSupprimes + " produit(s) supprimé(s)");

        System.out.println("\n📋 Catalogue après nettoyage :");
        catalogue.afficherParCategorie();

        // Modification d'un produit
        System.out.println("\n✏️  Modification du produit ELEC-005 :");
        catalogue.modifierProduit("ELEC-005", "Clavier Mécanique RGB Pro", 220.00);

        // ══════════════════════════════════════════════════
        // PARTIE 3 : Statistiques
        // ══════════════════════════════════════════════════
        separateur("PARTIE 3 : STATISTIQUES DU CATALOGUE");

        System.out.printf("  Nombre de produits    : %d%n", catalogue.getNombreProduits());

        Produit plusCher = catalogue.getProduitPlusCher();
        if (plusCher != null)
            System.out.printf("  Produit le plus cher  : %s → %.2f TND%n",
                    plusCher.getNom(), plusCher.calculerPrixFinal());

        Produit moinsCher = catalogue.getProduitMoinsCher();
        if (moinsCher != null)
            System.out.printf("  Produit le moins cher : %s → %.2f TND%n",
                    moinsCher.getNom(), moinsCher.calculerPrixFinal());

        System.out.printf("  Prix moyen            : %.2f TND%n", catalogue.getPrixMoyen());

        // ══════════════════════════════════════════════════
        // PARTIE 4 : Recherche, filtrage, regroupement
        // ══════════════════════════════════════════════════
        separateur("PARTIE 4 : COLLECTIONS AVANCÉES");

        System.out.println("🔍 Recherche par référence 'ELEC-002' :");
        Produit trouve = catalogue.rechercherParReference("ELEC-002");
        System.out.println("  → " + (trouve != null ? trouve : "Non trouvé"));

        System.out.println("\n🔍 Recherche par nom contenant 'pro' :");
        catalogue.rechercherParNom("pro")
                .forEach(p -> System.out.println("  → " + p.getNom()));

        System.out.println("\n💰 Filtrage : produits ≤ 200 TND :");
        catalogue.filtrerParPrixMax(200.0)
                .forEach(p -> System.out.printf("  → %-25s %.2f TND%n", p.getNom(), p.calculerPrixFinal()));

        System.out.println("\n📁 Regroupement par catégorie :");
        catalogue.afficherParCategorie();

        // TreeSet trié (utilise compareTo)
        System.out.println("\n🌳 TreeSet (tri naturel par prix) :");
        java.util.TreeSet<Produit> treeSet = new java.util.TreeSet<>(catalogue.getListeProduits());
        treeSet.forEach(p -> System.out.printf("  %.2f → %s%n", p.calculerPrixFinal(), p.getNom()));

        // ══════════════════════════════════════════════════
        // CLIENTS & COMMANDES
        // ══════════════════════════════════════════════════
        separateur("CLIENTS ET COMMANDES");

        GestionCommandes gestion = new GestionCommandes();

        // Créer les clients
        Client ahmed  = new Client(1, "Ahmed Ben Ali",    "ahmed@email.com");
        Client sonia  = new Client(2, "Sonia Trabelsi",   "sonia@email.com");
        Client karim  = new Client(3, "Karim Mansouri",   "karim@email.com");

        System.out.println("\n👤 Enregistrement des clients :");
        gestion.ajouterClient(ahmed);
        gestion.ajouterClient(sonia);
        gestion.ajouterClient(karim);

        // ─── Commande 1 : Ahmed - Normale ────────────────
        System.out.println("\n🛒 Commande 1 - Ahmed (Normale) :");
        CommandeNormale cmd1 = new CommandeNormale("CMD-001", ahmed.getNom());
        cmd1.ajouterProduit(laptop);
        cmd1.ajouterProduit(casque);
        cmd1.ajouterProduit(fromage);
        gestion.enregistrerCommande(ahmed, cmd1);

        System.out.println(cmd1);
        System.out.println("  💳 Paiement avec 3500 TND :");
        cmd1.payer(3500.0);

        // ─── Commande 2 : Sonia - Express ────────────────
        System.out.println("\n🛒 Commande 2 - Sonia (Express) :");
        CommandeExpress cmd2 = new CommandeExpress("CMD-002", sonia.getNom());
        cmd2.ajouterProduit(phone);
        cmd2.ajouterProduit(jus);
        cmd2.ajouterProduit(lait);
        gestion.enregistrerCommande(sonia, cmd2);

        System.out.println(cmd2);
        System.out.println("  💳 Paiement avec 2000 TND :");
        cmd2.payer(2000.0);

        // ─── Commande 3 : Karim - Express ─────────────────
        System.out.println("\n🛒 Commande 3 - Karim (Express) :");
        CommandeExpress cmd3 = new CommandeExpress("CMD-003", karim.getNom());
        cmd3.ajouterProduit(tablet);
        cmd3.ajouterProduit(clavier);
        gestion.enregistrerCommande(karim, cmd3);

        System.out.println(cmd3);
        System.out.println("  💳 Paiement insuffisant (500 TND) :");
        cmd3.payer(500.0);
        System.out.println("  💳 Paiement correct (3500 TND) :");
        cmd3.payer(3500.0);

        // ─── Commande 4 : Ahmed - 2ème commande ──────────
        System.out.println("\n🛒 Commande 4 - Ahmed (Normale) :");
        CommandeNormale cmd4 = new CommandeNormale("CMD-004", ahmed.getNom());
        cmd4.ajouterProduit(pain);
        gestion.enregistrerCommande(ahmed, cmd4);
        System.out.println(cmd4);
        cmd4.payer(10.0);

        // ══════════════════════════════════════════════════
        // QUEUE : File d'attente des commandes
        // ══════════════════════════════════════════════════
        separateur("FILE D'ATTENTE (QUEUE)");

        System.out.println("📥 Ajout des commandes à la file d'attente :");
        catalogue.ajouterCommandeFile("CMD-001");
        catalogue.ajouterCommandeFile("CMD-002");
        catalogue.ajouterCommandeFile("CMD-003");
        catalogue.ajouterCommandeFile("CMD-004");

        System.out.println("\n⚙️  Traitement de la file (" + catalogue.getTailleFile() + " commandes) :");
        while (catalogue.getTailleFile() > 0) {
            catalogue.traiterProchainCommande();
        }
        catalogue.traiterProchainCommande(); // file vide

        // ══════════════════════════════════════════════════
        // STATISTIQUES FINALES
        // ══════════════════════════════════════════════════
        separateur("STATISTIQUES FINALES DES VENTES");
        gestion.afficherStatistiques();

        separateur("FIN DU PROGRAMME");
    }

    private static void separateur(String titre) {
        System.out.println("\n╔══════════════════════════════════════════════╗");
        System.out.printf("║  %-44s║%n", titre);
        System.out.println("╚══════════════════════════════════════════════╝");
    }
}
