/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.utpl.edu.ec.modelo.clases;

/**
 *
 * @author Danilo Alejandro Ochoa Hidalgo
 */
public class StatisticHospitalized extends Statistic {

    private String quantityMild;
    private String quantitySevere;
    private String quantityUnknown;

    public StatisticHospitalized() {
    }

    public String getQuantityMild() {
        return quantityMild;
    }

    public void setQuantityMild(String quantityMild) {
        this.quantityMild = quantityMild;
    }

    public String getQuantitySevere() {
        return quantitySevere;
    }

    public void setQuantitySevere(String quantitySevere) {
        this.quantitySevere = quantitySevere;
    }

    public String getQuantityUnknown() {
        return quantityUnknown;
    }

    public void setQuantityUnknown(String quantityUnknown) {
        this.quantityUnknown = quantityUnknown;
    }

}
