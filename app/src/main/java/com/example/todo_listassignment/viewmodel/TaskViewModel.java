package com.example.todo_listassignment.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.todo_listassignment.model.Task;
import com.example.todo_listassignment.repository.TaskRepository;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {

    private TaskRepository repository;
    private LiveData<List<Task>> allTasks;
    private LiveData<List<Task>> completedTasks;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        repository = new TaskRepository(application);
        allTasks = repository.getAllTasks();
        completedTasks = repository.getCompletedTasks();
    }

    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }

    public LiveData<List<Task>> getCompletedTasks() {
        return completedTasks;
    }

    public void insert(Task task) {


        repository.insert(task);
    }

    public void update(Task task) {
        repository.update(task);
    }

    public void delete(int taskId) {
        repository.delete(taskId);
    }
}
