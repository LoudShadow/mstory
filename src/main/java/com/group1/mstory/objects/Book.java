package com.group1.mstory.objects;

import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import com.group1.mstory.controller.AuthorController;
import com.group1.mstory.controller.BookController;
import com.group1.mstory.controller.PublisherController;

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
    private String publisher;
    private ArrayList<String> authors;

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
