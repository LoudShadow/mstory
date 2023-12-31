package com.group1.mstory.view;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.group1.mstory.controller.BookController;
import com.group1.mstory.controller.FlanController;
import com.group1.mstory.objects.Book;
import com.group1.mstory.controller.PublisherController;
import com.group1.mstory.controller.AuthorController;
import com.group1.mstory.controller.BasketController;
import com.group1.mstory.controller.IllustratorController;
import com.group1.mstory.model.BookTile;

@Controller
public class CorePages {
    @Autowired
    AuthorController authorController;

    @Autowired
    BookController bookController;

    @Autowired
    PublisherController publisherController;

    @Autowired
    IllustratorController illustratorController;

    @Autowired
    BasketController basketController;

    @Autowired
    FlanController flanController;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String index(Model model){
        List<BookTile> books = bookController.getAllBookTiles();
        model.addAttribute("books", books);

        return "allBooks.html";
    }

    @RequestMapping(value = "/bookpage",method = RequestMethod.GET)
    public String page(Model model,@RequestParam("id") String idParam) throws SQLException{
        int id = Integer.parseInt(idParam);
        Book b = bookController.getBookByBookId(id);
        // convert b.product.price to dollars from cents
        float price = ((float) b.getProduct().getPrice()) / 100;
        model.addAttribute("book", b);
        model.addAttribute("price", price);
        return "bookpage.html";
    }

    @RequestMapping(value = "/testBookPage", method = RequestMethod.GET)
    public String testBookPage(){
        return "shoppingCart/blank.html";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String searchAllBooks(Model model, @RequestParam("search") String search){
        List<BookTile> books = bookController.getTilesSearch(search);
        model.addAttribute("books", books);
        return "allBooks.html";
    }
    


        
}
