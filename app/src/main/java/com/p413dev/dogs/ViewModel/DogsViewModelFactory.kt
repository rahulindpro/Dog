package com.p413dev.dogs.ViewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Created by srujan.gade on 24/09/2019
 */
class DogsViewModelFactory(
    private val mApplication: Application,
    private val mBreed: String,
    private val mSubBreed: String
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DogsViewModel(mApplication, mBreed, mSubBreed) as T
    }
}
