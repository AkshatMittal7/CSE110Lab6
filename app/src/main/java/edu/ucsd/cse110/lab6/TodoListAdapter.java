package edu.ucsd.cse110.lab6;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.ViewHolder69> {

    private List<TodoListItem> todoItems = Collections.emptyList();
    private Consumer<TodoListItem> onCheckBoxClicked;
    private BiConsumer<TodoListItem,String> onTextEditedHandler;
    private Consumer<TodoListItem> onDelete;

    public void setTodoIListItems(List<TodoListItem> newTodoItems){
        this.todoItems.clear();
        this.todoItems = newTodoItems;
        notifyDataSetChanged();
    }

    public void setOnCheckBoxClickedHandler(Consumer<TodoListItem> onCheckBoxClicked){
        this.onCheckBoxClicked = onCheckBoxClicked;
    }

    public void setOnTextEditedHandler(BiConsumer<TodoListItem, String> onTextEdited){
        this.onTextEditedHandler = onTextEdited;
    }

    public void setOnDeleteItemHandler(Consumer<TodoListItem> onDelete){
        this.onDelete = onDelete;
    }

    @NonNull
    @Override
    public ViewHolder69 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_list_item,parent,false);

        return new ViewHolder69(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder69 holder, int position) {
        holder.setTodoItem(todoItems.get(position));
    }
/*
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }
*/
    @Override
    public int getItemCount() {
        return todoItems.size();
    }

    @Override
    public long getItemId(int position) {
        return todoItems.get(position).id;
    }

    public class ViewHolder69 extends RecyclerView.ViewHolder{
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

            this.textView.setOnFocusChangeListener((view,hasFocus) -> {
                if(!hasFocus){
                    onTextEditedHandler.accept(todoItem, textView.getText().toString());
                }
            });

            this.checkBox.setOnClickListener(view -> {
                if(onCheckBoxClicked == null) return;
                onCheckBoxClicked.accept(todoItem);
            });

            this.deleteButton.setOnClickListener(view -> {
                onDelete.accept(todoItem);
            });
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
}
