package com.hxh.news.web.admin;

import com.hxh.news.po.News;
import com.hxh.news.po.User;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class NewController {

    private static final String INPUT = "admin/news-input";

    private static final String LIST = "admin/news";

    private static final String REDIRECT_LIST = "redirect:/admin/news";

    @Autowired
    private NewService newService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @RequestMapping("/news")
    public String news(@PageableDefault(size = 3,sort = {"updateTime"},direction = Sort.Direction.DESC)
                               Pageable pageable, NewQuery newQuery, Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("page",newService.listNew(pageable,newQuery));
        return LIST;

    }

    @PostMapping("/news/search")
    public String search(@PageableDefault(size = 3,sort = {"updateTime"},direction = Sort.Direction.DESC)
                               Pageable pageable, NewQuery newQuery, Model model){
        model.addAttribute("page",newService.listNew(pageable,newQuery));
        return "admin/news::newsList";

    }

    public void setTagAndType(Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
    }

    @GetMapping("/news/input")
    public String input(Model model){
        setTagAndType(model);
        model.addAttribute("news",new News());
        return INPUT;
    }

    @GetMapping("/news/{id}/toUpdate")
    public String toUpdate(@PathVariable Long id,Model model){
        setTagAndType(model);
        News news = newService.getNew(id);
        news.init();
        model.addAttribute("news",news);
        return INPUT;
    }

    @PostMapping("/news/add")
    public String post(News news, RedirectAttributes attributes, HttpSession session){
        news.setUser((User) session.getAttribute("user"));
        news.setType(typeService.getType(news.getType().getId()));
        news.setTags(tagService.listTag(news.getTagIds()));
        News news1;
        if(news.getId()==null){
            news1 = newService.saveNews(news);
        }else {
            news1 = newService.updateNew(news);
        }
        if(news1==null){
            attributes.addFlashAttribute("message","操作失败");
        }else{
            attributes.addFlashAttribute("message","操作成功");
        }
        return REDIRECT_LIST;
    }

    @GetMapping("news/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        newService.deleteNew(id);
        attributes.addFlashAttribute("message","删除成功");
        return REDIRECT_LIST;
    }

}
