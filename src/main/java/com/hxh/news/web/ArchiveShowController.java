package com.hxh.news.web;

import com.hxh.news.service.NewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ArchiveShowController {
    @Autowired
    NewService newService;

    @GetMapping("/archives")
    public String archives(Model model){
        model.addAttribute("archiveMap",newService.archiveNews());
        model.addAttribute("newsCount",newService.newCount());
        return "archives";
    }
}
