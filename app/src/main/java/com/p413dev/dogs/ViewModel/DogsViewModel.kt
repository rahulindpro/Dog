package com.p413dev.dogs.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.p413dev.dogs.Room.Entity.DogsEntity
import com.p413dev.dogs.Room.Repository.DogsRepo
import org.json.JSONArray
import org.json.JSONObject

/**
 * Created by srujan.gade on 24/09/2019
 */
class DogsViewModel(application: Application, breed: String, subBreed: String) :
    AndroidViewModel(application) {

    private var mDogsRepo: DogsRepo = DogsRepo(application)
    private lateinit var url: String

    init {
        fetchAndStoreDogs(breed, subBreed)
    }

    internal fun getDogsByBreedAndSubBreed(
        breed: String,
        subBreed: String
    ): LiveData<List<DogsEntity>> {
        return mDogsRepo.getListOfDogs(breed, subBreed)
    }

    internal fun getDogsByBreed(breed: String): LiveData<List<DogsEntity>> {
        return mDogsRepo.getDogsByBreed(breed)
    }

    private fun fetchAndStoreDogs(breed: String, subBreed: String) {
        var count: Int = 0
        if (subBreed.isNullOrBlank() || subBreed.equals("null") || subBreed.isEmpty()) {
            url = "https://dog.ceo/api/breed/$breed/images"
        } else {
            url = "https://dog.ceo/api/breed/$breed/$subBreed/images"
        }
        Log.v("dogs", url)
        AndroidNetworking.get(url)
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    Log.v("dogs", "Success response $response")
                    val mObject = JSONObject(response.toString())
                    val dogsArray: JSONArray = mObject.getJSONArray("message")
                    Log.v("dogs", "datelength ${dogsArray.length()}")
                    if (dogsArray.length() > 0) {
                        count = dogsArray.length()
                        handleDogs(dogsArray, breed, subBreed, count)
                    } else {
                        count = 0
                        // no dogs
                    }
                }

                override fun onError(error: ANError) {
                    count = 0
                    Log.v("dogs", "Failure response ${error.message}")
                }
            })
    }

    private fun handleDogs(
        dogsArray: JSONArray,
        breed: String,
        subBreed: String,
        count: Int
    ): Int {
        for (i in 1 until dogsArray.length()) {
            val dogsData: DogsEntity =
                if (subBreed.isNullOrBlank() || subBreed.equals("null") || subBreed.isEmpty()) {
                    DogsEntity(1, dogsArray[i].toString(), breed, "")
                } else {
                    DogsEntity(1, dogsArray[i].toString(), breed, subBreed)
                }
            mDogsRepo.insertDog(dogsData)
        }
        return count
    }
}