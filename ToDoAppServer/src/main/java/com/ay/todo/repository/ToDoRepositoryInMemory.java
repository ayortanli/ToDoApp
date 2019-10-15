package com.ay.todo.repository;

import com.ay.todo.TaskState;
import com.ay.todo.Todo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ToDoRepositoryInMemory implements ToDoRepository {

    Logger logger = LoggerFactory.getLogger(ToDoRepositoryInMemory.class);

    private List<Todo> todoList;
    private long sequence;

    public ToDoRepositoryInMemory(){
        todoList = new ArrayList<>();
        sequence = 0;
    }

    @Override
    public Todo insert(Todo todo) {
        todo.setTaskId(sequence);
        ++sequence;
        todoList.add(todo);
        return todo;
    }

    @Override
    public List<Todo> findAll() {
        return todoList;
    }

    @Override
    public Todo find(Long id) {
        for (Todo todo : todoList) {
            if (todo.getTaskId().equals(id))
                return todo;
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        Todo todo = find(id);
        if(todo!= null) {
            todoList.remove(todo);
        }
    }

    @Override
    public Todo update(Long id, String title, String description) {
        Todo todoFromRepo = find(id);
        if(todoFromRepo != null){
            todoFromRepo.setTaskDescription(description);
            todoFromRepo.setTaskTitle(title);
            return todoFromRepo;
        }
        return null;
    }

    @Override
    public Todo updateState(long id, TaskState state) {
        Todo todoFromRepo = find(id);
        if(todoFromRepo != null){
            todoFromRepo.setTaskState(state);
            return todoFromRepo;
        }
        return null;
    }

    @Override
    public List<Todo> updateState(List<Long> ids, TaskState state) {
        List<Todo> todoList = new ArrayList<>();
        for (Long id : ids){
            Todo todo = updateState(id, state);
            if(todo!=null)
                todoList.add(todo);
        }
        return todoList;
    }
}
