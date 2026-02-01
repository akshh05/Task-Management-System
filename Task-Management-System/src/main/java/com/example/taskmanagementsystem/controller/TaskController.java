package com.example.taskmanagementsystem.controller;

import com.example.taskmanagementsystem.enums.Priority;
import com.example.taskmanagementsystem.enums.Status;
import com.example.taskmanagementsystem.model.Task;
import com.example.taskmanagementsystem.model.User;
import com.example.taskmanagementsystem.service.TaskService;
import com.example.taskmanagementsystem.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;
    private final UserService userService;

    public TaskController(TaskService taskService,UserService userService){
        this.taskService=taskService;
        this.userService=userService;
    }

    @PostMapping
    public Task createTask(@RequestBody Task task, @RequestParam Long creatorId){
        return taskService.createTask(task,creatorId);
    }

    @GetMapping("/{id}")
    public Task getTask(@PathVariable Long id){
        return taskService.getTaskById(id);
    }

    @PatchMapping("/{id}/status")
    public Task updateStatus(@PathVariable Long id, @RequestParam Status status,@RequestParam Long userId){
        User user=userService.getUserById(userId);
        return taskService.updateStatus(id,status,user);
    }
    @PatchMapping("/{id}/assignee")
    public Task assignTask(@PathVariable Long id,@RequestParam Long assigneeId,@RequestParam Long performedBy){
        User performer=userService.getUserById(performedBy);
        return taskService.assignTask(id,assigneeId,performer);
    }

    @GetMapping("/search")
    public List<Task> searchTasks(@RequestParam(required = false) Status status, @RequestParam(required = false)Priority priority, @RequestParam(required = false)Long assigneeId, @RequestParam (required = false)Long creatorId){
        return taskService.searchTasks(status,priority,assigneeId,creatorId);
    }

}
