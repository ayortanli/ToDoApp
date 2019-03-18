package com.ay.todo.controller;

import com.ay.todo.Todo;
import com.ay.todo.service.TodoService;
import com.google.gson.Gson;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.GsonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(TodoController.class)
public class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoService service;

    @Test
    public void testPing() throws Exception {
        mockMvc.perform(get("/todos/ping"))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("true")));
    }

    @Test
    public void testInsert() throws Exception {
        Todo todo = createTodo(1l,"test"); //{"id":null,"description":"test"}
        when(service.insertTodo("test")).thenReturn(todo);
        mockMvc.perform(post("/todos")
                .content("test"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.description", is("test")));
        verify(service, times(1)).insertTodo("test");
        verifyNoMoreInteractions(service);
    }

    @Test
    public void testFindAll() throws Exception {
        List<Todo> todoList = Arrays.asList(
                createTodo(1l, "description1"),
                createTodo(2l, "description2")
        );
        when(service.findAll()).thenReturn(todoList);
        mockMvc.perform(get("/todos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)));
        verify(service, times(1)).findAll();
        verifyNoMoreInteractions(service);
    }

    @Test
    public void testFind() throws Exception {
        Todo todo = createTodo(1l,"test"); //{"id":1,"description":"test"}
        when(service.find(1l)).thenReturn(todo);
        mockMvc.perform(get("/todos/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.description", is("test")));
        verify(service, times(1)).find(1l);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/todos/1"))
                .andExpect(status().isOk());
        verify(service, times(1)).deleteTodo(1l);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void testUpdate() throws Exception {
        Todo todo = createTodo(1l,"test");
        when(service.updateTodo(1l, "test")).thenReturn(todo);
        mockMvc.perform(put("/todos/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new Gson().toJson(todo)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.description", is("test")));
        verify(service, times(1)).updateTodo(1l, "test");
        verifyNoMoreInteractions(service);
    }

    private Todo createTodo(Long id, String description){
        Todo todo = new Todo(description);
        todo.setId(id);
        return todo;
    }
}