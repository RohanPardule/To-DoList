package com.example.todo_listassignment.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.todo_listassignment.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    void insert(Task task);

    @Update
    void update(Task task);

    @Query("DELETE FROM task_table WHERE id = :taskId")
    void delete(int taskId);

    @Query("SELECT * FROM task_table WHERE completed = 0")
    LiveData<List<Task>> getAllTasks();

    @Query("SELECT * FROM task_table WHERE completed = 1")
    LiveData<List<Task>> getCompletedTasks();
}

