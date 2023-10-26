package com.group1.mstory.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.group1.mstory.objects.OrderedBook;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderSummary {
    private int orderId;
    private int totalprice;
    private ArrayList<OrderedBook> books;
    // private String isbn;
    // private String title;
    // private String imageUrl;

    // public OrderSummary(ResultSet rs) {
    //     try {
    //         this.setOrderId(rs.getInt("orderid"));
    //         this.setProductId(rs.getInt("productid"));
    //         this.setPrice(rs.getInt("price"));
    //         this.setIsbn(rs.getString("isbn"));
    //         this.setTitle(rs.getString("title"));
    //         this.setImageUrl(rs.getString("imageurl"));
    //     } catch (SQLException ex) {
    //         ex.printStackTrace();
    //     }
    // }
}
