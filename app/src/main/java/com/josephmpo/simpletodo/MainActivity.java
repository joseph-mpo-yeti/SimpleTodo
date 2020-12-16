package com.josephmpo.simpletodo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionManager;

import android.content.Intent;
import android.os.Bundle;
import org.apache.commons.io.FileUtils;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements TodoAdapter.OnItemClickedListener, TodoAdapter.OnItemLongClickedListener {
    private List<Todo> todos;
    private TodoAdapter adapter;
    private TextView defaultMessage;
    private final String TAG = "MainActivity";
    public static final int EDIT_TODO = 200;
    public static final int NEW_TODO = 300;
    private RecyclerView rvView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        todos = new ArrayList<>();
        loadItems();

        Log.i(TAG, "onCreate: "+todos.toString());

        rvView = findViewById(R.id.todosRecyclerView);
        defaultMessage = findViewById(R.id.defaultMessage);

        adapter = new TodoAdapter(todos, this, this);

        rvView.setAdapter(adapter);
        rvView.setLayoutManager(new LinearLayoutManager(this));

        updateMessageVisibility();
    }

    public void startAddActivity(View view){
        Intent intent = new Intent(MainActivity.this, AddActivity.class);
        startActivityForResult(intent, NEW_TODO);
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
    }

    private void updateMessageVisibility() {
        if(todos.size() == 0){
            defaultMessage.setVisibility(View.VISIBLE);
        } else {
            defaultMessage.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            switch (requestCode){
                case NEW_TODO:
                    addNewTodo(extras);
                    break;
                case EDIT_TODO:
                    editTodo(extras);
                    break;
                default:
                    break;
            }
        }
    }

    private void addNewTodo(Bundle extras) {
        todos.add(new Todo(
                extras.getString("title"),
                false
        ));
        updateMessageVisibility();
        saveItems();
        adapter.notifyItemInserted(todos.size()-1);
        Toast.makeText(getApplicationContext(), extras.getString("title") + " added", Toast.LENGTH_SHORT).show();
    }

    private void editTodo(Bundle extras) {
        Todo item = new Todo(
                extras.getString("title"),
                extras.getBoolean("completed")
        );
        todos.set(extras.getInt("position"), item);
        saveItems();
        adapter.notifyItemChanged(extras.getInt("position"));
        Toast.makeText(getApplicationContext(), "Todo updated", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClickedListener(int position) {
        Todo item = todos.get(position);
        Intent intent = new Intent(getApplicationContext(), EditActivity.class);
        intent.putExtra("title", item.title);
        intent.putExtra("position", position);
        intent.putExtra("completed", item.completed);
        startActivityForResult(intent, EDIT_TODO);
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
    }

    @Override
    public void onItemLongClickedListener(int position) {
        String title = todos.get(position).title;
        todos.remove(position);
        saveItems();
        adapter.notifyItemRemoved(position);
        Toast.makeText(getApplicationContext(), title + " deleted", Toast.LENGTH_SHORT).show();
        updateMessageVisibility();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
    }

    private File getDataFile(){
        return new File(getFilesDir(), "data.txt");
    }

    private void loadItems(){
        try {
            List<String> lines = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
            for(String line:lines){
                String[] parts = line.split("/-/-/");
                if(parts.length == 2){
                    todos.add(
                            new Todo(
                                    parts[0],
                                    Boolean.parseBoolean(parts[1])
                            )
                    );
                }
            }

        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void saveItems(){
        List<String> lines = new ArrayList<>();
        for(Todo item:todos){
            lines.add(item.title + "/-/-/" + item.completed);
        }

        try {
            FileUtils.writeLines(getDataFile(), lines);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

}