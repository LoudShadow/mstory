package com.group1.mstory.view;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.group1.mstory.controller.AuthorController;
import com.group1.mstory.controller.BookController;
import com.group1.mstory.controller.IllustratorController;
import com.group1.mstory.controller.PublisherController;
import com.group1.mstory.objects.Author;

@Controller
public class AddPages {
    @Autowired
    AuthorController ac;
    @Autowired
    PublisherController pc;

    @Autowired
    BookController bc;

    @Autowired
    IllustratorController ic;



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

    @RequestMapping(value = "/adding/book", method = RequestMethod.POST)
    public String addBook( 
        @RequestParam("title") String title,
        @RequestParam("author") String authorId,
        @RequestParam("description") String description,
        @RequestParam("publisher") String publisherId,
        @RequestParam("price") String price,
        @RequestParam("isbn") String isbn,
        @RequestParam("publicationDate") String publicationDate,
        @RequestParam("imageURL") String imageUrl,
        @RequestParam("binding") String binding,
        @RequestParam("pageCount") String pageCount,
        @RequestParam("weight") String weight){
        bc.addBook(
            title,
            Arrays.asList(authorId.split("\\s*,\\s*")),
            description,
            Integer.parseInt(publisherId),
            Integer.parseInt(price),
            isbn,
            publicationDate,
            imageUrl,
            binding,
            Integer.parseInt(pageCount),
            Double.parseDouble(weight)
        );
        return "adding/index.html";
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
