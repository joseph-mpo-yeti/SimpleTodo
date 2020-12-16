package com.josephmpo.simpletodo;

import java.time.LocalDate;

public class Todo {
    boolean completed;
    String title;

    public Todo(String title, boolean completed) {
        this.completed = completed;
        this.title = title;
    }

}
