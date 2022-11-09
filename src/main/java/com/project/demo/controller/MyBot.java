package com.project.demo.controller;

import com.project.demo.model.Notizia;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;

import static java.util.Objects.isNull;

public abstract class MyBot extends Buttons{
    public void help(String chatId){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setParseMode("MarkdownV2");
        sendMessage.setText("*Funzionamento dei pulsanti del bot:*" +
                            "\n" + like_emoji + " per dare un voto positivo alla notizia" +
                            "\n" + dislike_emoji + " per dare un voto negativo alla notizia" +
                            "\n_se cambi idea, schiaccia nuovamente su_ " + like_emoji+ "/" +dislike_emoji +
                            " _per annullare il voto_" +
                            "\n" + save_emoji + " per salvarti le notizie tra i messaggi fissati" +
                            "\n" + comment_emoji + " per scrivere un commento alla notizia" +
                            "\n" + occhio_emoji + comment_emoji + " per visualizzare i commenti di una notizia" +

                            "\n" + "*share* per inviare la notizia ad altri tuoi contatti telegram " +
                            "\n" + "*whatsapp facebook* e *twitter* per condividere le notizie sui tuoi social preferiti"+
                            "\n" + search_emoji +"*notizia* per cercare notizie in base a parole chiave del titolo" +
                            "\n" + search_emoji +"*data* per cercare notizie in base alla data di pubblicazione" +
                            "\n" + search_emoji +"*fonte* per cercare notizie in base al giornale online di provenienza" +
                            "\n" + close_emoji +" per chiudere e cancellare un messaggio" +
                            "\n" + back_emoji + next_emoji + " per scorrere avanti e indietro notizie e commenti"
                            );
        sendMsg(sendMessage);
    }
    public void searchNews(Update update, ArrayList<Notizia> notizie, int first, String tipo){
        if(notizie.isEmpty()){
            sendMsg(update.getMessage().getChatId().toString(), "Non ci sono notizie inerenti alla tua ricerca " + sad_emoji);
        }
        else{
            sendNotizie(notizie, update, first, tipo);
        }
    }
    public void addComment(Update update){
        String id_utente = dBget.getId_user(update.getMessage().getFrom().getUserName());
        String id_notizia = dBget.getId_notizia(id_utente);
        System.out.println(id_notizia);
        dBinsert.insertComment(update.getMessage().getText(), id_notizia, id_utente);
        sendMsg(update.getMessage().getChatId().toString(),"Il tuo commento è stato aggiunto correttamente "
                + festa_emoji, keyboardVizualizza(update,id_notizia,dBget.getNumCommenti(id_notizia)));
        System.out.println(dBget.getNumCommenti(id_notizia));
        dBdelete.deleteInteragiscono(id_notizia, id_utente);

    }
    public void start(Update update){
        if(isNull(update.getMessage().getFrom().getUserName())){
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(update.getMessage().getChatId());
            sendMessage.setParseMode("MarkdownV2");
            sendMessage.setText("Per poter usufruire dei servizi offerti dal bot devi aggiungere uno username al" +
                    " tuo profilo\\. Semplicemente vai nelle impostazioni di telegram per aggiungere uno" +
                    " username al tuo account\\. Una volta aggiunto lo username fai /start\\.");
            try{
                execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            sendMsg(update.getMessage().getChatId().toString(), "Benvenuto! " + wave_emoji +
                    "\nOra puoi cercare, leggere, commentare e condividere con i tuoi amici le notizie" +
                    "di attualità prese da fonti controllate dagli amministratori del bot!" +
                    "\nSe hai bisogno di aiuto usa /help " + help_emoji +
                    "\nPer accedere al menù del bot usa /menu " + menu_emoji +
                    "\n" + robot_emoji +" @GediNabooBot");
            dBinsert.insertUser(update.getMessage().getFrom().getUserName(), "User");
        }
    }

}
