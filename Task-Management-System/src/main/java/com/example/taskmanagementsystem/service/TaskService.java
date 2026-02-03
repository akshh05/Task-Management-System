package com.example.taskmanagementsystem.service;

import com.example.taskmanagementsystem.enums.ActivityType;
import com.example.taskmanagementsystem.enums.Priority;
import com.example.taskmanagementsystem.enums.Status;
import com.example.taskmanagementsystem.model.Task;
import com.example.taskmanagementsystem.model.User;
import com.example.taskmanagementsystem.model.ActivityEvent;
import com.example.taskmanagementsystem.repository.ActivityEventRepository;
import com.example.taskmanagementsystem.repository.TaskRepository;
import com.example.taskmanagementsystem.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private UserRepository userRepository;
    private ActivityEventRepository activityEventRepository;

    public TaskService(TaskRepository taskRepository,UserRepository userRepository,ActivityEventRepository activityEventRepository){
        this.taskRepository=taskRepository;
        this.userRepository=userRepository;
        this.activityEventRepository=activityEventRepository;
    }

    public Task createTask(Task task, Long creatorId){
        User creator = userRepository.findById(creatorId)
                .orElseThrow(() -> new RuntimeException("Creator not found"));

        task.setCreatedBy(creator);
        task.setVersion(1);
        task.setStatus(Status.OPEN);
        task.setPriority(Priority.MEDIUM);
        task.setAssignedTo(null);

        Task savedTask = taskRepository.save(task);

        logActivity(savedTask.getId(), ActivityType.TASK_CREATED, creator, "Task created");
        return savedTask;
    }


    private void logActivity(Long taskId,ActivityType type,User user,String details){
        ActivityEvent event=new ActivityEvent();
        event.setTaskId(taskId);
        event.setActivityType(type);
        event.setPerformedBy(user);
        event.setDetails(details);
        activityEventRepository.save(event);
    }

    public Task getTaskById(Long id){
        return taskRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Task not found"));
    }

    public Task updateStatus(Long taskId,Status newStatus,User user){
        Task task=getTaskById(taskId);
        task.setStatus(newStatus);
        Task updated=taskRepository.save(task);
        logActivity(taskId,ActivityType.STATUS_CHANGED,user,"Status changed to "+newStatus);
        return updated;
    }

    public Task assignTask(Long taskId, Long userId, User performedBy){
        Task task = getTaskById(taskId);

        User assignee = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        task.setAssignedTo(assignee);

        Task updated = taskRepository.save(task);

        logActivity(
                taskId,
                ActivityType.ASSIGNEE_CHANGED,
                performedBy,
                "Assigned to " + assignee.getName()
        );

        return updated;
    }

    public List<Task> searchTasks(Status status, Priority priority, Long assigneeId, Long creatoeId){
        if (status != null && priority != null) {
            return taskRepository.findByStatusAndPriority(status,priority);
        }
        if(status!=null){
            return taskRepository.findByStatus(status);
        }
        if(priority!=null){
            return taskRepository.findByPriority(priority);
        }
        if(assigneeId!=null){
            User assignee=userRepository.findById(assigneeId)
                    .orElseThrow(()->new RuntimeException("Assignee not found"));
            return taskRepository.findByAssignedTo(assignee);
        }
        if(creatoeId!=null){
            User creator=userRepository.findById(assigneeId)
                    .orElseThrow(()->new RuntimeException("Creator not found"));
            return taskRepository.findByCreatedBy(creator);
        }
        return taskRepository.findAll();

    }

}
