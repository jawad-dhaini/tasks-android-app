package com.example.tasks;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void register(View v){
        DatabaseHandler db = new DatabaseHandler(this);
        EditText etEmail = findViewById(R.id.et_email);
        String email = etEmail.getText().toString();
        EditText etPwd = findViewById(R.id.et_pwd);
        String pwd = etPwd.getText().toString();
        EditText etFN = findViewById(R.id.et_first_name);
        String fn = etFN.getText().toString();
        EditText etLN = findViewById(R.id.et_last_name);
        String ln = etLN.getText().toString();
        EditText etCPWD = findViewById(R.id.et_cpwd);
        String cpwd = etCPWD.getText().toString();
        //checks if email doesn't exist
        if(db.validate(email)){
            if(pwd.equals(cpwd)) {
                long id = db.register(fn, ln, email, pwd);
                Toast.makeText(this, "Your id is " +
                        String.valueOf(id), Toast.LENGTH_SHORT).show();
                this.finish();
            }
            else
                Toast.makeText(this, "Password not matching", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this, "Email alreadys used", Toast.LENGTH_SHORT).show();
    }
}