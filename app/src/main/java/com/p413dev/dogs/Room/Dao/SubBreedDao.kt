package com.p413dev.dogs.Room.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.p413dev.dogs.Room.Entity.SubBreedEntity

/**
 * Created by srujan.gade on 23/09/2019
 */

@Dao
interface SubBreedDao {

    @Query("SELECT * FROM SubBreeds WHERE _breedName = :breedName")
    fun getAllSubBreeds(breedName: String): LiveData<List<SubBreedEntity>>

    @Query("SELECT COUNT(*) from SubBreeds WHERE _breedName = :breed")
    fun getSubBreedsCount(breed: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSubBreed(subBreed: SubBreedEntity)
}