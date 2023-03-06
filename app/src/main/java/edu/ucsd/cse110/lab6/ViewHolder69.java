package edu.ucsd.cse110.lab6;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder69 extends RecyclerView.ViewHolder {
    private final TextView textView;
    private TodoListItem todoItem;
    private CheckBox completed;
    private final CheckBox checkBox;
    private final ImageButton deleteButton;

    public ViewHolder69(@NonNull View itemView){
        super(itemView);
        this.textView = itemView.findViewById(R.id.todo_item_text);
        this.completed = itemView.findViewById(R.id.completed);
        this.checkBox = itemView.findViewById(R.id.completed);
        this.deleteButton = itemView.findViewById(R.id.delete_button);
/*
        this.checkBox.setOnClickListener(view -> {
            if(onCheckBoxClicked == null) return;
            onCheckBoxClicked.accept(todoItem);
        });

 */
    }
    public TodoListItem getTodoItem() {
        return todoItem;
    }

    public void setTodoItem (TodoListItem todoItem){
        this.todoItem = todoItem;
        this.textView.setText(todoItem.text);
        this.completed.setChecked(todoItem.completed);
        this.checkBox.setChecked(todoItem.completed);
    }
}
