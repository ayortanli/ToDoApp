package com.ay.todo;

import com.ay.todo.repository.ToDoRepository;
import com.ay.todo.service.TodoService;
import com.ay.todo.service.TodoServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TodoConfiguration {

    Logger logger = LoggerFactory.getLogger(TodoConfiguration.class);

    @Bean
    public TodoService todoService(ToDoRepository repository){
        return new TodoServiceImpl(repository);
    }
}
