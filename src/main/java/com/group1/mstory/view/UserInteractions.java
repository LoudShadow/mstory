package com.group1.mstory.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
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
    public String signup(){
        return "userInteractions/signup.html";
    }

    @RequestMapping(value="/user/signup", method=RequestMethod.POST)
    public String signup(
        @RequestParam("signupEmail") String email,
        @RequestParam("signupEmailConfirm") String confirmedEmail,
        @RequestParam("signupPassword") String password,
        @RequestParam("signupPasswordConfirm") String confirmedPassword
    ){
        System.out.println(userController.checkEmailExists(email));
        if (!userController.checkEmailExists(email)){
            System.out.println("Does not exist?");
            System.out.println(email);
            System.out.println(confirmedEmail);
            System.out.println(password);
            System.out.println(confirmedPassword);

            if (!email.equals(confirmedEmail)){
                return "userInteractions/signup.html";
            }

            if (!password.equals(confirmedPassword)){
                return "userInteractions/signup.html";
            }

            int newUserId = userController.addNewUser(email,password);

            orderController.



            return "userInteractions/login.html";

        }

        return "userInteractions/signup.html";
    }
}
