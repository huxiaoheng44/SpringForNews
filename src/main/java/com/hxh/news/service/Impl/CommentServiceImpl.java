package com.hxh.news.service.Impl;

import com.hxh.news.dao.CommentRepository;
import com.hxh.news.po.Comment;
import com.hxh.news.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
//CommentServiceImpl.java
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> listCommentByNewId(Long newId) {
        Sort sort = Sort.by("createTime");
        List<Comment> comments = commentRepository.findByNewsIdAndParentCommentNull(newId,sort);
        return eachComment(comments);
    }

    private List<Comment> eachComment(List<Comment> comments){
        //复制comments，防止破坏原始数据
        List<Comment> commentsView = new ArrayList<>();
        for(Comment comment:comments){
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);
            commentsView.add(c);
        }
        //合并评论的各层子代到第一级子代中
        combineChildren(commentsView);
        return commentsView;
    }

    private void combineChildren(List<Comment> comments){
        for(Comment comment:comments){
            List<Comment> replyComments = comment.getReplyComments();
            for(Comment reply:replyComments){
                //循环算法，找出子代,存入临时存放区tempReplys
                recursively(reply);
            }
            comment.setReplyComments(tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }

    private List<Comment> tempReplys = new ArrayList<>();

    private void recursively(Comment comment){
        tempReplys.add(comment);
        if(comment.getReplyComments().size()>0){
            List<Comment> replys = comment.getReplyComments();
            for(Comment reply:replys){
                tempReplys.add(reply);
                if(reply.getReplyComments().size()>0){
                    recursively(reply);
                }
            }
        }
    }
    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        if(parentCommentId != -1){
            comment.setParentComment(commentRepository.findById(parentCommentId).orElse(null));
        }else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }
}
