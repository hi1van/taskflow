package com.example.taskflow.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    private String taskId;
    private String boardId;
    private String title;
    private String description;
    private String status;
    private String assigneeId;
    private Date createdAt;
    private Date updatedAt;
}
