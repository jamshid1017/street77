package org.streetbot.bot.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.streetbot.bot.MyBot;
import org.streetbot.bot.model.User;
import org.streetbot.bot.util.FilePaths;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class UserService {

   public static ObjectMapper objectMapper = new ObjectMapper();
    @SneakyThrows
   public static List<User> getUser(){


        return objectMapper.readValue(new File(FilePaths.USERPATH), new TypeReference<>() {});
    }
    public boolean existByChatId(Long chatId) {
        List<User> users = getUser();
        return users.stream().anyMatch(user -> Objects.equals(user.getChatId(),chatId));
    }
    @SneakyThrows
    public User add(User user) {
        List<User> users = getUser();

        validateUser(user, users);

        Date now = new Date();

        user.setId(UUID.randomUUID());
        users.add(user);
        String usersStringJson = objectMapper.writeValueAsString(users);
        FileUtils.write(MyBot.pathUser, usersStringJson);
        return user;
    }
    private void validateUser(User user, List<User> users) {
        users.stream().filter(u -> StringUtils.equals(u.getPhoneNumber(), user.getPhoneNumber()))
                .findFirst().ifPresent(u -> {
                    throw new IllegalArgumentException(String.format("%s user exists", u.getPhoneNumber()));
                });
    }

}
