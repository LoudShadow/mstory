package com.group1.mstory.controller;

import java.sql.PreparedStatement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.group1.mstory.connectors.JdbcConnector;

@Component
public class IllustratorController {

    @Autowired
    JdbcConnector jdbcConnector;
    

    public boolean addIllustrator(String name) {
        String sql = "INSERT INTO Illustrators(name) VALUES (?);";
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
}