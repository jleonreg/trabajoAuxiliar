package es.unex.dinopedia;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import es.unex.dinopedia.database.DinosaurioCRUD;
import es.unex.dinopedia.roomdb.DinosaurioDatabase;

public class DinosaurioManagerActivity extends AppCompatActivity {

  // Add a ToDoItem Request Code
    private static final int ADD_TODO_ITEM_REQUEST = 0;

    private static final String TAG = "Lab-UserInterface";

    // IDs for menu items
    private static final int MENU_DELETE = Menu.FIRST;
    private static final int MENU_DUMP = Menu.FIRST + 1;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private DinosaurioAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_manager);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // - Get a reference to the RecyclerView
        mRecyclerView = findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        // - Set a Linear Layout Manager to the RecyclerView
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // - Create a new Adapter for the RecyclerView
        // specify an adapter (see also next example)
        View view = findViewById(android.R.id.content);
        mAdapter = new DinosaurioAdapter(this, new DinosaurioAdapter.OnItemClickListener() {
            @Override public void onItemClick(Dinosaurio item) {
                Snackbar.make(view, "Item "+item.getName()+" Clicked", Snackbar.LENGTH_LONG).show();
            }
        });

        // - Attach the adapter to the RecyclerView
        mRecyclerView.setAdapter(mAdapter);

        DinosaurioCRUD crud = DinosaurioCRUD.getInstance(this);
        DinosaurioDatabase.getInstance(this);
    }
    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        log("Entered onActivityResult()");

        //  - Check result code and request code.
        // If user submitted a new ToDoItem
        // Create a new ToDoItem from the data Intent
        // and then add it to the adapter
        if (requestCode == ADD_TODO_ITEM_REQUEST){
            if (resultCode == RESULT_OK){
                Dinosaurio item = new Dinosaurio(data);

                //insert into DB
                DinosaurioCRUD crud = DinosaurioCRUD.getInstance(this);
                long id = crud.insert(item);

                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        DinosaurioDatabase database = DinosaurioDatabase.getInstance(DinosaurioManagerActivity.this);
                        long id = database.getDao().insert(item);

                        //update item ID
                        item.setId(id);

                        //insert into adapter list
                        runOnUiThread(() -> mAdapter.add(item));
                    }
                });
            }
        }
    }
    */
    @Override
    public void onResume() {
        super.onResume();

        // Load saved ToDoItems, if necessary

        if (mAdapter.getItemCount() == 0)
            loadItems();
    }

    @Override
    protected void onPause() {
        super.onPause();

        // ALTERNATIVE: Save all ToDoItems

    }

    @Override
    protected void onDestroy() {
        DinosaurioCRUD crud = DinosaurioCRUD.getInstance(this);
        DinosaurioDatabase.getInstance(this).close();
        crud.close();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(Menu.NONE, MENU_DELETE, Menu.NONE, "Delete all");
        menu.add(Menu.NONE, MENU_DUMP, Menu.NONE, "Dump to log");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_DELETE:
                DinosaurioCRUD crud = DinosaurioCRUD.getInstance(this);
                crud.deleteAll();
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        DinosaurioDatabase.getInstance(DinosaurioManagerActivity.this).getDao().deleteAll();
                       runOnUiThread(() -> mAdapter.clear());
                    }
                });

                return true;
            case MENU_DUMP:
                dump();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void dump() {

        for (int i = 0; i < mAdapter.getItemCount(); i++) {
            String data = ((Dinosaurio) mAdapter.getItem(i)).toLog();
            log("Item " + i + ": " + data.replace(Dinosaurio.ITEM_SEP, ","));
        }

    }

    // Load stored Dinosaurios
    private void loadItems() {
        DinosaurioCRUD crud = DinosaurioCRUD.getInstance(this);
        List<Dinosaurio> items = crud.getAll();

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<Dinosaurio> items = DinosaurioDatabase.getInstance(DinosaurioManagerActivity.this).getDao().getAll();
                runOnUiThread( () -> mAdapter.load(items));
            }
        });

    }

    private void log(String msg) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(TAG, msg);
    }
}
