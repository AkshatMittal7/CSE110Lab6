package edu.ucsd.cse110.lab6;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.manipulation.Ordering;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class TodoListActivityTest {
    TodoDataBase testDb;
    ToDoListItemDao toDoListItemDao;

    private static void forceLayout(RecyclerView recyclerView){
        recyclerView.measure(View.MeasureSpec.UNSPECIFIED,View.MeasureSpec.UNSPECIFIED);
        recyclerView.layout(0,0,1080,2280);
    }

    @Before
    public void resetDatabase(){
        Context context = ApplicationProvider.getApplicationContext();
        testDb = Room.inMemoryDatabaseBuilder(context,TodoDataBase.class)
                .allowMainThreadQueries().build();
        TodoDataBase.injectTestDatabase(testDb);

        List<TodoListItem> todos = TodoListItem.loadJSON(context,"demo_todos.json");
        toDoListItemDao = testDb.todoListItemDao();
        toDoListItemDao.insertAll(todos);
    }

    @Test
    public void testEditTodoText(){
        String newText = "Ensure all tests pass";
        ActivityScenario<ToDoListActivity> scenario =
                ActivityScenario.launch(ToDoListActivity.class);

        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.moveToState(Lifecycle.State.RESUMED);

        scenario.onActivity(activity -> {
            RecyclerView recyclerView = activity.recyclerView;
            RecyclerView.ViewHolder firstVH = recyclerView.findViewHolderForAdapterPosition(0);
            assertNotNull(firstVH);
            long id = firstVH.getItemId();
            System.out.println(id);

            EditText todoText = firstVH.itemView.findViewById(R.id.todo_item_text);
            todoText.requestFocus();
            todoText.setText("Ensure all tests pass");
            todoText.clearFocus();

            TodoListItem editedItem = toDoListItemDao.get(id);
            assertEquals(newText,editedItem.text);
        });
    }

    @Test
    public void testAddNewTodo(){
        String newText = "Ensure all tests pass";
        ActivityScenario<ToDoListActivity> scenario =
                ActivityScenario.launch(ToDoListActivity.class);

        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.moveToState(Lifecycle.State.RESUMED);

        scenario.onActivity(activity -> {

            List<TodoListItem> beforeTodoList = toDoListItemDao.getAll();

            EditText newTodoText = activity.findViewById(R.id.new_todo_text);
            Button addTodoButton = activity.findViewById(R.id.add_todo_btn);



            newTodoText.setText((newText));
            addTodoButton.performClick();

            List<TodoListItem> afterTodoList = toDoListItemDao.getAll();
            assertEquals(beforeTodoList.size()+1,afterTodoList.size());
            assertEquals(newText,afterTodoList.get(afterTodoList.size()-1).text);

        });

    }

    @Test
    public void testDeleteTodo() {

        ActivityScenario<ToDoListActivity> scenario =
                ActivityScenario.launch(ToDoListActivity.class);

        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.moveToState(Lifecycle.State.RESUMED);

        scenario.onActivity(activity -> {

            List<TodoListItem> beforeTodoList = toDoListItemDao.getAll();
            System.out.println(beforeTodoList.size());

            RecyclerView recyclerView = activity.recyclerView;
            RecyclerView.ViewHolder firstVH = recyclerView.findViewHolderForAdapterPosition(0);
            assertNotNull(firstVH);
            long id = firstVH.getItemId();

            View deleteButton = firstVH.itemView.findViewById(R.id.delete_button);
            deleteButton.performClick();


            List<TodoListItem> afterTodoList = toDoListItemDao.getAll();
            assertEquals(beforeTodoList.size() - 1, afterTodoList.size());

            TodoListItem editedItem = toDoListItemDao.get(id);
            assertNull(editedItem);
        });
    }

    @Test
    public void testCheckOff(){
        String newText = "Ensure all tests pass";
        ActivityScenario<ToDoListActivity> scenario =
                ActivityScenario.launch(ToDoListActivity.class);

        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.moveToState(Lifecycle.State.RESUMED);

        scenario.onActivity(activity -> {
            RecyclerView recyclerView = activity.recyclerView;
            RecyclerView.ViewHolder firstVH = recyclerView.findViewHolderForAdapterPosition(0);
            assertNotNull(firstVH);
            long id = firstVH.getItemId();

            EditText todoText = firstVH.itemView.findViewById(R.id.todo_item_text);
            CheckBox checkbox = firstVH.itemView.findViewById(R.id.completed);
            TodoListItem editedItem = toDoListItemDao.get(id);
            boolean oldVal = editedItem.completed;
            todoText.requestFocus();
            checkbox.performClick();
            todoText.clearFocus();


            TodoListItem editedItem2 = toDoListItemDao.get(id);

            assertEquals(editedItem2.completed,!oldVal);
        });
    }


}
