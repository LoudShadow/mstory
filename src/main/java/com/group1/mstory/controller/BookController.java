package com.group1.mstory.controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.group1.mstory.model.BookTile;
import com.group1.mstory.objects.Book;
import com.group1.mstory.connectors.JdbcConnector;


@Component
public class BookController {
    @Autowired
    JdbcConnector jdbcConnector;

    @Autowired
    AuthorController ac;

    @Autowired
    PublisherController pc;

    public ArrayList<BookTile> getAllBookTiles(){

        String sql = "SELECT Books.*, GROUP_CONCAT(Authors.Name) AS Authors FROM Books \r\n" + //
                "INNER JOIN Author_Book ON Author_Book.BookId = Books.BooksId \r\n" + //
                "INNER JOIN Authors ON Authors.AuthorId = Author_Book.AuthorId\r\n" + //
                "GROUP BY Books.BooksId;";

        ArrayList<BookTile> bookTileList = new ArrayList<BookTile>();

        try{
            ResultSet rs = jdbcConnector.prepareAndExecuteStatement(sql);

            while (rs.next()){
                BookTile bt = new BookTile(rs);
                bookTileList.add(bt);
            }

            
        } catch (Exception ex){
            System.out.println("Ruined");
            ex.printStackTrace();
        }

        return bookTileList;
    }


    private Book getBook(String sql, int id){
        try {
            PreparedStatement ps = jdbcConnector.prepareStatement(sql);
            ps.setInt(1, id);
            System.out.println(ps.toString());
            ResultSet rs = ps.executeQuery();
            rs.next();
            System.out.println(rs.toString());

            return new Book(
                    rs.getInt("booksid"),
                    rs.getInt("publisherid"),
                    rs.getString("isbn"),
                    rs.getString("title"),
                    rs.getString("publishdate"),
                    rs.getString("imageurl"),
                    rs.getInt("pagecount"),
                    rs.getString("binding"),
                    (float) rs.getLong("weight"),
                    pc.getPublisherByBookId(rs.getInt("publisherid")),
                    ac.getAuthorsByBookId(rs.getInt("booksid")));
        } catch (Exception ex){
            ex.printStackTrace();
        }

        return null;
    }

    public Book getBookByProductId(int id){
        String sql = "SELECT Books.* FROM Books" + 
        "INNER JOIN Book_Product ON Book_Product.BookId = Books.BooksId " +
        "INNER JOIN Products ON Products.BookId = Book_Product.ProductId" + 
        "WHERE Products.ProductId = ?;";

        return getBook(sql, id);
    }

    public Book getBookByBookId(int id){
        String sql = "SELECT * FROM Books WHERE BooksId = ?;";
        return getBook(sql, id);
    }


    

    
}
