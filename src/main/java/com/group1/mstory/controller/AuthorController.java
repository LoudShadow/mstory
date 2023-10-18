package com.group1.mstory.controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.group1.mstory.connectors.JdbcConnector;

@Component
public class AuthorController {
    @Autowired
    JdbcConnector jdbcConnector;

    public boolean addAuthor(String name) {
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

    public ArrayList<String> getAuthorsByBookId(int id){
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