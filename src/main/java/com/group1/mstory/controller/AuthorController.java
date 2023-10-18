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
        try {
            PreparedStatement ps = jdbcConnector.prepareStatement(sql);
            ps.setString(1, name);
            System.out.println(ps.toString());
            ps.executeUpdate();
            return true;

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public ArrayList<String> getAuthorsByBookId(int id){
        String sql = "SELECT Authors.Name AS Authors FROM Books \r\n" + //
                "INNER JOIN Author_Book ON Author_Book.BookId = Books.BooksId \r\n" + //
                "INNER JOIN Authors ON Authors.AuthorId = Author_Book.AuthorId\r\n" + //
                "where Books.BooksId =?;";

        try {
            PreparedStatement ps = jdbcConnector.prepareStatement(sql);
            ps.setInt(1, id);
            System.out.println(ps.toString());
            ResultSet rs = ps.executeQuery();

            ArrayList<String> returnedAuthors = new ArrayList<String>();

            while (rs.next()) {
                returnedAuthors.add(rs.getString("authors"));
            }
            return returnedAuthors;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static ArrayList<Author> getAllAuthors() {
        JdbcConnector jdbcConnector = new JdbcConnector("jdbc:mysql://192.168.1.106:3310/MStorey", "root", "password");
        String sql = "SELECT * FROM Authors;";

        ArrayList<Author> authorsList = new ArrayList<Author>();

        try {
            ResultSet rs = jdbcConnector.prepareAndExecuteStatement(sql);

            while (rs.next()) {
                Author a = new Author();
                a.setAuthorId(rs.getInt("AuthorId"));
                a.setName(rs.getString("Name"));
                authorsList.add(a);
            }

        } catch (Exception ex) {
            System.out.println("Ruined [All Author]");
            ex.printStackTrace();
        }

        return authorsList;
    }

    public static Author getAuthorById(int id) {
        JdbcConnector jdbcConnector = new JdbcConnector("jdbc:mysql://192.168.1.106:3310/MStorey", "root", "password");
        String sql = "SELECT * FROM Authors WHERE AuthorId = ?;";

        try {
            PreparedStatement ps = jdbcConnector.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            Author a = new Author();
            if (rs.next()) {
                a.setAuthorId(rs.getInt("AuthorId"));
                a.setName(rs.getString("Name"));
            }
            return a;
        } catch (Exception ex) {
            System.out.println("Ruined [Author by id]");
            ex.printStackTrace();
        }

        return null;
    }
}