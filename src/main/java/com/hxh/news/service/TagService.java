package com.hxh.news.service;

import com.hxh.news.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
//TagService.java
public interface TagService {
    Page<Tag> ListTag(Pageable pageable);

    Tag saveTag(Tag tag);

    void deleteTag(Long id);

    Tag getTagByName(String name);

    Tag getTag(Long id);

    Tag updateTag(Long id,Tag tag);

    List<Tag> listTag();

    List<Tag> listTag(String ids);
    //标签排行榜
    List<Tag> listTagTop(Integer size);
}
