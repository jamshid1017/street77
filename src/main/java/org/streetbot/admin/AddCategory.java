package org.streetbot.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.streetbot.bot.MyBot;
import org.streetbot.bot.model.Product;
import org.streetbot.bot.service.FileUtils;
import org.streetbot.bot.service.ProductService;
import java.util.*;


public class AddCategory {
    public static Scanner scanner = new Scanner(System.in);

    public static ProductService productService = new ProductService();
   public static void addProduct() throws JsonProcessingException {
       ObjectMapper objectMapper = new ObjectMapper();

            System.out.println("Enter name : ");
            String name = scanner.next();
            System.out.println("Enter product quantity : ");
            int quantity = scanner.nextInt();
            System.out.println("Enter image URl : ");
            String imageURL = scanner.next();
            Product product = new Product();

            List<Product> products =productService.getProduct();
            products.add(product);


            String usersStringJson = objectMapper.writeValueAsString(products);
            FileUtils.write(MyBot.pathProduct, usersStringJson);

   }
}
