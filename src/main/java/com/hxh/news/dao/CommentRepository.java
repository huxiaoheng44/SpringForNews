package com.hxh.news.dao;

import com.hxh.news.po.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
//CommentRepository.java
public interface CommentRepository extends JpaRepository<Comment,Long> {
    //符合有NewsId并且父级评论为空的评论（也就是顶级的评论
    List<Comment> findByNewsIdAndParentCommentNull(Long newId, Sort sort);
}
