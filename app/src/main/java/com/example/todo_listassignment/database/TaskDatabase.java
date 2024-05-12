package com.example.todo_listassignment.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.todo_listassignment.model.Task;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Database(entities = {Task.class}, version = 1)
public abstract class TaskDatabase extends RoomDatabase {


    private static volatile TaskDatabase INSTANCE;
    public static final Executor databaseWriteExecutor = Executors.newFixedThreadPool(4);

    public abstract TaskDao taskDao();

    public static synchronized TaskDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TaskDatabase.class, "task_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
