package com.project.demo.controller;

import com.project.demo.model.*;
import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ForceReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public abstract class KeyboardsAndEmoji extends TelegramLongPollingBot {

    //TODO sposta in cartella controller
    String like_emoji = EmojiParser.parseToUnicode(":thumbsup:");
    String dislike_emoji = EmojiParser.parseToUnicode(":thumbsdown:");
    String save_emoji = EmojiParser.parseToUnicode(":pushpin:");
    String comment_emoji = EmojiParser.parseToUnicode(":memo:");
    String search_emoji = EmojiParser.parseToUnicode(":mag:");
    String profile_emoji = EmojiParser.parseToUnicode(":bust_in_silhouette:");
    String help_emoji = EmojiParser.parseToUnicode(":woman_technologist:");
    String sad_emoji = EmojiParser.parseToUnicode(":pensive:");
    String occhio_emoji = EmojiParser.parseToUnicode(":eyes:");
    String festa_emoji =  EmojiParser.parseToUnicode(":heavy_check_mark:");
    String close_emoji = EmojiParser.parseToUnicode(":x:");
    String back_emoji = EmojiParser.parseToUnicode(":arrow_left:");
    String next_emoji = EmojiParser.parseToUnicode(":arrow_right:");
    String wave_emoji = EmojiParser.parseToUnicode(":wave:");
    String robot_emoji = EmojiParser.parseToUnicode(":robot_face:");
    String menu_emoji = EmojiParser.parseToUnicode(":scroll:");

    //DBconnect dBconnect = new DBconnect();
    DBdelate dBdelate = new DBdelate();
    DBget dBget = new DBget();
    DBinsert dBinsert = new DBinsert();

    ForceReplyKeyboard forceReplyKeyboard = new ForceReplyKeyboard();

    @Override
    public String getBotUsername() {
        return "il Mio bot";
    }

    @Override
    public String getBotToken() {
        return "5782337102:AAEMwjHCFgJY8mod-K-spPjEkPEOBrILjnQ";
    }

    public void sendNotizie(ArrayList<Notizia> notizie, Update update, int first, String tipo){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());
        String text = notizieToString(notizie, first);
        sendMessage.setText(text);
        sendMessage.setReplyToMessageId(update.getMessage().getMessageId());
        sendMessage.setReplyMarkup(setTastiera(notizie, tipo, first));
        sendMsg(sendMessage);
    }
    public void sendCommenti(ArrayList<Commento> commenti, CallbackQuery callbackQuery, int first){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(callbackQuery.getMessage().getChatId());
        String text = commentiToString(commenti, first);
        sendMessage.setText(text);
        sendMessage.setReplyToMessageId(callbackQuery.getMessage().getMessageId());
        sendMessage.setReplyMarkup(setTastieraCommento(commenti, "commenti", first));
        sendMsg(sendMessage);
    }

    public void sendMsg(SendMessage sendMessage){
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public String notizieToString(ArrayList<Notizia> notizie, int first){
        String text = "";
        int y = 1;
        if(notizie.size()<=10){
            for(int i = first; i< notizie.size(); i++){
                text = text + y + ". " + notizie.get(i).getPubblicazione() +
                        "- " + notizie.get(i).getTitolo() + "\n";
                y++;
            }
        }
        else if((notizie.size()-first)<=10){
            for(int i = first; i< notizie.size(); i++){
                text = text + y + ". " + notizie.get(i).getPubblicazione() +
                        "- " + notizie.get(i).getTitolo() + "\n";
                y++;
            }
        }
        else {
            for (int i = first; i < (first + 10); i++) {
                text = text + y + ". " + notizie.get(i).getPubblicazione() +
                        "- " + notizie.get(i).getTitolo() + "\n";
                y++;
            }
        }
        return text;
    }
    //TODO
    public String commentiToString(ArrayList<Commento> commenti, int first){
        String text = "";
        int y = 1;
        if(commenti.size()<=10){
            for(int i = first; i< commenti.size(); i++){
                text = text + y + ". " + commenti.get(i).toString();
                y++;
            }
        }
        else if((commenti.size()-first)<=10){
            for(int i = first; i< commenti.size(); i++){
                text = text + y + ". " + commenti.get(i).toString();
                y++;
            }
        }
        else {
            for (int i = first; i < (first + 10); i++) {
                text = text + y + ". " + commenti.get(i).toString();
                y++;
            }
        }
        return text;
    }
    public ReplyKeyboard setTastieraCommento(ArrayList<Commento> commenti, String tipo, int first){
        List<List<InlineKeyboardButton>> inline = new ArrayList();
        List<InlineKeyboardButton> inlineKeyboardButtonList = new ArrayList();
        int y = 1;
        if(commenti.size() <= 10){
            InlineKeyboardButton close = new InlineKeyboardButton();
            close.setCallbackData("close-" + 0);
            close.setText(close_emoji);
            inlineKeyboardButtonList.add(close);
            inline.add(inlineKeyboardButtonList);
        }
        else if((commenti.size()-first) <= 10){
            String id_prevCommento = commenti.get(first-10).getId_commento();
            InlineKeyboardButton indietro = new InlineKeyboardButton();
            indietro.setCallbackData(tipo + "Scorri-" + id_prevCommento);
            indietro.setText(back_emoji);
            inlineKeyboardButtonList.add(indietro);
            InlineKeyboardButton close = new InlineKeyboardButton();
            close.setCallbackData("close-" + 0);
            close.setText(close_emoji);
            inlineKeyboardButtonList.add(close);
            inline.add(inlineKeyboardButtonList);
        }
        else{
            if((first-10)>= 10){
                String id_prevCommento = commenti.get(first-10).getId_commento();
                InlineKeyboardButton indietro = new InlineKeyboardButton();
                indietro.setCallbackData(tipo + "Scorri-" + id_prevCommento);
                indietro.setText(back_emoji);
                inlineKeyboardButtonList.add(indietro);
            }
            String id_nextCommento = commenti.get(first+10).getId_commento();
            InlineKeyboardButton close = new InlineKeyboardButton();
            close.setCallbackData("close-" + id_nextCommento);
            close.setText(close_emoji);
            InlineKeyboardButton avanti = new InlineKeyboardButton();
            avanti.setCallbackData(tipo + "Scorri-" + id_nextCommento);
            avanti.setText(next_emoji);
            inlineKeyboardButtonList.add(close);
            inlineKeyboardButtonList.add(avanti);
            inline.add(inlineKeyboardButtonList);
        }
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup(inline);
        return inlineKeyboardMarkup;
    }
    public ReplyKeyboard setTastiera(ArrayList<Notizia> notizia, String tipo, int first){
        List<List<InlineKeyboardButton>> inline = new ArrayList();
        List<InlineKeyboardButton> inlineKeyboardButtonList = new ArrayList();
        List<InlineKeyboardButton> inlineKeyboardButtonList2 = new ArrayList();
        List<InlineKeyboardButton> inlineKeyboardButtonList3 = new ArrayList();
        int y = 1;
        if(notizia.size() <= 10){
            for(int i = first; i < notizia.size(); i++){
                y = addButtonToRow(notizia, inlineKeyboardButtonList, inlineKeyboardButtonList2, y, i);
                y++;
            }
            InlineKeyboardButton close = new InlineKeyboardButton();
            close.setCallbackData("close-" + 0);
            close.setText(close_emoji);
            inlineKeyboardButtonList3.add(close);
            inline.add(inlineKeyboardButtonList);
            inline.add(inlineKeyboardButtonList2);
            inline.add(inlineKeyboardButtonList3);
        }
        else if((notizia.size()-first) <= 10){
            for(int i = first; i < notizia.size(); i++){
                y = addButtonToRow(notizia, inlineKeyboardButtonList, inlineKeyboardButtonList2, y, i);
                y++;
            }
            String id_previousNotizia = notizia.get(first-10).getId_notizia();
            InlineKeyboardButton indietro = new InlineKeyboardButton();
            indietro.setCallbackData(tipo + "Scorri-" + id_previousNotizia);
            indietro.setText(back_emoji);
            inlineKeyboardButtonList3.add(indietro);
            InlineKeyboardButton close = new InlineKeyboardButton();
            close.setCallbackData("close-" + 0);
            close.setText(close_emoji);
            inlineKeyboardButtonList3.add(close);
            inline.add(inlineKeyboardButtonList);
            inline.add(inlineKeyboardButtonList2);
            inline.add(inlineKeyboardButtonList3);
        }
        else{
            for(int i = first; i < first+10; i++) {
                y = addButtonToRow(notizia, inlineKeyboardButtonList, inlineKeyboardButtonList2, y, i);
                y++;
            }
            if((first-10)>= 10){
                String id_previousNotizia = notizia.get(first-10).getId_notizia();
                InlineKeyboardButton indietro = new InlineKeyboardButton();
                indietro.setCallbackData(tipo + "Scorri-" + id_previousNotizia);
                indietro.setText(back_emoji);
                inlineKeyboardButtonList3.add(indietro);
            }
            String id_nextNotizia = notizia.get(first+10).getId_notizia();
            InlineKeyboardButton close = new InlineKeyboardButton();
            close.setCallbackData("close-" + id_nextNotizia);
            close.setText(close_emoji);
            InlineKeyboardButton avanti = new InlineKeyboardButton();
            avanti.setCallbackData(tipo + "Scorri-" + id_nextNotizia);
            avanti.setText(next_emoji);
            inlineKeyboardButtonList3.add(close);
            inlineKeyboardButtonList3.add(avanti);
            inline.add(inlineKeyboardButtonList);
            inline.add(inlineKeyboardButtonList2);
            inline.add(inlineKeyboardButtonList3);

        }
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup(inline);
        return inlineKeyboardMarkup;
    }

    private int addButtonToRow(ArrayList<Notizia> notizia,
                               List<InlineKeyboardButton> inlineKeyboardButtonList,
                               List<InlineKeyboardButton> inlineKeyboardButtonList2,
                               int y, int i) {
        if(y<=5) {
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText("" + (y));
            System.out.println(notizia.get(i).getId_notizia());
            inlineKeyboardButton.setCallbackData("news-" + notizia.get(i).getId_notizia());
            inlineKeyboardButtonList.add(inlineKeyboardButton);
        }
        else{
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText("" + (y));
            inlineKeyboardButton.setCallbackData("news-" + notizia.get(i).getId_notizia());
            inlineKeyboardButtonList2.add(inlineKeyboardButton);
        }
        return y;
    }

    /**public boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;

        } catch (NumberFormatException e) {
            return false;
        }
    }**/

    public void sendMsg(String chatId, String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(chatId);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendForceReplyMsg(String chatId, String text){
        SendMessage sendMessage = new SendMessage();
        ForceReplyKeyboard forceReplyKeyboard = new ForceReplyKeyboard();
        sendMessage.setText(text);
        sendMessage.setChatId(chatId);
        sendMessage.setReplyMarkup(forceReplyKeyboard);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    public String correctUsername(String username){
        if(username.contains("_")){
            return username.replaceAll("_", "\\\\_");
        }
        else
            return username;
    }
    public InlineKeyboardMarkup keyboardVizualizza(Update update, String id_notizia, int numTot) {
        //for editing a button in the two row button menu
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineButtons = new ArrayList();
        List<InlineKeyboardButton> inlineKeyboardButtonList = new ArrayList();
        InlineKeyboardButton visualizza = new InlineKeyboardButton();
        visualizza.setCallbackData("visualizza-" + id_notizia);
        visualizza.setText( occhio_emoji+ " " + numTot + " commenti " + comment_emoji);
        InlineKeyboardButton close = new InlineKeyboardButton();
        close.setCallbackData("close-" + id_notizia);
        close.setText(close_emoji);
        inlineKeyboardButtonList.add(visualizza);
        inlineKeyboardButtonList.add(close);
        inlineButtons.add(inlineKeyboardButtonList);
        inlineKeyboardMarkup.setKeyboard(inlineButtons);
        return inlineKeyboardMarkup;
    }

    public void sendMsg(String chatId, String text, ReplyKeyboard replyKeyboard){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(chatId);
        sendMessage.setReplyMarkup(replyKeyboard);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteMsg(Message message) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setMessageId(message.getMessageId());
        deleteMessage.setChatId(message.getChatId());
        try {
            execute(deleteMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    public InlineKeyboardMarkup menuKeyboard(String id_utente){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineButtons = new ArrayList();
        List<InlineKeyboardButton> inlineKeyboardButtonList1 = new ArrayList();
        List<InlineKeyboardButton> inlineKeyboardButtonList2 = new ArrayList();
        InlineKeyboardButton titolo = new InlineKeyboardButton();
        InlineKeyboardButton data = new InlineKeyboardButton();
        InlineKeyboardButton fonte = new InlineKeyboardButton();
        InlineKeyboardButton profilo = new InlineKeyboardButton();
        InlineKeyboardButton help = new InlineKeyboardButton();
        InlineKeyboardButton close = new InlineKeyboardButton();
        titolo.setText(search_emoji + " notizia");
        data.setText(search_emoji + " data");
        fonte.setText(search_emoji + " fonte");
        profilo.setText(profile_emoji + " profilo");
        close.setText(close_emoji);
        help.setText(help_emoji + " help");
        titolo.setCallbackData("notizia-" + id_utente);
        data.setCallbackData("data-"+ id_utente);
        fonte.setCallbackData("fonte-"+ id_utente);
        profilo.setCallbackData("profilo-"+ id_utente);
        close.setCallbackData("close-" + id_utente);
        help.setCallbackData("help-" + id_utente);
        inlineKeyboardButtonList1.add(titolo);
        inlineKeyboardButtonList1.add(data);
        inlineKeyboardButtonList1.add(fonte);
        inlineKeyboardButtonList2.add(profilo);
        inlineKeyboardButtonList2.add(close);
        inlineKeyboardButtonList2.add(help);
        inlineButtons.add(inlineKeyboardButtonList1);//prima riga
        inlineButtons.add(inlineKeyboardButtonList2);//seconda riga
        inlineKeyboardMarkup.setKeyboard(inlineButtons);
        return inlineKeyboardMarkup;
    }
    public void keyboardProfilo(SendMessage sendMessage, String id_utente){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineButtons = new ArrayList();
        List<InlineKeyboardButton> inlineKeyboardButtonList = new ArrayList();
        InlineKeyboardButton close = new InlineKeyboardButton();
        close.setText(close_emoji);
        close.setCallbackData("close-" + id_utente);
        inlineKeyboardButtonList.add(close);
        inlineButtons.add(inlineKeyboardButtonList);
        inlineKeyboardMarkup.setKeyboard(inlineButtons);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
    }
    public EditMessageReplyMarkup twoButtons(Update update, int buttonIndex, String emoji, String callbackData, int numTot, int button2, String emoji2, String callback, int num) {
        //for editing a button in the two row button menu
        EditMessageReplyMarkup addViewDelete = new EditMessageReplyMarkup();
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineButtons = new ArrayList();
        List<InlineKeyboardButton> inlineKeyboardButtonList1 = new ArrayList();
        List<InlineKeyboardButton> inlineKeyboardButtonList2 = new ArrayList();
        String id_notizia = update.getCallbackQuery().getMessage().getReplyMarkup().getKeyboard().get(0).get(buttonIndex).getCallbackData();
        id_notizia = id_notizia.substring(id_notizia.indexOf('-')+1);
        InlineKeyboardButton modificato = new InlineKeyboardButton();
        modificato.setCallbackData(callbackData + "-" + id_notizia);
        modificato.setText(EmojiParser.parseToUnicode(emoji) + numTot);
        InlineKeyboardButton modificato2 = new InlineKeyboardButton();
        modificato2.setCallbackData(callback+ "-" + id_notizia);
        modificato2.setText(EmojiParser.parseToUnicode(emoji2) + num);
        inlineKeyboardButtonList1.addAll(update.getCallbackQuery().getMessage().getReplyMarkup().getKeyboard().get(0));
        inlineKeyboardButtonList1.set(buttonIndex, modificato);
        inlineKeyboardButtonList1.set(button2, modificato2);
        inlineKeyboardButtonList2.addAll(update.getCallbackQuery().getMessage().getReplyMarkup().getKeyboard().get(1));
        inlineButtons.add(inlineKeyboardButtonList1);
        inlineButtons.add(inlineKeyboardButtonList2);
        inlineKeyboardMarkup.setKeyboard(inlineButtons);
        addViewDelete.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        addViewDelete.setChatId(update.getCallbackQuery().getMessage().getChatId());
        addViewDelete.setReplyMarkup(inlineKeyboardMarkup);
        return addViewDelete;
    }
    public EditMessageReplyMarkup modify(Update update, int buttonIndex, String emoji, String callbackData) {
        //for editing a button in the two row button menu
        EditMessageReplyMarkup modify = new EditMessageReplyMarkup();
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineButtons = new ArrayList();
        List<InlineKeyboardButton> inlineKeyboardButtonList1 = new ArrayList();
        List<InlineKeyboardButton> inlineKeyboardButtonList2 = new ArrayList();
        String id_notizia = update.getCallbackQuery().getMessage().getReplyMarkup().getKeyboard().get(0).get(buttonIndex).getCallbackData();
        id_notizia = id_notizia.substring(id_notizia.indexOf('-')+1);
        InlineKeyboardButton modificato = new InlineKeyboardButton();
        modificato.setCallbackData(callbackData+ "-" + id_notizia);
        modificato.setText(EmojiParser.parseToUnicode(emoji));
        inlineKeyboardButtonList1.addAll(update.getCallbackQuery().getMessage().getReplyMarkup().getKeyboard().get(0));
        inlineKeyboardButtonList1.set(buttonIndex,modificato);
        inlineKeyboardButtonList2.addAll(update.getCallbackQuery().getMessage().getReplyMarkup().getKeyboard().get(1));
        inlineButtons.add(inlineKeyboardButtonList1);
        inlineButtons.add(inlineKeyboardButtonList2);
        inlineKeyboardMarkup.setKeyboard(inlineButtons);
        modify.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        modify.setChatId(update.getCallbackQuery().getMessage().getChatId());
        modify.setReplyMarkup(inlineKeyboardMarkup);
        return modify;
    }
    public void keyboardNotizia(String id_notizia, String link, SendMessage sendMessage){
        String like_emoji = EmojiParser.parseToUnicode(":thumbsup:");
        String dislike_emoji = EmojiParser.parseToUnicode(":thumbsdown:");
        String save_emoji = EmojiParser.parseToUnicode(":pushpin:");
        String comment_emoji = EmojiParser.parseToUnicode(":memo:");
        String occhio_emoji = EmojiParser.parseToUnicode(":eyes:");
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineButtons = new ArrayList();
        List<InlineKeyboardButton> inlineKeyboardButtonList1 = new ArrayList();
        List<InlineKeyboardButton> inlineKeyboardButtonList2 = new ArrayList();
        InlineKeyboardButton like = new InlineKeyboardButton();
        InlineKeyboardButton dislike = new InlineKeyboardButton();
        InlineKeyboardButton save = new InlineKeyboardButton();
        InlineKeyboardButton share = new InlineKeyboardButton();
        InlineKeyboardButton comment = new InlineKeyboardButton();
        InlineKeyboardButton visualizza = new InlineKeyboardButton();
        InlineKeyboardButton whatsapp = new InlineKeyboardButton();
        InlineKeyboardButton twitter = new InlineKeyboardButton();
        InlineKeyboardButton facebook = new InlineKeyboardButton();
        InlineKeyboardButton menu = new InlineKeyboardButton();
        ForceReplyKeyboard nuovo = new ForceReplyKeyboard();
        like.setText(like_emoji);
        dislike.setText(dislike_emoji);
        save.setText(save_emoji);
        comment.setText(comment_emoji);
        visualizza.setText(occhio_emoji + comment_emoji);
        share.setText("share");
        menu.setText("MENÙ");
        whatsapp.setText("whatsapp");
        facebook.setText("facebook");
        twitter.setText("tweet");
        like.setCallbackData("like-"+id_notizia);
        dislike.setCallbackData("dislike-"+id_notizia);
        save.setCallbackData("save-"+id_notizia);
        comment.setCallbackData("comment-" + id_notizia);
        visualizza.setCallbackData("visualizza-" + id_notizia);
        share.setSwitchInlineQuery(sendMessage.getText());
        whatsapp.setUrl("https://api.whatsapp.com/send/?text=@GediNabooBot%20" + link.trim());
        facebook.setUrl("https://www.facebook.com/sharer/sharer.php?u=" + link);
        twitter.setUrl("https://twitter.com/intent/tweet?text=@GediNabooBot%0" + link.trim());
        menu.setCallbackData("menu-"+id_notizia);
        inlineKeyboardButtonList1.add(like);
        inlineKeyboardButtonList1.add(dislike);
        inlineKeyboardButtonList1.add(save);
        inlineKeyboardButtonList1.add(comment);
        inlineKeyboardButtonList1.add(visualizza);
        inlineKeyboardButtonList2.add(share);
        inlineKeyboardButtonList2.add(whatsapp);
        inlineKeyboardButtonList2.add(facebook);
        inlineKeyboardButtonList2.add(twitter);
        inlineKeyboardButtonList2.add(menu);
        inlineButtons.add(inlineKeyboardButtonList1);//prima riga
        inlineButtons.add(inlineKeyboardButtonList2);//seconda riga
        inlineKeyboardMarkup.setKeyboard(inlineButtons);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
    }
}
