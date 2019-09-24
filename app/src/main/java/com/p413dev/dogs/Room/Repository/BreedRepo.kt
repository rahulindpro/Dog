package com.p413dev.dogs.Room.Repository

import android.app.Application
import android.os.AsyncTask

import androidx.lifecycle.LiveData

import com.p413dev.dogs.Room.AppDataBase
import com.p413dev.dogs.Room.Dao.BreedDao
import com.p413dev.dogs.Room.Entity.BreedEntity

/**
 * Created by srujan.gade on 23/09/2019
 */

class BreedRepo(application: Application) {

    var mBreedDao: BreedDao

    init {
        val appDataBase = AppDataBase.getDatabase(application)
        mBreedDao = appDataBase.breedDao()
    }

    fun getListOfBreeds(): LiveData<List<BreedEntity>> {
        return mBreedDao.getBreedList()
    }

    fun getBreedsCount(): Int {
        return mBreedDao.getBreedCount()
    }

    fun insertBreed(breed: BreedEntity) {
        insertBreedTask(mBreedDao).execute(breed)
    }

    private class insertBreedTask internal constructor(private val mAsyncTaskDao: BreedDao) :
        AsyncTask<BreedEntity, Void, Void>() {

        override fun doInBackground(vararg params: BreedEntity): Void? {
            mAsyncTaskDao.insertBreed(params[0])
            return null
        }
    }
}