package com.blogapplication.repository;

import com.blogapplication.entity.Post;
import com.blogapplication.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {
    Optional<Tag> findById(Long theId);

    void deleteById(Long theId);

    Optional<Tag> findByName(String tagName);
}