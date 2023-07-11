package org.streetbot.bot.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.streetbot.bot.MyBot;
import org.streetbot.bot.model.Product;

import java.io.File;
import java.util.List;

public class ProductService {
    public ObjectMapper objectMapper = new ObjectMapper();
    @SneakyThrows
    public List<Product> getProduct() {
        return objectMapper.readValue(new File(MyBot.pathProduct), new TypeReference<List<Product>>() {
        });
    }
}
