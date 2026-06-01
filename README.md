# 🛒 Système de Gestion d'une Plateforme E-Commerce
**Projet Final Java — AU 2025-2026**  
*Programmation Orientée Objet + Collections Java*

---

## 📋 Description

Application Java console qui simule une plateforme de vente en ligne.  
Elle permet de gérer les produits, les clients, les commandes et les paiements en appliquant tous les concepts de la POO et des Collections Java.

---

## 🏗️ Structure du projet

```
src/main/java/com/ecommerce/
│
├── interfaces/
│   └── Payable.java              # Interface avec méthode payer()
│
├── models/
│   ├── Client.java               # Classe Client
│   ├── produits/
│   │   ├── Produit.java          # Classe abstraite
│   │   ├── ProduitElectronique.java  # Héritage + garantie
│   │   └── ProduitAlimentaire.java   # Héritage + dateExpiration
│   └── commandes/
│       ├── Commande.java         # Classe abstraite + Payable
│       ├── CommandeNormale.java  # Sans frais supplémentaires
│       └── CommandeExpress.java  # +15 TND frais livraison
│
├── collections/
│   └── Catalogue.java            # List + Set + Map + Queue
│
├── services/
│   └── GestionCommandes.java     # Statistiques et ventes
│
└── main/
    └── Main.java                 # Scénario complet
```

---

## 🎯 Concepts POO appliqués

| Concept | Où |
|---|---|
| **Encapsulation** | Attributs `private` + getters/setters dans toutes les classes |
| **Héritage** | `ProduitElectronique` et `ProduitAlimentaire` héritent de `Produit` |
| **Abstraction** | `Produit` et `Commande` sont des classes abstraites |
| **Interface** | `Payable` implémentée par `Commande` |
| **Polymorphisme** | `calculerPrixFinal()` et `calculerTotal()` redéfinis selon le type |

---

## 📦 Collections Java utilisées

| Collection | Classe | Usage |
|---|---|---|
| `ArrayList` | `Catalogue` | Liste ordonnée des produits |
| `LinkedList` | `Catalogue` | File d'attente des commandes (Queue) |
| `HashSet` | `Catalogue` | Produits uniques (pas de doublons) |
| `TreeSet` | `Main` | Tri naturel par prix (Comparable) |
| `HashMap` | `Catalogue` | Index référence → produit (O(1)) |
| `TreeMap` | `Catalogue` | Groupement par catégorie (trié alphabétiquement) |
| `Queue` | `Catalogue` | File d'attente FIFO des commandes |

---

## ✨ Fonctionnalités

### Partie 1 — Produits & Commandes
- Création de `ProduitElectronique` (prix + 5% × garantie)
- Création de `ProduitAlimentaire` (−20% si expiration < 7 jours)
- Commandes normales et express (frais fixes 15 TND)
- Paiement avec rendu de monnaie

### Partie 2 — Tri & Suppression
- Tri par prix croissant / décroissant (Comparable + Comparator)
- Tri par nom alphabétique
- Suppression conditionnelle des produits expirés

### Partie 3 — Statistiques
- Nombre total de produits
- Produit le plus cher / moins cher
- Prix moyen du catalogue
- Total des ventes, meilleur client

### Partie 4 — Collections avancées
- Recherche par référence (HashMap O(1))
- Recherche par nom (filtre partiel)
- Filtrage par prix maximum
- Regroupement par catégorie (TreeMap)
- File d'attente FIFO (Queue)

---

## ▶️ Instructions d'exécution

### Prérequis
- Java JDK 17 ou supérieur
- Pas de dépendances externes

### Compilation
```bash
# Depuis la racine du projet
mkdir -p out
javac -d out -sourcepath src/main/java $(find src -name "*.java")
```

### Exécution
```bash
java -cp out com.ecommerce.main.Main
```

### Sous Windows (PowerShell)
```powershell
# Compilation
$files = Get-ChildItem -Recurse -Filter *.java src | Select-Object -ExpandProperty FullName
javac -d out $files

# Exécution
java -cp out com.ecommerce.main.Main
```

---

## 👨‍💻 Auteur

Projet réalisé dans le cadre du cours de **Programmation Orientée Objet — Collections Java**  
iTeam University — AU 2025-2026
