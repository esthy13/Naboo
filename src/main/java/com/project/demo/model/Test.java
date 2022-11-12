package com.project.demo.model;

public class Test {
    public static void main(String [] args){
        DBinsert dBinsert = new DBinsert();
        //dBinsert.DatabaseToCSV();
        dBinsert.readCSV("C:\\Users\\giuli\\OneDrive\\Documenti\\Esame\\Naboo\\Notizia.csv", "www.corriere.it");

    }
}
