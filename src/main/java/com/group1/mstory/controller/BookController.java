package com.group1.mstory.controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.group1.mstory.model.BookTile;
import com.group1.mstory.objects.Book;
import com.group1.mstory.connectors.JdbcConnector;


@Component
public class BookController {
    @Autowired
    JdbcConnector jdbcConnector;

    @Autowired
    AuthorController authorController;

    @Autowired
    PublisherController publisherController;

    @Autowired
    ProductController productController;

    public List<BookTile> buildBookTiles(ResultSet rs){
        ArrayList<BookTile> bookTileList = new ArrayList<>();

        try{
            while (rs.next()){
                BookTile bt = new BookTile(rs);
                bookTileList.add(bt);
            }
        } catch (Exception ex){
            // ex.printStackTrace();
        }

        return bookTileList;
    }

    public List<BookTile> getAllBookTiles(){

        String sql = "SELECT Books.*, GROUP_CONCAT(Authors.Name) AS Authors FROM Books \r\n" + //
                "INNER JOIN Author_Book ON Author_Book.BookId = Books.BooksId \r\n" + //
                "INNER JOIN Authors ON Authors.AuthorId = Author_Book.AuthorId\r\n" + //
                "GROUP BY Books.BooksId;";


        try{
            ResultSet rs = jdbcConnector.prepareAndExecuteQuery(sql);
            return buildBookTiles(rs);
        } catch (Exception ex){
            // ex.printStackTrace();
            return null;
        }
    }

    public List<BookTile> getTilesSearch(String searchTerm){
        String sql = 
        "SELECT * FROM (SELECT Books.* , " +
        "    GROUP_CONCAT(Authors.Name) AS Authors," +
        "    CONCAT(Books.Title, " +
        "            ' '," +
        "            Books.Description," +
        "            ' '," +
        "            Publishers.Name," +
        "            ' '," +
        "            GROUP_CONCAT(Authors.Name)) AS searchText FROM Books " +
        "INNER JOIN Author_Book ON Author_Book.BookId = Books.BooksId " +
        "INNER JOIN Authors ON Authors.AuthorId = Author_Book.AuthorId " +
        "INNER JOIN Publishers ON Books.PublisherId = Publishers.PublisherId " +
        "GROUP BY Books.BooksId) as i " +
        "WHERE searchText LIKE ?; ";

        try{
            PreparedStatement ps = jdbcConnector.prepareStatement(sql);
            ps.setString(1, "%" + searchTerm + "%");
            ResultSet rs = ps.executeQuery();
            return buildBookTiles(rs);
        } catch (Exception ex){
            return new ArrayList<>();
        }
    }

    private Book getBook(String sql, int id, boolean isProductId) throws SQLException{
        PreparedStatement ps = jdbcConnector.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        rs.next();

        Book book = new Book(
                rs.getInt("booksid"),
                rs.getInt("publisherid"),
                rs.getString("isbn"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getString("publishdate"),
                rs.getString("imageurl"),
                rs.getInt("pagecount"),
                rs.getString("binding"),
                (float) rs.getLong("weight"),
                publisherController.getPublisherByBookId(rs.getInt("publisherid")),
                authorController.getAuthorsByBookId(rs.getInt("booksid")),
                null);

        if (isProductId){
            book.setProduct(productController.getProductByProductId(id));
        } else{
            int bookProductId = getProductIdFromBookId(book.getBookId());
            book.setProduct(productController.getProductByProductId(bookProductId));
        }

        return book;
    }

    public Book getBookByProductId(int id) throws SQLException{
        String sql = "SELECT * FROM Books " + 
        "INNER JOIN Book_Product ON Book_Product.BookId = Books.BooksId " +
        "INNER JOIN Products ON Products.BookId = Book_Product.ProductId " + 
        "WHERE Products.ProductId = ?;";

        return getBook(sql, id, true);
    }

    public Book getBookByBookId(int id) throws SQLException{
        String sql = "SELECT * FROM Books WHERE BooksId = ?;";
        return getBook(sql, id, false);
    }


    public int getProductIdFromBookId(int bookId) throws SQLException{
        String sql = "SELECT * FROM Book_Product WHERE BookId = ?;";
        PreparedStatement ps  = jdbcConnector.prepareStatement(sql);
        ps.setInt(1,bookId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt("productid");
    }


    public int addBook (
        String title,
        List<String> authorIds,
        String description,
        int publisherId,
        int price,
        String isbn,
        String publicationDate,
        String imageUrl,
        String binding,
        int pageCount,
        double weight)throws SQLException{

        String sql;

        // SQL to add a new book to Books Table
        sql = "INSERT INTO Books (publisherid, isbn, title, publishdate, imageurl, pagecount, binding, weight, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        
        PreparedStatement addBooksPS = jdbcConnector.prepareStatement(sql);
        addBooksPS.setInt(1, publisherId);
        addBooksPS.setString(2, isbn);
        addBooksPS.setString(3, title);
        addBooksPS.setString(4, publicationDate);
        addBooksPS.setString(5, imageUrl);
        addBooksPS.setInt(6, pageCount);
        addBooksPS.setString(7, binding);
        addBooksPS.setDouble(8, weight);
        addBooksPS.setString(9, description);
        addBooksPS.executeUpdate();

        // Getting id of added book
        int bookId = jdbcConnector.getLastInsertId();
        
        // SQL To add link Authors <-> Book

        for (String authorId : authorIds){
            sql = "INSERT INTO Author_Book (authorid, bookid) VALUES (?,?)";
            PreparedStatement addAuthorBookPs = jdbcConnector.prepareStatement(sql);
            addAuthorBookPs.setInt(1, Integer.parseInt(authorId));
            addAuthorBookPs.setInt(2, bookId);
            addAuthorBookPs.executeUpdate();
        }

        // SQL To add new product
        int productId = productController.addProduct(1,1,bookId);

        // SQL to link Product <-> Book
        sql = "INSERT INTO Book_Product (bookid,productid) VALUES (?,?)";
        PreparedStatement addBookProduct = jdbcConnector.prepareStatement(sql);
        addBookProduct.setInt(1, bookId);
        addBookProduct.setInt(2, productId);
        addBookProduct.executeUpdate();


        return bookId;

    }
}
