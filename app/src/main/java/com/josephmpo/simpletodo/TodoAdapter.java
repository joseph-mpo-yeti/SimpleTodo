package com.josephmpo.simpletodo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {
    private List<Todo> items;
    private OnItemClickedListener onItemClickedListener;
    private OnItemLongClickedListener onItemLongClickedListener;

    public interface OnItemClickedListener {
        void onItemClickedListener(int position);
    }

    public interface OnItemLongClickedListener {
        void onItemLongClickedListener(int position);
    }

    public TodoAdapter(List<Todo> items, OnItemClickedListener onItemClickedListener, OnItemLongClickedListener onItemLongClickedListener) {
        this.items = items;
        this.onItemClickedListener = onItemClickedListener;
        this.onItemLongClickedListener = onItemLongClickedListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Todo item = items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, completed;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.item_title);
            completed = itemView.findViewById(R.id.item_completed);

            itemView.setOnClickListener(view -> {
                onItemClickedListener.onItemClickedListener(getAdapterPosition());
            });

            itemView.setOnLongClickListener(view -> {
                onItemLongClickedListener.onItemLongClickedListener(getAdapterPosition());
                return true;
            });


        }

        public void bind(Todo item) {
            title.setText(item.title);
            completed.setText(item.completed ? "Done" : "Pending");
        }
    }
}
