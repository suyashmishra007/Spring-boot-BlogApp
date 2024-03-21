package com.springboot.blog.repository;

import com.springboot.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository<Post,Long> : Post Entity , Long because we use 'long' for id.
// We don't need @Repository and @Transaction(read=true) annotation , to this interface  because the JpaRepository interface has an implementation class called SimpleJpaRepository and it internally annoted with @Repositroy annotation and @Transaction annotation.
public interface PostRepository extends JpaRepository<Post,Long>{
    // No Code
    // JpaRepository : internally provides all the CRUD database operation methods and its implementation for us.
}
