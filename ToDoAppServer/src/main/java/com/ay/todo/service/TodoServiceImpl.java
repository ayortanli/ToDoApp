package com.ay.todo.service;

import com.ay.todo.TaskState;
import com.ay.todo.Todo;
import com.ay.todo.repository.ToDoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class TodoServiceImpl implements TodoService {

    Logger logger = LoggerFactory.getLogger(TodoServiceImpl.class);

    private ToDoRepository repository;

    @Autowired
    public TodoServiceImpl(ToDoRepository toDoRepository) {
        this.repository = toDoRepository;
    }

    @Override
    public Todo insertTodo(String title, String description) {
        Todo todo = new Todo(title, description);
        todo = repository.save(todo);
        return todo;
    }

    @Override
    public void deleteTodo(Long id) {
        Todo todo = find(id);
        if(todo != null)
            repository.deleteById(id);
    }

    @Override
    public Todo updateTodo(Long id, String title, String description) {
        Todo todo = find(id);
        if(todo != null) {
            todo.setTaskTitle(title);
            todo.setTaskDescription(description);
            todo = repository.save(todo);
        }
        return todo;
    }

    @Override
    public Todo updateTodoState(long id, TaskState state) {
        Todo todo = find(id);
        if(todo != null) {
            todo.setTaskState(state);
            todo = repository.save(todo);
        }
        return todo;
    }

    @Override
    public List<Todo> archiveTodos(List<Long> todoIds) {
        Iterable<Todo> todos = repository.findAllById(todoIds);
        todos.forEach(todo -> todo.setTaskState(TaskState.ARCHIVED));
        todos = repository.saveAll(todos);
        List<Todo> result = new ArrayList<>();
        todos.forEach(result::add);
        return result;
    }

    @Override
    public Todo find(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Todo> findAll() {
        List<Todo> result = new ArrayList<>();
        repository.findAll().forEach(result::add);
        return result;
    }
}
