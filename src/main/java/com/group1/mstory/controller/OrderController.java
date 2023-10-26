package com.group1.mstory.controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.group1.mstory.connectors.JdbcConnector;
import com.group1.mstory.objects.Order;
import com.mysql.cj.xdevapi.PreparableStatement;

@Component
public class OrderController {
    
    @Autowired
    JdbcConnector jdbcConnector;

    public ArrayList<Order> getOrdersByUserId(int userId){
        String sql = "SELECT * FROM Orders WHERE userid =?";
        ArrayList<Order> orderList = new ArrayList<Order>();

        try {
            ResultSet rs = jdbcConnector.prepareAndExecuteQuery(sql);
            while (rs.next()){
                Order order = new Order();
                order.setOrderId(rs.getInt("orderid"));
                order.setUserid(userId);
                order.setBaskerOrder(rs.getBoolean("basketorder"));

                orderList.add(order);
            }
        } catch (Exception ex){

        }
        return orderList;
    }

    public int getBasketOrderIdByUserId(int userId){
        String sql = "SELECT orderid FROM Orders WHERE userid = ? AND basketorder = 1;";

        try{
            ResultSet rs = jdbcConnector.prepareAndExecuteQuery(sql);
            rs.next();
            return rs.getInt("orderid");
        } catch (Exception ex){
            ex.printStackTrace();
        }

        return -1;
    }

    public int createBasket_returnBasketId(int userId){
        String sql = "INSERT INTO Orders (UserId, isBasket) VALUES (?, ?);";

        // As we are creating a basket, we need to insert a 1
        int isBasket = 1;

        try{
            PreparedStatement ps = jdbcConnector.prepareStatement(sql);
            ps.setInt(1,userId);
            ps.setInt(2,isBasket);
            ps.executeUpdate();
            return jdbcConnector.getLastInsertId();
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return -1;
    }

};
