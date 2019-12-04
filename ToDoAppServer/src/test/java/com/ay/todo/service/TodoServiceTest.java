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
import java.util.Optional;

public class TodoServiceTest {

    @Test
    public void testInsertTodo(){
        ToDoRepository repo = Mockito.mock(ToDoRepository.class);
        //return parameter given to save method of repo
        Mockito.when(repo.save(Matchers.any(Todo.class)))
                .thenAnswer(invocation -> invocation.getArguments()[0]);
        TodoService service = new TodoServiceImpl(repo);
        Todo todo = service.insertTodo("title", "description");
        Assert.assertEquals("Todo should created and persisted", "description", todo.getTaskDescription());
    }

    @Test
    public void testFindTodo(){
        ToDoRepository repo = Mockito.mock(ToDoRepository.class);
        Mockito.when(repo.findById(1l)).thenReturn(Optional.of(new Todo("","")));
        TodoService service = new TodoServiceImpl(repo);
        service.find(1l);
        Mockito.verify(repo).findById(1l);
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
        Mockito.when(repo.findById(1l)).thenReturn(Optional.of(todo));
        TodoService service = new TodoServiceImpl(repo);
        service.deleteTodo(1l);
        Mockito.verify(repo).deleteById(1l);
    }

    @Test
    public void testUpdateTodo(){
        ToDoRepository repo = Mockito.mock(ToDoRepository.class);
        Todo todo = new Todo("title","test");
        todo.setTaskId(1l);
        Mockito.when(repo.findById(1l)).thenReturn(Optional.of(todo));
        Mockito.when(repo.save(todo)).thenReturn(todo);
        TodoService service = new TodoServiceImpl(repo);
        todo = service.updateTodo(1l, "updatedTitle", "updated description");
        Assert.assertEquals("Title should be updated", "updatedTitle", todo.getTaskTitle());
        Assert.assertEquals("Description should be updated", "updated description", todo.getTaskDescription());
    }

    @Test
    public void testUpdateTodoState(){
        ToDoRepository repo = Mockito.mock(ToDoRepository.class);
        Todo todo = new Todo("title","test");
        todo.setTaskId(1l);
        Mockito.when(repo.findById(new Long(1l))).thenReturn(Optional.of(todo));
        Mockito.when(repo.save(todo)).thenReturn(todo);
        TodoService service = new TodoServiceImpl(repo);
        todo = service.updateTodoState(1l, TaskState.IN_PROGRESS);
        Assert.assertEquals("State should be updated", TaskState.IN_PROGRESS, todo.getTaskState());
        Mockito.verify(repo).save(todo);
    }


    @Test
    public void testArchiveTodos(){
        ToDoRepository repo = Mockito.mock(ToDoRepository.class);
        Todo todo = new Todo("title","test");
        todo.setTaskState(TaskState.DONE);
        todo.setTaskId(1l);
        List<Todo> todos = Arrays.asList(new Todo[]{todo});
        List<Long> ids = Arrays.asList(new Long[]{1l});
        Mockito.when(repo.findAllById(ids)).thenReturn(todos);
        Mockito.when(repo.saveAll(todos)).thenReturn(todos);
        TodoService service = new TodoServiceImpl(repo);
        todos = service.archiveTodos(ids);
        Assert.assertEquals("Tasks should be archived", TaskState.ARCHIVED, todos.get(0).getTaskState());
    }

}