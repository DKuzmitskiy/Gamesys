package com.gamesys.testtask;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ScheduledTaskService {
    private final ItemService itemService;
    private final ItemRepository itemRepository;
    @Getter
    private volatile boolean done = false;

    @Autowired
    public ScheduledTaskService(ItemService itemService, ItemRepository itemRepository) {
        this.itemService = itemService;
        this.itemRepository = itemRepository;
    }

    @Scheduled(fixedRate = 30000)
    public void getItemsAndSave() {
        List<Item> items = itemService.getItems();
        if (!items.isEmpty()) {
            int[] result = itemRepository.save(items);
            done = result.length > 0;
            log.info("Saved to db " + result.length);
        }
    }
}
