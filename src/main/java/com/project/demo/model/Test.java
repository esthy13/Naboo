package com.project.demo.model;

import com.project.demo.Scene.Encryptor;

import java.math.BigDecimal;
import java.util.Map;

public class Test {
    public static void main(String [] args){

        DBget dBget = new DBget();
        String roundOff = String.format("%.4f", dBget.getCountReported()/Double.parseDouble(dBget.getCountNews()));
        System.out.println(roundOff);
    }
}
