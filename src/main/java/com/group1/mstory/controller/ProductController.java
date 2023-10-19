package com.group1.mstory.controller;

import java.sql.PreparedStatement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.group1.mstory.connectors.JdbcConnector;

@Component
public class ProductController {
    @Autowired
    JdbcConnector jdbcConnector;


    public int addProduct(int productType, int productCount, int bookId){
        String sql = "INSERT INTO Products (producttype, productcount, bookid) VALUES (?,?,?)";

        try{
            PreparedStatement ps = jdbcConnector.prepareStatement(sql);
            ps.setInt(1, productType);
            ps.setInt(2, productCount);
            ps.setInt(3, bookId);
            ps.executeUpdate();
            return jdbcConnector.getLastInsertId();
        } catch (Exception ex){
            ex.printStackTrace();
        }

        return -1;

    }
    
}
