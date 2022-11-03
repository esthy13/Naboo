package com.project.demo.model;

import java.sql.*;
import java.util.ArrayList;


public class DBget  extends DBconnect{

    /*15. Visualizzare numero likes in base alla notizia */
    public int selectC_liked(int id_notizia){
        String query = "SELECT c_liked FROM Notizia where id_notizia ="+"'"+id_notizia+"';";
        int c_liked = 0;
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Naboo", "root", "");
            st = con.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                c_liked = rs.getInt(1);

                //System.out.println("Pubblicazione: " + pubblicazione);
            }
            con.close();
            st.close();
            return c_liked;
        }catch(Exception ex){
            System.out.println("Error:"+ex);
            //executeSQLQuery(query,"Inserimento non completato");
        }
        return c_liked;
    }

    /*16. Visualizzare numero dislikes in base alla notizia */
    public int selectC_disliked(int id_notizia){
        String query = "SELECT c_disliked FROM Notizia where id_notizia = " +"'"+id_notizia+"';";
        int c_disliked = 0;
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Naboo", "root", "");
            st = con.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                c_disliked = rs.getInt(1);

                //System.out.println("Pubblicazione: " + pubblicazione);
            }
            con.close();
            st.close();
            return c_disliked;
        }catch(Exception ex){
            System.out.println("Error:"+ex);
            //executeSQLQuery(query,"Inserimento non completato");
        }
        return c_disliked;
    }

    /*18.Visualizzaione numero c_shared in base alla notizia */
    public int selectC_shared(int id_notizia){
        String query = "SELECT c_shared FROM Notizia where id_notizia = " +"'"+id_notizia+"';";
        int c_shared = 0;
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Naboo", "root", "");
            st = con.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                c_shared = rs.getInt(1);

                //System.out.println("Pubblicazione: " + pubblicazione);
            }
            con.close();
            st.close();
            return c_shared;
        }catch(Exception ex){
            System.out.println("Error:"+ex);
            //executeSQLQuery(query,"Inserimento non completato");
        }
        return c_shared;
    }

    /*Resistuire l'id dell'ultimo commento inserito*/
    public int lastId_comment(){
        String query = "SELECT id_commento FROM Commenti ORDER BY id_commento DESC LIMIT 1;";
        int id_commento = 0;
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Naboo", "root", "");
            st = con.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                id_commento = rs.getInt(1);
            }
            con.close();
            st.close();
            return id_commento;
        }catch(Exception ex){
            System.out.println("Error:"+ex);
            //executeSQLQuery(query,"Inserimento non completato");
        }
        return id_commento;
    }

    /*20.Restituire un'arraylist di commenti visuallizaizone */

    public ArrayList<Commento> getCommentsList(int id_notizia) {
        ArrayList<Commento> Comments = new ArrayList<Commento>();
        Connection con = getConnection();
        Statement st;
        ResultSet rs;
        /*
        SELECT Possiede.id_notizia, Utenti.username, Commenti.commento
        FROM Possiede, Commenti, Utenti
        WHERE Possiede.id_commento = Commenti.id_commento and id_notizia = 48;
         */
        try {//
            String query = "SELECT Possiede.id_notizia, commenti.id_commento, Utenti.username, Commenti.commento " +
                    " FROM Possiede, Commenti, Utenti WHERE Possiede.id_commento = Commenti.id_commento and Possiede.id_utente = Utenti.id_utente " +
                    "and Possiede.id_notizia = "+ "'"+id_notizia+"';";
            st = con.createStatement();
            rs = st.executeQuery(query);
            Commento comm;
            while (rs.next()) {
                comm = new Commento(rs.getString(1),rs.getString(2),rs.getString(3), rs.getString(4));
                Comments.add(comm);
            }

        } catch (Exception ex) {
            System.out.println("Error:" + ex);
        }
        return Comments;
    }

    /*21. Ricercare - Visuallizare in base alla fonte restituirle poi in un arraylist di notizie */
    public ArrayList<Notizia> ricercaFonte(String fonte) {
        ArrayList<Notizia> NewsFonte = new ArrayList<Notizia>();
        Connection con = getConnection();
        Statement st;
        ResultSet rs;

        try {
            String query = "SELECT * FROM `Notizia` WHERE fonte = "+ "'"+fonte+"';";
            st = con.createStatement();
            rs = st.executeQuery(query);
            Notizia n;
            while (rs.next()) {
                n = new Notizia(rs.getString(1), rs.getString(2), rs.getString(3),rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getString(7),rs.getString(8));
                NewsFonte.add(n);
            }

        } catch (Exception ex) {
            System.out.println("Error:" + ex);
        }
        return NewsFonte;
    }

    /*22. Ricerca - Visuallizare in base alla data  */
    public ArrayList<Notizia> ricercaData(String data) {
        ArrayList<Notizia> NewsData = new ArrayList<Notizia>();
        Connection con = getConnection();
        Statement st;
        ResultSet rs;

        try {
            String query = "SELECT * FROM Notizia WHERE pubblicazione LIKE"+ "'%"+data+"%';";
            st = con.createStatement();
            rs = st.executeQuery(query);
            Notizia n;
            while (rs.next()) {
                n = new Notizia(rs.getString(1),rs.getString(2), rs.getString(3),rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getString(7),rs.getString(8));
                NewsData.add(n);
            }

        } catch (Exception ex) {
            System.out.println("Error:" + ex);
        }
        return NewsData;
    }

    /*23. Ricercare - Visuallizare in base al titolo  */

    public ArrayList<Notizia> ricercaTitolo(String titolo) {
        ArrayList<Notizia> News = new ArrayList<Notizia>();
        Connection con = getConnection();
        Statement st;
        ResultSet rs;

        try {
            String query = "SELECT * FROM Notizia WHERE titolo LIKE"+ "'%"+titolo+"%';";
            st = con.createStatement();
            rs = st.executeQuery(query);
            Notizia n;
            while (rs.next()) {
                n = new Notizia(rs.getString(1),rs.getString(2), rs.getString(3),rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getString(7),rs.getString(8));
                News.add(n);
            }

        } catch (Exception ex) {
            System.out.println("Error:" + ex);
        }
        return News;
    }

    /*Restituisce numero commenti in base alla notizia*/

    public int getNumCommenti(String id_notizia) {
        int numCommentiTot = 0;
        Connection con = getConnection();
        Statement st;
        ResultSet rs;

        try {
            String query = "SELECT COUNT(*) FROM Possiede,Commenti,Utenti " +
                    "WHERE Possiede.id_commento = Commenti.id_commento and Possiede.id_utente = Utenti.id_utente " +
                    "and id_notizia = " + "'" + id_notizia + "';";
            st = con.createStatement();
            rs = st.executeQuery(query);
            rs.next();
            numCommentiTot = Integer.parseInt(rs.getString(1));

        } catch (Exception ex) {
            System.out.println("Error:" + ex);
        }
        return numCommentiTot;
    }

    /* Restituire numero commenti in base all'utente */

    public int getNumCommentiUser(String id_utente) {
        int numCommentiTot = 0;
        Connection con = getConnection();
        Statement st;
        ResultSet rs;

        try {
            String query = "SELECT COUNT(*) FROM Possiede,Commenti,Notizia " +
                    "WHERE Possiede.id_commento = Commenti.id_commento and Possiede.id_notizia = Notizia.id_notizia  " +
                    "and Possiede.id_utente = " + "'" + id_utente + "';";
            st = con.createStatement();
            rs = st.executeQuery(query);
            rs.next();
            numCommentiTot = Integer.parseInt(rs.getString(1));

        } catch (Exception ex) {
            System.out.println("Error:" + ex);
        }
        return numCommentiTot;
    }

    /* Restituisce la Notizia*/
    public Notizia getNotizia(int id_notizia) {
        Notizia news;
        Connection con = getConnection();
        Statement st;
        ResultSet rs;

        try {
            //"SELECT c_shared FROM Notizia where id_notizia = " +"'"+id_notizia+"';"
            String query = "SELECT * FROM Notizia WHERE id_notizia = "+ "'"+id_notizia+"';";
            st = con.createStatement();
            rs = st.executeQuery(query);
            rs.next();
            news = new Notizia(rs.getString(1),rs.getString(2), rs.getString(3),rs.getString(4),
                    rs.getString(5), rs.getString(6), rs.getString(7),rs.getString(8));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return news;
    }

    /*Restituisce l'id della notizia*/

    public String getId_notizia(String id_utente) {
        String id_notizia;
        Connection con = getConnection();
        Statement st;
        ResultSet rs;

        try {
            //"SELECT c_shared FROM Notizia where id_notizia = " +"'"+id_notizia+"';"
            String query = "SELECT * FROM Interagiscono WHERE id_utente = "+ "'"+id_utente+"';";
            st = con.createStatement();
            rs = st.executeQuery(query);
            rs.next();
            id_notizia = rs.getString(2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id_notizia;
    }

    public String getRuolo(String id_utente) {
        String ruolo;
        Connection con = getConnection();
        Statement st;
        ResultSet rs;

        try {
            //"SELECT c_shared FROM Notizia where id_notizia = " +"'"+id_notizia+"';"
            String query = "SELECT * FROM Utenti WHERE id_utente = "+ "'"+id_utente+"';";
            st = con.createStatement();
            rs = st.executeQuery(query);
            rs.next();
            ruolo = rs.getString(3);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ruolo;
    }

    public ArrayList<Notizia> lastNews() {
        ArrayList<Notizia> lastNews = new ArrayList<Notizia>();
        Connection con = getConnection();
        Statement st;
        ResultSet rs;

        try {
            //2)String titolo, 3)String pubblicazione, 4)String descrizione,
            //5)String autore, 6)String fonte, 7)String link, 8)String image
            String query = "SELECT * FROM Notizia ORDER BY pubblicazione DESC LIMIT 10;";
            st = con.createStatement();
            rs = st.executeQuery(query);
            Notizia n;
            while (rs.next()) {
                n = new Notizia(rs.getString(1),rs.getString(2), rs.getString(3),rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getString(7),rs.getString(8));
                lastNews.add(n);
            }

        } catch (Exception ex) {
            System.out.println("Error:" + ex);
        }
        return lastNews;
    }

    /* restituisce l'id utente in base all'username */
    public String getId_user(String username){
        String query = "SELECT id_utente FROM Utenti WHERE username = "+"'"+username+"';";
        String id_utente = "";
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Naboo", "root", "");
            st = con.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                id_utente = "" + rs.getInt(1);
            }
            con.close();
            st.close();
            return id_utente;
        }catch(Exception ex){
            System.out.println("Error:"+ex);
            //executeSQLQuery(query,"Inserimento non completato");
        }
        return id_utente;
    }

    /*Restitusice l'id della notizia in base al commento */

    public int getId_newsComment(int id_commento){
        String query = "SELECT Possiede.id_notizia FROM Possiede, Notizia WHERE Possiede.id_notizia = Notizia.id_notizia and id_commento = "+"'"+id_commento+"';";
        int id_notizia = 0;
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Naboo", "root", "");
            st = con.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                id_notizia =  rs.getInt(1);
            }
            con.close();
            st.close();
            return id_notizia;
        }catch(Exception ex){
            System.out.println("Error:"+ex);
            //executeSQLQuery(query,"Inserimento non completato");
        }
        return id_notizia;
    }

    /* Restituisce le fonti  */
    public ArrayList<String> getFonti() {
        ArrayList<String> Sources = new ArrayList<String>();
        Connection con = getConnection();
        Statement st;
        ResultSet rs;

        try {
            String query = "SELECT * FROM Fonti";
            st = con.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                Sources.add(rs.getString(1));
            }

        } catch (Exception ex) {
            System.out.println("Error:" + ex);
        }
        return Sources;
    }

    public ArrayList<Utente> getUserList() {
        ArrayList<Utente> Users = new ArrayList<>();
        Connection con = getConnection();
        Statement st;
        ResultSet rs;

        try {
            String query = "SELECT * FROM Utenti;";
            st = con.createStatement();
            rs = st.executeQuery(query);
            Utente n;
            while (rs.next()) {
                n = new Utente(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(4));
                Users.add(n);
            }

        } catch (Exception ex) {
            System.out.println("Error:" + ex);
        }
        return Users;
    }




}
