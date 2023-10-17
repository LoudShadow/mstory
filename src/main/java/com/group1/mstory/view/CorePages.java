package com.group1.mstory.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.group1.mstory.controller.BookController;

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

    @RequestMapping(value = "/testConnection", method = RequestMethod.GET)
    public String testConnection(){
        BookController bc = new BookController();
        bc.getAllBookTiles();
        return "bookpage.html";
    }
}
