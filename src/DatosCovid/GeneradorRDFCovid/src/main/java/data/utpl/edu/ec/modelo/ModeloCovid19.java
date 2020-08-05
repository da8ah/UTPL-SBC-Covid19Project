/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.utpl.edu.ec.modelo;

import data.utpl.edu.ec.modelo.clases.ContainmentMeasure;
import data.utpl.edu.ec.modelo.clases.Country;
import data.utpl.edu.ec.modelo.clases.Dataset;
import data.utpl.edu.ec.modelo.clases.Province;
import data.utpl.edu.ec.modelo.clases.Statistic;
import data.utpl.edu.ec.modelo.clases.StatisticHospitalized;
import data.utpl.edu.ec.modelo.clases.Test;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFWriter;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.DCTerms;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.apache.jena.vocabulary.XSD;

/**
 *
 * @author Danilo Alejandro Ochoa Hidalgo
 */
public class ModeloCovid19 {

    // String Classes
    private final String CLASS_COUNTRY = "Country";
    private final String CLASS_PROVINCE = "Province";

    private final String CLASS_STATISTICS = "Statistic";
    private final String CLASS_CONFIRMED = "Confirmed";
    private final String CLASS_RECOVERED = "Recovered";
    private final String CLASS_DEATHS = "Death";
    private final String CLASS_ACTIVE = "Actived";
    private final String CLASS_HOSPITALIZED = "Hospitalized";
    private final String CLASS_TESTS = "Test";

    private final String CLASS_CONTAINMENT_MEASURES = "ContainmentMeasure";
    private final String CLASS_DATASET = "Dataset";

    // URIs base para el Modelo
    private final String utplOntoPrefix = "http://data.utpl.edu.ec/COVID19-Pandemic/ontology#";
    private final Model ontoModel = ModelFactory.createDefaultModel();
    private final String utplDataPrefix = "http://data.utpl.edu.ec/COVID19-Pandemic/resource/";
    private final Model model = ModelFactory.createDefaultModel();

    // Vocabularios externos
    // SCHEMA
    private final String schema = "http://schema.org/";
    private final Model schemaModel = ModelFactory.createDefaultModel();
    // Dbpedia Ontology
    private final String dbo = "http://dbpedia.org/ontology/";
    private final Model dboModel = ModelFactory.createDefaultModel();
    // Dbpedia Resource
    private final String dbr = "http://dbpedia.org/resource/";
    private final Model dbrModel = ModelFactory.createDefaultModel();
    // Dbpedia Property
    private final String dbp = "http://dbpedia.org/property/";
    private final Model dbpModel = ModelFactory.createDefaultModel();
    // Geonames
    private final String gn = "http://www.geonames.org/ontology#";
    private final Model gnModel = ModelFactory.createDefaultModel();
    // DCAT
    private final String dcat = "http://www.w3.org/ns/dcat#";
    private final Model dcatModel = ModelFactory.createDefaultModel();
    // prov
    private final String prov = "http://www.w3.org/ns/prov#";
    private final Model provModel = ModelFactory.createDefaultModel();

    public ModeloCovid19() {
        // Prefijos
        model.setNsPrefix("utplOnto", utplOntoPrefix);
        model.setNsPrefix("utplData", utplDataPrefix);

        // Prefijos Externos
        model.setNsPrefix("schema", schema);
        model.setNsPrefix("dbo", dbo);
        model.setNsPrefix("dbr", dbr);
        model.setNsPrefix("dbp", dbp);
        model.setNsPrefix("gn", gn);
        model.setNsPrefix("dcat", dcat);
        model.setNsPrefix("prov", prov);
        model.setNsPrefix("dcterms", DCTerms.getURI());
        crearModeloOntologico();
    }

    private void crearModeloOntologico() {
        // CLASES
        Resource schemaObservation = schemaModel.getResource(schema + "Obsevation");

        // Statistics
        Resource ontoStatistics = ontoModel.createResource(utplOntoPrefix + CLASS_STATISTICS)
                .addProperty(RDFS.subClassOf, schemaObservation)
                .addProperty(RDF.type, RDFS.Class)
                .addProperty(RDFS.label, "Statistics")
                .addProperty(RDFS.comment, "Statistics class");
        Resource ontoStatisticConfirmed = ontoModel.createResource(utplOntoPrefix + CLASS_CONFIRMED)
                .addProperty(RDFS.subClassOf, ontoStatistics)
                .addProperty(RDF.type, RDFS.Class)
                .addProperty(RDFS.label, "Confirmed")
                .addProperty(RDFS.comment, "Statistics Confirmed class");
        Resource ontoStatisticRecovered = ontoModel.createResource(utplOntoPrefix + CLASS_RECOVERED)
                .addProperty(RDFS.subClassOf, ontoStatistics)
                .addProperty(RDF.type, RDFS.Class)
                .addProperty(RDFS.label, "Recovered")
                .addProperty(RDFS.comment, "Statistics Recovered class");
        Resource ontoStatisticDeaths = ontoModel.createResource(utplOntoPrefix + CLASS_DEATHS)
                .addProperty(RDFS.subClassOf, ontoStatistics)
                .addProperty(RDF.type, RDFS.Class)
                .addProperty(RDFS.label, "Deaths")
                .addProperty(RDFS.comment, "Statistics Deaths class");
        Resource ontoStatisticActive = ontoModel.createResource(utplOntoPrefix + CLASS_ACTIVE)
                .addProperty(RDFS.subClassOf, ontoStatistics)
                .addProperty(RDF.type, RDFS.Class)
                .addProperty(RDFS.label, "Active")
                .addProperty(RDFS.comment, "Statistics Active class");
        Resource ontoStatisticHospitalized = ontoModel.createResource(utplOntoPrefix + CLASS_HOSPITALIZED)
                .addProperty(RDFS.subClassOf, ontoStatistics)
                .addProperty(RDF.type, RDFS.Class)
                .addProperty(RDFS.label, "Hospitalized")
                .addProperty(RDFS.comment, "Statistics Hospitalized class");

        // Tests
        Resource ontoTests = ontoModel.createResource(utplOntoPrefix + CLASS_TESTS)
                .addProperty(RDFS.subClassOf, schemaObservation)
                .addProperty(RDF.type, RDFS.Class)
                .addProperty(RDFS.label, "Tests")
                .addProperty(RDFS.comment, "Tests class");

        // PROPERTIES
        // Statistics
        Resource ontoQuantity = ontoModel.createProperty(utplOntoPrefix + "quantity")
                .addProperty(RDF.type, RDF.Property)
                .addProperty(RDFS.label, "Quantity")
                .addProperty(RDFS.comment, "Statistics quantity property")
                .addProperty(RDFS.domain, ontoStatistics)
                .addProperty(RDFS.range, XSD.integer);
        Resource ontoTotalQuantity = ontoModel.createProperty(utplOntoPrefix + "totalQuantity")
                .addProperty(RDF.type, RDF.Property)
                .addProperty(RDFS.label, "Total quantity")
                .addProperty(RDFS.comment, "Statistics total quantity property")
                .addProperty(RDFS.domain, ontoStatistics)
                .addProperty(RDFS.range, XSD.integer);
        Resource ontoQuantityMild = ontoModel.createProperty(utplOntoPrefix + "quantityMild")
                .addProperty(RDF.type, RDF.Property)
                .addProperty(RDFS.label, "Quantity mild")
                .addProperty(RDFS.comment, "Statistics Hospitalized quantity mild property")
                .addProperty(RDFS.domain, ontoStatistics)
                .addProperty(RDFS.range, XSD.integer);
        Resource ontoQuantitySevere = ontoModel.createProperty(utplOntoPrefix + "quantitySevere")
                .addProperty(RDF.type, RDF.Property)
                .addProperty(RDFS.label, "Quantity severe")
                .addProperty(RDFS.comment, "Statistics Hospitalized quantity severe property")
                .addProperty(RDFS.domain, ontoStatistics)
                .addProperty(RDFS.range, XSD.integer);
        Resource ontoQuantityUnknown = ontoModel.createProperty(utplOntoPrefix + "quantityUnknown")
                .addProperty(RDF.type, RDF.Property)
                .addProperty(RDFS.label, "Quantity unknown")
                .addProperty(RDFS.comment, "Statistics Hospitalized quantity unknown property")
                .addProperty(RDFS.domain, ontoStatistics)
                .addProperty(RDFS.range, XSD.integer);

        // Tests
        Resource ontoPositives = ontoModel.createProperty(utplOntoPrefix + "positives")
                .addProperty(RDF.type, RDF.Property)
                .addProperty(RDFS.label, "Positives")
                .addProperty(RDFS.comment, "Positive tests property")
                .addProperty(RDFS.domain, ontoTests)
                .addProperty(RDFS.range, XSD.integer);
        Resource ontoNegatives = ontoModel.createProperty(utplOntoPrefix + "negatives")
                .addProperty(RDF.type, RDF.Property)
                .addProperty(RDFS.label, "Negatives")
                .addProperty(RDFS.comment, "Negative tests property")
                .addProperty(RDFS.domain, ontoTests)
                .addProperty(RDFS.range, XSD.integer);
        Resource ontoRealized = ontoModel.createProperty(utplOntoPrefix + "realized")
                .addProperty(RDF.type, RDF.Property)
                .addProperty(RDFS.label, "Realized")
                .addProperty(RDFS.comment, "Realized tests property")
                .addProperty(RDFS.domain, ontoTests)
                .addProperty(RDFS.range, XSD.integer);
        Resource ontoNotConfirmed = ontoModel.createProperty(utplOntoPrefix + "notConfirmed")
                .addProperty(RDF.type, RDF.Property)
                .addProperty(RDFS.label, "Not confirmed")
                .addProperty(RDFS.comment, "Not confirmed tests property")
                .addProperty(RDFS.domain, ontoTests)
                .addProperty(RDFS.range, XSD.integer);
    }

    public void addCountryToModel(Country country) {
        String nombre = country.getName().replaceAll(" ", "_");
        String continent = country.getContinent().replaceAll(" ", "_");
        Resource dbrContinent = dbrModel.getResource(dbr + continent); // Continente tomado directamente de Dbpedia
        Resource myCountry = model.createResource(utplDataPrefix + country.getCountryCode())
                .addProperty(OWL.sameAs, dbrModel.getResource(dbr + nombre))
                .addProperty(RDF.type, dboModel.getResource(dbo + CLASS_COUNTRY))
                .addProperty(dboModel.getProperty(dbo, "name"), country.getName())
                .addProperty(gnModel.getProperty(gn, "countryCode"), country.getCountryCode())
                .addProperty(schemaModel.getProperty(schema, "latitude"), country.getLatitude())
                .addProperty(schemaModel.getProperty(schema, "longitude"), country.getLongitude())
                .addProperty(dboModel.getProperty(dbo, "populationTotal"), String.valueOf(country.getPopulation()))
                .addProperty(dbpModel.getProperty(dbp, "gdpNominalPerCapita"), String.valueOf(country.getGdp()))
                .addProperty(dboModel.getProperty(dbo, "continent"), dbrContinent);
    }

    public void addProvinceToModel(Province province) {
        String nombre = province.getName().replaceAll(" ", "_");
        Resource myCountry = model.getResource(utplDataPrefix + province.getCountryCode());
        Resource myProvince = model.createResource(utplDataPrefix + province.getProvinceCode())
                .addProperty(OWL.sameAs, dbrModel.getResource(dbr + nombre))
                .addProperty(RDF.type, dboModel.getResource(dbo + CLASS_PROVINCE))
                .addProperty(dboModel.getProperty(dbo, "name"), province.getName())
                .addProperty(dboModel.getProperty(dbo, "country"), myCountry);
    }

    public void addDatasetToModel(Dataset dataset) {
        String nombre = dataset.getName().replaceAll(" ", "_");
        Resource myDataset = model.createResource(utplDataPrefix + nombre.toUpperCase())
                .addProperty(RDF.type, dcatModel.getResource(dcat + CLASS_DATASET))
                .addProperty(dboModel.getProperty(dbo, "name"), dataset.getName())
                .addProperty(dboModel.getProperty(dbo, "description"), dataset.getDescription())
                .addProperty(dcatModel.getProperty(dcat, "downloadURL"), dataset.getDownloadURL())
                .addProperty(DCTerms.dateSubmitted, dataset.getDateSubmitted())
                .addProperty(DCTerms.modified, dataset.getDateModification());
    }

    public void addStatisticConfirmedToModel(Statistic statistic) {
        Resource myPlace;
        String uri = utplDataPrefix + "confirmed_";
        if (statistic.getProvinceCode() != null) {
            myPlace = model.getResource(utplDataPrefix + statistic.getProvinceCode());
            uri += statistic.getProvinceCode() + "_" + statistic.getId();
        } else {
            myPlace = model.getResource(utplDataPrefix + statistic.getCountryCode());
            uri += statistic.getCountryCode() + "_" + statistic.getId();
        }
        Resource myStatistic = model.createResource(uri)
                .addProperty(RDF.type, ontoModel.getResource(utplOntoPrefix + CLASS_CONFIRMED));
        Resource myDataset = model.getResource(utplDataPrefix + statistic.getDataset());
        if (statistic.getObservationDate() != null) {
            myStatistic.addProperty(schemaModel.getProperty(schema, "observationDate"), statistic.getObservationDate());
        }
        if (statistic.getQuantity() != null) {
            myStatistic.addProperty(ontoModel.getProperty(utplOntoPrefix, "quantity"), statistic.getQuantity());
        }
        if (statistic.getTotalQuantity() != null) {
            myStatistic.addProperty(ontoModel.getProperty(utplOntoPrefix, "totalQuantity"), statistic.getTotalQuantity());
        }
        myStatistic.addProperty(gnModel.getProperty(gn, "locatedIn"), myPlace);
        myStatistic.addProperty(provModel.getProperty(prov, "wasDerivedFrom"), myDataset);
    }

    public void addStatisticRecoveredToModel(Statistic statistic) {
        Resource myPlace;
        String uri = utplDataPrefix + "recovered_";
        if (statistic.getProvinceCode() != null) {
            myPlace = model.getResource(utplDataPrefix + statistic.getProvinceCode());
            uri += statistic.getProvinceCode() + "_" + statistic.getId();
        } else {
            myPlace = model.getResource(utplDataPrefix + statistic.getCountryCode());
            uri += statistic.getCountryCode() + "_" + statistic.getId();
        }
        Resource myStatistic = model.createResource(uri)
                .addProperty(RDF.type, ontoModel.getResource(utplOntoPrefix + CLASS_RECOVERED));
        Resource myDataset = model.getResource(utplDataPrefix + statistic.getDataset());
        if (statistic.getObservationDate() != null) {
            myStatistic.addProperty(schemaModel.getProperty(schema, "observationDate"), statistic.getObservationDate());
        }
        if (statistic.getQuantity() != null) {
            myStatistic.addProperty(ontoModel.getProperty(utplOntoPrefix, "quantity"), statistic.getQuantity());
        }
        if (statistic.getTotalQuantity() != null) {
            myStatistic.addProperty(ontoModel.getProperty(utplOntoPrefix, "totalQuantity"), statistic.getTotalQuantity());
        }
        myStatistic.addProperty(gnModel.getProperty(gn, "locatedIn"), myPlace);
        myStatistic.addProperty(provModel.getProperty(prov, "wasDerivedFrom"), myDataset);
    }

    public void addStatisticDeathToModel(Statistic statistic) {
        Resource myPlace;
        String uri = utplDataPrefix + "death_";
        if (statistic.getProvinceCode() != null) {
            myPlace = model.getResource(utplDataPrefix + statistic.getProvinceCode());
            uri += statistic.getProvinceCode() + "_" + statistic.getId();
        } else {
            myPlace = model.getResource(utplDataPrefix + statistic.getCountryCode());
            uri += statistic.getCountryCode() + "_" + statistic.getId();
        }
        Resource myStatistic = model.createResource(uri)
                .addProperty(RDF.type, ontoModel.getResource(utplOntoPrefix + CLASS_DEATHS));
        Resource myDataset = model.getResource(utplDataPrefix + statistic.getDataset());
        if (statistic.getObservationDate() != null) {
            myStatistic.addProperty(schemaModel.getProperty(schema, "observationDate"), statistic.getObservationDate());
        }
        if (statistic.getQuantity() != null) {
            myStatistic.addProperty(ontoModel.getProperty(utplOntoPrefix, "quantity"), statistic.getQuantity());
        }
        if (statistic.getTotalQuantity() != null) {
            myStatistic.addProperty(ontoModel.getProperty(utplOntoPrefix, "totalQuantity"), statistic.getTotalQuantity());
        }
        myStatistic.addProperty(gnModel.getProperty(gn, "locatedIn"), myPlace);
        myStatistic.addProperty(provModel.getProperty(prov, "wasDerivedFrom"), myDataset);
    }

    public void addStatisticActiveToModel(Statistic statistic) {
        Resource myPlace;
        String uri = utplDataPrefix + "active_";
        if (statistic.getProvinceCode() != null) {
            myPlace = model.getResource(utplDataPrefix + statistic.getProvinceCode());
            uri += statistic.getProvinceCode() + "_" + statistic.getId();
        } else {
            myPlace = model.getResource(utplDataPrefix + statistic.getCountryCode());
            uri += statistic.getCountryCode() + "_" + statistic.getId();
        }
        Resource myStatistic = model.createResource(uri)
                .addProperty(RDF.type, ontoModel.getResource(utplOntoPrefix + CLASS_ACTIVE));
        Resource myDataset = model.getResource(utplDataPrefix + statistic.getDataset());
        if (statistic.getObservationDate() != null) {
            myStatistic.addProperty(schemaModel.getProperty(schema, "observationDate"), statistic.getObservationDate());
        }
        if (statistic.getQuantity() != null) {
            myStatistic.addProperty(ontoModel.getProperty(utplOntoPrefix, "quantity"), statistic.getQuantity());
        }
        if (statistic.getTotalQuantity() != null) {
            myStatistic.addProperty(ontoModel.getProperty(utplOntoPrefix, "totalQuantity"), statistic.getTotalQuantity());
        }
        myStatistic.addProperty(gnModel.getProperty(gn, "locatedIn"), myPlace);
        myStatistic.addProperty(provModel.getProperty(prov, "wasDerivedFrom"), myDataset);
    }

    public void addStatisticHospitalizedToModel(StatisticHospitalized statisticHospitalized) {
        Resource myPlace;
        String uri = utplDataPrefix + "hospitalized_";
        if (statisticHospitalized.getProvinceCode() != null) {
            myPlace = model.getResource(utplDataPrefix + statisticHospitalized.getProvinceCode());
            uri += statisticHospitalized.getProvinceCode() + "_" + statisticHospitalized.getId();
        } else {
            myPlace = model.getResource(utplDataPrefix + statisticHospitalized.getCountryCode());
            uri += statisticHospitalized.getCountryCode() + "_" + statisticHospitalized.getId();
        }
        Resource myStatistic = model.createResource(uri)
                .addProperty(RDF.type, ontoModel.getResource(utplOntoPrefix + CLASS_HOSPITALIZED));
        Resource myDataset = model.getResource(utplDataPrefix + statisticHospitalized.getDataset());
        if (statisticHospitalized.getObservationDate() != null) {
            myStatistic.addProperty(schemaModel.getProperty(schema, "observationDate"), statisticHospitalized.getObservationDate());
        }
        if (statisticHospitalized.getQuantity() != null) {
            myStatistic.addProperty(ontoModel.getProperty(utplOntoPrefix, "quantity"), statisticHospitalized.getQuantity());
        }
        if (statisticHospitalized.getQuantityMild() != null) {
            myStatistic.addProperty(ontoModel.getProperty(utplOntoPrefix, "quantityMild"), statisticHospitalized.getQuantityMild());
        }
        if (statisticHospitalized.getQuantitySevere() != null) {
            myStatistic.addProperty(ontoModel.getProperty(utplOntoPrefix, "quantitySevere"), statisticHospitalized.getQuantitySevere());
        }
        if (statisticHospitalized.getQuantityUnknown() != null) {
            myStatistic.addProperty(ontoModel.getProperty(utplOntoPrefix, "quantityUnknown"), statisticHospitalized.getQuantityUnknown());
        }
        if (statisticHospitalized.getTotalQuantity() != null) {
            myStatistic.addProperty(ontoModel.getProperty(utplOntoPrefix, "totalQuantity"), statisticHospitalized.getTotalQuantity());
        }
        myStatistic.addProperty(gnModel.getProperty(gn, "locatedIn"), myPlace);
        myStatistic.addProperty(provModel.getProperty(prov, "wasDerivedFrom"), myDataset);
    }

    public void addTestToModel(Test test) {
        Resource myPlace;
        String uri = utplDataPrefix + "test_";
        if (test.getProvinceCode() != null) {
            myPlace = model.getResource(utplDataPrefix + test.getProvinceCode());
            uri += test.getProvinceCode() + "_" + test.getId();
        } else {
            myPlace = model.getResource(utplDataPrefix + test.getCountryCode());
            uri += test.getCountryCode() + "_" + test.getId();
        }
        Resource myTest = model.createResource(uri)
                .addProperty(RDF.type, ontoModel.getResource(utplOntoPrefix + CLASS_TESTS));
        Resource myDataset = model.getResource(utplDataPrefix + test.getDataset());
        if (test.getObservationDate() != null) {
            myTest.addProperty(schemaModel.getProperty(schema, "observationDate"), test.getObservationDate());
        }
        if (test.getPositives() != null) {
            myTest.addProperty(ontoModel.getProperty(utplOntoPrefix, "positives"), test.getPositives());
        }
        if (test.getNegatives() != null) {
            myTest.addProperty(ontoModel.getProperty(utplOntoPrefix, "negatives"), test.getNegatives());
        }
        if (test.getRealized() != null) {
            myTest.addProperty(ontoModel.getProperty(utplOntoPrefix, "realized"), test.getRealized());
        }
        if (test.getNotConfirmed() != null) {
            myTest.addProperty(ontoModel.getProperty(utplOntoPrefix, "notConfirmed"), test.getNotConfirmed());
        }
        myTest.addProperty(gnModel.getProperty(gn, "locatedIn"), myPlace);
        myTest.addProperty(provModel.getProperty(prov, "wasDerivedFrom"), myDataset);
    }

    public void addContainmentMeasureToModel(ContainmentMeasure containmentMeasure) {
        Resource myPlace;
        String uri = utplDataPrefix + "containment_measure_";
        if (containmentMeasure.getProvinceCode() != null) {
            myPlace = model.getResource(utplDataPrefix + containmentMeasure.getProvinceCode());
            uri += containmentMeasure.getProvinceCode() + "_" + containmentMeasure.getId();
        } else {
            myPlace = model.getResource(utplDataPrefix + containmentMeasure.getCountryCode());
            uri += containmentMeasure.getCountryCode() + "_" + containmentMeasure.getId();
        }
        Resource myContainmentMeasure = model.createResource(uri)
                .addProperty(RDF.type, ontoModel.getResource(utplOntoPrefix + CLASS_CONTAINMENT_MEASURES));
        Resource myDataset = model.getResource(utplDataPrefix + containmentMeasure.getDataset());
        if (containmentMeasure.getName() != null) {
            myContainmentMeasure.addProperty(dboModel.getProperty(dbo, "name"), containmentMeasure.getName());
        }
        if (containmentMeasure.getDate() != null) {
            myContainmentMeasure.addProperty(dboModel.getProperty(dbo, "date"), containmentMeasure.getDate());
        }
        if (containmentMeasure.getDescription() != null) {
            myContainmentMeasure.addProperty(dboModel.getProperty(dbo, "description"), containmentMeasure.getDescription());
        }
        myContainmentMeasure.addProperty(gnModel.getProperty(gn, "locatedIn"), myPlace);
        myContainmentMeasure.addProperty(provModel.getProperty(prov, "wasDerivedFrom"), myDataset);
    }

    public void imprimirModeloOntologico() {
        File f = new File("./src/main/java/data/utpl/edu/ec/onto.rdf");
        FileOutputStream os = null;
        ontoModel.write(System.out, "N3");
        try {
            os = new FileOutputStream(f);
            RDFWriter writer = ontoModel.getWriter("RDF/XML");
            writer.write(ontoModel, os, "");
            os.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            ontoModel.close();
        }
    }

    public void imprimirModelo() {
        File f = new File("./src/main/java/data/utpl/edu/ec/data.rdf");
        FileOutputStream os = null;
        model.write(System.out, "N3");
        try {
            os = new FileOutputStream(f);
            RDFWriter writer = model.getWriter("RDF/XML");
            writer.write(model, os, "");
            os.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            model.close();
        }
    }

}
