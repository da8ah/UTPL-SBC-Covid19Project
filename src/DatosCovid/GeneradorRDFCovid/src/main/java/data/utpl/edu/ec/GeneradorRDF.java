/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.utpl.edu.ec;

import data.utpl.edu.ec.modelo.ModeloCovid19;
import data.utpl.edu.ec.modelo.clases.ContainmentMeasure;
import data.utpl.edu.ec.modelo.clases.Country;
import data.utpl.edu.ec.modelo.clases.Dataset;
import data.utpl.edu.ec.modelo.clases.Province;
import data.utpl.edu.ec.modelo.clases.Statistic;
import data.utpl.edu.ec.modelo.clases.StatisticHospitalized;
import data.utpl.edu.ec.modelo.clases.Test;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author Danilo Alejandro Ochoa Hidalgo
 */
public class GeneradorRDF {

    private static final String COUNTRY_PATH = "./src/main/java/data/utpl/edu/ec/src/countries.csv";
    private static final String PROVINCE_PATH = "./src/main/java/data/utpl/edu/ec/src/provinces.csv";
    private static final String DATASETS_PATH = "./src/main/java/data/utpl/edu/ec/src/datasets.csv";
    private static final String STATISTICS_PATH = "./src/main/java/data/utpl/edu/ec/src/statistics.csv";
    private static final String MEASURES_PATH = "./src/main/java/data/utpl/edu/ec/src/measures.csv";
    private static final ModeloCovid19 modeloCovid19 = new ModeloCovid19();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        // Las funciones deben ser llamadas en un orden específico:
        modeloCovid19.imprimirModeloOntologico();
        cargarCountriesDesdeArchivo();
        cargarProvincesDesdeArchivo();
        cargarDatasetsDesdeArchivo();
        cargarMainDataSource();
        cargarContainmentMeasuresDesdeArchivo();
        modeloCovid19.imprimirModelo();

    }

    private static void cargarCountriesDesdeArchivo() {
        Country objCountry = new Country();
        FileInputStream fileInputStream = null;
        Scanner sc = null;
        try {
            fileInputStream = new FileInputStream(COUNTRY_PATH);
            sc = new Scanner(fileInputStream, "UTF-8");
            String line;
            String[] splitted;
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                splitted = line.split(";");
                objCountry.setContinent("South_America");
                objCountry.setCountryCode(splitted[0]);
                objCountry.setName(splitted[1]);
                objCountry.setLatitude(splitted[2]);
                objCountry.setLongitude(splitted[3]);
                objCountry.setPopulation(Integer.valueOf(splitted[4]));
                objCountry.setGdp(Float.valueOf(splitted[5]));
                modeloCovid19.addCountryToModel(objCountry);
            }
            // Note that Scanner suppresses exceptions
            if (sc.ioException() != null) {
                throw sc.ioException();
            }

            fileInputStream.close();
            sc.close();
        } catch (IOException io) {
            System.out.println(io);
        }
    }

    private static void cargarProvincesDesdeArchivo() {
        Province objProvince = new Province();
        FileInputStream fileInputStream = null;
        Scanner sc = null;
        try {
            fileInputStream = new FileInputStream(PROVINCE_PATH);
            sc = new Scanner(fileInputStream, "UTF-8");
            String line;
            String[] splitted;
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                splitted = line.split(";");
                objProvince.setCountryCode(splitted[0]);
                objProvince.setProvinceCode(splitted[1].replaceAll("-", "_"));
                objProvince.setName(splitted[2]);
                modeloCovid19.addProvinceToModel(objProvince);
            }
            // Note that Scanner suppresses exceptions
            if (sc.ioException() != null) {
                throw sc.ioException();
            }

            fileInputStream.close();
            sc.close();
        } catch (IOException io) {
            System.out.println(io);
        }
    }

    private static void cargarDatasetsDesdeArchivo() {
        Dataset objDataset = new Dataset();
        FileInputStream fileInputStream = null;
        Scanner sc = null;
        try {
            fileInputStream = new FileInputStream(DATASETS_PATH);
            sc = new Scanner(fileInputStream, "UTF-8");
            String line;
            String[] splitted;
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                splitted = line.split(";");
                objDataset.setName(splitted[0]);
                objDataset.setDescription(splitted[1]);
                objDataset.setDownloadURL(splitted[2]);
                objDataset.setDateSubmitted(splitted[3]);
                objDataset.setDateModification(splitted[4]);
                modeloCovid19.addDatasetToModel(objDataset);
            }
            // Note that Scanner suppresses exceptions
            if (sc.ioException() != null) {
                throw sc.ioException();
            }

            fileInputStream.close();
            sc.close();
        } catch (IOException io) {
            System.out.println(io);
        }
    }

    private static void cargarMainDataSource() {
        Statistic objConfirmed = new Statistic();
        Statistic objRecovered = new Statistic();
        Statistic objDeath = new Statistic();
        StatisticHospitalized objHospitalized = new StatisticHospitalized();
        Test objTest = new Test();
        FileInputStream fileInputStream = null;
        Scanner sc = null;
        try {
            fileInputStream = new FileInputStream(STATISTICS_PATH);
            sc = new Scanner(fileInputStream, "UTF-8");
            String line;
            String[] splitted;
            String date, countryCode, provinceCode, confirmed, confirmedTotal,
                    decease, deceaseTotal, recovered, recoveredTotal, hospitalized,
                    hospitalizedTotal, unknown, test, testTotal, severe, mild;
            String previousPlaceState = "";
            int contadorConfirmed = 0;
            int contadorRecovered = 0;
            int contadorDecease = 0;
            int contadorHospitalized = 0;
            int contadorTest = 0;

            String encabezados = sc.nextLine();
            int contador = 2;
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                if (Character.compare(line.charAt(line.length() - 1), ',') == 0) {
                    line += "0";
                }
                splitted = line.split(",");
                //System.out.println(contador + ": " + Arrays.toString(splitted));

                // CAPTURA DE DATOS
                date = splitted[1];
                countryCode = splitted[4];
                provinceCode = splitted[6];
                confirmed = splitted[37];
                decease = splitted[38];
                recovered = splitted[39];
                test = splitted[40];
                confirmedTotal = splitted[41];
                deceaseTotal = splitted[42];
                recoveredTotal = splitted[43];
                testTotal = splitted[44];
                hospitalized = splitted[65];
                hospitalizedTotal = splitted[66];
                unknown = splitted[67];
                severe = splitted[69];
                mild = (!hospitalizedTotal.equals("") && !severe.equals(""))
                        ? String.valueOf(Integer.valueOf(hospitalizedTotal) - Integer.valueOf(severe)) : "";
                String dataset = "covid_19_open_data".toUpperCase();

                // MAPEO DE DATOS
                // Set Place
                if (!provinceCode.equals("")) { // Country and Province Requerido
                    objConfirmed.setProvinceCode(countryCode + "_" + provinceCode);
                    objRecovered.setProvinceCode(countryCode + "_" + provinceCode);
                    objDeath.setProvinceCode(countryCode + "_" + provinceCode);
                    objHospitalized.setProvinceCode(countryCode + "_" + provinceCode);
                    objTest.setProvinceCode(countryCode + "_" + provinceCode);
                } else if (!countryCode.equals("")) {
                    objConfirmed.setCountryCode(countryCode);
                    objRecovered.setCountryCode(countryCode);
                    objDeath.setCountryCode(countryCode);
                    objHospitalized.setCountryCode(countryCode);
                    objTest.setCountryCode(countryCode);
                }

                // Set Date
                if (!date.equals("")) { // Fecha Requerida
                    objConfirmed.setObservationDate(date);
                    objRecovered.setObservationDate(date);
                    objDeath.setObservationDate(date);
                    objHospitalized.setObservationDate(date);
                    objTest.setObservationDate(date);
                }

                // Set Quantity
                if (!confirmed.equals("")) {
                    objConfirmed.setQuantity(confirmed);
                }
                if (!recovered.equals("")) {
                    objRecovered.setQuantity(recovered);
                }
                if (!decease.equals("")) {
                    objDeath.setQuantity(decease);
                }
                if (!hospitalized.equals("")) {
                    objHospitalized.setQuantity(hospitalized);
                }

                // Set totalQuantity
                if (!confirmedTotal.equals("")) {
                    objConfirmed.setTotalQuantity(confirmedTotal);
                }
                if (!recoveredTotal.equals("")) {
                    objRecovered.setTotalQuantity(recoveredTotal);
                }
                if (!deceaseTotal.equals("")) {
                    objDeath.setTotalQuantity(deceaseTotal);
                }
                if (!hospitalizedTotal.equals("")) {
                    objHospitalized.setTotalQuantity(hospitalizedTotal);
                }

                // Set values to Hospilized
                if (!mild.equals("")) {
                    objHospitalized.setQuantityMild(mild);
                }
                if (!severe.equals("")) {
                    objHospitalized.setQuantitySevere(severe);
                }
                if (!unknown.equals("")) {
                    objHospitalized.setQuantityUnknown(unknown);
                }

                // Set values to Test
                if (!test.equals("")) {
                    objTest.setNotConfirmed(test);
                }
                if (!testTotal.equals("")) {
                    objTest.setRealized(testTotal);
                }

                // Set Datasets
                objConfirmed.setDataset(dataset);
                objRecovered.setDataset(dataset);
                objDeath.setDataset(dataset);
                objHospitalized.setDataset(dataset);
                objTest.setDataset(dataset);

                // INTEGRACIÓN DE DATOS AL MODELO
                // Crear Instancias
                if (!previousPlaceState.equals(countryCode + provinceCode)) {
                    previousPlaceState = countryCode + provinceCode;
                    contadorConfirmed = 0;
                    contadorRecovered = 0;
                    contadorDecease = 0;
                    contadorHospitalized = 0;
                    contadorTest = 0;
                }

                if (!date.equals("") && (!provinceCode.equals("") || !countryCode.equals(""))) {
                    if (!confirmed.equals("") || !confirmedTotal.equals("")) {
                        contadorConfirmed++;
                        objConfirmed.setId(String.valueOf(contadorConfirmed));
                        modeloCovid19.addStatisticConfirmedToModel(objConfirmed);
                    }
                    if (!recovered.equals("") || !recoveredTotal.equals("")) {
                        contadorRecovered++;
                        objRecovered.setId(String.valueOf(contadorRecovered));
                        modeloCovid19.addStatisticRecoveredToModel(objRecovered);
                    }
                    if (!decease.equals("") || !deceaseTotal.equals("")) {
                        contadorDecease++;
                        objDeath.setId(String.valueOf(contadorDecease));
                        modeloCovid19.addStatisticDeathToModel(objDeath);
                    }
                    if (!hospitalized.equals("") || !hospitalizedTotal.equals("") || !mild.equals("") || !severe.equals("") || !unknown.equals("")) {
                        contadorHospitalized++;
                        objHospitalized.setId(String.valueOf(contadorHospitalized));
                        modeloCovid19.addStatisticHospitalizedToModel(objHospitalized);
                    }
                    if (!test.equals("") || !testTotal.equals("")) {
                        contadorTest++;
                        objTest.setId(String.valueOf(contadorTest));
                        modeloCovid19.addTestToModel(objTest);
                    }
                }
                contador++;
            }
            // Note that Scanner suppresses exceptions
            if (sc.ioException() != null) {
                throw sc.ioException();
            }

            fileInputStream.close();
            sc.close();
        } catch (IOException io) {
            System.out.println(io);
        }
    }

    private static void cargarContainmentMeasuresDesdeArchivo() {
        ContainmentMeasure objContainmentMeasure = new ContainmentMeasure();
        FileInputStream fileInputStream = null;
        Scanner sc = null;
        try {
            fileInputStream = new FileInputStream(MEASURES_PATH);
            sc = new Scanner(fileInputStream, "UTF-8");
            String line;
            String[] splitted;
            int contadorMeasures = 0;
            String dataset = "COVID19_GOVERNMENT_MEASURES_DATASET".toUpperCase();

            String encabezados = sc.nextLine();
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                splitted = line.split(";");
                objContainmentMeasure.setCountryCode(splitted[0]);
                if (!splitted[1].equals("")) {
                    objContainmentMeasure.setDate(splitted[1]);
                }
                if (!splitted[2].equals("")) {
                    objContainmentMeasure.setName(splitted[2]);
                }
                if (!splitted[3].equals("")) {
                    objContainmentMeasure.setDescription(splitted[3]);
                }
                objContainmentMeasure.setDataset(dataset);
                contadorMeasures++;
                objContainmentMeasure.setId(String.valueOf(contadorMeasures));
                modeloCovid19.addContainmentMeasureToModel(objContainmentMeasure);
            }
            // Note that Scanner suppresses exceptions
            if (sc.ioException() != null) {
                throw sc.ioException();
            }

            fileInputStream.close();
            sc.close();
        } catch (IOException io) {
            System.out.println(io);
        }
    }
}
