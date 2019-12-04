package com.ay.todo;

import javax.persistence.*;

@Entity
@Table(name = "Task")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false)
    private Long taskId;

    @Column(name = "TITLE")
    private String taskTitle;

    @Column(name = "DESCRIPTION")
    private String taskDescription;

    @Column(name = "STATE")
    @Enumerated(EnumType.STRING)
    private TaskState taskState;

    protected Todo(){}

    public Todo(String taskTitle, String description) {
        this.taskTitle = taskTitle;
        this.taskDescription = description;
        this.taskState = TaskState.TODO;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getTaskDescription() {
        return this.taskDescription;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public TaskState getTaskState() {
        return taskState;
    }

    public void setTaskState(TaskState taskState) {
        this.taskState = taskState;
    }
}

