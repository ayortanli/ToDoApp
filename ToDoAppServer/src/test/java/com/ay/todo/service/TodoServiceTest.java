package com.ay.todo.service;

import com.ay.todo.Todo;
import com.ay.todo.repository.ToDoRepository;
import org.junit.gen5.api.Assertions;
import org.junit.gen5.api.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import java.util.ArrayList;

public class TodoServiceTest {

    @Test
    public void testInsertTodo(){
        ToDoRepository repo = Mockito.mock(ToDoRepository.class);
        //return parameter given to insert method of repo
        Mockito.when(repo.insert(Matchers.any(Todo.class)))
                .thenAnswer(invocation -> invocation.getArguments()[0]);
        TodoService service = new TodoServiceImpl(repo);
        Todo todo = service.insertTodo("Test task");
        Assertions.assertEquals("Test task", todo.getDescription(), "Todo should created and persisted");
    }

    @Test
    public void testFindTodo(){
        ToDoRepository repo = Mockito.mock(ToDoRepository.class);
        Mockito.when(repo.find(1l)).thenReturn(new Todo(""));
        TodoService service = new TodoServiceImpl(repo);
        service.find(1l);
        Mockito.verify(repo).find(1l);
    }

    @Test
    public void testFindAll(){
        ToDoRepository repo = Mockito.mock(ToDoRepository.class);
        Mockito.when(repo.findAll()).thenReturn(new ArrayList<>());
        TodoService service = new TodoServiceImpl(repo);
        service.findAll();
        Mockito.verify(repo).findAll();
    }

    @Test
    public void testDeleteTodo(){
        ToDoRepository repo = Mockito.mock(ToDoRepository.class);
        Todo todo = new Todo("test");
        todo.setId(1l);
        Mockito.when(repo.find(1l)).thenReturn(todo);
        TodoService service = new TodoServiceImpl(repo);
        service.deleteTodo(1l);
        Mockito.verify(repo).delete(1l);
    }

    @Test
    public void testUpdateTodo(){
        ToDoRepository repo = Mockito.mock(ToDoRepository.class);
        Todo todo = new Todo("test");
        todo.setId(1l);
        Mockito.when(repo.find(1l)).thenReturn(todo);
        TodoService service = new TodoServiceImpl(repo);
        service.updateTodo(1l, "updated test");
        Mockito.verify(repo).update(1l, "updated test");
    }

}