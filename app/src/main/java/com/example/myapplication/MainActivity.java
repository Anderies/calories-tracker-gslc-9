package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ArrayList<Entry> entries;
    private ArrayAdapter<Entry> adapter;
    private ListView listView;
    private TextView totalCaloriesTextView;
    private Button btnAdd;
    // Declare the OnItemClickListener

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//      Tambahkan Variable untuk Menampung Data di View
        entries = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, entries);

//      Insialisasi View
        listView = findViewById(R.id.listView);
        totalCaloriesTextView = findViewById(R.id.totalCaloriesTextView);
        btnAdd = findViewById(R.id.btnAdd);

        listView.setAdapter(adapter);

        btnAdd.setOnClickListener(this);

        listView.setOnItemClickListener(this);
    }

    private void updateTotalCalories() {
        double totalCalories = 0.0;
        for (Entry entry : entries) {
            totalCalories += entry.getCalories();
        }
        totalCaloriesTextView.setText("Total Calories: " + totalCalories);
    }

    // Assuming you have a method to add entries, for example:
    public void addEntry(Entry entry) {
        entries.add(entry);
        adapter.notifyDataSetChanged();
        updateTotalCalories(); // Update total calories when a new entry is added
    }

    private void openAddEntryDialog() {
        DialogUtils.openAddEntryDialog(this, new DialogUtils.AddEntryListener() {
            @Override
            public void onAddEntry(String foodName, double calories) {
                // Create a new FoodEntry and add it to the list
                FoodEntry newEntry = new FoodEntry(foodName, calories);
                addEntry(newEntry);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v == btnAdd) {
            openAddEntryDialog();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(view == listView){
            Entry entry = entries.get(position);
            Toast.makeText(MainActivity.this, "Selected: " + entry.getDetails(), Toast.LENGTH_SHORT).show();
        }
    }
}