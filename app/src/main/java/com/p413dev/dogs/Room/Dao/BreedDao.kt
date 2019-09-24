package com.p413dev.dogs.Room.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.p413dev.dogs.Room.Entity.BreedEntity

/**
 * Created by srujan.gade on 23/09/2019
 */
@Dao
interface BreedDao {

    @Query("SELECT * FROM Breeds ORDER BY _breedName ASC")
    fun getBreedList(): LiveData<List<BreedEntity>>

    @Query("SELECT COUNT(*) from Breeds")
    fun getBreedCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBreed(mBreedEntity: BreedEntity)
}