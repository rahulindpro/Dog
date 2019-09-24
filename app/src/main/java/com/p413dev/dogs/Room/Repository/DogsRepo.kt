package com.p413dev.dogs.Room.Repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.p413dev.dogs.Room.AppDataBase
import com.p413dev.dogs.Room.Dao.DogsDao
import com.p413dev.dogs.Room.Entity.DogsEntity

/**
 * Created by srujan.gade on 23/09/2019
 */
class DogsRepo(application: Application) {

    var mDogsDao: DogsDao

    init {
        val appDataBase = AppDataBase.getDatabase(application)
        mDogsDao = appDataBase.dogsDao()
    }

    fun getListOfDogs(breed: String, subBreed: String): LiveData<List<DogsEntity>> {
        return mDogsDao.getAllDogs(breed, subBreed)
    }

    fun getDogsByBreed(breed: String): LiveData<List<DogsEntity>> {
        return mDogsDao.getDogsByBreed(breed)
    }


    fun getDogsCountByBreedAndSubBreed(breed: String, subBreed: String): Int {
        return mDogsDao.getDogsCount(breed, subBreed)
    }

    fun insertDog(breed: DogsEntity) {
        insertDogTask(mDogsDao).execute(breed)
    }

    private class insertDogTask internal constructor(private val mAsyncTaskDao: DogsDao) :
        AsyncTask<DogsEntity, Void, Void>() {

        override fun doInBackground(vararg params: DogsEntity): Void? {
            mAsyncTaskDao.insertAllDogs(params[0])
            return null
        }
    }
}