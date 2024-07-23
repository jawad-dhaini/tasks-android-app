package com.example.tasks;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseHandler extends SQLiteOpenHelper {
    private final static String DATABASE_NAME = "MyTasksDatabase";
    private final static int VERSION = 1;

    public DatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query1 = "create table users(id integer primary key, " +
                "first_name text, last_name text, email text, " +
                "password text)";
        db.execSQL(query1);

        String query2 = "create table lists(list_id integer primary key, " +
                "email text, list_name text)";
        db.execSQL(query2);

        String query3 = "create table tasks(task_id integer primary key, " +
                "email text, list_name text, task_name text)";
        db.execSQL(query3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query1 = "drop table if exists users";
        db.execSQL(query1);
        String query2 = "drop table if exists lists";
        db.execSQL(query2);
        String query3 = "drop table if exists tasks";
        db.execSQL(query3);
        onCreate(db);
    }

    //returns false if email exists: we can't use it for a new user
    //returns true otherwise
    public boolean validate(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from users where email =?";
        Cursor cursor = db.rawQuery(query, new String[]{email});
        if (cursor.getCount() == 0)
            return true;
        return false;
    }

    public long register(String fn, String ln, String email, String pwd) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("first_name", fn);
        values.put("last_name", ln);
        values.put("email", email);
        values.put("password", pwd);

        long id = db.insert("users", null, values);
        db.close();
        return id;
    }

    //returns true if password matches the email: login
    //returns false otherwise
    public boolean validate(String email, String pwd) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from users " +
                "where email =? and password = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email, pwd});
        if (cursor.getCount() == 0)
            return false;
        return true;
    }

    public ArrayList<HashMap<String, String>> getUsers() {
        ArrayList<HashMap<String, String>> users = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from users order by first_name";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> user = new HashMap<>();
                user.put("id", String.valueOf(cursor.getInt(0)));
                user.put("name", cursor.getString(1) + " " + cursor.getString(2));
                user.put("email", cursor.getString(3));
                user.put("pwd", cursor.getString(4));
                users.add(user);
            } while (cursor.moveToNext());
        }
        return users;
    }

    public HashMap<String, String> getUser(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from users where id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            HashMap<String, String> user = new HashMap<>();
            user.put("id", String.valueOf(cursor.getInt(0)));
            user.put("fn", cursor.getString(1));
            user.put("ln", cursor.getString(2));
            user.put("email", cursor.getString(3));
            user.put("pwd", cursor.getString(4));
            return user;
        }
        return null;
    }




    public long add_task(String em, String ln, String tsk) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("email",em);
        values.put("list_name", ln);
        values.put("task_name", tsk);

        long id = db.insert("tasks", null, values);
        db.close();
        return id;
    }


    public long add_list(String em, String ln) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("email",em);
        values.put("list_name", ln);

        long id = db.insert("lists", null, values);
        db.close();
        return id;
    }

    public ArrayList<HashMap<String, String>> getLists(String em){
        ArrayList<HashMap<String, String>> lists = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from lists";
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                String email = cursor.getString(1);
                if (email.equals(em)){
                    HashMap<String, String> list = new HashMap<>();
                    list.put("list_name", cursor.getString(2));
                    lists.add(list);
                }
            }while(cursor.moveToNext());
        }

        return lists;
    }


    public ArrayList<HashMap<String, String>> getTasks(String em, String ln){
        ArrayList<HashMap<String, String>> tasks = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from tasks";
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                String email = cursor.getString(1);
                String list_name = cursor.getString(2);
                if (email.equals(em) && list_name.equals(ln)){
                    HashMap<String, String> task = new HashMap<>();
                    task.put("task_name", cursor.getString(3));
                    tasks.add(task);
                }
            }while(cursor.moveToNext());
        }

        return tasks;
    }

}

