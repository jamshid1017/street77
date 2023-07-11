package org.streetbot.bot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    private UUID id;
    private String name;
    private UUID parentId;
    private boolean hasChild;
    private boolean hasProduct;

    {
        id = UUID.randomUUID();
    }

    public Category(String name, UUID parentId) {
        this.name = name;
        this.parentId = parentId;
    }
}
