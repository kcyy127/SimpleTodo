package com.example.simpletodo.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.simpletodo.entities.Item;
import com.example.simpletodo.repositories.ItemRepository;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ItemViewModel extends AndroidViewModel {
    private ItemRepository itemRepo;
    private final LiveData<List<Item>> items;

    public ItemViewModel(@NonNull @NotNull Application application) {
        super(application);
        itemRepo = new ItemRepository(application);
        items = itemRepo.getAllItems();
    }

    public LiveData<List<Item>> getAllItems() {
        return items;
    }

    public void insert(Item item){
        itemRepo.insert(item);
    }

    public void delete(Item item) {
        itemRepo.delete(item);
    }

    public void deleteAll() {
        itemRepo.deleteAll();
    }

    public void update(Item item) {
        itemRepo.update(item);
    }
}
