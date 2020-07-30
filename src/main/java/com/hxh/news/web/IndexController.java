package com.hxh.news.web;

import com.hxh.news.po.User;
import com.hxh.news.service.NewService;
import com.hxh.news.service.TagService;
import com.hxh.news.service.TypeService;
import com.hxh.news.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {

    @Autowired
    private NewService newService;

    @Autowired
    private TagService tagService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String index(@PageableDefault(size = 3,sort = {"updateTime"},direction = Sort.Direction.DESC)
                        Pageable pageable, Model model){
        model.addAttribute("page",newService.listNew(pageable));
        model.addAttribute("types",typeService.listTypeTop(3));
        return "index";
    }

}
