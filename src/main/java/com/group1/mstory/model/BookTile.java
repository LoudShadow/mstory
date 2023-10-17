package com.group1.mstory.model;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookTile {
    private String title;
    private ArrayList<String> authors;
    private String description;
    private int PublisherId;
    private String isbn;
    private int price;
    private String image;
}
