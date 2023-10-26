package com.group1.mstory.view;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.group1.mstory.controller.BasketController;
import com.group1.mstory.controller.BookController;
import com.group1.mstory.controller.OrderController;
import com.group1.mstory.controller.UserController;
import com.group1.mstory.login.UserDetailsImpl;
import com.group1.mstory.model.OrderSummary;
import com.group1.mstory.objects.Book;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class ShoppingCart {
    @Autowired
    BookController bookController;

    @Autowired
    BasketController basketController;

    @Autowired
    UserController userController;

    @Autowired
    OrderController orderController;

    @RequestMapping(value = "/cart/addBook", method = RequestMethod.GET)
    public String addItem(@RequestParam("id") String idParam, Model model,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ArrayList<Book> cart = new ArrayList<Book>();

        int userBasketId = userController.getUserBasketId(userDetails.getId());
        int id = Integer.parseInt(idParam);
        System.out.println("CurUserBasketId: " + userBasketId);

        basketController.addProductByProductId(userBasketId, Integer.parseInt(idParam));
        cart.add(bookController.getBookByProductId(id));

        model.addAttribute("shoppingCart", cart);
        return "shoppingCart/cartItem.html";
    }

    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    public String getCart(@RequestParam("id") String idParam, Model model,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        int userBasketId = userController.getUserBasketId(userDetails.getId());
        ArrayList<Book> cart = basketController.getBasketProductsFromOrderId(userBasketId);

        model.addAttribute("shoppingCart", cart);
        return "shoppingCart/cartItem.html";
    }

    @RequestMapping(value = "/cart/removeBook", method = RequestMethod.GET)
    public String removeBook(@RequestParam("id") String idParam, Model model,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        int userBasketId = userController.getUserBasketId(userDetails.getId());
        basketController.removeProductByProductId(userBasketId, Integer.parseInt(idParam));
        return "shoppingCart/blank.html";
    }


    @RequestMapping(value = "/checkoutPage", method = RequestMethod.GET)
    public String checkoutPage(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails){
        int userBasketId = userController.getUserBasketId(userDetails.getId());
        ArrayList<Book> books = basketController.getBasketProductsFromOrderId(userBasketId);
        System.out.println("Checkout: " + books.size());
        model.addAttribute("shoppingCart", books);
        return "shoppingCart/CheckoutCart.html";
    }

    @RequestMapping(value = "cart/checkout", method = RequestMethod.GET)
    public String checkout(Model model,HttpServletResponse response,@AuthenticationPrincipal UserDetailsImpl userDetails) throws SQLException {
        basketController.basketCheckout(userDetails.getId());
        response.addHeader("HX-Redirect", "/");
        return "shoppingCart/blank.html";
    }

    @RequestMapping(value = "/orderHistory", method = RequestMethod.GET)
    public String getOrderHistory(Model model,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ArrayList<OrderSummary> orderSummaries = orderController.getAllUserOrders(userDetails.getId());
        model.addAttribute("orderHistory", orderSummaries);
        return "orders/orderHistory.html";
    }
}
