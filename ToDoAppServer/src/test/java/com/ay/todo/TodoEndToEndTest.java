package com.ay.todo;

import com.ay.todo.controller.TodoController;
import com.ay.todo.controller.TodoControllerTest;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TodoEndToEndTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testTodoScenario() throws Exception {
        //ping service
        mockMvc.perform(get("/todos/ping"))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("true")));
        //Insert a task
        mockMvc.perform(post("/todos")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new Gson().toJson(new Todo("task1","description1"))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.taskId", is(0)))
                .andExpect(jsonPath("$.taskTitle", is("task1")))
                .andExpect(jsonPath("$.taskDescription", is("description1")))
                .andExpect(jsonPath("$.taskState", is("TODO")));
        //Insert another task
        mockMvc.perform(post("/todos")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new Gson().toJson(new Todo("task2","description2"))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.taskId", is(1)))
                .andExpect(jsonPath("$.taskTitle", is("task2")))
                .andExpect(jsonPath("$.taskDescription", is("description2")))
                .andExpect(jsonPath("$.taskState", is("TODO")));
        //find all tasks
        mockMvc.perform(get("/todos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)));
        //update second task
        mockMvc.perform(put("/todos/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new Gson().toJson(new Todo("updated task2","updated description2"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.taskId", is(1)))
                .andExpect(jsonPath("$.taskTitle", is("updated task2")))
                .andExpect(jsonPath("$.taskDescription", is("updated description2")));
        //delete first task
        mockMvc.perform(delete("/todos/0"))
                .andExpect(status().isOk());
        //try to get first task
        mockMvc.perform(get("/todos/0"))
                .andExpect(status().isOk()); //Not_Found yazacaz
        //get the second task
        mockMvc.perform(get("/todos/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.taskId", is(1)))
                .andExpect(jsonPath("$.taskState", is("TODO")));
        //update second task state
        mockMvc.perform(put("/todos/state/1")
                .content("DONE"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.taskId", is(1)))
                .andExpect(jsonPath("$.taskState", is("DONE")));
        //archive tasks
        mockMvc.perform(put("/todos/archive")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new Gson().toJson(new Long[]{1l})))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].taskId", is(1)))
                .andExpect(jsonPath("$[0].taskState", is("ARCHIVED")));
    }
}
