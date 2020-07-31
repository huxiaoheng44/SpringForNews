package com.hxh.news.service;

import com.hxh.news.po.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> listCommentByNewId(Long NewId);

    Comment saveComment(Comment comment);
}
