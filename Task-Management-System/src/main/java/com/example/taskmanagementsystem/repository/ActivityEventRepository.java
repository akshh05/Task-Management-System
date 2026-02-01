package com.example.taskmanagementsystem.repository;

import com.example.taskmanagementsystem.model.ActivityEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ActivityEventRepository extends JpaRepository<ActivityEvent, Long> {
}
