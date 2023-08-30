package com.blogapplication.services;

import com.blogapplication.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service

public interface PostService {
    List<Post> findAll();

    Post findById(Long theId);
    

    void save(Post thePost);

    void deleteById(int theId);
    List<Post> Searching(String searchParam, Pageable pageable);

    List<Post> sortBy(String searchParam,Pageable pageable);
    Page<Post> findAll(Pageable pageable);
    Page<Post> searchPosts(String keyword, Pageable pageable);
    List<Post> findAllSortedBy(String sortParam, Sort sort);
    List<String> getAllAuthors();

    List<String> getAllTags();
    Page<Post> filterPostsByAuthorsAndTags(Set<String> authors, Set<String> tags,LocalDateTime startDate,LocalDateTime endDate, Pageable pageable);

    List<Post> findAllDraftPost();

    void updatePost(Post existingPost);

    Page<Post> findPublishedPosts(Pageable pageable);


    public List<Post> findAllPostsByUser(String username);

    Object findAllPostsForProfile(String username, String authorName);

    List<Post> findAllDraftPostByUser(String username);
}