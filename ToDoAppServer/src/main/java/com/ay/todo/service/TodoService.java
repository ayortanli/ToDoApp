package com.ay.todo.service;

import com.ay.todo.TaskState;
import com.ay.todo.Todo;

import java.util.List;

public interface TodoService {
    Todo insertTodo(String title, String description);

    void deleteTodo(Long id);

    Todo find(Long id);

    List<Todo> findAll();

    Todo updateTodo(Long id, String title, String description);

    Todo updateTodoState(long id, TaskState state);

    List<Todo> archiveTodos(List<Long> todoIds);
}
