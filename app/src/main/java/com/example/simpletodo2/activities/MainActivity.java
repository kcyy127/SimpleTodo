package com.example.simpletodo2.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.simpletodo2.R;
import com.example.simpletodo2.adapters.ItemsAdapter;
import com.example.simpletodo2.dao.ItemDao;
import com.example.simpletodo2.database.ItemDatabase;
import com.example.simpletodo2.entities.Item;
import com.example.simpletodo2.viewmodels.ItemViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_ADD_ITEM = 1;
    public static final int REQUEST_CODE_UPDATE_ITEM = 2;
    private ItemViewModel itemViewModel;

    private RecyclerView itemRecyclerView;
    private ItemsAdapter itemsAdapter;
    private ImageView moreButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moreButton = findViewById(R.id.moreButton);

        itemRecyclerView = findViewById(R.id.itemRecyclerView);
        itemsAdapter = new ItemsAdapter(new ItemsAdapter.ItemDiff());
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(itemRecyclerView);

        itemRecyclerView.setAdapter(itemsAdapter);
        itemRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);

        itemViewModel.getAllItems().observe(this, items -> {
            itemsAdapter.submitList(items);
        });

        //Add Item
        FloatingActionButton addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActivityAddItem.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_ITEM);
            }
        });

        //Edit Item
        itemsAdapter.setOnItemClickListener(new ItemsAdapter.ClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                Item item = itemsAdapter.getItemAtPosition(position);
                launchUpdateItemActivity(item);
            }
        });

        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context wrapper = new ContextThemeWrapper(MainActivity.this, R.style.MyPopupMenu);
                PopupMenu popupMenu = new PopupMenu(wrapper, moreButton);
                popupMenu.getMenuInflater().inflate(R.menu.main_menu, popupMenu.getMenu());
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case (R.id.delete):
                                itemViewModel.deleteAll();
                                return true;
                        }
                        return false;
                    }
                });
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD_ITEM && resultCode == RESULT_OK) {
            String text = data.getStringExtra("item_text");
            int year = data.getIntExtra("item_year", 0);
            int month = data.getIntExtra("item_month", 0);
            int date = data.getIntExtra("item_date", 0);
            int priority = data.getIntExtra("item_priority", -1);

            Item item_temp = new Item(text, year, month, date, priority);

            itemViewModel.insert(item_temp);
        }

        if (requestCode == REQUEST_CODE_UPDATE_ITEM && resultCode == RESULT_OK) {
            int id = data.getIntExtra("item_id", -1);
            String text = data.getStringExtra("item_text");
            int year = data.getIntExtra("item_year", 0);
            int month = data.getIntExtra("item_month", 0);
            int date = data.getIntExtra("item_date", 0);
            int priority = data.getIntExtra("item_priority", -1);

            if (id != -1) {
                Item item_temp = new Item(id, text, year, month, date, priority);
                itemViewModel.update(item_temp);
            }
        }
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback =
            new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT) {
                @Override
                public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                    Item item_to_del = itemsAdapter.getItemAtPosition(viewHolder.getAdapterPosition());
                    itemViewModel.delete(item_to_del);
                }
            };


    private void launchUpdateItemActivity(Item item) {
        Intent intent = new Intent(this, ActivityAddItem.class);
        intent.putExtra("item_id", item.getId());
        intent.putExtra("item_text", item.getText());
        intent.putExtra("item_year", item.getYear());
        intent.putExtra("item_month", item.getMonth());
        intent.putExtra("item_date", item.getDate());
        intent.putExtra("item_priority", item.getPriority());
        startActivityForResult(intent, REQUEST_CODE_UPDATE_ITEM);
    }
}