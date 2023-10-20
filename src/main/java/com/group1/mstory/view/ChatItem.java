package com.group1.mstory.view;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.group1.mstory.objects.Message;

@Controller
public class ChatItem {
    
    @RequestMapping(value = "/chat/template", method = RequestMethod.GET)
    public String chat(Model model){
        ArrayList<Message> messages = new ArrayList<Message>();
        messages.add(new Message(0, "Hello I am your friendly chat bot please ask me a question"));
        model.addAttribute("messages", messages);
        return "chat/chat.html";
    }

    @RequestMapping(value = "/chat", method = RequestMethod.GET)
    public String chat(@RequestParam("message") String message, Model model){
        ArrayList<Message> messages = new ArrayList<Message>();
        messages.add(new Message(1, message));
        messages.add(new Message(0, "I'm Sorry Dave, I'm Afraid I Can't Do That"));
        model.addAttribute("messages", messages);
        return "chat/chatItem.html";
    }

}
