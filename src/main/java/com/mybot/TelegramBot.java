package com.mybot;

import java.security.SecureRandom;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class TelegramBot extends TelegramLongPollingBot {
       private static final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+";
    private static final SecureRandom random = new SecureRandom();

    public static String generatePassword(int length) {
        if (length < 1) length = 8;
        if (length > 100) length = 100;

        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(chars.length());
            password.append(chars.charAt(index));
        }
        return password.toString();
    }
public TelegramBot() {
super("BOT_TOKEN");
}

public static void main(String[] args) throws TelegramApiException {
System.out.println("Starting telegram bot");
TelegramBot telegramBot = new TelegramBot();
TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
telegramBotsApi.registerBot(telegramBot);
}

@Override
public String getBotUsername() {
return "MyPasswordGeneratorBot";
}

@Override
public String getBotToken() {
return "BOT_TOKEN";
}

@Override
public void onUpdateReceived(final Update update) {
    if (!update.hasMessage() || !update.getMessage().hasText()) return;
    
    Message message = update.getMessage();
    String messageText = message.getText();
    long chatId = message.getChatId();
    
  
    System.out.println("From: " + message.getFrom().getFirstName() + " " + message.getFrom().getUserName() +" Text: " + messageText);

if (messageText.startsWith("/start") || messageText.startsWith("Başla")) {
    String rawName = message.getFrom().getFirstName();
    String name = escapeMarkdownV2(rawName);
    String info = escapeMarkdownV2("! To generate a password, send command: ");
    String command = "`/generate [length] or Oluştur [length]`, where [length] is optional and defaults to 12"; 
    String fullMessage = name + ", Hello" + info + command;
    System.out.println("Sending message: " + fullMessage); 
    sendMessage(chatId, fullMessage, true);
}
     else if (messageText.startsWith("/generate")|| messageText.startsWith("Oluştur")) {
        String[] messageParts = messageText.split(" ");
        int passwordLength = 12; 
        
        try {
            if (messageParts.length > 1) {
                passwordLength = Integer.parseInt(messageParts[1]);
            }
            
            String password = generatePassword(passwordLength);
            String safePassword = escapeMarkdownV2(password);
            sendMessage(chatId, "||" + safePassword + "||", true);
            
        } catch (NumberFormatException e) {
            sendMessage(chatId, "Please enter a valid number for length. Example: `/generate 12`", true);
        }
    }
}

private String escapeMarkdownV2(String text) {
    return text.replace("!", "\\!")
               .replace("-", "\\-")
               .replace("_", "\\_")
               .replace(".", "\\.")
               .replace("(", "\\(")
               .replace(")", "\\)")
               .replace("{", "\\{")
               .replace("}", "\\}")
               .replace("[", "\\[")
               .replace("]", "\\]")
               .replace("#", "\\#")
               .replace("+", "\\+")
               .replace(">", "\\>")
               .replace("=", "\\=");
}

void sendMessage(long chatId, String messageText, boolean isMarkdown) {
    SendMessage sendMessage = new SendMessage();
    if (isMarkdown) {
        sendMessage.setParseMode("MarkdownV2");
    }
    sendMessage.setText(messageText);
    sendMessage.setChatId(String.valueOf(chatId));
    try {
        execute(sendMessage);
    } catch (TelegramApiException e) {
        e.printStackTrace(); 
        System.out.println("Failed to send a message: " + e.getMessage());
    }
}
}
