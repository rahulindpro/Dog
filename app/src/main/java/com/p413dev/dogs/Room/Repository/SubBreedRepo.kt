package com.p413dev.dogs.Room.Repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.p413dev.dogs.Room.AppDataBase
import com.p413dev.dogs.Room.Dao.SubBreedDao
import com.p413dev.dogs.Room.Entity.SubBreedEntity

/**
 * Created by srujan.gade on 23/09/2019
 */
class SubBreedRepo(application: Application) {
    private var mSubBreedDao: SubBreedDao

    init {
        val appDataBase = AppDataBase.getDatabase(application)
        mSubBreedDao = appDataBase.subBreedDao()
    }

    fun getListOfSubBreeds(breedName: String): LiveData<List<SubBreedEntity>> {
        return mSubBreedDao.getAllSubBreeds(breedName)
    }

    fun getSubBreedCount(breed: String): Int {
        return mSubBreedDao.getSubBreedsCount(breed)
    }

    fun insertSubBreed(subBreed: SubBreedEntity) {
        insertSubBreedTask(mSubBreedDao).execute(subBreed)
    }

    private class insertSubBreedTask internal constructor(private val mAsyncTaskDao: SubBreedDao) :
        AsyncTask<SubBreedEntity, Void, Void>() {

        override fun doInBackground(vararg params: SubBreedEntity): Void? {
            mAsyncTaskDao.insertSubBreed(params[0])
            return null
        }
    }
}