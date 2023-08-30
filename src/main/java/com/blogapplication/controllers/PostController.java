package com.blogapplication.controllers;

import com.blogapplication.entity.Post;
import com.blogapplication.entity.Tag;
import com.blogapplication.entity.User;
import com.blogapplication.services.PostService;
import com.blogapplication.services.TagService;
import com.blogapplication.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@Controller
public class PostController {
    private PostService postService;
    private TagService tagService;
    private UserService userService;

    @Autowired
    public PostController(PostService postService, TagService tagService, UserService userService) {
        this.postService = postService;
        this.tagService = tagService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String homePage(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            @RequestParam(name = "search", required = false) String searchParam,
            @RequestParam(name = "sort", required = false) String sortParam,
            @RequestParam(name = "author", required = false) String[] selectedAuthors,
            @RequestParam(name = "tag", required = false) String[] selectedTags,
            @RequestParam(name = "startDate", required = false) LocalDateTime startDate,
            @RequestParam(name = "endDate", required = false) LocalDateTime endDate,
            Model model) {
        Set<String> authors = getSelectedValues(selectedAuthors);
        Set<String> tags = getSelectedValues(selectedTags);

        List<String> allAuthors = postService.getAllAuthors();
        List<String> allTags = postService.getAllTags();

        Pageable pageable = createPageable(page, size, sortParam);
        Page<Post> postsPage = getFilteredOrSearchedPosts(authors, tags, startDate, endDate, searchParam, pageable);
        addModelAttributes(model, postsPage, authors, tags, searchParam, sortParam, allAuthors, allTags, startDate, endDate);

        return "home";
    }

    private Set<String> getSelectedValues(String[] values) {
        return (values != null) ? new HashSet<>(Arrays.asList(values)) : new HashSet<>();
    }

    private Pageable createPageable(int page, int size, String sortParam) {
        Sort sort = (sortParam != null) ? Sort.by("publishedAt").ascending(): Sort.by("publishedAt").descending();
        return PageRequest.of(page, size, sort);
    }

    private Page<Post> getFilteredOrSearchedPosts(Set<String> authors, Set<String> tags, LocalDateTime startDate, LocalDateTime endDate, String searchParam, Pageable pageable) {
        if (!authors.isEmpty() || !tags.isEmpty() || startDate != null || endDate != null) {
            return postService.filterPostsByAuthorsAndTags(authors, tags, startDate, endDate, pageable);
        } else if (searchParam != null) {
            return postService.searchPosts(searchParam, pageable);
        }
        return postService.findPublishedPosts(pageable);
    }

    private void addModelAttributes(Model model, Page<Post> postsPage, Set<String> authors, Set<String> tags, String searchParam, String sortParam, List<String> allAuthors, List<String> allTags, LocalDateTime startDate, LocalDateTime endDate) {
        model.addAttribute("postsPage", postsPage);
        model.addAttribute("selectedAuthors", authors);
        model.addAttribute("selectedTags", tags);
        model.addAttribute("searchParam", searchParam);
        model.addAttribute("sortParam", sortParam);
        model.addAttribute("authors", allAuthors);
        model.addAttribute("tags", allTags);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByName(username);
        Post post = new Post();
        model.addAttribute("user", user);
        model.addAttribute("post", post);
        return "post-form";

    }

    @PostMapping("/save")
    public String savePost(@ModelAttribute("post") Post post, @RequestParam("tagsInput") String tagsInput,
                           @RequestParam("action") String action) {
        String[] tagNames = tagsInput.split(",");
        Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tagNames) {
            tagName = tagName.trim();
            if (!tagName.isEmpty()) {
                Tag tag = tagService.findOrCreateTag(tagName);
                tagSet.add(tag);
            }
        }
        List<Tag> tags = new ArrayList<>(tagSet);
        post.setTags(tags);
        if ("Published".equals(action)) {
            post.setPublished(true);
        } else if ("Save as Draft".equals(action)) {
            post.setPublished(false);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userService.findByName(username);
        post.setUser(user);
        post.setAuthor(user.getName());
        if (post.getId() != null) {

            Post existingPost = postService.findById(post.getId());

            existingPost.setTitle(post.getTitle());
            existingPost.setExcerpt(post.getExcerpt());
            existingPost.setContent(post.getContent());
            existingPost.setTags(post.getTags());
            existingPost.setPublished(post.isPublished());
            existingPost.setUpdatedAt(LocalDateTime.now());
            postService.updatePost(existingPost);
        } else {
            post.setCreatedAt(LocalDateTime.now());
            postService.save(post);
        }

//        if ("Published".equals(action)) {
//            return "redirect:/";
//        } else {
//            return "home";
//        }
        return "redirect:/";
    }

//    @RequestMapping("/draftPost")
//    public String draftPost(Model model) {
//        List<Post> draftPost = postService.findAllDraftPost();
//        model.addAttribute("posts", draftPost);
//        return "draft_post";
//    }

    @RequestMapping("/draftPost")
    public String draftPost(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Get the logged-in username

        List<Post> draftPosts = postService.findAllDraftPostByUser(username);
        model.addAttribute("posts", draftPosts);
        return "draft_post";
    }



    @GetMapping("/showPost")
    public String showPost(@RequestParam("postId") Long postId, Model model) {
        Post post = postService.findById(postId);
        model.addAttribute("post", post);
        return "view-post";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("postId") Long theId, Model model) {
        Post post = postService.findById(theId);
        model.addAttribute("post", post);
        String currentTags = "";
        List<Tag> tags = post.getTags();
        for (int i = 0; i < tags.size(); i++) {
            if (i > 0) {
                currentTags += ", ";
            }
            currentTags += tags.get(i).getName();
        }
        model.addAttribute("tagsInput", currentTags);
        return "post-form";
    }


    @GetMapping("/delete")
    public String delete(@RequestParam("postId") Long theId) {
        postService.deleteById(Math.toIntExact(theId));
        return "redirect:/";
    }

    @GetMapping("/search")
    public String searching(@RequestParam("search") String searchParam, Model model, Pageable pageable) {
        List<Post> posts = postService.Searching(searchParam, pageable);
        model.addAttribute("posts", posts);
        return "home";
    }

    @GetMapping("/sortByParam")
    public String sortByParam(@RequestParam("sort") String searchParam, Model model, Pageable pageable) {
        List<Post> posts = postService.sortBy(searchParam, pageable);
        model.addAttribute("posts", posts);
        return "home";
    }

   @GetMapping("/profile")
    public String viewProfile(Model model){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        User user=userService.findByName(userName);
        List<Post> myPosts=postService.findAllPostsByUser(userName);
        model.addAttribute("user",user);
        model.addAttribute("myPosts",myPosts);
        return "profile";
    }
}
