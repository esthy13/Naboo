package com.project.demo.controller;

import com.project.demo.Scene.Encryptor;
import com.project.demo.model.Commento;
import com.project.demo.model.Notizia;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;

public abstract class Buttons extends KeyboardsAndEmoji{
    public void close(Update update){
        if(update.hasMessage() && update.getMessage().isReply()) {
                deleteMsg(update.getMessage().getReplyToMessage());
                deleteMsg(update.getMessage());
        }
        else if(update.hasCallbackQuery()) {
            DeleteMessage deleteMessage = new DeleteMessage();
            deleteMessage.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
            deleteMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
            try {
                execute(deleteMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void profilo(CallbackQuery callbackQuery, int ID){
        SendMessage user = new SendMessage();
        user.setChatId(callbackQuery.getMessage().getChatId());
        user.setParseMode("MarkdownV2");
        int n_commenti = dBget.getNumCommentiUser("" +ID);
        String commenti = "";
        if(n_commenti == 1)
            commenti = " commento ";
        else
            commenti = " commenti ";
        String ruolo = "" + dBget.getRuolo("" + ID);
        if(ruolo.equals("User")) {
            user.setText("Il tuo profilo: " + profile_emoji
                    + "\n*Username*: " + correctUsername(callbackQuery.getFrom().getUserName())
                    + "\n*Ruolo*: " + ruolo
                    + "\nHai scritto " + n_commenti + commenti + comment_emoji);
            keyboardProfilo(user, "" + ID);
            sendMsg(user);
        }
        else{
            String key = "Bar12345Bar12345";
            String initVector = "RandomInitVector";
            System.out.println(callbackQuery.getFrom().getUserName());
            String encrypted = dBget.getEncryptedPass(callbackQuery.getFrom().getUserName());
            System.out.println(encrypted);
            System.out.println("encrypted");
            String password = Encryptor.decrypt(key, initVector, encrypted);
            user.setText("Il tuo profilo: " + profile_emoji
                + "\n*Username*: " + correctUsername(callbackQuery.getFrom().getUserName())
                + "\n*Ruolo*: " + ruolo
                + "\n*Password*: ||" + password + "||"
                + "\nHai scritto " + n_commenti + commenti + comment_emoji);
            keyboardAdmin(user, "" + ID);
            sendMsg(user);
        }
    }

    public void menu(Update update){
        String chat_id = "";
        String id_utente = "";
        if(update.hasCallbackQuery()){
           chat_id = "" + update.getCallbackQuery().getMessage().getChatId();
           id_utente = "" + dBget.getId_user(update.getCallbackQuery().getFrom().getUserName());
        }
        else if(update.hasMessage()) {
            chat_id = "" + update.getMessage().getChatId();
            id_utente = "" + dBget.getId_user(update.getMessage().getFrom().getUserName());
        }
        sendMsg(chat_id, "MENÙ", menuKeyboard(id_utente));

    }
    public void scorriNotizie(CallbackQuery callbackQuery, String tipoRicerca){
        ArrayList<Notizia> notizie = new ArrayList<>();
        int first = 0;
        switch (tipoRicerca){
            case "titolo":
                notizie = dBget.ricercaTitolo(callbackQuery.getMessage().getReplyToMessage().getText());
                break;
            case "data" :
                notizie = dBget.ricercaData(callbackQuery.getMessage().getReplyToMessage().getText());
                break;
            case "fonte" :
                notizie = dBget.ricercaFonte(callbackQuery.getMessage().getReplyToMessage().getText());
                break;
        }
        first = getNextFirstIndex(callbackQuery, notizie);
        editNotizie(notizie, callbackQuery, first,tipoRicerca);
    }
    public void scorriCommenti(CallbackQuery callbackQuery, int id_commento){
        int id_notizia = dBget.getId_newsComment(id_commento);
        System.out.println(id_notizia);
        ArrayList<Commento> commenti = dBget.getCommentsList(id_notizia);
        System.out.println(commenti);
        int first = getNextFirst(callbackQuery, commenti);
        EditMessageText sendMessage = new EditMessageText();
        String text = commentiToString(commenti, first);
        sendMessage.setText(text);
        sendMessage.setChatId(callbackQuery.getMessage().getChatId());
        sendMessage.setMessageId(callbackQuery.getMessage().getMessageId());
        EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();
        editMessageReplyMarkup.setChatId(callbackQuery.getMessage().getChatId());
        editMessageReplyMarkup.setMessageId(callbackQuery.getMessage().getMessageId());
        editMessageReplyMarkup.setReplyMarkup((InlineKeyboardMarkup) setTastieraCommento(commenti, "commenti",first));
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        try {
            execute(editMessageReplyMarkup);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    public void editNotizie(ArrayList<Notizia> notizie, CallbackQuery callbackQuery, int first, String tipo){
        EditMessageText sendMessage = new EditMessageText();
        String text = notizieToString(notizie, first);
        sendMessage.setText(text);
        sendMessage.setChatId(callbackQuery.getMessage().getChatId());
        sendMessage.setMessageId(callbackQuery.getMessage().getMessageId());
        EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();
        editMessageReplyMarkup.setChatId(callbackQuery.getMessage().getChatId());
        editMessageReplyMarkup.setMessageId(callbackQuery.getMessage().getMessageId());
        editMessageReplyMarkup.setReplyMarkup((InlineKeyboardMarkup) setTastiera(notizie, tipo, first));
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        try {
            execute(editMessageReplyMarkup);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    public int getNextFirstIndex(CallbackQuery callbackQuery,
                             ArrayList<Notizia> notizie) {
        String[] tokens = callbackQuery.getData().split("-");
        int id_notizia = Integer.parseInt(tokens[1]);
        for (int i = 0; i < notizie.size(); i++) {
            if (notizie.get(i).getId_notizia()==id_notizia) {
                return i;
            }
        }
        return -1;
    }
    public int getNextFirst(CallbackQuery callbackQuery, ArrayList<Commento> commenti) {
        String[] tokens = callbackQuery.getData().split("-");
        int id_commento = Integer.parseInt(tokens[1]);
        System.out.println(id_commento);
        for (int i = 0; i < commenti.size(); i++) {
            System.out.println(commenti.get(i).getId_commento());
            if (commenti.get(i).getId_commento() == id_commento ) {
                return i;
            }
        }
        return -1;
    }

    public void visualizzaCommenti(ArrayList<Commento> commenti, CallbackQuery callbackQuery, int first){
        sendCommenti(commenti, callbackQuery, first);
    }
    public void ricerca(CallbackQuery callbackQuery, String sms, String IFPh) {
        SendMessage notizia = new SendMessage();
        notizia.setChatId(callbackQuery.getMessage().getChatId());
        notizia.setParseMode("MarkdownV2");
        notizia.setText(sms);
        notizia.setReplyToMessageId(callbackQuery.getMessage().getMessageId());
        forceReplyKeyboard.setInputFieldPlaceholder(IFPh);
        notizia.setReplyMarkup(forceReplyKeyboard);
        try {
            execute(notizia);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void like(Update update, int id_notizia){
        String dislikeButton = update.getCallbackQuery().getMessage().getReplyMarkup().getKeyboard().get(0).get(1).getCallbackData();
        dislikeButton = dislikeButton.substring(0, dislikeButton.indexOf('-'));
        if (dislikeButton.equals("togliDislike")) {
            //verifico se tasto dislike e' gia' stato schiacciato (nel caso callbackquery del tasto dislike = like)
            //tolgo dislike
            dBdelete.minusDislike(id_notizia);
            dBinsert.addLike(id_notizia);
            int dislikeTot = dBget.selectC_disliked(id_notizia);
            int likeTot = dBget.selectC_liked(id_notizia);
            //modifica callbackdata del InlineKeyboardButton dislike from like into dislike
            EditMessageReplyMarkup deleteDislike = twoButtons(update, 1, dislike_emoji, "dislike", dislikeTot, 0, like_emoji, "togliLike", likeTot);
            try {
                execute(deleteDislike);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        } else {
            //schiaccio InlineKeyboardButton like
            dBinsert.addLike(id_notizia);
            String callback2 = update.getCallbackQuery().getMessage().getReplyMarkup().getKeyboard().get(0).get(1).getCallbackData();
            callback2 = callback2.substring(0, callback2.indexOf('-'));
            EditMessageReplyMarkup addLike = twoButtons(update, 0, like_emoji, "togliLike",
                    dBget.selectC_liked(id_notizia), 1, dislike_emoji, callback2, dBget.selectC_disliked(id_notizia));
            try {
                execute(addLike);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void dislike(Update update, int id_notizia){
        String likeButton = update.getCallbackQuery().getMessage().getReplyMarkup().getKeyboard().get(0).get(0).getCallbackData();
        likeButton = likeButton.substring(0, likeButton.indexOf('-'));
        if (likeButton.equals("togliLike")) {
            //verifico se tasto like e' gia' stato schiacciato (nel caso callbackquery del tasto like = dislike)
            //tolgo like
            dBdelete.minusLike(id_notizia);
            dBinsert.addDislike(id_notizia);
            //modifica callbackdata del InlineKeyboardButton dislike from like into dislike
            EditMessageReplyMarkup deleteDislike = twoButtons(update, 0, like_emoji, "like", dBget.selectC_liked(id_notizia),
                    1, dislike_emoji, "togliDislike", dBget.selectC_disliked(id_notizia));
            try {
                execute(deleteDislike);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        } else {
            dBinsert.addDislike(id_notizia);
            String callback3 = update.getCallbackQuery().getMessage().getReplyMarkup().getKeyboard().get(0).get(0).getCallbackData();
            callback3 = callback3.substring(0, callback3.indexOf('-'));
            EditMessageReplyMarkup addDislike = twoButtons(update, 0, like_emoji, callback3, dBget.selectC_liked(id_notizia),
                    1, dislike_emoji, "togliDislike", dBget.selectC_disliked(id_notizia));
            try {
                execute(addDislike);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void report(Update update, int id_notizia){
        dBinsert.addC_reported(id_notizia);
        EditMessageReplyMarkup addReport = oneButton(update, 5, report_emoji, "disreport", dBget.selectC_reported(id_notizia));
        try {
            execute(addReport);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    public void disreport(Update update, int id_notizia){
        //System.out.println("disreport");
        dBdelete.minusReported(id_notizia);
        EditMessageReplyMarkup addReport = oneButton(update, 5, report_emoji, "report", dBget.selectC_reported(id_notizia));
        try {
            execute(addReport);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
