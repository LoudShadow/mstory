package com.group1.mstory.controller;


import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.group1.mstory.connectors.JdbcConnector;


@Component
public class PublisherController {

    @Autowired
    JdbcConnector jdbcConnector;
    

    public boolean addPublisher(String name) {
        String sql = "INSERT INTO Publishers(name) VALUES (?);";

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


    public String getPublisherByBookId(int id){
        String sql = "SELECT * FROM Publishers WHERE PublisherId = ?;";

        try {
            PreparedStatement ps = jdbcConnector.prepareStatement(sql);
            ps.setInt(1, id);
            System.out.println(ps.toString());
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getString("name");
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}


