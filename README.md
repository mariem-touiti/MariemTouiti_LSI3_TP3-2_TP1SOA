# TP1 SOA — Modélisation XML et Interrogation XPath avec Maven en Java

## 1. Présentation du TP

Ce TP a pour objectif de découvrir la modélisation des données en XML, la validation d'un document XML à l'aide d'un schéma XSD et l'interrogation des données avec XPath dans un projet Java utilisant Maven.

## 2. Domaine choisi

Le domaine choisi est la gestion d'un magasin de produits cosmétiques.

Le document XML contient plusieurs produits avec les informations suivantes :
- Identifiant du produit
- Nom du produit
- Prix
- État de promotion

## 3. Technologies utilisées

- Java
- Maven
- XML
- XSD
- XPath
- IntelliJ IDEA

## 4. Structure du projet

```text
TP1-SOA/
├── pom.xml
├── README.md
└── src/
    └── main/
        ├── java/
        │   └── tn/
        │       └── soa/
        │           └── Main.java
        └── resources/
            ├── produits.xml
            └── produits.xsd
```

## 5. Fonctionnalités réalisées

### Validation XML/XSD

Le fichier `produits.xsd` définit la structure attendue du document XML. La classe Java utilise `SchemaFactory` et `Validator` pour vérifier que le document `produits.xml` respecte ce schéma.

### Interrogation XPath

La classe principale utilise XPath pour :
- Afficher les noms de tous les produits.
- Afficher uniquement les produits en promotion.

## 6. Exécution du projet

1. Ouvrir le projet Maven dans IntelliJ IDEA.
2. Vérifier la présence des fichiers XML et XSD dans `src/main/resources`.
3. Exécuter la méthode `main()` de la classe `Main.java`.
4. Consulter les résultats dans la console.

## 7. Résultat attendu

Le programme affiche un message confirmant la validation XML/XSD, suivi de la liste de tous les produits et de la liste des produits en promotion.

## 8. Conclusion

Ce TP permet de comprendre comment représenter des données en XML, vérifier leur structure avec XSD et extraire des informations à l'aide de XPath dans une application Java avec Maven.
