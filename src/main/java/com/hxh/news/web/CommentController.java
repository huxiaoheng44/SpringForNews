package com.hxh.news.web;

import com.hxh.news.po.Comment;
import com.hxh.news.po.User;
import com.hxh.news.service.CommentService;
import com.hxh.news.service.NewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
//CommentController.java
@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private NewService newService;

    private String avatar = "http://bbsfiles.vivo.com.cn/vivobbs/attachment/forum/201610/10/223520gj6otfv9t51t9oi9.jpg";
    @PostMapping("/comments")
    private String post(Comment comment, HttpSession session){
        Long newid = comment.getNews().getId();
        comment.setNews(newService.getNew(newid));
        User user = (User) session.getAttribute("user");
        if(user!=null){
            comment.setAdminComment(true);
            comment.setAvatar(avatar);
        }else {
            comment.setAvatar(avatar);
        }
        commentService.saveComment(comment);
        return "redirect:/comments/"+newid;

    }
    @GetMapping("/comments/{newId}")
    private String comments (@PathVariable Long newId, Model model){
        model.addAttribute("comments",commentService.listCommentByNewId(newId));
        return "new::commentList";
    }
}
