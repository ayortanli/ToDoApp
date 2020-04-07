package com.ay.todo;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TodoEndToEndTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .defaultRequest(get("/todos")
                        .with(csrf().asHeader())
                        .with(user("user").roles("TASK_READ","TASK_MODIFY","TASK_ARCHIVE")))
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    public void testTodoScenario() throws Exception {
        //ping service
        mockMvc.perform(get("/todos/ping"))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("true")));
        //Insert a task
        mockMvc.perform(post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(new Todo("task1","description1"))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.taskId", is(1)))
                .andExpect(jsonPath("$.taskTitle", is("task1")))
                .andExpect(jsonPath("$.taskDescription", is("description1")))
                .andExpect(jsonPath("$.taskState", is("TODO")));
        //Insert another task
        mockMvc.perform(post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(new Todo("task2","description2"))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.taskId", is(2)))
                .andExpect(jsonPath("$.taskTitle", is("task2")))
                .andExpect(jsonPath("$.taskDescription", is("description2")))
                .andExpect(jsonPath("$.taskState", is("TODO")));
        //find all tasks
        mockMvc.perform(get("/todos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
        //update second task
        mockMvc.perform(put("/todos/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(new Todo("updated task2","updated description2"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.taskId", is(2)))
                .andExpect(jsonPath("$.taskTitle", is("updated task2")))
                .andExpect(jsonPath("$.taskDescription", is("updated description2")));
        //deleteById first task
        mockMvc.perform(delete("/todos/1"))
                .andExpect(status().isOk());
        //try to get first task
        mockMvc.perform(get("/todos/1"))
                .andExpect(status().isOk()); //Not_Found yazacaz
        //get the second task
        mockMvc.perform(get("/todos/2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.taskId", is(2)))
                .andExpect(jsonPath("$.taskState", is("TODO")));
        //update second task state
        mockMvc.perform(put("/todos/state/2")
                .content("DONE"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.taskId", is(2)))
                .andExpect(jsonPath("$.taskState", is("DONE")));
        //archive tasks
        mockMvc.perform(put("/todos/archive")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(new Long[]{2l})))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].taskId", is(2)))
                .andExpect(jsonPath("$[0].taskState", is("ARCHIVED")));
    }
}
