package com.group1.mstory.view;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.group1.mstory.controller.OrderController;
import com.group1.mstory.controller.UserController;

@Controller
public class UserInteractions {
    
    @Autowired
    UserController userController;

    @Autowired
    OrderController orderController;


    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String login(){
        return "userInteractions/login.html";
    }

    @RequestMapping(value="/signup", method = RequestMethod.GET)
    public String signup(Model model){
        model.addAttribute("result", "");
        return "userInteractions/signup.html";
    }

    @RequestMapping(value="/signup", method=RequestMethod.POST)
    public String signup(
        @RequestParam("signupEmail") String email,
        @RequestParam("signupEmailConfirm") String confirmedEmail,
        @RequestParam("signupPassword") String password,
        @RequestParam("signupPasswordConfirm") String confirmedPassword,
        Model model
    ){

        System.out.println("\n\nEmail, confirmedEmail, password, confirmed password:");
        System.out.println(email);
        System.out.println(confirmedEmail);
        System.out.println(password);
        System.out.println(confirmedPassword);

        ArrayList<String> errors = new ArrayList<String>();
        model.addAttribute("errors", errors);


        if (!email.equals(confirmedEmail)){errors.add("Emails do not match");}
        if (!password.equals(confirmedPassword)){errors.add("Passwords do not match");}
        if (errors.size() > 0){return "userInteractions/signup.html";}
        
        if (userController.checkEmailExists(email)){
            errors.add("Email already exists");
            return "userInteractions/signup.html";
        }

        // Adding new user
        int newUserId = userController.addNewUser(email,password);
        if (newUserId == -1){
            errors.add("Error adding new user");
            return "userInteractions/signup.html";
        }

        // Adding new basket order
        int basketId = orderController.createBasket_returnBasketId(newUserId);
        if (basketId == -1){
            errors.add("Error adding new basket");
            return "userInteractions/signup.html";
        }

        // Adding link to user <-> basket
        boolean linkBasketUserSuccess = userController.updateBasketId(newUserId, basketId);
        if (linkBasketUserSuccess == false){
            errors.add("Error linking basket to user");
            return "userInteractions/signup.html";
        }
        return "userInteractions/login.html";
    }
}
