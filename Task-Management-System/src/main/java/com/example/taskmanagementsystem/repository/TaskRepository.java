package com.example.taskmanagementsystem.repository;

import com.example.taskmanagementsystem.enums.Priority;
import com.example.taskmanagementsystem.enums.Status;
import com.example.taskmanagementsystem.model.Task;
import com.example.taskmanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatus(Status status);
    List<Task> findByPriority(Priority priority);
    List<Task> findByAssignedTo(User asignee);
    List<Task> findByCreatedBy(User creator);
    List<Task> findByDueDateBefore(LocalDate date);
    List<Task> findByStatusAndPriority(Status status,Priority priority);
}
