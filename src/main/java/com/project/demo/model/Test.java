package com.project.demo.model;

import com.project.demo.Scene.Encryptor;

import java.util.Map;

public class Test {
    public static void main(String [] args){
        //DBinsert dBinsert = new DBinsert();
        //dBinsert.modifyPasswordCrypt(18,"top13");
        DBget dBget = new DBget();
        //Map<String, Integer> map = dBget.getCount();
        System.out.println(dBget.getCount());
    }
}
