package com.group1.mstory.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import com.group1.mstory.objects.Book;

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
    private String imageUrl;
    private int bookId;


    public BookTile(ResultSet rs){
        try{
            this.setBookId(rs.getInt("booksid"));
            this.setTitle(rs.getString("title"));
            this.setIsbn(rs.getString("isbn"));
            this.setImageUrl(rs.getString("imageurl"));
            this.setPublisherId(rs.getInt("publisherid"));
            this.setAuthors( new ArrayList<String>(Arrays.asList(rs.getString("authors").split(","))));
        } catch (SQLException ex){
            ex.printStackTrace();
        }

    }

    public BookTile(Book book){
        this.setTitle(book.getTitle());
        this.setIsbn(book.getIsbn());
        this.setImageUrl(book.getImageUrl());
        this.setPublisherId(book.getPublisherId());     
    }
}
