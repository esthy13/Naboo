package com.project.demo.model;

import java.sql.DriverManager;


public class DBdelate extends DBconnect{
    /*5. Rimuove notizie dalla base di dati*/
    public static void deleteNotizia(int id_notizia){
        deleteFormataNotizia(id_notizia); //eseguito
        String query = "DELETE Possiede , Commenti  FROM Commenti  INNER JOIN Possiede " +
                "WHERE Possiede.id_notizia = "+"'"+id_notizia+"';";
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Naboo", "root", "");
            st = con.createStatement();
            //st.executeUpdate(query);
            executeSQLQuery(query,"Cancellazione Possiede e commento eseguito");
            deleteNotiziaRow(id_notizia);
            con.close();
            st.close();
        }catch(Exception ex){
            System.out.println("Error:"+ex);
            //executeSQLQuery(query,"Inserimento non completato");
        }
    }

    /*5. Rimuove notizie dalla base di dati*/
    public static void deleteNotiziaRow(int id_notizia){
        deleteFormataNotizia(id_notizia);

        String query ="DELETE FROM Notizia WHERE id_notizia = "+"'"+id_notizia+"';";

        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Naboo", "root", "");
            st = con.createStatement();
            //st.executeUpdate(query);
            executeSQLQuery(query,"Cancellazione Riga Notizia eseguita");
            con.close();
            st.close();
        }catch(Exception ex){
            System.out.println("Error:"+ex);
            //executeSQLQuery(query,"Inserimento non completato");
        }
    }

    /*5. Rimuove Interagiscono dalla base di dati*/
    public static void deleteInteragiscono(String id_notizia, String id_utente){

        String query = "DELETE FROM Interagiscono WHERE id_notizia = "+"'"+id_notizia+"' and id_utente = " + "'" + id_utente + "';";

        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Naboo", "root", "");
            st = con.createStatement();
            //st.executeUpdate(query);
            executeSQLQuery(query,"Cancellazione interagiscono eseguito");
            con.close();
            st.close();
        }catch(Exception ex){
            System.out.println("Error:"+ex);
            //executeSQLQuery(query,"Inserimento non completato");
        }
    }

    /*7. Rimuovere profili degli utenti */
    //TODO NON FUNZIONA!!!!!
    public static void deleteUser(String username){

        String query = "DELETE FROM Utenti WHERE username = "+" '"+username+"'";

        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Naboo", "root", "");
            st = con.createStatement();
            //st.executeUpdate(query);
            executeSQLQuery(query,"Cancellazione utente eseguito");
            con.close();
            st.close();
        }catch(Exception ex){
            System.out.println("Error:"+ex);
            //executeSQLQuery(query,"Inserimento non completato");
        }
    }

    /*9. Rimuove commenti degli utenti*/
    public static void deleteComment(int id_notizia, int id_utente){

        String query = "DELETE Possiede , Commenti  FROM Possiede  INNER JOIN Commenti " +
                "WHERE Possiede.id_notizia = "+"'"+id_notizia+"'"+" and Possiede.id_utente = "+"'"+id_utente+"';";
        /*
       DELETE Possiede , Commenti  FROM Possiede  INNER JOIN Commenti
WHERE Commenti.id_commento = Possiede.id_commento and Possiede.id_notizia = "757" AND  Possiede.id_utente = "1"
        */


        /*
        String query = "DELETE Possiede , Commenti  FROM Commenti  INNER JOIN Possiede " +
                "WHERE Commenti.id_commento = Possiede.id_commento and Possiede.id_notizia = "+"'"+id_notizia+"'"+" and Possiede.id_utente = "+"'"+id_utente+"';";
        */
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Naboo", "root", "");
            st = con.createStatement();
            //st.executeUpdate(query);
            executeSQLQuery(query,"Cancellazione commento eseguito");
            con.close();
            st.close();
        }catch(Exception ex){
            System.out.println("Error:"+ex);
            //executeSQLQuery(query,"Inserimento non completato");
        }
    }

    /*12. Decrementare numero likes in base alla notizia */
    public void minusLike(int id_notizia){
        /*ricorda che qunado verrà usato il pulsante DISLIKE, bisognerà controllare che non ci sia
          già like del utente, in caso comtrario si richiama il metodo per toglierlo e poi si usa il metodo
          per incrementare il c_dislike*/
        String query = "UPDATE Notizia " + "SET c_liked = c_liked - 1 WHERE id_notizia = " +"'"+id_notizia+"';";
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Naboo", "root", "");
            st = con.createStatement();
            //st.executeUpdate(query);
            executeSQLQuery(query,"Like diminuito");
            con.close();
            st.close();
        }catch(Exception ex){
            System.out.println("Error:"+ex);
            //executeSQLQuery(query,"Inserimento non completato");
        }
    }

    /*14. Decrementare numero dislike in base alla notizia */
    public void minusDislike(int id_notizia){
        String query = "UPDATE Notizia " + "SET c_disliked = c_disliked - 1 WHERE id_notizia = " +"'"+id_notizia+"';";
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Naboo", "root", "");
            st = con.createStatement();
            //st.executeUpdate(query);
            executeSQLQuery(query,"Dislike diminuito");
            con.close();
            st.close();
        }catch(Exception ex){
            System.out.println("Error:"+ex);
            //executeSQLQuery(query,"Inserimento non completato");
        }
    }

    /* Rimuovere fonte */
    public void deleteFonte(String rss){

        String query = "DELETE FROM Fonti WHERE rss = "+" '"+rss+"'";

        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Naboo", "root", "");
            st = con.createStatement();
            //st.executeUpdate(query);
            deleteFormata(rss);
            executeSQLQuery(query,"Cancellazione fonte eseguito");
            con.close();
            st.close();
        }catch(Exception ex){
            System.out.println("Error:"+ex);
            //executeSQLQuery(query,"Inserimento non completato");
        }
    }

    /* Rimuovere fonte */
    public static void deleteFormataNotizia(int id_notizia){

        String query = "DELETE FROM Formata WHERE id_notizia = "+" '"+id_notizia+"'";

        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Naboo", "root", "");
            st = con.createStatement();
            st.executeUpdate(query);
            //executeSQLQuery(query,"Cancellazione fonte eseguito"); toomanyc
            con.close();
            st.close();
        }catch(Exception ex){
            System.out.println("Error:"+ex);
            //executeSQLQuery(query,"Inserimento non completato");
        }
    }

    public void deleteFormata(String rss){

        String query = "DELETE FROM Formata WHERE rss = "+" '"+rss+"'";

        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Naboo", "root", "");
            st = con.createStatement();
            //st.executeUpdate(query);
            executeSQLQuery(query,"Cancellazione fonte eseguito");
            con.close();
            st.close();
        }catch(Exception ex){
            System.out.println("Error:"+ex);
            //executeSQLQuery(query,"Inserimento non completato");
        }
    }

}
