package com.blogapplication.repository;

import com.blogapplication.entity.Comments;
import com.blogapplication.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CommentRepository extends JpaRepository<Comments, Long> {

    Optional<Comments> findById(int theId);

    void deleteById(int theId);


}
