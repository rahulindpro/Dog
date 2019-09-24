package com.p413dev.dogs.Room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.p413dev.dogs.Room.Dao.BreedDao;
import com.p413dev.dogs.Room.Dao.DogsDao;
import com.p413dev.dogs.Room.Dao.SubBreedDao;
import com.p413dev.dogs.Room.Entity.BreedEntity;
import com.p413dev.dogs.Room.Entity.DogsEntity;
import com.p413dev.dogs.Room.Entity.SubBreedEntity;

/**
 * Created by srujan.gade on 23/09/2019
 */
@Database(
        entities = {BreedEntity.class, DogsEntity.class, SubBreedEntity.class},
        exportSchema = false,
        version = 1
)

public abstract class AppDataBase extends RoomDatabase {

    public abstract BreedDao breedDao();

    public abstract SubBreedDao subBreedDao();

    public abstract DogsDao dogsDao();

    private static volatile AppDataBase Instance;

    public static AppDataBase getDatabase(Context context) {
        if (Instance == null) {
            synchronized (AppDataBase.class) {
                if (Instance == null) {
                    Instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDataBase.class, "DOGS").build();
                }
            }
        }
        return Instance;
    }

}
