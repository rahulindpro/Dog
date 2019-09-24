package com.p413dev.dogs.Room.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.p413dev.dogs.Room.Entity.DogsEntity

/**
 * Created by srujan.gade on 23/09/2019
 */
@Dao
interface DogsDao {
    @Query("SELECT * FROM Dogs WHERE _breedName = :breed AND _subBreedName=:subBreed")
    fun getAllDogs(breed: String, subBreed: String): LiveData<List<DogsEntity>>

    @Query("SELECT COUNT(*) FROM Dogs WHERE _breedName = :breed AND _subBreedName=:subBreed")
    fun getDogsCount(breed: String, subBreed: String): Int

    @Query("SELECT COUNT(*) FROM Dogs WHERE _breedName = :breed")
    fun getDogsCountByBreed(breed: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllDogs(mDogs: DogsEntity)

    @Query("SELECT * FROM Dogs WHERE _breedName = :breed")
    fun getDogsByBreed(breed: String): LiveData<List<DogsEntity>>
}