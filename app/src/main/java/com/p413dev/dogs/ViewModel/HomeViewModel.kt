package com.p413dev.dogs.ViewModel

import android.app.Application
import android.util.Log
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
import org.json.JSONArray

import org.json.JSONObject

/**
 * Created by srujan.gade on 23/09/2019
 */
class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private var mBreedRepo: BreedRepo = BreedRepo(application)
    private var mSubBreedRepo: SubBreedRepo = SubBreedRepo(application)

    init {
        fetchAndStoreData()
    }

    private fun fetchAndStoreData() {
        AndroidNetworking.get("https://dog.ceo/api/breeds/list/all")
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    val mObject = JSONObject(response.toString())
                    if (mObject.optString("status").equals("success")) {
                        val messageOBJ: JSONObject = mObject.getJSONObject("message")
                        handleResponse(messageOBJ)
                    } else {

                    }
                }

                override fun onError(error: ANError) {
                }
            })
    }

    private fun handleResponse(response: JSONObject) {
        val iterator = response.keys()
        var i = 1
        while (iterator.hasNext()) {
            val key = iterator.next()
            val mBreed = BreedEntity(i, key)
            mBreedRepo.insertBreed(mBreed);
            i++
        }
    }

    internal fun getAllBreeds(): LiveData<List<BreedEntity>> {
        return mBreedRepo.getListOfBreeds()
    }


    fun getBreedsCount(): Int {
        return mBreedRepo.getBreedsCount()
    }

    fun checkForSubBreeds(breed: String): Int {
        var count = 0
        AndroidNetworking.get("https://dog.ceo/api/breed/$breed/list")
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    val mObject = JSONObject(response.toString())
                    if (mObject.optString("status").equals("success")) {
                        val subBreedArray: JSONArray = mObject.getJSONArray("message")
                        if (subBreedArray.length() > 0) {
                            count = subBreedArray.length()
                            handleSubBreedData(subBreedArray, breed)
                        } else {
                            count = 0
                        }
                    } else {
                        count = 0
                    }
                }

                override fun onError(error: ANError) {
                    count = 0
                }
            })
        // Log.v("count info", "count is $count")
        return count
    }

    private fun handleSubBreedData(messageArray: JSONArray, breed: String) {
        for (i in 1 until messageArray.length()) {
            val subBreedData = SubBreedEntity(1, messageArray[i].toString(), breed)
            mSubBreedRepo.insertSubBreed(subBreedData)
        }
    }

    private fun getSubBreedCount(breed: String): Int {
        return mSubBreedRepo.getSubBreedCount(breed)
    }
}