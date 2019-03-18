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
                .content("task1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(0)))
                .andExpect(jsonPath("$.description", is("task1")));
        //Insert another task
        mockMvc.perform(post("/todos")
                .content("task2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.description", is("task2")));
        //find all tasks
        mockMvc.perform(get("/todos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)));
        //update second task
        mockMvc.perform(put("/todos/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new Gson().toJson(new Todo("updated task2"))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.description", is("updated task2")));
        //delete first task
        mockMvc.perform(delete("/todos/0"))
                .andExpect(status().isOk());
        //try to get first task
        mockMvc.perform(get("/todos/0"))
                .andExpect(status().isOk()); //Not_FOund yazacaz
        //get the second task
        mockMvc.perform(get("/todos/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.description", is("updated task2")));

    }
}
