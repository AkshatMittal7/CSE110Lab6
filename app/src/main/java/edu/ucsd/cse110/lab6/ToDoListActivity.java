package edu.ucsd.cse110.lab6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;
import androidx.recyclerview.widget.RecyclerView;

public class ToDoListActivity extends AppCompatActivity {
    public RecyclerView recyclerView;
    private TodoListViewModel viewModel;
    private EditText newTodoText;
    private Button addTdoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);


        viewModel = new ViewModelProvider(this).get(TodoListViewModel.class);
        


        //ToDoListItemDao todoListItemDao = TodoDataBase.getSingleton(this).todoListItemDao();
        //List<TodoListItem> todoListItems = todoListItemDao.getAll();



        TodoListAdapter adapter =new TodoListAdapter();
        adapter.setHasStableIds(true);
        adapter.setOnTextEditedHandler((viewModel::updateText));
        adapter.setOnCheckBoxClickedHandler(viewModel::toggleCompleted);
        adapter.setOnDeleteItemHandler(viewModel::deleteTodo);

        //adapter.setTodoIListItems(todoListItems);
        viewModel.getTodoListItems().observe(this,adapter::setTodoIListItems);
        
        

        recyclerView = findViewById(R.id.todo_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        //RecyclerView recyclerView = findViewById(R.id.recyclerview1);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //recyclerView.setAdapter(adapter);

        //List < TodoListItem > todos = TodoListItem.loadJSON(this, "demo_todos.json");
        //adapter.setTodoIListItems(todos);
        //Log.d("TodoListActivity",todos.toString());

        this.newTodoText = this.findViewById(R.id.new_todo_text);
        this.addTdoButton = this.findViewById(R.id.add_todo_btn);

        addTdoButton.setOnClickListener(this::onAddTodoClicked);
    }

    void onAddTodoClicked(View view){
        String text = newTodoText.getText().toString();
        newTodoText.setText("");
        viewModel.createTodo(text);
    }

    public void deleteItem(View view) {

    }
}