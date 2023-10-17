package com.group1.mstory.controller;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.stereotype.Component;
import com.group1.mstory.model.BookTile;
import com.group1.mstory.connectors.JdbcConnector;


@Component
public class BookController {

    public static ArrayList<BookTile> getAllBookTiles(){
        JdbcConnector jdbcConnector = new JdbcConnector("jdbc:mysql://192.168.1.106:3310/MStorey","root","password");
        String sql = "SELECT Books.*, GROUP_CONCAT(Authors.Name) AS Authors FROM Books \r\n" + //
                "INNER JOIN Author_Book ON Author_Book.BookId = Books.BooksId \r\n" + //
                "INNER JOIN Authors ON Authors.AuthorId = Author_Book.AuthorId\r\n" + //
                "GROUP BY Books.BooksId;";

        ArrayList<BookTile> bookTileList = new ArrayList<BookTile>();

        try{
            ResultSet rs = jdbcConnector.prepareAndExecuteStatement(sql);

            while (rs.next()){
                BookTile bt = new BookTile();
                bt.setTitle(rs.getString("title"));
                bt.setIsbn(rs.getString("isbn"));
                bt.setImage(rs.getString("imageurl"));
                bt.setDescription(rs.getString("description"));
                bt.setPublisherId(rs.getInt("publisherid"));
                bt.setAuthors( new ArrayList<String>(Arrays.asList(rs.getString("authors").split(","))));


                bookTileList.add(bt);
            }

            
        } catch (Exception ex){
            System.out.println("Ruined");
            ex.printStackTrace();
        }


        return bookTileList;
    }

    
}
