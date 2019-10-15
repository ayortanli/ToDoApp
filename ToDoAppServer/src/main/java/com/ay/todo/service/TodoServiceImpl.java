package com.ay.todo.service;

import com.ay.todo.TaskState;
import com.ay.todo.Todo;
import com.ay.todo.repository.ToDoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TodoServiceImpl implements TodoService {

    Logger logger = LoggerFactory.getLogger(TodoServiceImpl.class);

    private ToDoRepository repository;

    @Autowired
    public TodoServiceImpl(ToDoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Todo insertTodo(String title, String description) {
        Todo todo = new Todo(title, description);
        todo = repository.insert(todo);
        return todo;
    }

    @Override
    public void deleteTodo(Long id) {
        Todo todo = repository.find(id);
        if(todo != null)
            repository.delete(id);
    }

    @Override
    public Todo updateTodo(Long id, String title, String description) {
        Todo todo = repository.find(id);
        if(todo != null) {
            repository.update(id, title, description);
        }
        return todo;
    }

    @Override
    public Todo updateTodoState(long id, TaskState state) {
        Todo todo = repository.find(id);
        if(todo != null) {
            repository.updateState(id, state);
        }
        return todo;
    }

    @Override
    public List<Todo> archiveTodos(List<Long> todoIds) {
        return repository.updateState(todoIds, TaskState.ARCHIVED);
    }

    @Override
    public Todo find(Long id) {
        return repository.find(id);
    }

    @Override
    public List<Todo> findAll() {
        return repository.findAll();
    }
}
