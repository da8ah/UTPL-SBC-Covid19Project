/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.utpl.edu.ec.visualizadordatoscovid;

import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import static spark.Spark.get;
import static spark.Spark.staticFileLocation;
import spark.template.freemarker.FreeMarkerEngine;

/**
 *
 * @author Danilo Alejandro Ochoa Hidalgo
 */
public class AppWebUI {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        /* RUTA PARA ENCONTRAR RECURSOS ***************************************/
        staticFileLocation("/public");

        // Inicio        
        get("/", (req, res) -> {

            String vista = "index.ftl";
            Map<String, Object> modelo = new HashMap<>();
            String datos = "";
            int contador = 0;
            for (HashMap<String, String> dato : ConsultorRDF.queryFechaUS(ConsultorRDF.getRepositoryConnection())) {
                contador++;
                datos += "<tr><td>" + contador + "</td><td>" + dato.get("recurso") + "</td><td>" + dato.get("fecha") + "</tr>";
            }
            modelo.put("datos", datos);
            return new ModelAndView(modelo, vista);
        }, new FreeMarkerEngine());

    }

}
