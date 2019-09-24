package com.p413dev.dogs.Room.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by srujan.gade on 23/09/2019
 */
@Entity(tableName = "Dogs")
data class DogsEntity(

    @ColumnInfo(name = "_id")
    var dogid: Int = 0,

    @PrimaryKey
    @ColumnInfo(name = "_image")
    var dogimage: String = "",

    @ColumnInfo(name = "_breedName")
    var breedid: String = "",

    @ColumnInfo(name = "_subBreedName")
    var subbreedid: String = ""
)