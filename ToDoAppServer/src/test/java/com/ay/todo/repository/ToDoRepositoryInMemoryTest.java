package com.ay.todo.repository;

import com.ay.todo.TaskState;
import com.ay.todo.Todo;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;


public class ToDoRepositoryInMemoryTest {

    @Test
    public void testCrud(){
        ToDoRepository repo = new ToDoRepositoryInMemory();
        Todo todo = repo.insert(new Todo("Simple Todo","Simple Todo"));
        Assert.assertEquals( "One todo should be in repository", 1, repo.findAll().size());
        Assert.assertEquals("Todo obj should be in repository", todo, repo.find(todo.getTaskId()));
        todo = repo.update(todo.getTaskId(), "update title", "update description");
        Assert.assertEquals("Todo should be updated", "update description", repo.find(todo.getTaskId()).getTaskDescription());
        repo.updateState(todo.getTaskId(), TaskState.IN_PROGRESS);
        Assert.assertEquals("Todo state should be updated", TaskState.IN_PROGRESS, repo.find(todo.getTaskId()).getTaskState());
        repo.updateState(Arrays.asList(new Long[]{todo.getTaskId()}), TaskState.ARCHIVED);
        Assert.assertEquals("Todo state should be updated", TaskState.ARCHIVED, repo.find(todo.getTaskId()).getTaskState());
        repo.delete(todo.getTaskId());
        Assert.assertNull("Todo should not be in repository", repo.find(todo.getTaskId()));
    }
}