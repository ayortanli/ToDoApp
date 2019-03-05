package com.ay.todo.repository;

import com.ay.todo.Todo;
import org.junit.gen5.api.Assertions;
import org.junit.gen5.api.Test;


public class ToDoRepositoryInMemoryTest {

    @Test
    public void testCrud(){
        ToDoRepository repo = new ToDoRepositoryInMemory();
        Todo todo = repo.insert(new Todo("Simple Todo"));
        Assertions.assertEquals(1, repo.findAll().size(), "One todo should be in repository");
        Assertions.assertEquals(todo, repo.find(todo.getId()),  "Todo obj should be in repository");
        todo.setDescription("Test task");
        Todo updatedTodo = repo.update(todo);
        Assertions.assertEquals("Test task", repo.find(todo.getId()).getDescription(), "Todo should be updated");
        repo.delete(todo.getId());
        Assertions.assertNull(repo.find(todo.getId()),  "Todo should not be in repository");
    }
}