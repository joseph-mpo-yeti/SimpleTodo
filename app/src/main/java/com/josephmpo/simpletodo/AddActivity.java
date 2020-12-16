package com.josephmpo.simpletodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class AddActivity extends AppCompatActivity {
    private final String TAG = "AddActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        getSupportActionBar().hide();

        TextInputEditText editTitle = findViewById(R.id.editTodoTitle);

        Button addTodo = findViewById(R.id.addTodo);
        Button cancel = findViewById(R.id.cancelAdd);

        addTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editTitle.getText().toString();

                if(title.isEmpty()){
                    Toast.makeText(getApplicationContext(), "The title must be specified", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("title", title);
                    setResult(RESULT_OK, intent);
                    finish();
                    overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
                }
            }
        });

        cancel.setOnClickListener(view -> {
            finish();
            overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
    }


}