package com.my.chatgpttask.repository;

import com.my.chatgpttask.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Post getPostById(Long id);

    List<Post> getPostsByAuthorId(Long authorId);
}
