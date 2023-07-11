package org.streetbot;

import lombok.SneakyThrows;
import org.streetbot.admin.AddCategory;
import org.streetbot.bot.MyBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    @SneakyThrows
    public static void main(String[] args) {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(new MyBot());
        AddCategory.addProduct();
    }
}