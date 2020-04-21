package com.gamesys.testtask;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Item {
    private String guid;
    private String pubDate;
    private String title;
    private String link;
    private String description;
    private String category;
}
