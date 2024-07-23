package com.example.tasks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;


public class ListsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lists);

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");

        TextView textView = findViewById(R.id.textView2);
        textView.setText(email);

        ImageButton img =findViewById(R.id.imageButton3);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ListsActivity.this, NewListActivity.class);
                intent1.putExtra("email", email.toString());
                startActivity(intent1);
            }
        });

        ListView lvLists = findViewById(R.id.lv_lists);
        DatabaseHandler db = new DatabaseHandler(this);
        ArrayList<HashMap<String, String>> lists = db.getLists(email);
        String[] from = new String[]{"list_name"};
        int[] to = new int[]{R.id.row_list};
        SimpleAdapter adapter = new SimpleAdapter(this, lists, R.layout.one_row_list_layout, from, to);
        lvLists.setAdapter(adapter);

        lvLists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String selectedList = parent.getItemAtPosition(position).toString();
                Intent intent2 = new Intent(ListsActivity.this, EditListActivity.class);
                intent2.putExtra("email", email.toString());
                intent2.putExtra("listname", selectedList.toString());
                startActivity(intent2);
            }
        });
    }
}

