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
public class AuthorController {
    

    public static boolean addAuthor(String name) {
        JdbcConnector jdbcConnector = new JdbcConnector("jdbc:mysql://192.168.1.106:3310/MStorey","root","password");
        String sql = "INSERT INTO Authors(name) VALUES (?);";
        try{
            PreparedStatement ps = jdbcConnector.prepareStatement(sql);
            ps.setString(1,name);
            System.out.println(ps.toString());
            ps.executeUpdate();
            return true;

        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    public static ArrayList<String> getAuthorsByBookId(int id){
        JdbcConnector jdbcConnector = new JdbcConnector("jdbc:mysql://192.168.1.106:3310/MStorey","root","password");
        String sql = "SELECT Authors.Name AS Authors FROM Books \r\n" + //
                "INNER JOIN Author_Book ON Author_Book.BookId = Books.BooksId \r\n" + //
                "INNER JOIN Authors ON Authors.AuthorId = Author_Book.AuthorId\r\n" + //
                "where Books.BooksId =?;";

        try{
            PreparedStatement ps = jdbcConnector.prepareStatement(sql);
            ps.setInt(1,id);
            System.out.println(ps.toString());
            ResultSet rs = ps.executeQuery();

            ArrayList<String> returnedAuthors = new ArrayList<String>();

            while (rs.next()){
                returnedAuthors.add(rs.getString("authors"));
            }
            return returnedAuthors;

        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
}