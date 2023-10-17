package com.group1.mstory.view;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.group1.mstory.controller.BookController;
import com.group1.mstory.model.BookTile;

@Controller
public class CorePages {
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String index(){
        return "index.html";
    }
    @RequestMapping(value = "/bookpage",method = RequestMethod.GET)
    public String page(){
        return "bookpage.html";
    }

    @RequestMapping(value = "/testBookPage", method = RequestMethod.GET)
    public String testBookPage(){
        ArrayList<BookTile> bookTiles = BookController.getAllBookTiles();

        

        return "bookpage.html";
    }
}
