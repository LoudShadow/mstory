package com.group1.mstory.controller;

import java.sql.ResultSet;
import java.util.ArrayList;
import com.group1.mstory.model.BookTile;
import com.group1.mstory.connectors.JdbcConnector;

public class BookController {

    public ArrayList<BookTile> getAllBookTiles(){
        JdbcConnector jdbcConnector = new JdbcConnector("jdbc:mysql://localhost:3310/MStorey","root","password");
        
        String sql = "SELECT * FROM Books";
        try{
            ResultSet rs = jdbcConnector.prepareAndExecuteStatement(sql);

            while (rs.next()){
                System.out.println(rs.getString("title"));
            }
        } catch (Exception ex){
            System.out.println("Ruined");
            ex.printStackTrace();
        }


        return null;
    }   

    
}
