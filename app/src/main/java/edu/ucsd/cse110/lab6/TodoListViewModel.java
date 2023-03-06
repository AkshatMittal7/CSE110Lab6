package edu.ucsd.cse110.lab6;


import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TodoListViewModel extends AndroidViewModel {
    private LiveData<List<TodoListItem>> todoListItems;
    private final ToDoListItemDao toDoListItemDao;

    public TodoListViewModel(@NonNull Application application){
        super(application);
        Context context = getApplication().getApplicationContext();
        TodoDataBase db = TodoDataBase.getSingleton(context);
        toDoListItemDao = db.todoListItemDao();
    }

    public LiveData<List<TodoListItem>> getTodoListItems(){
        if(todoListItems==null){
            loadUsers();
        }
        return todoListItems;
    }

    private void loadUsers(){
        todoListItems = toDoListItemDao.getAllLive();
    }

    public void toggleCompleted(TodoListItem todoListItem){
        todoListItem.completed = !todoListItem.completed;
        toDoListItemDao.update(todoListItem);
    }

    public void updateText(TodoListItem todoListItem, String newText){
        todoListItem.text = newText;
        toDoListItemDao.update(todoListItem);
    }

    public void createTodo(String text){
        int endOfListOrder = toDoListItemDao.getOrderForAppend();
        TodoListItem newItem = new TodoListItem(text,false,endOfListOrder);
        toDoListItemDao.insert(newItem);
    }

    public void deleteTodo(TodoListItem toDelete) {
        toDoListItemDao.delete(toDelete);
    }
}
