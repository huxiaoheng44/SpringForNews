package com.hxh.news.service.Impl;

import com.hxh.news.dao.NewRepository;
import com.hxh.news.po.News;
import com.hxh.news.service.NewService;
import com.hxh.news.vo.NewQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//NewServiceImpl.java
@Service
public class NewServiceImpl implements NewService {

    @Autowired
    private NewRepository newRepository;

    //新闻管理条件查询显示
    @Override
    public Page<News> listNew(Pageable pageable, NewQuery newQuery) {
        return newRepository.findAll(new Specification<News>() {
            @Override
            public Predicate toPredicate(Root<News> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                //是否存在标题检索
                if(!"".equals(newQuery.getTitle())&&newQuery.getTitle()!=null){
                    predicateList.add(criteriaBuilder.like(root.<String>get("title"),"%"+newQuery.getTitle()+"%"));
                }
                //是否分类检索
                if(newQuery.getTypeId()!=null){
                    predicateList.add(criteriaBuilder.equal(root.get("type").get("id"),newQuery.getTypeId()));
                }
                //是否推荐
                if(newQuery.isRecommend()){
                    predicateList.add(criteriaBuilder.equal(root.get("recommend"),newQuery.isRecommend()));
                }
                criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
                return null;
            }
        }, (org.springframework.data.domain.Pageable) pageable);
    }

    @Override
    public News saveNews(News news) {
        if(news.getId()==null){
            news.setCreateTime(new Date());
            news.setUpdateTime(new Date());
        }
        return newRepository.save(news);
    }

    @Override
    public News getNew(Long id) {
        return newRepository.findById(id).orElse(null);
    }

    @Override
    public News updateNew(News news) {
        News news1 = newRepository.findById(news.getId()).orElse(null);
        if(news1==null){
            System.out.println("未获取更新对象");
        }
        BeanUtils.copyProperties(news,news1);
        news1.setUpdateTime(new Date());
        return newRepository.save(news1);
    }

    @Override
    public void deleteNew(Long id) {
        newRepository.deleteById(id);
    }

    @Override
    public Page<News> listNew(Pageable pageable) {
        return newRepository.findAll(pageable);
    }
}
