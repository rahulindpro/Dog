package com.p413dev.dogs.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.p413dev.dogs.Room.Entity.BreedEntity
import com.p413dev.dogs.Room.Entity.SubBreedEntity
import com.p413dev.dogs.Room.Repository.BreedRepo
import com.p413dev.dogs.Room.Repository.SubBreedRepo
import org.json.JSONObject

/**
 * Created by srujan.gade on 24/09/2019
 */
class SubBreedViewModel(application: Application) : AndroidViewModel(application) {
    private var mSubBreedRepo: SubBreedRepo = SubBreedRepo(application)

    init {
        fetchAndStoreData("")
    }

    private fun fetchAndStoreData(breed: String) {
        AndroidNetworking.get("https://dog.ceo/api/breed/$breed/list")
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    val mObject = JSONObject(response.toString())
                    if (mObject.optString("status").equals("success")) {
                        val messageOBJ: JSONObject = mObject.getJSONObject("message")
                        handleResponse(messageOBJ)
                    } else {
                        showProgressBar(false)
                    }
                }

                override fun onError(error: ANError) {
                    showProgressBar(false)
                }
            })
    }

    private fun handleResponse(response: JSONObject) {
        val iterator = response.keys()
        var i = 1
        while (iterator.hasNext()) {
            val key = iterator.next()
            val mBreed = BreedEntity(i, key)
            // mBreedRepo.insertBreed(mBreed);
            i++
        }
        showProgressBar(false)
    }

    internal fun showProgressBar(status: Boolean): Boolean {
        return status
    }

    internal fun getAllSubBreeds(breed: String): LiveData<List<SubBreedEntity>> {
        return mSubBreedRepo.getListOfSubBreeds(breed)
    }
}