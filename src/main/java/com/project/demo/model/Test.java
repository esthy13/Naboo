package com.project.demo.model;

import com.project.demo.Scene.Encryptor;

public class Test {
    public static void main(String [] args){
        //dBinsert.DatabaseToCSV();
        //dBinsert.readCSV("C:\\Users\\giuli\\OneDrive\\Documenti\\Esame\\Naboo\\Notizia.csv", "www.corriere.it");
       // dBinsert.addC_shared(29);
        String key = "Bar12345Bar12345";
        String initVector = "RandomInitVector";
        String password = "top13";
        System.out.println(Encryptor.encrypt(key, initVector,password));

    }
}
