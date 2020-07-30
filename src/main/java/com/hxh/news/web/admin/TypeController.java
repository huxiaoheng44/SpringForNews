package com.hxh.news.web.admin;

import com.hxh.news.dao.TypeRepository;
import com.hxh.news.po.Type;
import com.hxh.news.service.TypeService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

//TypeController.java
@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @RequestMapping("/types")
    public String type(@PageableDefault(size = 3,sort = {"id"},direction = Sort.Direction.DESC)
                       Pageable pageable, Model model){
        model.addAttribute("page",typeService.listType(pageable));
        return "admin/types";
    }

    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "admin/types-input";
    }

    @PostMapping("/types/add")
    public String add(@Valid Type type, BindingResult result, RedirectAttributes attributes){
        System.out.println(type);
        Type type1 = typeService.getTypeByName(type.getName());
        System.out.println(type1);
        if(type1!=null){
            result.rejectValue("name","nameError","不能添加相同的分类");
        }
        if(result.hasErrors()){
            return "admin/types-input";
        }
        Type type2 = typeService.saveType(type);
        System.out.println(type2);
        if(type2==null){
            attributes.addFlashAttribute("message","新增失败");
        }else{
            attributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/admin/types";
    }

    @RequestMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        typeService.delete(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";
    }

    //跳转到更新界面
    @RequestMapping("/types/{id}/toUpdate")
    public String toUpdate(@PathVariable Long id, Model model){
        model.addAttribute("type",typeService.getType(id));
        return "admin/types-input";
    }

    //更新信息
    @RequestMapping("/types/update/{id}")
    public String update(Type type,@PathVariable Long id,RedirectAttributes attributes,BindingResult result){
        Type type1 = typeService.getTypeByName(type.getName());
        if(type1!=null){
            result.rejectValue("name","nameError","不能添加重复的类");
        }
        if(result.hasErrors()){
            return "admin/types-input";
        }
        Type type2 = typeService.updateType(id,type);
        if(type2!=null){
            attributes.addFlashAttribute("message","更新成功");
        }else{
            attributes.addFlashAttribute("message","更新失败");
        }
        return "redirect:/admin/types";
    }
}
