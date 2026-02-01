package com.example.taskmanagementsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id",nullable=false)
    private Task task;

    @ManyToOne
    @JoinColumn(name = "author_id",nullable=false)
    private User author;

    @Column(nullable = false,length=500)
    private String text;

    private LocalDateTime timestamp;

    @PrePersist
    protected void onCreate(){
        timestamp=LocalDateTime.now();
    }
}
