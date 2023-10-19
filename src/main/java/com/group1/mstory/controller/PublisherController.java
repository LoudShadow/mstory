package com.group1.mstory.controller;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.group1.mstory.connectors.JdbcConnector;
import com.group1.mstory.objects.Publisher;


@Component
public class PublisherController {

    @Autowired
    JdbcConnector jdbcConnector;
    

    public boolean addPublisher(String name) {
        String sql = "INSERT INTO Publishers(name) VALUES (?);";

        try{
            PreparedStatement ps = jdbcConnector.prepareStatement(sql);
            ps.setString(1,name);
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
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getString("name");
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public ArrayList<Publisher> getAllPublishers(){
        String sql = "SELECT * FROM Publishers;";
        ArrayList<Publisher> publishersList = new ArrayList<Publisher>();

        try{
            ResultSet rs = jdbcConnector.prepareAndExecuteQuery(sql);

            while(rs.next()){
                Publisher p = new Publisher();
                p.setPublisherID(rs.getInt("PublisherId"));
                p.setName(rs.getString("Name"));
                publishersList.add(p);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return publishersList;
    }
}


