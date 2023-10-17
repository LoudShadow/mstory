package com.group1.mstory.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AddPages {
   @RequestMapping(value = "/adding" , method = RequestMethod.GET)
    public String newItems(){
        return "adding/index.html";
    } 

    @RequestMapping(value = "/adding/author" , method = RequestMethod.GET)
    public String newAuthor(){
        return "adding/author.html";
    }

    @RequestMapping(value = "/adding/book" , method = RequestMethod.GET)
    public String newBook(){
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
