package com.hxh.news.dao;

import com.hxh.news.po.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


//NewRepository.java
public interface NewRepository extends JpaRepository<News,Long>, JpaSpecificationExecutor<News> {
    //
    @Query("select n from News n where n.recommend=true")
    List<News> findTop(Pageable pageable);

    @Query("select n from News n where n.title like ?1 or n.content like ?1")
    Page<News> findByQuery(String query, Pageable pageable);

    //select date_format(n.update_time,'%Y') from t_news n group by date_format(n.update_time,'%Y') order by date_format(n.update_time,'%Y')desc
//    @Query("select function('data_format',n.updateTime,'%Y') as year from News n group by year order by year desc")
//    List<String> findGroupYear();
    @Query("select function('date_format',n.updateTime,'%Y') as year from News n group by year order by year desc ")
    List<String> findGroupYear();

    @Query("select n from News n where function('date_format',n.updateTime,'%Y') = ?1 ")
    List<News> findByYear(String year);


}