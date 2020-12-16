package com.josephmpo.simpletodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class EditActivity extends AppCompatActivity {
    private TextInputEditText editTitle;
    private RadioButton done;
    private RadioButton pending;
    private int pos;
    private boolean completed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        getSupportActionBar().hide();

        Bundle extras = getIntent().getExtras();

        pos = extras.getInt("position");

        editTitle = findViewById(R.id.editTodoTitle);
        editTitle.setText(extras.getString("title"));

        pending = findViewById(R.id.pendingRadio);
        done = findViewById(R.id.completedRadio);

        completed = extras.getBoolean("completed");

        if(completed){
            done.setChecked(true);
            pending.setChecked(false);
        } else {
            done.setChecked(false);
            pending.setChecked(true);
        }

    }

    public void updateCompleted(View view){
        if(view.getId() == R.id.completedRadio){
            completed = true;
        } else {
            completed = false;
        }
    }

    public void cancel(View view){
        finish();
        overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
    }

    public void save(View view){
        Intent data = new Intent();
        String title = editTitle.getText().toString();

        if(!title.isEmpty()){
            data.putExtra("title", title);
            data.putExtra("completed", completed);
            data.putExtra("position", pos);

            setResult(RESULT_OK, data);
            finish();
            overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
        } else {
            Toast.makeText(getApplicationContext(), "Invalid title", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
    }
}