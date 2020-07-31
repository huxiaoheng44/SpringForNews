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
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/")
    public String index(@PageableDefault(size = 3,sort = {"updateTime"},direction = Sort.Direction.DESC)
                        Pageable pageable, Model model){
        //主页新闻
        model.addAttribute("page",newService.listNew(pageable));
        //分类排行榜
        model.addAttribute("types",typeService.listTypeTop(3));
        //标签排行榜
        model.addAttribute("tags",tagService.listTagTop(3));
        //推荐新闻
        model.addAttribute("recommendNews",newService.listRecommendNewsTop(3));
        return "index";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = 3,sort = {"updateTime"},direction = Sort.Direction.DESC)
                                 Pageable pageable, Model model,@RequestParam String query) {
        model.addAttribute("page",newService.listNew("%"+query+"%",pageable));
        model.addAttribute("query",query);
        return "search";
    }

    @RequestMapping("/news/{id}")
    public String news(@PathVariable Long id,Model model){
        model.addAttribute("news",newService.getAndConvert(id));
        return "new";
    }

}
