package com.group1.mstory.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private int bookId;
    private int publisherId;
    private String isbn;
    private String title;
    private String publishDate;
    private String imageUrl;
    private int pageCount;
    private String binding;
    private float weight;
}
