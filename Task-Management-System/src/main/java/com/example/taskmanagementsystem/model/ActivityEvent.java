package com.example.taskmanagementsystem.model;

import com.example.taskmanagementsystem.enums.ActivityType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="activity_events")
public class ActivityEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false)
    private Long taskId;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActivityType activityType;
    @ManyToOne
    @JoinColumn(name = "performed_by",nullable = false)
    private User performedBy;
    private LocalDateTime timestamp;
    private String details;
    @PrePersist
    protected void onCreate(){
        timestamp=LocalDateTime.now();
    }
}
