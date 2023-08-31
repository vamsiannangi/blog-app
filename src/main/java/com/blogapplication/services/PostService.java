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
    List<Post> sortBy(String searchParam,Pageable pageable);
    Page<Post> findAll(Pageable pageable);
    Page<Post> searchPosts(String keyword, Pageable pageable);
    List<Post> findAllSortedBy(String sortParam, Sort sort);
    Set<String> getAllAuthors();
    Set<String> getAllTags();
    Page<Post> filterPostsByAuthorsAndTags(Set<String> authors, Set<String> tags,Pageable pageable);
    List<Post> findAllDraftPost();
    void updatePost(Post existingPost);
    Page<Post> findPublishedPosts(Pageable pageable);
    public List<Post> findAllPostsByUser(String username);
    Object findAllPostsForProfile(String username, String authorName);
    List<Post> findAllDraftPostByUser(String username);
    Page<Post> filterPostsByAuthorsTagsAndDates(Set<String> authors, Set<String> tags, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    Page<Post> filterPostsByAuthorsAndDates(Set<String> authors, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    Page<Post> filterPostsByTagsAndDates(Set<String> tags, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    Page<Post> filterPostsByAuthorsOrTagsOrDates(Set<String> authors, Set<String> tags, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    Page<Post> authorFilterOnSearch(String searchParam, Set<String> authors, Pageable pageable);

    Page<Post> tagsFilterOnSearch(String searchParam, Set<String> tags, Pageable pageable);

    Page<Post> dateFilterOnSearch(String searchParam,LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}