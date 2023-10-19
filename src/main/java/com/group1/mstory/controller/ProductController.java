package com.group1.mstory.controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.group1.mstory.connectors.JdbcConnector;
import com.group1.mstory.objects.Product;

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

    public Product getProductByProductId(int productId){
        Product product = new Product();

        try {
            String sql = "SELECT * FROM Products WHERE productId = ?;";
            PreparedStatement ps = jdbcConnector.prepareStatement(sql);
            ps.setInt(1,productId);
            ResultSet rs = ps.executeQuery();
            rs.next();
            product.setProductId(rs.getInt("productId"));
            product.setBookId(rs.getInt("bookId"));
            product.setPrice(rs.getInt("price"));
            product.setProductCount(rs.getInt("productCount"));
            product.setProductType(rs.getInt("productType"));

            return product;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
    
}
