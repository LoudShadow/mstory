package com.group1.mstory.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.group1.mstory.connectors.JdbcConnector;
import com.group1.mstory.objects.Book;

@Component
public class BasketController {
    @Autowired
    OrderController orderController;

    @Autowired
    JdbcConnector jdbcConnector;

    @Autowired
    BookController bookController;

    Logger logger = LogManager.getLogger(JdbcConnector.class);


    public List<Book> getBasketProductsFromOrderId(int orderId) throws SQLException{
        ArrayList<Book> booksInBasket = new ArrayList<>();

        String sql = "SELECT * FROM Books " + 
        "INNER JOIN Book_Product ON Book_Product.BookId = Books.BooksId " + 
        "INNER JOIN Products ON Products.ProductId = Book_Product.ProductId " + 
        "INNER JOIN Orders_Products ON Orders_Products.ProductId = Products.ProductId " + 
        "INNER JOIN Orders ON Orders.OrderId = Orders_Products.OrderId " + 
        "WHERE Orders.OrderId = ? and Orders.isBasket = 1";

        PreparedStatement ps = jdbcConnector.prepareStatement(sql);
        ps.setInt(1, orderId);
        ResultSet rs = ps.executeQuery();
    
        ArrayList<Integer> bookIds = new ArrayList<>();

        while (rs.next()){
            bookIds.add(rs.getInt("bookid"));
        }

        for (int bookId : bookIds){
            booksInBasket.add(bookController.getBookByBookId(bookId));
        }

        return booksInBasket;
    }

    public void addProductByProductId(int orderId, int productId) throws SQLException{
        String sql = "INSERT INTO Orders_Products (orderid, productid, price) values (?,?,?);";
        PreparedStatement ps = jdbcConnector.prepareStatement(sql);
        ps.setInt(1,orderId);
        ps.setInt(2, productId);
        ps.setInt(3, 100);
        ps.executeUpdate();
    }



    public void removeProductByProductId(int userBasketId, int productId) throws SQLException{
        String sql = "DELETE FROM Orders_Products WHERE orderid = ? AND productid = ? LIMIT 1;";

        PreparedStatement ps = jdbcConnector.prepareStatement(sql);
        ps.setInt(1,userBasketId);
        ps.setInt(2,productId);
        ps.executeUpdate();
    }

    public HashMap<Integer, Integer> getProductsInBasket(int userId)
            throws SQLException {
        String sql = "SELECT op.ProductId, COUNT(op.ProductId) ProductCount FROM Orders o " +
                "JOIN Orders_Products op ON o.OrderId = op.OrderId " +
                "WHERE o.UserId = ? AND o.isBasket = 1 " +
                "GROUP BY op.ProductId;";

        HashMap<Integer, Integer> basket = new HashMap<>();

        PreparedStatement getBasket = jdbcConnector.prepareStatement(sql);
        getBasket.setInt(1, userId);
        ResultSet rs = getBasket.executeQuery();
        while (rs.next()) {
            basket.put(rs.getInt("ProductId"), rs.getInt("ProductCount"));
        }
        return basket;
    }

    public void basketCheckout(int userId) throws SQLException {
        String stockSql = "UPDATE Products p " + 
                "SET p.ProductCount = p.ProductCount - ? " + 
                "WHERE p.ProductId = ?; ";
        String basketSql = "UPDATE Orders o SET o.isBasket = 0 WHERE o.UserId = ?";
        String newBasketSql = "INSERT INTO Orders (UserId, isBasket) VALUES (?, 1)";
        String assignBasketSql = "UPDATE users SET basketId = (SELECT OrderId FROM Orders ORDER BY OrderId DESC LIMIT 1) WHERE users.id = ?";
        Connection con = jdbcConnector.getConnection();

        HashMap<Integer, Integer> basket = getProductsInBasket(userId);
        try (PreparedStatement updateStock = con.prepareStatement(stockSql);
                PreparedStatement updateBasket = con.prepareStatement(basketSql);
                PreparedStatement newBasket = con.prepareStatement(newBasketSql);
                PreparedStatement assignBasket = con.prepareStatement(assignBasketSql)) {
            con.setAutoCommit(false);
            for (HashMap.Entry<Integer, Integer> entry : basket.entrySet()) {
                // Update stock
                int prodId = entry.getKey();
                int count = entry.getValue();
                updateStock.setInt(1, count);
                updateStock.setInt(2, prodId);
                updateStock.executeUpdate();
            }

            // Empty authenticated user's basket
            updateBasket.setInt(1, userId);
            updateBasket.executeUpdate();

            //Generate new basket
            newBasket.setInt(1, userId);
            newBasket.executeUpdate();

            // Assign new basket to current user
            assignBasket.setInt(1, userId);
            assignBasket.executeUpdate();

            // Commit transaction
            con.commit();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            if (con != null) {
                try {
                    logger.info("Transaction is being rolled back");
                    con.rollback();
                } catch (SQLException excep) {
                    System.out.println(excep.getMessage());
                }
            }
        }
    }
}
