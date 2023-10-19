package com.group1.mstory.view;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.group1.mstory.controller.BasketController;
import com.group1.mstory.controller.BookController;
import com.group1.mstory.controller.UserController;
import com.group1.mstory.login.UserDetailsImpl;
import com.group1.mstory.objects.Book;

@Controller
public class ShoppingCart {
    @Autowired
    BookController bookController;

    @Autowired
    BasketController basketController;

    @Autowired
    UserController userController;

    @RequestMapping(value = "/cart/addBook", method = RequestMethod.GET)
    public String addItem(@RequestParam("id") String idParam, Model model, @AuthenticationPrincipal UserDetailsImpl userDetails){
        ArrayList<Book> cart = new ArrayList<Book>();

        int userBasketId = userController.getUserBasketId(userDetails.getId());
        int id = Integer.parseInt(idParam);
        System.out.println("CurUserBasketId: " + userBasketId);

        basketController.addProductByProductId(userBasketId, Integer.parseInt(idParam));
        cart.add(bookController.getBookByProductId(id));

        model.addAttribute("shoppingCart", cart);
        return "shoppingCart/cartitem.html";
    }

    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    public String getCart(@RequestParam("id") String idParam, Model model, @AuthenticationPrincipal UserDetailsImpl userDetails){
        int userBasketId = userController.getUserBasketId(userDetails.getId());
        ArrayList<Book> cart = basketController.getBasketProductsFromOrderId(userBasketId);

        model.addAttribute("shoppingCart", cart);
        return "shoppingCart/cartitem.html";
    }

    @RequestMapping(value = "/cart/removeBook", method = RequestMethod.GET)
    public String removeBook(@RequestParam("id") String idParam, Model model, @AuthenticationPrincipal UserDetailsImpl userDetails){
        int userBasketId = userController.getUserBasketId(userDetails.getId());
        basketController.removeProductByProductId(userBasketId,Integer.parseInt(idParam));
        return "shoppingCart/blank.html";
    }
}
