package com.project.demo.model;

import com.project.demo.controller.Naboo;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * Telegram
 */
public class
Telegram {

    public static void main(String[] args) {
        TelegramBotsApi botsApi;
        try {
            botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new Naboo());
        } catch (TelegramApiException e) {
           
            e.printStackTrace();
        }

        
    }
}