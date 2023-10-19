package com.group1.mstory.controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

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

    public ArrayList<Book> getBasketProductsFromOrderId(int orderId){
        ArrayList<Book> booksInBasket = new ArrayList<Book>();

        String sql = "SELECT * FROM Books " + 
        "INNER JOIN Book_Product ON Book_Product.BookId = Books.BooksId " + 
        "INNER JOIN Products ON Products.ProductId = Book_Product.ProductId " + 
        "INNER JOIN Orders_Products ON Orders_Products.ProductId = Products.ProductId " + 
        "INNER JOIN Orders ON Orders.OrderId = Orders_Products.OrderId " + 
        "WHERE Orders.OrderId = ? and Orders.isBasket = 1";

        try {
            PreparedStatement ps = jdbcConnector.prepareStatement(sql);
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
        
            ArrayList<Integer> bookIds = new ArrayList<Integer>();

            while (rs.next()){
                bookIds.add(rs.getInt("bookid"));
            }

            for (int bookId : bookIds){
                booksInBasket.add(bookController.getBookByBookId(bookId));
            }

            return booksInBasket;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }

    public void addProductByProductId(int orderId, int productId){
        String sql = "INSERT INTO Orders_Products (orderid, productid, price) values (?,?,?);";

        try {
            PreparedStatement ps = jdbcConnector.prepareStatement(sql);
            ps.setInt(1,orderId);
            ps.setInt(2, productId);
            ps.setInt(3, 100);
            ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }



    public void removeProductByProductId(int userBasketId, int productId){
        String sql = "DELETE FROM Orders_Products WHERE orderid = ? AND productid = ? LIMIT 1;";

        try {
            PreparedStatement ps = jdbcConnector.prepareStatement(sql);
            ps.setInt(1,userBasketId);
            ps.setInt(2,productId);
            System.out.println(ps.toString());
            ps.executeUpdate();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }


}
