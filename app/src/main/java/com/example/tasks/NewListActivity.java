package com.example.tasks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class NewListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void list_add(View v) {
        DatabaseHandler db = new DatabaseHandler(this);

        EditText etListname;
        etListname = findViewById(R.id.ListName);
        String Listname = etListname.getText().toString();

        if (!Listname.isEmpty()) {
            Intent intent = getIntent();
            String email = intent.getStringExtra("email");

            long id = db.add_list(email, Listname);

            Intent intent1 = new Intent(    NewListActivity.this, ListsActivity.class);
            intent1.putExtra("email", email);
            startActivity(intent1);

        } else {
            Toast.makeText(NewListActivity.this, "Please enter a listname", Toast.LENGTH_SHORT).show();
        }


    }


}