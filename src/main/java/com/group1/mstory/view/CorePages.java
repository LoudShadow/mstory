package com.group1.mstory.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
}
