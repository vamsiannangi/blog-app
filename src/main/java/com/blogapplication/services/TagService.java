package com.blogapplication.services;

import com.blogapplication.entity.Tag;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public interface TagService {
    List<Tag> findAll();
    Tag findById(Long theId);
    void save(Tag tag);
    void deleteById(Long theId);

    Tag findOrCreateTag(String s);
}