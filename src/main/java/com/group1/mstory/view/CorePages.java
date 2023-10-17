package com.group1.mstory.view;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.group1.mstory.controller.BookController;
import com.group1.mstory.model.BookTile;

@Controller
public class CorePages {
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String index(Model model){
        ArrayList<String> authors = new ArrayList<String>();
        authors.add("J.R.R. Tolkien");
        ArrayList<BookTile> books = new ArrayList<BookTile>();
        books.add(new BookTile("The Hobbit", authors, "A book about a hobbit", 1, "1234567890", 10, "https://upload.wikimedia.org/wikipedia/en/4/4a/TheHobbit_FirstEdition.jpg"));
        books.add(new BookTile("The Hobbit", authors, "A book about a hobbit", 1, "1234567890", 10, "https://upload.wikimedia.org/wikipedia/en/4/4a/TheHobbit_FirstEdition.jpg"));

        model.addAttribute("books", books);

        return "allBooks.html";
    }
    @RequestMapping(value = "/bookpage",method = RequestMethod.GET)
    public String page(){
        return "bookpage.html";
    }

    @RequestMapping(value = "/testConnection", method = RequestMethod.GET)
    public String testConnection(){
        BookController bc = new BookController();
        bc.getAllBookTiles();
        return "bookpage.html";
    }
}