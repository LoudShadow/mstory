package com.group1.mstory.objects;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private int bookId;
    private int publisherId;
    private String isbn;
    private String title;
    private String description;
    private String publishDate;
    private String imageUrl;
    private int pageCount;
    private String binding;
    private float weight;
    private String publisher;
    private List<String> authors;
    private Product product;

    public void display(){
        System.out.println(bookId);
        System.out.println(publisherId);
        System.out.println(isbn);
        System.out.println(title);
        System.out.println(publishDate);
        System.out.println(imageUrl);
        System.out.println(pageCount);
        System.out.println(binding);
        System.out.println(weight);
        System.out.println(publisher);
        System.out.println(authors);
    }

}
