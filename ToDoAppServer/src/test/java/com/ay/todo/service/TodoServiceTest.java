package com.ay.todo.service;

import com.ay.todo.TaskState;
import com.ay.todo.Todo;
import com.ay.todo.repository.ToDoRepository;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TodoServiceTest {

    @Test
    public void testInsertTodo(){
        ToDoRepository repo = Mockito.mock(ToDoRepository.class);
        //return parameter given to insert method of repo
        Mockito.when(repo.insert(Matchers.any(Todo.class)))
                .thenAnswer(invocation -> invocation.getArguments()[0]);
        TodoService service = new TodoServiceImpl(repo);
        Todo todo = service.insertTodo("title", "description");
        Assert.assertEquals("Todo should created and persisted", "description", todo.getTaskDescription());
    }

    @Test
    public void testFindTodo(){
        ToDoRepository repo = Mockito.mock(ToDoRepository.class);
        Mockito.when(repo.find(1l)).thenReturn(new Todo("",""));
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
        Todo todo = new Todo("","test");
        todo.setTaskId(1l);
        Mockito.when(repo.find(1l)).thenReturn(todo);
        TodoService service = new TodoServiceImpl(repo);
        service.deleteTodo(1l);
        Mockito.verify(repo).delete(1l);
    }

    @Test
    public void testUpdateTodo(){
        ToDoRepository repo = Mockito.mock(ToDoRepository.class);
        Todo todo = new Todo("title","test");
        todo.setTaskId(1l);
        Mockito.when(repo.find(1l)).thenReturn(todo);
        TodoService service = new TodoServiceImpl(repo);
        service.updateTodo(1l, "updatedTitle", "updated description");
        Mockito.verify(repo).update(1l, "updatedTitle", "updated description");
    }

    @Test
    public void testUpdateTodoState(){
        ToDoRepository repo = Mockito.mock(ToDoRepository.class);
        Todo todo = new Todo("title","test");
        todo.setTaskId(1l);
        Mockito.when(repo.find(1l)).thenReturn(todo);
        TodoService service = new TodoServiceImpl(repo);
        service.updateTodoState(1l, TaskState.IN_PROGRESS);
        Mockito.verify(repo).updateState(1l, TaskState.IN_PROGRESS);
    }


    @Test
    public void testArchiveTodos(){
        ToDoRepository repo = Mockito.mock(ToDoRepository.class);
        Todo todo = new Todo("title","test");
        todo.setTaskState(TaskState.DONE);
        todo.setTaskId(1l);
        List<Todo> todos = Arrays.asList(new Todo[]{todo});
        List<Long> ids = Arrays.asList(new Long[]{1l});
        Mockito.when(repo.updateState(ids,TaskState.ARCHIVED)).thenReturn(todos);
        TodoService service = new TodoServiceImpl(repo);
        service.archiveTodos(ids);
        Mockito.verify(repo).updateState(ids, TaskState.ARCHIVED);
    }

}