package com.hxh.news.web;

import com.hxh.news.po.Tag;
import com.hxh.news.po.Type;
import com.hxh.news.service.NewService;
import com.hxh.news.service.TagService;
import com.hxh.news.service.TypeService;
import com.hxh.news.vo.NewQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TagShowController {
    @Autowired
    NewService newService;
    @Autowired
    TagService tagService;

    @GetMapping("/tags/{id}")
    public String types(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        @PathVariable Long id, Model model){
        List<Tag> tags = tagService.listTagTop(20);
        if(id==-1){
            id = tags.get(0).getId();
        }

        model.addAttribute("activeTypeId",id);
        model.addAttribute("tags",tags);
        model.addAttribute("page",newService.listNew(id,pageable));
        return "tags";
    }
}