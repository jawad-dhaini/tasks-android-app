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

public class LoginActivity extends AppCompatActivity {

    EditText etEmail, etPwd;
    DatabaseHandler dbhandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etEmail = findViewById(R.id.et_email);
        etPwd = findViewById(R.id.et_pwd);
        dbhandler = new DatabaseHandler(this);
    }

    public void login(View v){
        if(dbhandler.validate(etEmail.getText().toString(),
                etPwd.getText().toString()) == true){
            Intent intent = new Intent(LoginActivity.this, ListsActivity.class);
            intent.putExtra("email", etEmail.getText().toString());
            startActivity(intent);
        }
        else
            Toast.makeText(this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
    }


    public void register(View v){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}