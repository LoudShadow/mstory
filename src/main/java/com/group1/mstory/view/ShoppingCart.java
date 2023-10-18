package com.group1.mstory.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.group1.mstory.controller.BookController;
import com.group1.mstory.objects.Book;


@Controller
public class ShoppingCart {
    @RequestMapping(value = "/cart/addBook", method = RequestMethod.GET)
    public String addItem(@RequestParam("id") String idParam, Model model){
        int id = Integer.parseInt(idParam);
        Book b = BookController.getBookByBookId(id);

        model.addAttribute("book", b);
        return "shoppingCart/cartitem.html";
    }

    @RequestMapping(value = "/cart/removeBook", method = RequestMethod.GET)
    public String removeBook(@RequestParam("id") String idParam, Model model){

        return "shoppingCart/blank.html";
    }
}
