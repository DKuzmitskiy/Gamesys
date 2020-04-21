package com.gamesys.testtask;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
public class ItemControllerTest {

    private MockMvc mockMvc;
    @Mock
    private ItemRepository itemRepository;
    @InjectMocks
    private ItemController itemController;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();
    }

    @Test
    public void shouldGetItems() throws Exception {
        List<Item> items = Collections.nCopies(10, new Item());
        when(itemRepository.findLastTen()).thenReturn(items);
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(10)));
        verify(itemRepository, times(1)).findLastTen();
        verifyNoMoreInteractions(itemRepository);
    }
}
