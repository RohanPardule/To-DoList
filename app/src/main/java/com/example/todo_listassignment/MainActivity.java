package com.example.todo_listassignment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.todo_listassignment.adapter.MyPagerAdapter;
import com.example.todo_listassignment.fragments.CompletedTasksFragment;
import com.example.todo_listassignment.fragments.TasksFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    ViewPager2 viewPager2;
    TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager2 = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new TasksFragment());
        fragments.add(new CompletedTasksFragment());


        MyPagerAdapter pagerAdapter = new MyPagerAdapter(MainActivity.this, fragments);
        viewPager2.setAdapter(pagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> tab.setText(getTabTitle(position))
        ).attach();

    }

    private String getTabTitle(int position) {
        if (position == 0) {
            return "Tasks";
        } else {
            return "Completed Tasks";
        }
    }
}