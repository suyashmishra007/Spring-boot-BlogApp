package com.springboot.blog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Lambok :  will set setter and getter , hascode , toString at runtime.
@AllArgsConstructor // Generate all-args constructor.
@NoArgsConstructor // when we create a JPA Entity with an argument constructor then which should also need a no argument. constructor , because Hibernate internally use proxies to create objects.
@Entity
@Table(
        name = "posts",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})}
)
public class Post {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private long id ;

    @Column(name = "title" , nullable = false)
    private String title;

    @Column(name = "description" , nullable = false)
    private String description;

    @Column(name = "content" , nullable = false)
    private String content;

}
