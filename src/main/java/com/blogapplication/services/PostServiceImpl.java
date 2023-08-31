package com.blogapplication.services;

import com.blogapplication.entity.Post;
import com.blogapplication.entity.User;
import com.blogapplication.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;


@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;
    private UserService userService;
    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }
    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }
    @Override
    public Post findById(Long theId) {
        Optional<Post> result= postRepository.findById(theId);
        Post post = null;
        if(result.isPresent()){
            post = result.get();
        }
        else{
            throw new RuntimeException("Did not find employee id - " + theId);
        }
        return post;
    }

    @Override
    public void save(Post thePost) {
        thePost.setPublishedAt(LocalDateTime.now());
        postRepository.save(thePost);
    }

    @Override
    public void updatePost(Post post) {
        postRepository.save(post);
    }

    @Override
    public Page<Post> findPublishedPosts(Pageable pageable) {
        return postRepository.findPublishedPosts(pageable);
    }


    @Override
    public void deleteById(int theId) {
        postRepository.deleteById(theId);
    }


    @Override
    public List<Post> sortBy(String searchParam,Pageable pageable) {
        List<Post> post = postRepository.sortBy(searchParam,pageable);
        return post;
    }

    @Override
    public Page<Post> findAll(Pageable pageable) {
        return postRepository.findAll(pageable);
    }
    @Override
    public Page<Post> searchPosts(String keyword, Pageable pageable) {
        return postRepository.searchPosts(keyword, pageable);
    }

    @Override
    public List<Post> findAllSortedBy(String sortParam, Sort sort) {
        return postRepository.findAllSortedBy(sortParam, sort);
    }

    @Override
    public Set<String> getAllAuthors() {
        Set<String> author = postRepository.getAllAuthors();
        return author;
    }

    @Override
    public Set<String> getAllTags() {
        Set<String> tag = postRepository.getAllTags();
        return tag;
    }
    @Override
    public Page<Post> filterPostsByAuthorsOrTagsOrDates(Set<String> authors, Set<String> tags,LocalDateTime startDate,LocalDateTime endDate, Pageable pageable) {
        return postRepository.filterPostsByAuthorsOrTagsOrDates(authors, tags,startDate,endDate,pageable);
    }

    @Override
    public Page<Post> authorFilterOnSearch(String searchParam, Set<String> authors, Pageable pageable) {
        return postRepository.authorFilterOnSearch(searchParam,authors,pageable);
    }

    @Override
    public Page<Post> tagsFilterOnSearch(String searchParam, Set<String> tags, Pageable pageable) {
        return postRepository.tagsFilterOnSearch(searchParam,tags,pageable);
    }

    @Override
    public Page<Post> dateFilterOnSearch(String searchParam,LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return postRepository.dateFilterOnSearch(searchParam,startDate,endDate,pageable);
    }

    @Override
    public Page<Post> filterPostsByAuthorsAndTags(Set<String> authors, Set<String> tags, Pageable pageable) {
        return postRepository.filterPostsByAuthorsAndTags(authors,tags,pageable);
    }

    @Override
    public List<Post> findAllDraftPost() {
        return postRepository.findAllDraftPost();
    }


    @Override
    public List<Post> findAllPostsByUser(String username) {
        return postRepository.findAllByUser_Name(username); // Use the correct method name
    }


    @Override
    public List<Post> findAllPostsForProfile(String username, String author) {
        return postRepository.findAllByUser_NameAndAuthor(username, author);
    }

    public List<Post> findAllDraftPostByUser(String username) {
        User user = userService.findByName(username);
        return postRepository.findAllDraftPostsByUser(user);
    }

    @Override
    public Page<Post> filterPostsByAuthorsTagsAndDates(Set<String> authors, Set<String> tags, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return postRepository.filterPostsByAuthorsTagsAndDates(authors,tags,startDate,endDate,pageable);

    }

    @Override
    public Page<Post> filterPostsByAuthorsAndDates(Set<String> authors, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return postRepository.filterPostsByAuthorsAndDates(authors,startDate,endDate,pageable);
    }

    @Override
    public Page<Post> filterPostsByTagsAndDates(Set<String> tags, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return postRepository.filterPostsByTagsAndDates(tags,startDate,endDate,pageable);

    }


}