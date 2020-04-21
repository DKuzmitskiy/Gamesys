package com.gamesys.testtask;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ItemServiceTest {

    @Autowired
    private ItemService itemService;

    @Test
    public void shouldGetItems() {
        List<Item> items = itemService.getItems();
        Assertions.assertFalse(items.isEmpty());
    }
}
