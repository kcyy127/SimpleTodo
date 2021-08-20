package com.example.simpletodo2.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.simpletodo2.dao.ItemDao;
import com.example.simpletodo2.database.ItemDatabase;
import com.example.simpletodo2.entities.Item;

import java.util.List;

public class ItemRepository {
    private ItemDao itemDao;
    private LiveData<List<Item>> items;

    public ItemRepository(Application application) {
        ItemDatabase itemDatabase = ItemDatabase.getInstance(application);
        itemDao = itemDatabase.itemDao();
        items = itemDao.getAll();
    }

    public LiveData<List<Item>> getAllItems() {
        return items;
    }

    public void insert(Item item) {
        ItemDatabase.databaseWriteExecutor.execute(() -> {
            itemDao.insert(item);
        });
    }

    public void delete(Item item) {
        ItemDatabase.databaseWriteExecutor.execute(() -> {
            itemDao.delete(item);
        });
    }

    public void deleteAll() {
        ItemDatabase.databaseWriteExecutor.execute(() -> {
            itemDao.deleteAll();
        });
    }

    public void update(Item item) {
        ItemDatabase.databaseWriteExecutor.execute(() -> {
            itemDao.update(item);
        });
    }
}
