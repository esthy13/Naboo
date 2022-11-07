package com.project.demo.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class DBconnect {
    public static Connection con;
    public static Statement st;
    public ResultSet rs;

    ArrayList<Notizia> Catalogo;
    //ArrayList<Commento> Comments;

   

 public static Connection getConnection(){
        Connection con;
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Naboo", "root", "");
            return con;
        }catch(Exception ex){
            System.out.println("Error:"+ex);
            return null;
        }
    }
    public static void executeSQLQuery(String query, String message){
        Connection con = getConnection();
        try {
            st = con.createStatement();
            if((st.executeUpdate(query)) == 1){
                System.out.println(message);
            }else{
                System.out.println(message);
            }
        } catch (Exception ex) {
            System.out.println("Error:" + ex);
        }
    }

    /*1. Aggiungere una fonte  JAVA FX */

    /*2. Rimuovere una fonte  JAVA FX */

    /*3. Aggiorna la base di dati, invocando News Collector per controllare eventuali aggiornamenti dalle fonti JAVA FX */



    /*24. Evitare notizie dublicate all'inseriemnto */

    public String inspector(String fonte){
        // prende la data dell'ultimo insrimento!

        String query = "SELECT pubblicazione FROM Notizia WHERE fonte = "
                + "'"+fonte+"'"+" ORDER BY pubblicazione DESC LIMIT 1";
        String pubblicazione = "";
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Naboo", "root", "");
            st = con.createStatement();
            //executeSQLQuery(query,"Seleziono...");
            rs = st.executeQuery(query);
            while (rs.next()) {
                 pubblicazione = rs.getString(1);

                //System.out.println("Pubblicazione: " + pubblicazione);
            }
            con.close();
            st.close();
            return pubblicazione;
        }catch(Exception ex){
            System.out.println("Error:"+ex);
            //executeSQLQuery(query,"Inserimento non completato");
        }
        return pubblicazione;
    }

    public static void deleteInteragiscono(){}

}
