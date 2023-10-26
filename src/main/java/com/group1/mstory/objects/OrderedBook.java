package com.group1.mstory.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderedBook{
    private String title;
    private String isbn;
    private String imageUrl;
    private int price;
}