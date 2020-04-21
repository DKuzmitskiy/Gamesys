package com.gamesys.testtask;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void shouldWriteEndRead() {
        List<Item> items = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            items.add(new Item(String.valueOf(i), "2", "3", "4", "5", "6"));
        }
        int[] result = itemRepository.save(items);
        Assertions.assertEquals(15, result.length);
        List<Item> resultList = itemRepository.findLastTen();
        Assertions.assertFalse(resultList.isEmpty());
        Assertions.assertEquals(10, resultList.size());
    }
}
