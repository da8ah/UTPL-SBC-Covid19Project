/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.utpl.edu.ec.visualizadordatoscovid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.eclipse.rdf4j.model.impl.SimpleIRI;
import org.eclipse.rdf4j.model.impl.SimpleLiteral;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.QueryEvaluationException;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.http.HTTPRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/**
 *
 * @author Danilo Alejandro Ochoa Hidalgo
 */
public class ConsultorRDF {

    private static Logger logger = (Logger) LoggerFactory.getLogger(ConsultorRDF.class);
// Why This Failure marker
    private static final Marker WTF_MARKER = MarkerFactory.getMarker("WTF");
// GraphDB 
    private static final String GRAPHDB_SERVER = "http://localhost:7200/";
    private static final String REPOSITORY_ID = "utplCovid19";

    private static String queryConfirmados;
    private static String queryFechaUS;

    public static RepositoryConnection getRepositoryConnection() {
        Repository repository = new HTTPRepository(GRAPHDB_SERVER, REPOSITORY_ID);
        repository.init();
        RepositoryConnection repositoryConnection = repository.getConnection();
        return repositoryConnection;
    }

    static {
        queryConfirmados = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
                + "PREFIX utplData: <http://data.utpl.edu.ec/COVID19-Pandemic/resource/>\n"
                + "PREFIX gn: <http://www.geonames.org/ontology#>\n"
                + "PREFIX utplOnto: <http://data.utpl.edu.ec/COVID19-Pandemic/ontology#>\n"
                + "PREFIX schema: <http://schema.org/>\n"
                + "SELECT ?recurso ?fecha\n"
                + "WHERE {\n"
                + "    ?recurso rdf:type utplOnto:Confirmed ;\n"
                + "    schema:observationDate ?fecha .\n"
                + "} ORDER BY ?fecha";

        queryFechaUS = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
                + "PREFIX gn: <http://www.geonames.org/ontology#>\n"
                + "PREFIX utplData: <http://data.utpl.edu.ec/COVID19-Pandemic/resource/>\n"
                + "PREFIX utplOnto: <http://data.utpl.edu.ec/COVID19-Pandemic/ontology#>\n"
                + "PREFIX schema: <http://schema.org/>\n"
                + "SELECT DISTINCT * \n"
                + "WHERE {\n"
                + "	?recurso gn:locatedIn utplData:US ;\n"
                + "    schema:observationDate ?fecha .\n"
                + "    FILTER (?fecha >= \"2020-02-01\" && ?fecha < \"2020-03-01\")\n"
                + "} Orderby ?fecha";
    }

    public static List<HashMap<String, String>> queryConfirmados(RepositoryConnection repositoryConnection) {
        TupleQuery tupleQuery = repositoryConnection
                .prepareTupleQuery(QueryLanguage.SPARQL, queryConfirmados);
        TupleQueryResult result = null;

        List<HashMap<String, String>> listadoMapaDatos = new ArrayList<>();
        try {
            result = tupleQuery.evaluate();
            while (result.hasNext()) {
                BindingSet bindingSet = result.next();
                SimpleIRI recurso = (SimpleIRI) bindingSet.getValue("recurso");
                SimpleLiteral fecha = (SimpleLiteral) bindingSet.getValue("fecha");

                HashMap<String, String> mapaDatos = new HashMap<>();

                mapaDatos.put("recurso", recurso.stringValue());
                mapaDatos.put("fecha", fecha.stringValue());

                listadoMapaDatos.add(mapaDatos);
            }
        } catch (QueryEvaluationException qee) {
            logger.error(WTF_MARKER,
                    qee.getStackTrace().toString(), qee);
        } finally {
            result.close();
        }
        return listadoMapaDatos;
    }

    public static List<HashMap<String, String>> queryFechaUS(RepositoryConnection repositoryConnection) {
        TupleQuery tupleQuery = repositoryConnection
                .prepareTupleQuery(QueryLanguage.SPARQL, queryFechaUS);
        TupleQueryResult result = null;

        List<HashMap<String, String>> listadoMapaDatos = new ArrayList<>();
        try {
            result = tupleQuery.evaluate();
            while (result.hasNext()) {
                BindingSet bindingSet = result.next();
                SimpleIRI recurso = (SimpleIRI) bindingSet.getValue("recurso");
                SimpleLiteral fecha = (SimpleLiteral) bindingSet.getValue("fecha");

                HashMap<String, String> mapaDatos = new HashMap<>();

                mapaDatos.put("recurso", recurso.stringValue());
                mapaDatos.put("fecha", fecha.stringValue());

                listadoMapaDatos.add(mapaDatos);
            }
        } catch (QueryEvaluationException qee) {
            logger.error(WTF_MARKER,
                    qee.getStackTrace().toString(), qee);
        } finally {
            result.close();
        }
        return listadoMapaDatos;
    }

}
