package org.streetbot.bot.convert;

import org.streetbot.bot.model.User;
import org.telegram.telegrambots.meta.api.objects.Contact;

public class UserConverter {

    public User convertToEntity(Contact contact){
        return User.builder()
                .phoneNumber(contact.getPhoneNumber())
                .firstName(contact.getFirstName())
                .lastName(contact.getLastName())
                .chatId(contact.getUserId())
                .build();
    }
}
