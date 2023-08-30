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
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;


    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
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
    public List<Post> Searching(String searchParam, Pageable pageable) {
        List<Post> post = postRepository.Searching(searchParam,pageable);
        return post ;
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
    public List<String> getAllAuthors() {
        List<String> author = postRepository.getAllAuthors();
        return author;
    }

    @Override
    public List<String> getAllTags() {
        List<String> tag = postRepository.getAllTags();
        return tag;
    }
    @Override
    public Page<Post> filterPostsByAuthorsAndTags(Set<String> authors, Set<String> tags,LocalDateTime startDate,LocalDateTime endDate, Pageable pageable) {
        return postRepository.filterPostsByAuthorsAndTags(authors, tags,startDate,endDate,pageable);
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

}