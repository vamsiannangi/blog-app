package com.blogapplication.repository;

import com.blogapplication.entity.Post;
import com.blogapplication.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {

    Optional<Post> findById(Long theId);

    @Query("SELECT DISTINCT p FROM Post p " +
            "LEFT JOIN p.tags t " +
            "WHERE p.title LIKE %:param% " +
            "OR p.content LIKE %:param% " +
            "OR p.author LIKE %:param% " +
            "OR t.name LIKE %:param%")
    List<Post> Searching(@Param("param") String query,Pageable pageable);

//    @Query("SELECT p FROM Post p WHERE p.isPublished = true AND lower(p.title) LIKE lower(concat('%', :keyword, '%')) " +
//            "OR lower(p.content) LIKE lower(concat('%', :keyword, '%'))"+
//            "OR lower(p.author) LIKE lower(concat('%', :keyword, '%'))")
//    Page<Post> searchPosts(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.isPublished = true AND (" +
            "LOWER(p.title) LIKE LOWER(concat('%', :keyword, '%')) " +
            "OR LOWER(p.content) LIKE LOWER(concat('%', :keyword, '%')) " +
            "OR LOWER(p.author) LIKE LOWER(concat('%', :keyword, '%')))")
    Page<Post> searchPosts(@Param("keyword") String keyword, Pageable pageable);

    List<Post> findAllSortedBy(String sortParam, Sort sort);

    @Query("SELECT p FROM Post p ORDER BY "
            + "CASE WHEN :param = 'title' THEN p.title END DESC, "
            + "CASE WHEN :param = 'publishedAt' THEN p.publishedAt END DESC, "
            + "CASE WHEN :param = 'author' THEN p.author END DESC")
    List<Post> sortBy(@Param("param") String searchParam,Pageable pageable);

    @Query("SELECT DISTINCT p.author FROM Post p")
    List<String> getAllAuthors();

    @Query("SELECT DISTINCT t.name FROM Tag t")
    List<String> getAllTags();

    @Query("SELECT p FROM Post p WHERE p.isPublished = true AND (p.author IN :authors OR " +
            "EXISTS (SELECT t FROM p.tags t where t.name IN :tags) OR p.publishedAt BETWEEN :startDate AND :endDate)")
    Page<Post> filterPostsByAuthorsAndTags(@Param("authors") Set<String> authors, @Param("tags") Set<String> tags,
                                           @Param("startDate") LocalDateTime startDate , @Param("endDate") LocalDateTime endDate , Pageable pageable);
    @Query("SELECT p FROM Post p WHERE p.isPublished = false")
    List<Post> findAllDraftPost();


    @Query("SELECT p FROM Post p WHERE p.isPublished = true")
    Page<Post> findPublishedPosts(Pageable pageable);


    List<Post> findAllByUser_Name(String name); // Use the correct property name


    List<Post> findAllByUser_NameAndAuthor(String username, String author);

    @Query("SELECT p FROM Post p WHERE p.user = :user AND p.isPublished = false")
    List<Post> findAllDraftPostsByUser(@Param("user") User user);


}