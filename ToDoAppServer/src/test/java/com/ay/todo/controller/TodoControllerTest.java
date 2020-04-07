package com.ay.todo.controller;

import com.ay.todo.TaskState;
import com.ay.todo.Todo;
import com.ay.todo.service.TodoService;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(TodoController.class)
public class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private TodoService service;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                    .apply(SecurityMockMvcConfigurers.springSecurity())
                    .build();
    }

    @Test
    public void testPing() throws Exception {
        mockMvc.perform(get("/todos/ping")
                .with(csrf().asHeader())
                .with(user("user").password("password")))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("true")));
    }

    @Test
    public void testInsert() throws Exception {
        Todo todo = createTodo(1l,"title", "description", TaskState.TODO);
        when(service.insertTodo("title", "description")).thenReturn(todo);
        mockMvc.perform(post("/todos")
                .with(csrf().asHeader())
                .with(user("user").password("password").roles("TASK_MODIFY"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(todo)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.taskId", is(1)))
                .andExpect(jsonPath("$.taskDescription", is("description")));
        verify(service, times(1)).insertTodo("title","description");
        verifyNoMoreInteractions(service);
    }

    @Test
    public void testFindAll() throws Exception {
        List<Todo> todoList = Arrays.asList(
                createTodo(1l, "title1", "description1", TaskState.IN_PROGRESS),
                createTodo(2l, "title2", "description2", TaskState.DONE)
        );
        when(service.findAll()).thenReturn(todoList);
        mockMvc.perform(get("/todos")
                .with(csrf().asHeader())
                .with(user("user").password("password").roles("TASK_READ")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
        verify(service, times(1)).findAll();
        verifyNoMoreInteractions(service);
    }

    @Test
    public void testFind() throws Exception {
        Todo todo = createTodo(1l,"title", "description", TaskState.IN_PROGRESS); //{"id":1,"description":"test"}
        when(service.find(1l)).thenReturn(todo);
        mockMvc.perform(get("/todos/1")
                .with(csrf().asHeader())
                .with(user("user").password("password").roles("TASK_READ")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.taskId", is(1)))
                .andExpect(jsonPath("$.taskTitle", is("title")))
                .andExpect(jsonPath("$.taskDescription", is("description")))
                .andExpect(jsonPath("$.taskState", is("IN_PROGRESS")));
        verify(service, times(1)).find(1l);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/todos/1")
                .with(csrf().asHeader())
                .with(user("user").password("password").roles("TASK_MODIFY")))
                .andExpect(status().isOk());
        verify(service, times(1)).deleteTodo(1l);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void testUpdate() throws Exception {
        Todo todo = createTodo(1l,"title", "description", TaskState.TODO);
        when(service.updateTodo(1l, "title", "description")).thenReturn(todo);
        mockMvc.perform(put("/todos/1")
                .with(csrf().asHeader())
                .with(user("user").password("password").roles("TASK_MODIFY"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(todo)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.taskId", is(1)))
                .andExpect(jsonPath("$.taskTitle", is("title")))
                .andExpect(jsonPath("$.taskDescription", is("description")));
        verify(service, times(1)).updateTodo(1l, "title", "description");
        verifyNoMoreInteractions(service);
    }

    @Test
    public void testUpdateState() throws Exception {
        Todo todo = createTodo(1l,"title", "description", TaskState.IN_PROGRESS);
        when(service.updateTodoState(1l, TaskState.IN_PROGRESS)).thenReturn(todo);
        mockMvc.perform(put("/todos/state/1")
                .with(csrf().asHeader())
                .with(user("user").password("password").roles("TASK_MODIFY"))
                .content("IN_PROGRESS"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.taskId", is(1)))
                .andExpect(jsonPath("$.taskTitle", is("title")))
                .andExpect(jsonPath("$.taskDescription", is("description")))
                .andExpect(jsonPath("$.taskState", is("IN_PROGRESS")));
        verify(service, times(1)).updateTodoState(1l, TaskState.IN_PROGRESS);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void testArchiveTodos() throws Exception {
        List<Long> todoIds = new ArrayList(Arrays.asList(new Long[]{1l,2l}));
        List<Todo> todos = new ArrayList(Arrays.asList(new Todo[]{
                createTodo(1l,"title", "description", TaskState.DONE),
                createTodo(2l,"title", "description", TaskState.DONE)
        }));
        when(service.archiveTodos(todoIds)).thenReturn(todos);
        mockMvc.perform(put("/todos/archive")
                .with(csrf().asHeader())
                .with(user("user").password("password").roles("TASK_ARCHIVE"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(todoIds)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
        verify(service, times(1)).archiveTodos(todoIds);
        verifyNoMoreInteractions(service);
    }

    private Todo createTodo(Long id, String title, String description, TaskState state){
        Todo todo = new Todo(title, description);
        todo.setTaskId(id);
        todo.setTaskState(state);
        return todo;
    }
}