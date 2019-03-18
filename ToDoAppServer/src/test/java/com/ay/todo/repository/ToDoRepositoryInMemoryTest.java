package com.ay.todo.repository;

import com.ay.todo.Todo;
import org.junit.Assert;
import org.junit.Test;


public class ToDoRepositoryInMemoryTest {

    @Test
    public void testCrud(){
        ToDoRepository repo = new ToDoRepositoryInMemory();
        Todo todo = repo.insert(new Todo("Simple Todo"));
        Assert.assertEquals( "One todo should be in repository", 1, repo.findAll().size());
        Assert.assertEquals("Todo obj should be in repository", todo, repo.find(todo.getId()));
        Todo updatedTodo = repo.update(todo.getId(), "Test task");
        Assert.assertEquals("Todo should be updated", "Test task", repo.find(todo.getId()).getDescription());
        repo.delete(todo.getId());
        Assert.assertNull("Todo should not be in repository", repo.find(todo.getId()));
    }
}