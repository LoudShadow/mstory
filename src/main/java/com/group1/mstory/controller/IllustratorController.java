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
public class IllustratorController {
    

    public static boolean addIllustrator(String name) {
        JdbcConnector jdbcConnector = new JdbcConnector("jdbc:mysql://192.168.1.106:3310/MStorey","root","password");
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