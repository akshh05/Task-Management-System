package com.example.taskmanagementsystem.model;

import com.example.taskmanagementsystem.enums.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Integer version=1;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false,length = 1000)
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status=Status.OPEN;
    @Enumerated(EnumType.STRING)
    private Priority priority= Priority.MEDIUM;

    @ManyToOne
    @JoinColumn(name="created_by",nullable = false)
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "assigned_to")
    private User assignedTo;

    private LocalDate dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy="task",cascade = CascadeType.ALL)
    private List<Comment> comments;

    @PrePersist
    protected void onCreate(){
        dueDate=LocalDate.now();
        createdAt=LocalDateTime.now();
        updatedAt=LocalDateTime.now();
    }
}
