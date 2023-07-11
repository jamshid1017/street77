package org.streetbot.bot;

import org.streetbot.bot.model.Product;
import org.streetbot.bot.service.ProductService;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class MyReplyKeyboard {
    public static ProductService productService = new ProductService();
    public static ReplyKeyboard createReplyKeyboardMarkup(List<String> category) {
        ReplyKeyboardMarkup replyKeyboard = new ReplyKeyboardMarkup();

        KeyboardRow row = new KeyboardRow();
        List<KeyboardRow> rows = new ArrayList<>();
        for (int i = 1; i <= category.size(); i++) {
            row.add(new KeyboardButton(category.get(i - 1)));
            if (i % 2 == 0) {
                rows.add(row);
                row = new KeyboardRow();
            }
        }

        if (category.size() % 2 != 0) {
            rows.add(row);
        }
        replyKeyboard.setKeyboard(rows);
        replyKeyboard.setResizeKeyboard(true);
        return replyKeyboard;
    }
    public static ReplyKeyboard createReplyKeyboardMarkup1(List<String> category) {
        ReplyKeyboardMarkup replyKeyboard = new ReplyKeyboardMarkup();

        KeyboardRow row = new KeyboardRow();
        List<KeyboardRow> rows = new ArrayList<>();
        for (int i = 1; i <= category.size(); i++) {
            row.add(new KeyboardButton(category.get(i - 1)));
                rows.add(row);
                row = new KeyboardRow();
        }
        replyKeyboard.setKeyboard(rows);
        replyKeyboard.setResizeKeyboard(true);
        return replyKeyboard;
    }
    public static ReplyKeyboard createInlineKeyboardMarkup(String callBackData) {
        List<Product> products = productService.getProduct();
        return createInlineKeyboardMarkup1(products);

//        if (callBackData.startsWith("back")) {
//            UUID parentBackId = UUID.fromString(callBackData.substring(4));
//
//            UUID parentId = categoryService.getById(parentBackId).getParentId();
//            List<Category> childCategories = categoryService.listCategoryByParentId(parentId);
//            return createInlineKeyboardMarkup(childCategories);
//        }
        /*Category category1 = categoryService.getById(UUID.fromString(callBackData));
        if (category1.isHasProduct()){
            getProduct(callBackData);
            List<Product> products = categoryService.getProduct()
                    .stream().filter(product -> Objects.equals(product.getCategoryId(),UUID.fromString(callBackData))).toList();
            return  createInlineKeyboardMarkup1(products);
        }*/
    }
    public static ReplyKeyboard createInlineKeyboardMarkup1(List<Product> products) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        for (int i = 1; i <= products.size(); i++) {
            Product product = products.get(i - 1);
            InlineKeyboardButton button = new InlineKeyboardButton(product.getName());
            button.setCallbackData(String.valueOf(product.getId()));
            row.add(button);
            if (i % 2 == 0) {
                rows.add(row);
                row = new ArrayList<>();
            }
        }

        if (products.size() % 2 != 0) {
            rows.add(row);
        }
        inlineKeyboardMarkup.setKeyboard(rows);
        return inlineKeyboardMarkup;
    }
    public static ReplyKeyboard shareContact() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        KeyboardButton phoneButton = new KeyboardButton();
        phoneButton.setText("share contact !!!");
        phoneButton.setRequestContact(true);
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(phoneButton);
        keyboardMarkup.setResizeKeyboard(true);
        List<KeyboardRow> keyboardRows = List.of(keyboardRow);
        keyboardMarkup.setKeyboard(keyboardRows);
        return keyboardMarkup;
    }
}
