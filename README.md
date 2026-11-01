# TP1 – Modélisation XML et Interrogation XPath avec Maven en Java

## 1. Présentation du projet

Ce projet consiste à modéliser un magasin de produits cosmétiques à l'aide du langage XML, à définir un schéma XSD pour contrôler la structure et les données du document XML, puis à développer une application Java permettant d'effectuer la validation XML/XSD et l'interrogation des données avec XPath.

Le projet est réalisé dans le cadre du TP1 de la matière **Architecture SOA et Services Web**.

## 2. Objectifs du TP

Les principaux objectifs de ce travail sont :

- Modéliser un domaine réel à travers une structure XML hiérarchique.
- Définir un schéma XSD contenant des règles et des contraintes de validation.
- Créer un projet Java avec Maven.
- Vérifier la conformité d'un document XML par rapport à son schéma XSD.
- Tester la validation avec un document valide et un document non valide.
- Interroger les données XML à l'aide de plusieurs requêtes XPath.

## 3. Domaine choisi : Magasin de produits cosmétiques

Le domaine choisi est un magasin spécialisé dans la vente de produits cosmétiques.

La structure XML est organisée en trois parties principales :

### 3.1 Informations du magasin

Cette partie contient les informations générales du magasin :

- Nom du magasin.
- Ville.
- Adresse.
- Numéro de téléphone.

### 3.2 Gestion des catégories

Cette partie permet de représenter les différentes catégories de produits, par exemple :

- Soins du visage.
- Soins capillaires.
- Parfumerie.

Chaque catégorie possède un identifiant unique, un nom et une description.

### 3.3 Gestion des produits

Chaque produit contient les informations suivantes :

- Identifiant du produit.
- Nom.
- Marque.
- Prix.
- Quantité en stock.
- État de promotion.
- Référence vers sa catégorie.

L'attribut `categorieRef` permet d'associer chaque produit à une catégorie existante.

## 4. Structure du projet

```text
TP1-SOA/
│
├── pom.xml
├── README.md
│
└── src/
    └── main/
        ├── java/
        │   └── tn/
        │       └── soa/
        │           └── Main.java
        │
        └── resources/
            ├── produits.xml
            ├── produits.xsd
            └── produits-invalide.xml
```

## 5. Description des fichiers

| Fichier | Description |
|---|---|
| `pom.xml` | Configuration du projet Maven |
| `produits.xml` | Document XML contenant les données du magasin |
| `produits.xsd` | Schéma définissant la structure et les contraintes de validation |
| `produits-invalide.xml` | Document contenant volontairement une erreur pour tester la validation négative |
| `Main.java` | Classe Java contenant les fonctionnalités de validation et d'interrogation XPath |

## 6. Schéma XSD et contraintes de validation

Le fichier `produits.xsd` définit la structure attendue du document XML et impose plusieurs contraintes :

- **Identifiants (xs:ID) :** chaque catégorie et chaque produit doit posséder un identifiant unique.
- **Références (xs:IDREF) :** chaque produit doit référencer une catégorie existante.
- **Prix :** le prix doit être strictement positif et inférieur ou égal à 10 000.
- **Stock :** la quantité doit être un entier positif ou égal à zéro.
- **Téléphone :** le numéro doit contenir exactement huit chiffres.
- **Nom :** le nom doit contenir entre 2 et 100 caractères.
- **Structure :** les éléments doivent respecter l'ordre et l'organisation définis dans le schéma.

Ces contraintes permettent de détecter les erreurs structurelles et les valeurs incorrectes.

## 7. Implémentation Java

La classe `Main.java` utilise les API Java XML pour réaliser les opérations suivantes :

### 7.1 Validation XML/XSD

La validation est réalisée à l'aide des classes :

- `SchemaFactory`
- `Schema`
- `Validator`
- `StreamSource`

Deux tests sont effectués :

**Test 1 : Document valide**

Le fichier `produits.xml` respecte les règles définies dans le schéma XSD. La validation doit donc réussir.

**Test 2 : Document non valide**

Le fichier `produits-invalide.xml` contient un prix négatif. Cette valeur ne respecte pas les contraintes du schéma XSD. La validation doit donc échouer.

### 7.2 Interrogation XPath

Les requêtes XPath permettent d'extraire différentes informations du document XML.

Les requêtes intégrées dans le programme sont :

| N° | Requête XPath | Résultat attendu |
|---|---|---|
| 1 | `/magasin/produits/produit/nom` | Tous les noms des produits |
| 2 | `/magasin/produits/produit[promotion='true']/nom` | Les produits en promotion |
| 3 | `/magasin/produits/produit[prix > 30]/nom` | Les produits dont le prix dépasse 30 |
| 4 | `/magasin/produits/produit[stock < 30]/nom` | Les produits avec un stock inférieur à 30 |
| 5 | `/magasin/produits/produit[@categorieRef='C1']/nom` | Les produits appartenant à la catégorie C1 |
| 6 | `/magasin/produits/produit/marque` | Les marques des produits |
| 7 | `/magasin/categories/categorie/nom` | Les noms des catégories |
| 8 | `/magasin/produits/produit[stock=0]/nom` | Les produits sans stock |

## 8. Technologies et outils utilisés

- **Java :** langage de programmation utilisé pour développer l'application.
- **Maven :** outil de gestion et de configuration du projet.
- **XML :** langage utilisé pour représenter les données.
- **XSD :** langage utilisé pour définir les règles de validation XML.
- **XPath :** langage utilisé pour interroger et extraire les données XML.
- **IntelliJ IDEA :** environnement de développement utilisé pour réaliser le projet.

## 9. Exécution du projet

Pour exécuter le projet :

1. Ouvrir le projet `TP1-SOA` dans IntelliJ IDEA.
2. Vérifier la structure des fichiers.
3. Vérifier que les fichiers XML et XSD sont présents dans `src/main/resources`.
4. Ouvrir la classe `Main.java`.
5. Exécuter la méthode `main()`.

Le programme effectue automatiquement les deux tests de validation, puis exécute les requêtes XPath si le document principal est valide.

## 10. Résultats attendus

À l'exécution, le programme doit afficher :

- La réussite de la validation du fichier `produits.xml`.
- L'échec de la validation du fichier `produits-invalide.xml`.
- Les résultats des huit requêtes XPath.
- Les messages d'erreur en cas de document non conforme.

## 11. Conclusion

Ce TP a permis de mettre en pratique la modélisation des données avec XML, la définition de contraintes avec XSD et l'interrogation des documents XML avec XPath.

L'utilisation de Java et Maven a permis de regrouper ces fonctionnalités dans une application qui vérifie la conformité des données et facilite leur exploitation.

Ce travail a également permis de comprendre l'importance de la validation des documents XML afin de garantir la cohérence et la fiabilité des données.