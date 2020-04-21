package com.gamesys.testtask;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.when;


@SpringBootTest
public class ScheduledTaskServiceTest {

    private ScheduledTaskService scheduledTaskService;

    @BeforeEach
    public void init() {
        List<Item> items = Collections.singletonList(new Item());
        ItemService itemService = Mockito.mock(ItemService.class);
        when(itemService.getItems()).thenReturn(items);
        ItemRepository itemRepository = Mockito.mock(ItemRepository.class);
        when(itemRepository.save(items)).thenReturn(new int[]{1});
        scheduledTaskService = new ScheduledTaskService(itemService, itemRepository);
    }

    @Test
    public void shouldGetHttpDataAndSave() {
        Assertions.assertNotNull(scheduledTaskService);
        scheduledTaskService.getItemsAndSave();
        await().until(scheduledTaskService::isDone);
    }
}
