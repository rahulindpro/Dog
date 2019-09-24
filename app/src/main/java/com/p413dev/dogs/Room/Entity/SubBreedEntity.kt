package com.p413dev.dogs.Room.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by srujan.gade on 23/09/2019
 */

@Entity(tableName = "SubBreeds")
data class SubBreedEntity(
    @ColumnInfo(name = "_id")
    var subbreedid: Int = 0,

    @PrimaryKey
    @ColumnInfo(name = "_name")
    var subbreedname: String = "",

    @ColumnInfo(name = "_breedName")
    var parentbreedid: String = ""
)