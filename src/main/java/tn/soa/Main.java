package tn.soa;

import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.transform.stream.StreamSource;

public class Main {

    public static void main(String[] args) {

        try {
            // 1. Validation du document XML avec le schema XSD
            SchemaFactory schemaFactory =
                    SchemaFactory.newInstance(
                            XMLConstants.W3C_XML_SCHEMA_NS_URI);

            InputStream xsdStream =
                    Main.class.getResourceAsStream("/produits.xsd");

            InputStream xmlStream =
                    Main.class.getResourceAsStream("/produits.xml");

            if (xsdStream == null || xmlStream == null) {
                throw new Exception(
                        "Fichier XML ou XSD introuvable !");
            }

            Schema schema =
                    schemaFactory.newSchema(
                            new StreamSource(xsdStream));

            javax.xml.validation.Validator validator =
                    schema.newValidator();

            validator.validate(new StreamSource(xmlStream));

            System.out.println("Validation XML/XSD reussie !");

            xsdStream.close();
            xmlStream.close();

            // 2. Lecture du document XML pour XPath
            InputStream xpathStream =
                    Main.class.getResourceAsStream("/produits.xml");

            DocumentBuilderFactory factory =
                    DocumentBuilderFactory.newInstance();

            factory.setFeature(
                    XMLConstants.FEATURE_SECURE_PROCESSING, true);

            Document document =
                    factory.newDocumentBuilder().parse(xpathStream);

            xpathStream.close();

            XPath xpath = XPathFactory.newInstance().newXPath();

            // Requete XPath : afficher tous les noms
            NodeList noms = (NodeList) xpath.evaluate(
                    "/magasin/produit/nom",
                    document,
                    XPathConstants.NODESET);

            System.out.println("\nTous les produits :");

            for (int i = 0; i < noms.getLength(); i++) {
                System.out.println("- " +
                        noms.item(i).getTextContent());
            }

            // Requete XPath : afficher les produits en promotion
            NodeList promotions = (NodeList) xpath.evaluate(
                    "/magasin/produit[promotion='true']/nom",
                    document,
                    XPathConstants.NODESET);

            System.out.println("\nProduits en promotion :");

            for (int i = 0; i < promotions.getLength(); i++) {
                System.out.println("- " +
                        promotions.item(i).getTextContent());
            }

        } catch (SAXException e) {
            System.out.println(
                    "Erreur de validation XML/XSD : " + e.getMessage());

        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
