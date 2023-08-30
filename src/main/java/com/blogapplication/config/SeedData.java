//package com.blogapplication.config;
//
////
////import com.blogapplication.entity.Post;
////import com.blogapplication.services.PostService;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.boot.CommandLineRunner;
////
////import java.util.List;
////
////public class SeedData implements CommandLineRunner {
////    @Autowired
////    private PostService postService;
////    @Override
////    public void run(String... args) throws Exception {
////        List<Post> posts=postService.getAll();
////        if(posts.size()==0){
////            Post post1=new Post();
////            post1.setTitle("title of post 1");
////            post1.setAuthor("Author1");
////            post1.getContent("This is the body of post 1");
////            post1.getExcerpt("Excerpt of post1");
////
////            Post post2=new Post();
////            post2.setTitle("title of post 1");
////            post2.setAuthor("Author1");
////            post2.getContent("This is the body of post 1");
////            post2.getExcerpt("Excerpt of post1");
////
////            postService.save(post1);
////            postService.save(post2);
////
////        }
////    }
////}
//
//
//import com.blogapplication.entity.Comment;
//import com.blogapplication.entity.Post;
//import com.blogapplication.services.CommentService;
//import com.blogapplication.services.PostService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//public class SeedData implements CommandLineRunner {
//
//    @Autowired
//    private PostService postService;
//
////    @Autowired
////    private AccountService accountService;
////
////    @Autowired
////    private AuthorityRepository authorityRepository;
////
////    @Autowired
////    private CommentService commentService;
//
//
//    @Override
//    public void run(String... args) throws Exception {
//        List<Post> posts = postService.getAll();
//
//        if (posts.size() == 0) {
//
////            Comment comment1=new Comment();
////
////            comment1.setComments("comment of post1");
////            comment1.setName("user");
//
//            Post post1 = new Post();
//            post1.setTitle("title of post 1");
//            post1.setAuthor("Author1");
//            post1.setContent("This is the body of post 1");
//            post1.setExcerpt("Excerpt of post1");
//
//            Post post2 = new Post();
//            post2.setTitle("title of post 2");
//            post2.setAuthor("Author2");
//            post2.setContent("This is the body of post 2");
//            post2.setExcerpt("Excerpt of post2");
//
////            Post post3 = new Post();
////            post3.setTitle("title of post 3");
////            post3.setAuthor("Author3");
////            post3.setContent("This is the body of post3");
////            post3.setExcerpt("Excerpt of post3");
//
//            postService.save(post1);
//            postService.save(post2);
////            postService.save(post3);
//
//        }
//    }
//
//}