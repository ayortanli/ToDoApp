package com.ay.todo.controller;

import com.ay.todo.Todo;
import com.ay.todo.service.TodoService;
import org.junit.gen5.api.Test;
import org.mockito.Mockito;

public class TodoControllerTest {

    @Test
    public void testInsert(){
        TodoService service = Mockito.mock(TodoService.class);
        TodoController controller = new TodoController(service);
        controller.insertNewTodo("todo description");
        Mockito.verify(service).insertTodo("todo description");
    }

    @Test
    public void testFind(){
        TodoService service = Mockito.mock(TodoService.class);
        TodoController controller = new TodoController(service);
        controller.findAllTodo();
        Mockito.verify(service).findAll();
    }

    @Test
    public void testFindAll(){
        TodoService service = Mockito.mock(TodoService.class);
        TodoController controller = new TodoController(service);
        controller.findTodo(1l);
        Mockito.verify(service).find(1l);
    }

    @Test
    public void testDelete(){
        TodoService service = Mockito.mock(TodoService.class);
        TodoController controller = new TodoController(service);
        controller.deleteTodo(1l);
        Mockito.verify(service).deleteTodo(1l);
    }

    @Test
    public void testUpdate() {
        TodoService service = Mockito.mock(TodoService.class);
        TodoController controller = new TodoController(service);
        controller.updateTodoDescription(1l, "updated description");
        Mockito.verify(service).updateTodo(1l, "updated description");
    }

}