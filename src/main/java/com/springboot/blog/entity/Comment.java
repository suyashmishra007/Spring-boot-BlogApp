package com.springboot.blog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(
        name = "comments"
)
public class Comment {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private long id;
    private String name, body, email;

    @ManyToOne(fetch = FetchType.LAZY) // We want to establish a bi-directional realationship between Post and Comment
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
}
/*
Many means Comments and one means post , So 1 post have multiple comments or multiple comments have 1 post.

FetchType.LAZY : tells hibernate to only fetch the related entites form the database when you use the relationship

Now we want to use JoinColoumn annotation, to specify foreign key.

 */
