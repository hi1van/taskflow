package com.example.taskflow.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Board {
    private String boardId;
    private String name;
    private String description;
    private String ownerId;
    private List<String> members;
    private Date createdAt;
}
