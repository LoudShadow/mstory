package com.group1.mstory.view;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.group1.mstory.controller.BookController;
import com.group1.mstory.objects.Book;
import com.group1.mstory.controller.PublisherController;
import com.group1.mstory.controller.AuthorController;
import com.group1.mstory.controller.IllustratorController;

import com.group1.mstory.model.BookTile;

@Controller
public class CorePages {
    @Autowired
    AuthorController ac;

    @Autowired
    BookController bc;

    @Autowired
    PublisherController pc;

    @Autowired
    IllustratorController ic;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String index(Model model){
        ArrayList<BookTile> books = bc.getAllBookTiles();
        model.addAttribute("books", books);

        return "allBooks.html";
    }

    @RequestMapping(value = "/bookpage",method = RequestMethod.GET)
    public String page(Model model,@RequestParam("id") String idParam){
        int id = Integer.parseInt(idParam);
        Book b = bc.getBookByBookId(id);
        model.addAttribute("book", b);
        b.display();
        return "bookpage.html";
    }

    @RequestMapping(value = "/testBookPage", method = RequestMethod.GET)
    public String testBookPage(){
        return "bookpage.html";
    }

    @RequestMapping(value = "/addAuthor", method = RequestMethod.POST)
    public String addAuthor( @RequestParam("name") String name){
        ac.addAuthor(name);
        return "adding/index.html";
    }

    @RequestMapping(value = "/addPublisher", method = RequestMethod.POST)
    public String addPublisher( @RequestParam("name") String name){
        pc.addPublisher(name);
        return "adding/index.html";
    }

    @RequestMapping(value = "/addIllustrator", method = RequestMethod.POST)
    public String addIllustrator( @RequestParam("name") String name){
        ic.addIllustrator(name);
        return "adding/index.html";
    }
}
