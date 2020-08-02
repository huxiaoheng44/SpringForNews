package com.hxh.news.service.Impl;

import com.hxh.news.dao.NewRepository;
import com.hxh.news.po.News;
import com.hxh.news.service.NewService;
import com.hxh.news.utils.MarkdownUtils;
import com.hxh.news.vo.NewQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.awt.*;
import java.util.*;
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

    @Override
    public List<News> listRecommendNewsTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"updateTime");
        Pageable pageable = PageRequest.of(0,size,sort);
        return newRepository.findTop(pageable);
    }

    @Override
    public Page<News> listNew(String query, Pageable pageable) {
        return newRepository.findByQuery(query,pageable);
    }

    @Override
    public News getAndConvert(Long id) {
        News news = newRepository.findById(id).orElse(null);
        if(news==null){
            System.out.println("新闻不存在");
        }
        News news1 = new News();
        BeanUtils.copyProperties(news,news1);
        String content = news1.getContent();
        news1.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        return news1;
    }

    @Override
    public Page<News> listNew(Long tagId, Pageable pageable) {
        return newRepository.findAll(new Specification<News>() {
            @Override
            public Predicate toPredicate(Root<News> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Join join = root.join("tags");
                return criteriaBuilder.equal(join.get("id"),tagId);
            }
        },pageable);
    }

    @Override
    public Map<String, List<News>> archiveNews() {
        List<String> years = newRepository.findGroupYear();
        //LinkedHashMap是有序集合，会按照添加的顺序保存
        Map<String,List<News>> map = new LinkedHashMap<>();
        for(String year:years){
            map.put(year,newRepository.findByYear(year));
        }
        return map;

    }

    @Override
    public Long newCount() {
        return newRepository.count();
    }
}
