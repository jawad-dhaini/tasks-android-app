package com.example.tasks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

public class EditListActivity extends AppCompatActivity {

    private ArrayList<String> tasks;
    private ArrayAdapter<String> adapter;
    private EditText taskInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_list);

        tasks = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasks);

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        String listname = intent.getStringExtra("listname");
        String list_name = listname.substring(11, listname.length()-1);


        ListView listView = findViewById(R.id.listView);
        DatabaseHandler db = new DatabaseHandler(this);
        ArrayList<HashMap<String, String>> tasks = db.getTasks(email, list_name);
        String[] from = new String[]{"task_name"};
        int[] to = new int[]{R.id.row_list};
        SimpleAdapter adapter = new SimpleAdapter(this, tasks, R.layout.one_row_list_layout, from, to);
        listView.setAdapter(adapter);


        taskInput = findViewById(R.id.taskInput);
        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task = taskInput.getText().toString();
                if (!task.isEmpty()) {
                    long id = db.add_task(email, list_name, task);
                    //   listView.add(task);
                    Intent intent = new Intent(EditListActivity.this, EditListActivity.class);
                    intent.putExtra("email", email);
                    intent.putExtra("listname", listname);
                    startActivity(intent);
                } else {
                    Toast.makeText(EditListActivity.this, "Please enter a task", Toast.LENGTH_SHORT).show();
                }
            }


        });
    }
}
