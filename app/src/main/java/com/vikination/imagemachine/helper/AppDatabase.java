package com.vikination.imagemachine.helper;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.vikination.imagemachine.dao.MachineDao;
import com.vikination.imagemachine.model.Converters;
import com.vikination.imagemachine.model.Machine;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Machine.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract MachineDao machineDao();
    private static AppDatabase instance;
    public static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriterExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static AppDatabase db(Context context){
        return Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "machine-db").build();
    }

    public static AppDatabase getInstance(Context context){
        if (instance == null) {
            synchronized (AppDatabase.class){
                if (instance == null){
                    instance = AppDatabase.db(context);
                }
                return instance;
            }
        }
        return instance;
    }
}
