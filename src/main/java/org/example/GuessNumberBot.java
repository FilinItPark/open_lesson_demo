package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.Random;

public class GuessNumberBot extends TelegramLongPollingBot {

    private Random random = new Random();
    private int targetNumber;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText()) {
                String chatId = String.valueOf(message.getChatId());
                String input = message.getText();

                if (input.equals("/start")) {
                    targetNumber = random.nextInt(100) + 1;
                    sendMessage(chatId, "Привет! Я загадал число от 1 до 100. Попробуй угадать!");
                } else {
                    int guess = Integer.parseInt(input);
                    if (guess == targetNumber) {
                        sendMessage(chatId, "Поздравляю, ты угадал число!");
                        targetNumber = random.nextInt(100) + 1;
                    } else if (guess < targetNumber) {
                        sendMessage(chatId, "Загаданное число больше!");
                    } else {
                        sendMessage(chatId, "Загаданное число меньше!");
                    }
                }
            }
        }
    }

    private void sendMessage(String chatId, String text) {
        SendMessage message = new SendMessage(chatId, text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        // Здесь нужно указать имя бота
        return "YourBotUsername";
    }

    @Override
    public String getBotToken() {
        // Здесь нужно указать токен, полученный от BotFather
        return "YourBotToken";
    }

    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            botsApi.registerBot(new GuessNumberBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
