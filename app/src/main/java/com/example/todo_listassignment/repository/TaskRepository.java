package com.example.todo_listassignment.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.todo_listassignment.database.TaskDao;
import com.example.todo_listassignment.database.TaskDatabase;
import com.example.todo_listassignment.model.Task;

import java.util.List;

public class TaskRepository {

    private TaskDao taskDao;
    private LiveData<List<Task>> allTasks;
    private LiveData<List<Task>> completedTasks;

    public TaskRepository(Application application) {
        TaskDatabase database = Room.databaseBuilder(application.getApplicationContext(),
                TaskDatabase.class, "task_database").build();

        taskDao = database.taskDao();
        allTasks = taskDao.getAllTasks();
        completedTasks = taskDao.getCompletedTasks();
    }

    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }

    public LiveData<List<Task>> getCompletedTasks() {
        return completedTasks;
    }

    public void insert(Task task) {

        TaskDatabase.databaseWriteExecutor.execute(() -> taskDao.insert(task));
    }

    public void update(Task task) {
        TaskDatabase.databaseWriteExecutor.execute(() -> taskDao.update(task));
    }

    public void delete(int taskId) {
        TaskDatabase.databaseWriteExecutor.execute(() -> taskDao.delete(taskId));
    }
}
