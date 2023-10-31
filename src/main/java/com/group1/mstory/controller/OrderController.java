package com.group1.mstory.controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.group1.mstory.connectors.JdbcConnector;
import com.group1.mstory.model.OrderSummary;
import com.group1.mstory.objects.Order;
import com.group1.mstory.objects.OrderedBook;

@Component
public class OrderController {

    @Autowired
    JdbcConnector jdbcConnector;

    public ArrayList<Order> getOrdersByUserId(int userId) {
        String sql = "SELECT * FROM Orders WHERE userid =?";
        ArrayList<Order> orderList = new ArrayList<Order>();

        try {
            ResultSet rs = jdbcConnector.prepareAndExecuteQuery(sql);
            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("orderid"));
                order.setUserid(userId);
                order.setBaskerOrder(rs.getBoolean("basketorder"));

                orderList.add(order);
            }
        } catch (Exception ex) {

        }
        return orderList;
    }

    public int getBasketOrderIdByUserId(int userId) {
        String sql = "SELECT orderid FROM Orders WHERE userid = ? AND basketorder = 1;";

        try {
            ResultSet rs = jdbcConnector.prepareAndExecuteQuery(sql);
            rs.next();
            return rs.getInt("orderid");
        } catch (Exception ex) {
            // ex.printStackTrace();
        }

        return -1;
    }

    public ArrayList<OrderSummary> getAllUserOrders(int userId){
        ArrayList<OrderSummary> orders = new ArrayList<>();
        String query = "SELECT * FROM Orders WHERE Orders.UserId = ?";
        try (PreparedStatement stmt = jdbcConnector.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()){
                orders.add(fetchOrderHistory(resultSet.getInt("OrderId")));
            }

        } catch (Exception ex) {
            // ex.printStackTrace();
        }
        return orders;
    }

    public OrderSummary fetchOrderHistory(int orderId) {
        String query = "SELECT o.OrderId, p.ProductId, p.price, b.ISBN, b.Title, b.ImageURL " +
                "FROM Orders o " +
                "JOIN Orders_Products op ON o.OrderId = op.OrderId " +
                "JOIN Products p ON p.ProductId = op.ProductId " +
                "JOIN Books b ON b.BooksId = p.BookId " +
                "WHERE o.OrderId=?";



        try (PreparedStatement stmt = jdbcConnector.prepareStatement(query)) {
            stmt.setInt(1, orderId);
            ResultSet resultSet = stmt.executeQuery();
            int price =0;
            ArrayList<OrderedBook> books= new ArrayList<>();
            while (resultSet.next()) {
                OrderedBook book = new OrderedBook(
                    resultSet.getString("Title"),
                    resultSet.getString("ISBN"),
                    resultSet.getString("ImageURL"),
                    resultSet.getInt("price")
                );
                price += book.getPrice();
                books.add(book);
            }
            return new OrderSummary(
                orderId,
                price,
                books
            );
        } catch (Exception ex) {
            // ex.printStackTrace();
            return new OrderSummary();
        }
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
            // ex.printStackTrace();
        }
        return -1;
    }

};
