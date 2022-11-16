package com.project.demo.model;

import com.project.demo.Scene.Encryptor;

public class Test {
    public static void main(String [] args){
        //DBinsert dBinsert = new DBinsert();
        //dBinsert.modifyPasswordCrypt(18,"top13");
        DBget dBget = new DBget();
        //System.out.println(dBget.getListFonti());
        System.out.println(dBget.getCount());
    }
}
