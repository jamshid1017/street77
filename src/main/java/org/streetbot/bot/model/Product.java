package org.streetbot.bot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    private UUID id;
    private String name;
    private String imageUrl;

    {
        id = UUID.randomUUID();
    }
}
