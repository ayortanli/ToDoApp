package com.ay.todo.controller;

import com.ay.todo.TaskState;
import com.ay.todo.Todo;
import com.ay.todo.service.TodoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
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
        logger.debug("ping call");
        return true;
    }

    @GetMapping("/{id}")
    @RolesAllowed("ROLE_TASK_READ")
    public Todo findTodo(@PathVariable("id") long id) {
        return service.find(id);
    }

    @GetMapping("")
    @RolesAllowed("ROLE_TASK_READ")
    public List<Todo> findAllTodo() {
        List<Todo> todoList = service.findAll();
        return todoList;
    }

    @PostMapping("")
    @RolesAllowed("ROLE_TASK_MODIFY")
    public Todo insertNewTodo(@RequestBody Todo todo) {
        return service.insertTodo(todo.getTaskTitle(), todo.getTaskDescription());
    }

    @DeleteMapping("/{id}")
    @RolesAllowed("ROLE_TASK_MODIFY")
    public void deleteTodo(@PathVariable("id") long id) {
        service.deleteTodo(id);
    }

    @PutMapping(value = "/{id}")
    @RolesAllowed("ROLE_TASK_MODIFY")
    public Todo updateTodo(@PathVariable("id") long id, @RequestBody Todo todo) {
        return service.updateTodo(id, todo.getTaskTitle(), todo.getTaskDescription());
    }

    @PutMapping(value = "/state/{id}")
    @RolesAllowed("ROLE_TASK_MODIFY")
    public Todo updateTodoState(@PathVariable("id") long id, @RequestBody String state) {
        TaskState taskState = TaskState.valueOf(state);
        if(taskState!=null)
            return service.updateTodoState(id, taskState);
        //else validation error
        return null;
    }

    @PutMapping(value = "/archive")
    @RolesAllowed("ROLE_TASK_ARCHIVE")
    public List<Todo> archiveTodos(@RequestBody List<Long> todoIds) {
        return service.archiveTodos(todoIds);
    }
}
