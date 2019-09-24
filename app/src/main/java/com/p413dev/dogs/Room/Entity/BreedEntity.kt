package com.p413dev.dogs.Room.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by srujan.gade on 23/09/2019
 */
@Entity(tableName = "Breeds")
data class BreedEntity(
    @ColumnInfo(name = "_id")
    val breedid: Int,

    @PrimaryKey
    @ColumnInfo(name = "_breedName")
    var breedname: String = ""
)
