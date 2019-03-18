package com.ay.todo.controller;

import com.ay.todo.Todo;
import com.ay.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoService service;

    public TodoController(TodoService service) {
        this.service = service;
    }

    @GetMapping("/ping")
    public boolean ping(){
        return true;
    }

    @PostMapping("")
    public Todo insertNewTodo(@RequestBody String description) {
        return service.insertTodo(description);
    }

    @GetMapping("/{id}")
    public Todo findTodo(@PathVariable("id") long id) {
        return service.find(id);
    }

    @GetMapping("")
    public List<Todo> findAllTodo() {
        return service.findAll();
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable("id") long id) {
        service.deleteTodo(id);
    }

    @PutMapping(value = "/{id}")
    public Todo updateTodoDescription(@PathVariable("id") long id, @RequestBody Todo todo) {
        return service.updateTodo(id, todo.getDescription());
    }
}
