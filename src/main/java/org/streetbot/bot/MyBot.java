package org.streetbot.bot;

import org.apache.commons.lang3.StringUtils;
import org.streetbot.bot.convert.UserConverter;
import org.streetbot.bot.model.Product;
import org.streetbot.bot.model.User;
import org.streetbot.bot.service.UserService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import static org.streetbot.bot.MyReplyKeyboard.*;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class MyBot extends TelegramLongPollingBot {
    public static final String pathProduct = "src/main/resources/product.json";
    public static final String pathUser = "src/main/resources/user.json";
    private static final UserService userService = new UserService();
    private static final UserConverter userConverter = new UserConverter();
   private static List<String> branchs = List.of("Wok && Street Beruniy", "Wok && Street Disney", "Wok && Street Anhor Park", "Wok && Street ECO Chimgan",
           "Street N1", "Wok && Street Sergili", "Wok Samarqand Darvoza", "Wok && Mega Polis");

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Long chatId = update.getMessage().getChatId();
            Contact contact = update.getMessage().getContact();
            boolean exist = userService.existByChatId(chatId);
            if (Objects.nonNull(contact)) {
                User user = userConverter.convertToEntity(contact);
                userService.add(user);
                myExecuteCategory(chatId, "Successfully registered", createReplyKeyboardMarkup(List.of("Menu", "Buyurtma tarixi",
                        "Fikr bildirish", "Ma'lumot", "Biz bilan aloqa")));
                return;
            }
            if (!exist) {
                myExecuteCategory(chatId, "Welcome to our bot !", shareContact());
                return;
            }
            if (StringUtils.equals(update.getMessage().getText(), "/start")) {
                myExecuteCategory(chatId, "Welcome to our bot !", createReplyKeyboardMarkup(List.of("\uD83C\uDF7D Menu", "\uD83D\uDCD6 Buyurtma tarixi",
                        "✍\uFE0F Fikr bildirish", "ℹ\uFE0F Ma'lumot", "☎\uFE0F Biz bilan aloqa")));
            } else {
                String text = update.getMessage().getText();
                if (StringUtils.equalsAnyIgnoreCase(text, "\uD83C\uDF7D menu")) {
                    myExecuteCategory(chatId, "choose category", createReplyKeyboardMarkup(List.of("Olib ketish", "Yetkazib berish", "Ortga")));
                } else if (StringUtils.equalsAnyIgnoreCase(text, "\uD83D\uDCD6 Buyurtma tarixi")) {
                    myExecuteCategory(chatId, "choose category", createReplyKeyboardMarkup(List.of("Olib ketish", "Yetkazib berish", "Ortga")));
                } else if (StringUtils.equalsAnyIgnoreCase(text, "✍\uFE0F Fikr bildirish")) {
                    myExecuteCategory(chatId, "choose category", createReplyKeyboardMarkup1(List.of("Hammasi a'lo⭐\uFE0F⭐\uFE0F⭐\uFE0F⭐\uFE0F⭐\uFE0F", "Yaxshi⭐\uFE0F⭐\uFE0F⭐\uFE0F⭐\uFE0F", "Yoqmadi ⭐\uFE0F⭐\uFE0F", "Yomon ⭐\uFE0F")));
                } else if (StringUtils.equalsAnyIgnoreCase(text, "ℹ\uFE0F Ma'lumot")) {
                    myExecuteCategory(chatId, "choose category", createReplyKeyboardMarkup(branchs));
                } else if (StringUtils.equalsAnyIgnoreCase(text, "☎\uFE0F Biz bilan aloqa")) {
                    myExecuteCategory(chatId, "Agar sizda Savollar/Shikoyatlar/Takliflar bo'lsa bizga yozishingiz mumkin: http://t.me/street77_offical_bot\n" +
                            "\n" +
                            "☎\uFE0F Telefon raqam: 71-200-73-73 / 71 200-86-86", createReplyKeyboardMarkup(List.of("\uD83C\uDF7D Menu", "\uD83D\uDCD6 Buyurtma tarixi",
                            "✍\uFE0F Fikr bildirish", "ℹ\uFE0F Ma'lumot", "☎\uFE0F Biz bilan aloqa")));
                } else if (StringUtils.equalsAnyIgnoreCase(text, "Yetkazib berish")) {
                    myExecuteCategory(chatId, "choose category", createReplyKeyboardMarkup(List.of("Fillialarimiz", "Mening manzilim", "Ortga")));
                } else if (StringUtils.equalsAnyIgnoreCase(text, "Mening manzilim")) {
                    myExecuteCategory(chatId, "Share location !", shareLocation());
                } else if (StringUtils.equalsAnyIgnoreCase(text, "Olib ketish") ||
                        StringUtils.equalsAnyIgnoreCase(text, "Fillialarimiz")) {
                    myExecuteCategory(chatId, "choose category", createReplyKeyboardMarkup(branchs));
                } else if (branchs.contains(text)) {
                    myExecuteCategory(chatId, "choose product", createInlineKeyboardMarkup((String) null));
                }
            }
        } else if (update.hasCallbackQuery()) {
            String data = update.getCallbackQuery().getData();
            Long chatId = update.getCallbackQuery().getMessage().getChatId();
            myExecute(chatId, UUID.fromString(data), createInlineKeyboardMarkup(data));
        }

    }

    private ReplyKeyboard shareLocation() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        KeyboardButton phoneButton = new KeyboardButton();
        phoneButton.setText("share location !!!");
        phoneButton.setRequestLocation(true);
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(phoneButton);
        keyboardMarkup.setResizeKeyboard(true);
        List<KeyboardRow> keyboardRows = List.of(keyboardRow);
        keyboardMarkup.setKeyboard(keyboardRows);
        return keyboardMarkup;
    }

    private void myExecuteCategory(Long chatId, String message, ReplyKeyboard r) {
        SendMessage s = new SendMessage();
        s.setChatId(chatId);
        s.setText(message);
        s.setReplyMarkup(r);
        try {
            execute(s);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void myExecute(Long chatId, UUID productId, ReplyKeyboard r) {
        Product products = productService.getProduct().stream().filter(product -> Objects.equals(product.getId(), productId)).findFirst().orElseThrow();
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(new InputFile(new File(products.getImageUrl())));
        try {
            execute(sendPhoto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public String getBotUsername() {
        return MyBotConstant.USERNAME;
    }

    @Override
    public String getBotToken() {
        return MyBotConstant.TOKEN;
    }
}
