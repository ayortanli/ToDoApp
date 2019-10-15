package com.ay.todo;

public class Todo {
    private Long taskId;
    private String taskTitle;
    private String taskDescription;
    private TaskState taskState;

    public Todo(){}

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

