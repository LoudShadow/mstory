package com.group1.mstory.view;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.group1.mstory.controller.AuthorController;
import com.group1.mstory.controller.PublisherController;
import com.group1.mstory.objects.Author;

@Controller
public class AddPages {
    @Autowired
    AuthorController ac;
    @Autowired
    PublisherController pc;

   @RequestMapping(value = "/adding" , method = RequestMethod.GET)
    public String newItems(){
        return "adding/index.html";
    } 

    @RequestMapping(value = "/adding/author" , method = RequestMethod.GET)
    public String newAuthor(){
        return "adding/author.html";
    }

    @RequestMapping(value = "/adding/book" , method = RequestMethod.GET)
    public String newBook(Model model){
        model.addAttribute("authors", ac.getAllAuthors());
        model.addAttribute("publishers", pc.getAllPublishers());

        return "adding/book.html";
    }

    @RequestMapping(value = "/adding/publisher" , method = RequestMethod.GET)
    public String newPublisher(){
        return "adding/publisher.html";
    }

    @RequestMapping(value = "/adding/illustrator" , method = RequestMethod.GET)
    public String newIllustrator(){
        return "adding/illustrator.html";
    }
}
