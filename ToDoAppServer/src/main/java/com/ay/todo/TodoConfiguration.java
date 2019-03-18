package com.ay.todo;

import com.ay.todo.repository.ToDoRepository;
import com.ay.todo.service.TodoService;
import com.ay.todo.service.TodoServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TodoConfiguration {

    @Bean
    public TodoService todoService(ToDoRepository repository){
        return new TodoServiceImpl(repository);
    }
}
