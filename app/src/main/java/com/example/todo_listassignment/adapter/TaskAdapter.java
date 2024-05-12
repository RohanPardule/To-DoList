package com.example.todo_listassignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todo_listassignment.R;
import com.example.todo_listassignment.model.Task;

import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task> {

    private Context context;
    private List<Task> tasks;

    public TaskAdapter(@NonNull Context context, @NonNull List<Task> tasks) {
        super(context, 0, tasks);
        this.context = context;
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.taskTitleTextView = view.findViewById(R.id.taskTitle);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Task currentTask = tasks.get(position);
        viewHolder.taskTitleTextView.setText(currentTask.getTitle());

        return view;
    }


    static class ViewHolder {
        TextView taskTitleTextView;
    }


}
