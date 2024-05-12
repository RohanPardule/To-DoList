package com.example.todo_listassignment.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.todo_listassignment.R;
import com.example.todo_listassignment.adapter.TaskAdapter;
import com.example.todo_listassignment.model.Task;
import com.example.todo_listassignment.viewmodel.TaskViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class TasksFragment extends Fragment {

    private TaskViewModel taskViewModel;
    private TaskAdapter taskAdapter;
    private FloatingActionButton addBtn;
    List<Task> taskList;
    ListView tasksListView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);
        addBtn = view.findViewById(R.id.addBtn);
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        tasksListView= view.findViewById(R.id.ListView);





        taskViewModel.getAllTasks().observe(getViewLifecycleOwner(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                taskList= new ArrayList<>();
                tasksListView= view.findViewById(R.id.ListView);

                Log.d("TasksFragment", "Received tasks: " + tasks.size());
                taskList.clear(); // Clear existing tasks

                for (Task task : tasks) {
                    taskList.add(task);
                }
                Collections.reverse(taskList);

                taskAdapter = new TaskAdapter(getActivity(),taskList);
                tasksListView.setAdapter(taskAdapter);
                taskAdapter.notifyDataSetChanged();
            }
        });

        tasksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task task = taskAdapter.getItem(position);

                showTaskOptionsDialog(task);
            }
        });

        tasksListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Task task = taskAdapter.getItem(position);
                showEditDeleteDialog(task);
                return true;
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddTaskDialog();
            }
        });

        return view;
    }

    private void showTaskOptionsDialog(Task task) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Task Options");
        String[] options = {"Complete Task"};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        completeTask(task);
                        break;

                }
            }
        });
        builder.show();
    }

    private void completeTask(Task task) {

        task.setCompleted(true);
        taskViewModel.update(task);
        Toast.makeText(getActivity(), "Task completed", Toast.LENGTH_SHORT).show();

    }

    private void showEditDeleteDialog(final Task task) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Edit or Delete Task");
        String[] options = {"Edit", "Delete"};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        showEditTaskDialog(task);
                        break;
                    case 1:
                        deleteTask(task);
                        break;
                }
            }
        });
        builder.show();
    }

    private void showEditTaskDialog(final Task task) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Edit Task");

        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(task.getTitle());
        builder.setView(input);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newTitle = input.getText().toString().trim();
                if (!newTitle.isEmpty()) {
                    task.setTitle(newTitle);
                    taskViewModel.update(task);
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void deleteTask(Task task) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete Task");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                taskViewModel.delete(task.getId());
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }


    private void showAddTaskDialog() {

        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_add_task, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(dialogView);
        builder.setTitle("Add Task");


        EditText input = dialogView.findViewById(R.id.editTextTask);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String taskTitle = input.getText().toString();
                if (!taskTitle.isEmpty()) {
                    Task newTask = new Task(taskTitle);
                    Log.d("fragment",newTask.toString());
                    taskViewModel.insert(newTask);
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

}
