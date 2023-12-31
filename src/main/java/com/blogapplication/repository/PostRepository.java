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
    @Query("SELECT p FROM Post p WHERE p.isPublished = true AND (" +
            "LOWER(p.title) LIKE LOWER(concat('%', :keyword, '%')) " +
            "OR LOWER(p.content) LIKE LOWER(concat('%', :keyword, '%')) " +
            "OR LOWER(p.author) LIKE LOWER(concat('%', :keyword, '%')) " +
            "OR EXISTS (SELECT t FROM p.tags t WHERE LOWER(t.name) LIKE LOWER(concat('%', :keyword, '%'))))")
    Page<Post> searchPosts(@Param("keyword") String keyword, Pageable pageable);

    List<Post> findAllSortedBy(String sortParam, Sort sort);

    @Query("SELECT p FROM Post p ORDER BY "
            + "CASE WHEN :param = 'title' THEN p.title END DESC, "
            + "CASE WHEN :param = 'publishedAt' THEN p.publishedAt END DESC, "
            + "CASE WHEN :param = 'author' THEN p.author END DESC")
    List<Post> sortBy(@Param("param") String searchParam,Pageable pageable);

    @Query("SELECT DISTINCT p.author FROM Post p")
    Set<String> getAllAuthors();

    @Query("SELECT DISTINCT t.name FROM Tag t")
    Set<String> getAllTags();

    @Query("SELECT p FROM Post p WHERE p.isPublished = true AND (p.author IN :authors OR " +
            "EXISTS (SELECT t FROM p.tags t where t.name IN :tags) OR p.publishedAt BETWEEN :startDate AND :endDate)")
    Page<Post> filterPostsByAuthorsOrTagsOrDates(@Param("authors") Set<String> authors, @Param("tags") Set<String> tags,
                                                 @Param("startDate") LocalDateTime startDate , @Param("endDate") LocalDateTime endDate , Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.isPublished = true AND (p.author IN :authors AND " +
            "EXISTS (SELECT t FROM p.tags t where t.name IN :tags) AND p.publishedAt BETWEEN :startDate AND :endDate)")
    Page<Post> filterPostsByAuthorsTagsAndDates(@Param("authors") Set<String> authors, @Param("tags") Set<String> tags,
                                                @Param("startDate") LocalDateTime startDate , @Param("endDate") LocalDateTime endDate , Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.isPublished = true AND (p.author IN :authors AND " +
            "EXISTS (SELECT t FROM p.tags t where t.name IN :tags))")
    Page<Post> filterPostsByAuthorsAndTags(@Param("authors") Set<String> authors, @Param("tags") Set<String> tags , Pageable pageable);


    @Query("SELECT p FROM Post p WHERE p.isPublished = false")
    List<Post> findAllDraftPost();


    @Query("SELECT p FROM Post p WHERE p.isPublished = true")
    Page<Post> findPublishedPosts(Pageable pageable);


    List<Post> findAllByUser_Name(String name);


    List<Post> findAllByUser_NameAndAuthor(String username, String author);

    @Query("SELECT p FROM Post p WHERE p.user = :user AND p.isPublished = false")
    List<Post> findAllDraftPostsByUser(@Param("user") User user);

    @Query("SELECT p FROM Post p WHERE p.isPublished = true AND (p.author IN :authors AND " +
            "p.publishedAt BETWEEN :startDate AND :endDate)")
    Page<Post> filterPostsByAuthorsAndDates(Set<String> authors, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.isPublished = true AND (" +
            "EXISTS (SELECT t FROM p.tags t where t.name IN :tags) AND p.publishedAt BETWEEN :startDate AND :endDate)")
    Page<Post> filterPostsByTagsAndDates(Set<String> tags, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);



    @Query("SELECT p FROM Post p WHERE p.isPublished = true AND (" +
            "LOWER(p.title) LIKE LOWER(concat('%', :keyword, '%')) " +
            "OR LOWER(p.content) LIKE LOWER(concat('%', :keyword, '%')) " +
            "OR LOWER(p.author) LIKE LOWER(concat('%', :keyword, '%'))" +
            "OR EXISTS (SELECT t FROM p.tags t WHERE LOWER(t.name) LIKE LOWER(concat('%', :keyword, '%')))) AND p.author IN :authors")
    Page<Post> authorFilterOnSearch(@Param("keyword") String keyword, Set<String> authors, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.isPublished = true AND (" +
            "LOWER(p.title) LIKE LOWER(concat('%', :keyword, '%')) " +
            "OR LOWER(p.content) LIKE LOWER(concat('%', :keyword, '%')) " +
            "OR LOWER(p.author) LIKE LOWER(concat('%', :keyword, '%'))" +
            "OR EXISTS (SELECT t FROM p.tags t WHERE LOWER(t.name) LIKE LOWER(concat('%', :keyword, '%')))) AND EXISTS (SELECT t FROM p.tags t where t.name IN :tags)")
    Page<Post> tagsFilterOnSearch(@Param("keyword") String keyword, Set<String> tags, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.isPublished = true AND (" +
            "LOWER(p.title) LIKE LOWER(concat('%', :keyword, '%')) " +
            "OR LOWER(p.content) LIKE LOWER(concat('%', :keyword, '%')) " +
            "OR LOWER(p.author) LIKE LOWER(concat('%', :keyword, '%'))" +
            "OR EXISTS (SELECT t FROM p.tags t WHERE LOWER(t.name) LIKE LOWER(concat('%', :keyword, '%')))) AND p.publishedAt BETWEEN :startDate AND :endDate")
    Page<Post> dateFilterOnSearch(@Param("keyword") String keyword, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}