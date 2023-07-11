package org.streetbot.bot.service;

import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileUtils {
    @SneakyThrows
    public static void write(String path, String data) {
        Files.writeString(Path.of(path), data, StandardOpenOption.WRITE);
    }
}
