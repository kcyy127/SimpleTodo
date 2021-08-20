package com.example.simpletodo2.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.simpletodo2.dao.ItemDao;
import com.example.simpletodo2.entities.Item;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Item.class}, version = 1, exportSchema = false)
public abstract class ItemDatabase extends RoomDatabase {

    private static final String database_name = "item_database";
    public abstract ItemDao itemDao();
    private static ItemDatabase instance;

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static ItemDatabase getInstance(final Context context) {
        if (instance == null) {
            synchronized (ItemDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            ItemDatabase.class, database_name)
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return instance;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull @NotNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                ItemDao dao = instance.itemDao();
//                dao.deleteAll();
//
//                Item i1 = new Item("Save items", 2021, 3, 4, 2);
//                Item i2 = new Item("Edit text", 2022,1, 27, 1);
//                Item i3 = new Item("Fix appearance", 2019, 4, 23, 4);
//
//                dao.insert(new Item("Save items", 2021, 3, 4, 2));
//                dao.insert(i2);
//                dao.insert(i3);
            });
        }
    };

}
