package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

//import android.os.FileUtils;

public class MainActivity extends AppCompatActivity {
    List<String> items;

    Button btnAdd;
    EditText etItem;
    RecyclerView rvItems;
    itemsAdapter ItemsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        etItem = findViewById(R.id.editTextItem);
        rvItems = findViewById(R.id.rvItems);



        loadItems();
//        items.add("Buy milk");
//        items.add("do homework");
//        items.add("gym");


        itemsAdapter.OnLongClickListener onLongClickListener = new itemsAdapter.OnLongClickListener(){
            @Override
            public void OnItemLongClicked(int position){
                items.remove(position);
                ItemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), "Item was removed", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        };


        ItemsAdapter = new itemsAdapter(items, onLongClickListener);
        rvItems.setAdapter(ItemsAdapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));

        btnAdd.setOnClickListener(new View.OnClickListener (){
            @Override

            public void onClick(View v){
                String todoItem = etItem.getText().toString();
                items.add(todoItem);
                ItemsAdapter.notifyItemInserted(items.size() - 1);
                etItem.setText("");
                Toast.makeText(getApplicationContext(), "Item was added", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private File getDataFile(){
        return new File(getFilesDir(), "data.txt");
    }

    private void loadItems(){
        try{
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        }

        catch (IOException e){
            Log.e("MainActivity", "Error reading items", e);
            items = new ArrayList<>();
        }
    }

    private void saveItems(){
        try{
            FileUtils.writeLines(getDataFile(), items);
        }

        catch(IOException e){
            Log.e("MainActivity", "Error writng items", e);
        }
    }
}
