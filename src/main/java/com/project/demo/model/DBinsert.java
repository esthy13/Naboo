package com.project.demo.model;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

import java.io.FileReader;
import java.io.FileWriter;
import java.sql.*;
import java.util.List;


/*4. Inserire notizie nella base di dati*/
public class DBinsert extends DBconnect {
    public void insertData(String Titolo, String Pubblicazione, String Descrizione,
                           String Autore, String Fonte, String Link, String Immagine,
                           int C_liked,int C_disliked,int C_reported,String rss){

        String query = "insert into Notizia"
                +"(Titolo,Pubblicazione,Descrizione,Autore,Fonte,Link,Immagine,C_liked,C_disliked,C_reported)"
                +"values ("
                +"'"+Titolo+"',"
                +"'"+Pubblicazione+"',"
                +"'"+Descrizione+"',"
                +"'"+Autore+"',"
                +"'"+Fonte+"',"
                +"'"+Link+"',"
                +"'"+Immagine+"',"
                +"'"+C_liked+"',"
                +"'"+C_disliked+"',"
                +"'"+C_reported+"');";

        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Naboo", "root", "");
            st = con.createStatement();
            //st.executeUpdate(query);
            executeSQLQuery(query,"Inserimento NOTIZIA completato");

            insertFormata(getRss(rss),lastId_notizia());

            con.close();
            st.close();
        }catch(Exception ex){
            System.out.println("Error:"+ex);
            //executeSQLQuery(query,"Inserimento non completato");
        }
    }


    public void insertDataDefault(String Titolo, String Pubblicazione, String Descrizione,
                                  String Autore, String Fonte, String Link, String Immagine,String rss){

        String query = "INSERT INTO Notizia"
                +"(Titolo,Pubblicazione,Descrizione,Autore,Fonte,Link,Immagine)"
                +"SELECT * FROM (SELECT"
                +"'"+Titolo+"',"
                +"'"+Pubblicazione+"',"
                +"'"+Descrizione+"',"
                +"'"+Autore+"',"
                +"'"+Fonte+"',"
                +"'"+Link+"',"
                +"'"+Immagine+"') AS tmp WHERE NOT EXISTS ( SELECT titolo FROM Notizia WHERE titolo = "+"'"+Titolo+"' ) LIMIT 1;";;

        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Naboo", "root", "");
            st = con.createStatement();
            //st.executeUpdate(query);
            executeSQLQuery(query,"Inserimento NOTIZIA completato");

            insertFormata(getRss(rss),lastId_notizia());

            con.close();
            st.close();
        }catch(Exception ex){
            System.out.println("Error:"+ex);
            //executeSQLQuery(query,"Inserimento non completato");
        }
    }


    /*6. Aggiunge profili degli utenti*/
    public void insertUser(String username, String password, String ruolo){

        String query = "INSERT INTO utenti (username, password, ruolo) SELECT "+ "'" + username + "', '" + password + "', '" +
                ruolo + "'" + " FROM DUAL WHERE NOT EXISTS (SELECT username FROM utenti WHERE username = " + "'" + username +"');";

        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Naboo", "root", "");
            st = con.createStatement();
            //st.executeUpdate(query);
            executeSQLQuery(query,"Inserimento notizia completato");
            con.close();
            st.close();
        }catch(Exception ex){
            System.out.println("Error:"+ex);
            //executeSQLQuery(query,"Inserimento non completato");
        }
    }
    public void insertUser(String username, String ruolo){

        String query = "INSERT INTO utenti (username, ruolo) SELECT "+ "'" + username + "', '" +
                ruolo + "'" + " FROM DUAL WHERE NOT EXISTS (SELECT username FROM utenti WHERE username = " + "'" + username +"');";

        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Naboo", "root", "");
            st = con.createStatement();
            //st.executeUpdate(query);
            executeSQLQuery(query,"Inserimento utente completato");
            con.close();
            st.close();
        }catch(Exception ex){
            System.out.println("Error:"+ex);
            //executeSQLQuery(query,"Inserimento non completato");
        }
    }

    /*8. Modificare username degli utenti */
    public void updateUser(String id_utente, String newusername){

        String query = "UPDATE Utenti SET username ="+" '"+newusername+"'"+" WHERE Utenti.id_utente = "+"'"+id_utente+"'";

        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Naboo", "root", "");
            st = con.createStatement();
            //st.executeUpdate(query);
            executeSQLQuery(query,"Aggiornamento username riuscito");
            con.close();
            st.close();
        }catch(Exception ex){
            System.out.println("Error:"+ex);
            //executeSQLQuery(query,"Inserimento non completato");
        }
    }


    public DBinsert DatabaseToCSV(String filepath) {
        String filename = filepath + "/Notizia.csv";
        try {
            FileWriter fw = new FileWriter(filename);
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Naboo", "root", "");
            String query = "select * from Notizia";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            fw.append(("id_notizia"));  fw.append(';'); fw.append(("Titolo")); fw.append(';'); fw.append(("Pubblicazione"));fw.append(';');
            fw.append(("Descrizione")); fw.append(';');  fw.append(("Autore")); fw.append(';');  fw.append(("Fonte")); fw.append(';');  fw.append(("Link"));
            fw.append(';');  fw.append(("Immagine"));  fw.append(';');  fw.append(("C_liked")); fw.append(';');  fw.append(("C_disliked"));
            fw.append(';');  fw.append(("C_shared"));fw.append('\n');
            while (rs.next()) {
                fw.append(rs.getString(1));
                fw.append(';');
                fw.append(rs.getString(2));
                fw.append(';');
                fw.append(rs.getString(3));
                fw.append(';');
                fw.append(rs.getString(4));
                fw.append(';');
                fw.append(rs.getString(5));
                fw.append(';');
                fw.append(rs.getString(6));
                fw.append(';');
                fw.append(rs.getString(7));
                fw.append(';');
                fw.append(rs.getString(8));
                fw.append(';');
                fw.append(rs.getString(9));
                fw.append(';');
                fw.append(rs.getString(10));
                fw.append(';');
                fw.append(rs.getString(11));
                fw.append('\n');
            }
            fw.flush();
            fw.close();
            con.close();
            System.out.println("Il file CSV e' stato creato.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*10. Importare dati da file*/
    public DBinsert readCSV(String filename, String rss){
        try {
            /*String Titolo, String Pubblicazione, String Descrizione,
             String Autore, String Fonte, String Link, String Immagine,
             int C_liked,int C_disliked,int C_shared*/
            FileReader filereader = new FileReader(filename);
            CSVParser parser = new CSVParserBuilder().withSeparator(';').build();
            CSVReader csvReader = new CSVReaderBuilder(filereader).withCSVParser(parser).build();
            // Lettura di tutti i dati
            List<String[]> allData = csvReader.readAll();
            String titolo = "";
            String Pubblicazione  = "";
            String Descrizione = "";
            String Autore = "";
            String Fonte = "";
            String Link = "";
            String Immagine = "";
            int C_liked;
            int C_disliked;
            int C_shared;
            allData.remove(0);
            for (String[] row : allData) {
                titolo = row[1];
                Pubblicazione = row[2];
                Descrizione = row[3];
                Autore = row[4];
                Fonte = row[5];
                Link = row[6];
                Immagine=row[7];
                C_liked = Integer.parseInt(row[8]);
                C_disliked = Integer.parseInt(row[9]);
                C_shared = Integer.parseInt(row[10]);
                //insertDataCSV(titolo, Pubblicazione,Descrizione,Autore,Fonte,Link,Immagine,C_liked,C_disliked,C_shared,rss);
                InsertNews(titolo, Pubblicazione,Descrizione,Autore,Fonte,Link,Immagine,C_liked,C_disliked,C_shared,rss);

            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*11. Incrementare numero likes in base alla notizia */
    public void addLike(int id_notizia){
        String query = "UPDATE Notizia " +"SET c_liked = c_liked + 1 WHERE id_notizia = " +"'"+id_notizia+"';";
        //UPDATE Notizia SET c_liked = c_liked + 1 WHERE id_notizia = '427';
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Naboo", "root", "");
            st = con.createStatement();
            //st.executeUpdate(query);
            executeSQLQuery(query,"Like aggiunto");
            con.close();
            st.close();
        }catch(Exception ex){
            System.out.println("Error:"+ex);
            //executeSQLQuery(query,"Inserimento non completato");
        }
    }

    /*13. Incrementare numero dislike in base alla notizia */
    public void addDislike(int id_notizia){
        String query = "UPDATE Notizia " + "SET c_disliked = c_disliked + 1 WHERE id_notizia = " +"'"+id_notizia+"';";
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Naboo", "root", "");
            st = con.createStatement();
            //st.executeUpdate(query);
            executeSQLQuery(query,"Dislike aggiunto");
            con.close();
            st.close();
        }catch(Exception ex){
            System.out.println("Error:"+ex);
            //executeSQLQuery(query,"Inserimento non completato");
        }
    }

    /*17.Incrementare numero shared in base alla notizia */

    public void addC_shared(int id_notizia){
        String query = "UPDATE Notizia " +"SET c_shared = c_shared + 1 WHERE id_notizia = " +"'"+id_notizia+"';";

        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Naboo", "root", "");
            st = con.createStatement();
            //st.executeUpdate(query);
            executeSQLQuery(query,"c_shared aggiunto");
            con.close();
            st.close();
        }catch(Exception ex){
            System.out.println("Error:"+ex);
            //executeSQLQuery(query,"Inserimento non completato");
        }
    }

    /*19.Inseriemnto commento in base alla notizia e all'utente */

    public void insertComment(String commento, String id_notizia, String id_utente){

        String query = "INSERT INTO Commenti"
                +"(commento)"
                +"values ("
                +"'"+commento+"');";

        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Naboo", "root", "");
            st = con.createStatement();
            //st.executeUpdate(query);
            executeSQLQuery(query,"Inserimento  commento completato");
            DBget dBget = new DBget();
            int id_commento = dBget.lastId_comment();

            insertPossiede(id_commento,id_notizia, id_utente);
            con.close();
            st.close();
        }catch(Exception ex){
            System.out.println("Error:"+ex);
            //executeSQLQuery(query,"Inserimento non completato");
        }
    }

    /* Inseriemnto in possiede */
    public void insertPossiede(int id_commento, String id_notizia, String id_utente){
        String query  = "INSERT INTO Possiede"
                +"(id_commento,id_notizia,id_utente)"
                +"values ("
                +"'"+id_commento+"',"
                +"'"+id_notizia+"',"
                +"'"+id_utente+"');";

        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Naboo", "root", "");
            st = con.createStatement();
            st.executeUpdate(query);
            //executeSQLQuery(query,"Inserimento possiede completato"); toomanyconn
            con.close();
            st.close();
        }catch(Exception ex){
            System.out.println("Error:"+ex);
            //executeSQLQuery(query,"Inserimento non completato");
        }
    }

    /* Inseriemnto Interagiscono */
    public void inserInteragisce(int id_notizia, int id_utente){
        String query  = "INSERT INTO Interagiscono"
                +"(id_notizia,id_utente) "
                +"values ("
                +"'"+id_notizia+"',"
                +"'"+id_utente+"');";

        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Naboo", "root", "");
            st = con.createStatement();
            //st.executeUpdate(query);
            executeSQLQuery(query,"Inserimento interagiscono completato");
            con.close();
            st.close();
        }catch(Exception ex){
            System.out.println("Error:"+ex);
            //executeSQLQuery(query,"Inserimento non completato");
        }
    }

    /*6. Aggiunge fonte */
    public void insertFonte(String rss){

        String query = "INSERT INTO Fonti (rss) SELECT "+ "'" + rss + "'" + " FROM DUAL WHERE NOT EXISTS (SELECT rss FROM Fonti WHERE rss = " + "'" + rss +"');";

        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Naboo", "root", "");
            st = con.createStatement();
            //st.executeUpdate(query);
            executeSQLQuery(query,"Inserimento fonte completato");
            con.close();
            st.close();
        }catch(Exception ex){
            System.out.println("Error:"+ex);
            //executeSQLQuery(query,"Inserimento non completato");
        }
    }

    /* Inseriemnto Formata */
    public void insertFormata(String rss,int id_notizia){
        String query  = "INSERT INTO Formata"
                +"(rss,id_notizia) "
                +"values ("
                +"'"+rss+"',"
                +"'"+id_notizia+"');";

        try{
            st = con.createStatement();
            st.executeUpdate(query);
            //executeSQLQuery(query,"Inserimento formata completato"); toomanyconnection
            st.close();
        }catch(Exception ex){
            System.out.println("Error:"+ex);
            //executeSQLQuery(query,"Inserimento non completato");
        }
    }

    /*Resistuire l'id dell'ultima news inserita*/
    public int lastId_notizia(){
        String query = "SELECT id_notizia FROM Notizia ORDER BY id_notizia DESC LIMIT 1;";
        int id_notizia = 0;
        try{
            st = con.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                id_notizia = rs.getInt(1);
            }

            st.close();
            return id_notizia;
        }catch(Exception ex){
            System.out.println("Error:"+ex);
            //executeSQLQuery(query,"Inserimento non completato");
        }
        return id_notizia;
    }

    /*Resistuire rss */
    public String getRss(String rss){
        String query = "SELECT rss FROM Fonti where rss = "+"'"+rss+"';";
        String srss = "";
        try{
            st = con.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                srss = rs.getString(1);
            }
            st.close();
            return srss;
        }catch(Exception ex){
            System.out.println("Error:"+ex);
            //executeSQLQuery(query,"Inserimento non completato");
        }
        return srss;
    }
    public void modifyRole(int id_utente, String ruolo){
        String query = "UPDATE Utenti SET ruolo = '" +ruolo+ "' WHERE id_utente = '" +id_utente+"';";

        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Naboo", "root", "");
            st = con.createStatement();
            //st.executeUpdate(query);
            executeSQLQuery(query,"c_shared aggiunto");
            con.close();
            st.close();
        }catch(Exception ex){
            System.out.println("Error:"+ex);
            //executeSQLQuery(query,"Inserimento non completato");
        }
    }



    public void InsertNews(String Titolo, String Pubblicazione, String Descrizione,
                                  String Autore, String Fonte, String Link, String Immagine,
                                  int C_liked,int C_disliked,int C_reported,String rss){

        PreparedStatement psinsert = null;
        PreparedStatement pscheckNewsExists = null;
        ResultSet resultSet = null;

        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Naboo", "root", "");;
            pscheckNewsExists = con.prepareStatement("SELECT * FROM notizia WHERE titolo = ?");
            pscheckNewsExists.setString(1, Titolo);
            resultSet = pscheckNewsExists.executeQuery();

            if (resultSet.isBeforeFirst()){
                System.out.println("Notizia gia' presente nel database.");
            }else {
                psinsert = con.prepareStatement("INSERT INTO notizia(`titolo`, `pubblicazione`, `descrizione`, `autore`, `fonte`, `link`, `immagine`, `c_liked`, `c_disliked`, `c_reported`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                psinsert.setString(1, Titolo);
                psinsert.setString(2, Pubblicazione);
                psinsert.setString(3, Descrizione);
                psinsert.setString(4, Autore);
                psinsert.setString(5, Fonte);
                psinsert.setString(6, Link);
                psinsert.setString(7, Immagine);
                psinsert.setInt(8, C_liked);
                psinsert.setInt(9, C_disliked);
                psinsert.setInt(10, C_reported);
                psinsert.executeUpdate();
                insertFormata(getRss(rss),lastId_notizia());

                st = con.createStatement();
                //st.executeUpdate(query);
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }finally {
            if (resultSet != null){
                try {
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (pscheckNewsExists != null){
                try {
                    pscheckNewsExists.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (psinsert != null){
                try {
                    psinsert.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (con != null){
                try {
                    con.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void modifyPassword(int id_utente){
        String query = "UPDATE Utenti SET password = '"+null+"' WHERE id_utente = '" +id_utente+"';";

        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Naboo", "root", "");
            st = con.createStatement();
            //st.executeUpdate(query);
            executeSQLQuery(query,"password aggiornata a null");
            con.close();
            st.close();
        }catch(Exception ex){
            System.out.println("Error:"+ex);
            //executeSQLQuery(query,"Inserimento non completato");
        }
    }






}
