package com.example.todo_listassignment.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CompletedTasksFragment extends Fragment {

    private TaskViewModel taskViewModel;
    private TaskAdapter completedTasksAdapter;
    ListView completedTasksListView;
    List<Task> completedTaskList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);



        completedTasksListView = view.findViewById(R.id.tasksListView);


        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        taskViewModel.getCompletedTasks().observe(getViewLifecycleOwner(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> completedTasks) {
                completedTaskList= new ArrayList<>();
                completedTasksListView= view.findViewById(R.id.tasksListView);

                completedTaskList.clear();
                for (Task task : completedTasks) {
                    completedTaskList.add(task);
                }
                Collections.reverse(completedTaskList);

                completedTasksAdapter = new TaskAdapter(getActivity(),completedTaskList);
                completedTasksListView.setAdapter(completedTasksAdapter);
                completedTasksAdapter.notifyDataSetChanged();

            }
        });
        completedTasksListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Task task = completedTasksAdapter.getItem(position);
                showDeleteDialog(task);
                return true;
            }
        });

        return view;
    }
    private void showDeleteDialog(final Task task) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Edit or Delete Task");
        String[] options = {"Delete"};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        deleteTask(task);
                        break;

                }
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


}
