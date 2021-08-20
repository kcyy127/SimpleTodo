package com.example.simpletodo2.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.simpletodo2.entities.Item;

import java.util.List;

@Dao
public interface ItemDao {
    @Query("SELECT * FROM item_table")
    LiveData<List<Item>> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Item item);

    @Update
    void update(Item item);

    @Delete
    void delete(Item item);

    @Query("DELETE from item_table")
    void deleteAll();
}
