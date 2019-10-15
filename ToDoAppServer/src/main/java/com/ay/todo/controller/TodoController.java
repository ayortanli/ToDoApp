package com.ay.todo.controller;

import com.ay.todo.TaskState;
import com.ay.todo.Todo;
import com.ay.todo.service.TodoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {

    Logger logger = LoggerFactory.getLogger(TodoController.class);

    private TodoService service;

    @Autowired
    public TodoController(TodoService service) {
        this.service = service;
    }

    @GetMapping("/ping")
    public boolean ping(){
        return true;
    }

    @PostMapping("")
    public Todo insertNewTodo(@RequestBody Todo todo) {
        return service.insertTodo(todo.getTaskTitle(), todo.getTaskDescription());
    }

    @GetMapping("/{id}")
    public Todo findTodo(@PathVariable("id") long id) {
        return service.find(id);
    }

    @GetMapping("")
    public List<Todo> findAllTodo() {
        List<Todo> todoList = service.findAll();
        return todoList;
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable("id") long id) {
        service.deleteTodo(id);
    }

    @PutMapping(value = "/{id}")
    public Todo updateTodo(@PathVariable("id") long id, @RequestBody Todo todo) {
        return service.updateTodo(id, todo.getTaskTitle(), todo.getTaskDescription());
    }

    @PutMapping(value = "/state/{id}")
    public Todo updateTodoState(@PathVariable("id") long id, @RequestBody String state) {
        TaskState taskState = TaskState.valueOf(state);
        if(taskState!=null)
            return service.updateTodoState(id, taskState);
        //else validation error
        return null;
    }

    @PutMapping(value = "/archive")
    public List<Todo> archiveTodos(@RequestBody List<Long> todoIds) {
        return service.archiveTodos(todoIds);
    }
}
