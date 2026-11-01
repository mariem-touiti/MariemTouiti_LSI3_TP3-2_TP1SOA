package tn.soa;

import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class Main {

    // Validation d'un document XML avec un schema XSD
    public static boolean validerXML(String fichierXML) {

        try {

            SchemaFactory schemaFactory =
                    SchemaFactory.newInstance(
                            XMLConstants.W3C_XML_SCHEMA_NS_URI);

            Schema schema;

            try (InputStream xsd =
                         Main.class.getResourceAsStream("/produits.xsd")) {

                if (xsd == null) {
                    throw new Exception("Fichier produits.xsd introuvable");
                }

                schema = schemaFactory.newSchema(
                        new StreamSource(xsd));
            }

            Validator validator = schema.newValidator();

            try (InputStream xml =
                         Main.class.getResourceAsStream("/" + fichierXML)) {

                if (xml == null) {
                    throw new Exception(
                            "Fichier " + fichierXML + " introuvable");
                }

                validator.validate(new StreamSource(xml));
            }

            System.out.println(
                    "Validation reussie : " + fichierXML);

            return true;

        } catch (Exception e) {

            System.out.println(
                    "Validation echouee : " + fichierXML);

            System.out.println("Erreur : " + e.getMessage());

            return false;
        }
    }

    // Chargement du document XML
    public static Document chargerXML(String fichierXML)
            throws Exception {

        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();

        factory.setFeature(
                XMLConstants.FEATURE_SECURE_PROCESSING, true);

        factory.setFeature(
                "http://apache.org/xml/features/disallow-doctype-decl",
                true);

        DocumentBuilder builder =
                factory.newDocumentBuilder();

        try (InputStream xml =
                     Main.class.getResourceAsStream("/" + fichierXML)) {

            if (xml == null) {
                throw new Exception("Fichier XML introuvable");
            }

            return builder.parse(xml);
        }
    }

    // Execution d'une requete XPath
    public static void executerXPath(
            Document document,
            String description,
            String expression) throws Exception {

        XPath xpath =
                XPathFactory.newInstance().newXPath();

        NodeList resultats = (NodeList) xpath.evaluate(
                expression,
                document,
                XPathConstants.NODESET);

        System.out.println("\n" + description);

        if (resultats.getLength() == 0) {
            System.out.println("Aucun resultat trouve.");
        }

        for (int i = 0; i < resultats.getLength(); i++) {

            System.out.println(
                    "- " +
                            resultats.item(i).getTextContent().trim());
        }
    }

    // Methode principale
    public static void main(String[] args) {

        System.out.println("====================================");
        System.out.println("      VALIDATION XML / XSD");
        System.out.println("====================================");

        // Test du fichier valide
        boolean fichierValide =
                validerXML("produits.xml");

        // Test du fichier non valide
        boolean fichierNonValide =
                validerXML("produits-invalide.xml");

        System.out.println("\n====================================");
        System.out.println("          RESULTATS DES TESTS");
        System.out.println("====================================");

        System.out.println(
                "Fichier valide accepte : " + fichierValide);

        System.out.println(
                "Fichier invalide rejete : " + !fichierNonValide);

        // Execution des requetes XPath
        if (fichierValide) {

            try {

                Document document =
                        chargerXML("produits.xml");

                System.out.println("\n====================================");
                System.out.println("        INTERROGATION XPATH");
                System.out.println("====================================");

                // 1. Tous les noms des produits
                executerXPath(
                        document,
                        "1. Tous les noms des produits :",
                        "/magasin/produits/produit/nom"
                );

                // 2. Les produits en promotion
                executerXPath(
                        document,
                        "2. Les produits en promotion :",
                        "/magasin/produits/produit[promotion='true']/nom"
                );

                // 3. Les produits dont le prix depasse 30
                executerXPath(
                        document,
                        "3. Les produits dont le prix depasse 30 :",
                        "/magasin/produits/produit[prix > 30]/nom"
                );

                // 4. Les produits dont le stock est inferieur a 30
                executerXPath(
                        document,
                        "4. Les produits avec un stock inferieur a 30 :",
                        "/magasin/produits/produit[stock < 30]/nom"
                );

                // 5. Les produits de la categorie C1
                executerXPath(
                        document,
                        "5. Les produits de la categorie C1 :",
                        "/magasin/produits/produit[@categorieRef='C1']/nom"
                );

                // 6. Les noms des marques
                executerXPath(
                        document,
                        "6. Les marques des produits :",
                        "/magasin/produits/produit/marque"
                );

                // 7. Toutes les categories
                executerXPath(
                        document,
                        "7. Les categories du magasin :",
                        "/magasin/categories/categorie/nom"
                );

                // 8. Les produits non disponibles en stock
                executerXPath(
                        document,
                        "8. Les produits sans stock :",
                        "/magasin/produits/produit[stock=0]/nom"
                );

            } catch (Exception e) {

                System.out.println(
                        "Erreur lors de l'execution XPath : "
                                + e.getMessage());
            }
        }
    }
}
