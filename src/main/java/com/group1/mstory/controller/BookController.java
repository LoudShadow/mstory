package com.group1.mstory.controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.stereotype.Component;
import com.group1.mstory.model.BookTile;
import com.mysql.cj.x.protobuf.MysqlxPrepare.Prepare;
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
                BookTile bt = new BookTile(rs);
                bookTileList.add(bt);
            }

            
        } catch (Exception ex){
            System.out.println("Ruined");
            ex.printStackTrace();
        }


        return bookTileList;
    }


    private static BookTile getBook(String sql, int id){
        JdbcConnector jdbcConnector = new JdbcConnector("jdbc:mysql://192.168.1.106:3310/MStorey","root","password");

        try {
            PreparedStatement ps = jdbcConnector.prepareStatement(sql);
            ps.setInt(1, id);
            System.out.println(ps.toString());
            ResultSet rs = ps.executeQuery();

            return new BookTile(rs);
        } catch (Exception ex){
            ex.printStackTrace();
        }

        return null;
    }

    public static BookTile getBookByProductId(int id){
        String sql = "SELECT Books.* FROM Books" + 
        "INNER JOIN Book_Product ON Book_Product.BookId = Books.BooksId " +
        "INNER JOIN Products ON Products.BookId = Book_Product.ProductId" + 
        "WHERE Products.ProductId = ?;";

        return getBook(sql, id);
    }

    public static BookTile getBookByBookId(int id){
        String sql = "SELECT * FROM Books WHERE BooksId = ?;";
        return getBook(sql, id);
    }


    

    
}
