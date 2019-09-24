package com.p413dev.dogs

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.p413dev.dogs.Room.Entity.BreedEntity
import com.p413dev.dogs.Room.Entity.DogsEntity
import com.p413dev.dogs.ViewModel.DogsViewModel
import com.p413dev.dogs.ViewModel.HomeViewModel
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.p413dev.dogs.ViewModel.DogsViewModelFactory
import android.widget.ProgressBar
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.opengl.Visibility
import androidx.core.view.isVisible


class DogsActivity : AppCompatActivity() {

    private lateinit var dogsViewModel: DogsViewModel
    lateinit var mRecyclerView: RecyclerView
    lateinit var mDogsAdapter: DogsAdapter
    lateinit var breed: String
    lateinit var subBreed: String
    lateinit var tagFROM: String
    lateinit var factory: DogsViewModelFactory
    lateinit var back_img: ImageView
    lateinit var mainImg: ImageView
    lateinit var tvBreed: TextView
    lateinit var tvSubBreed: TextView
    lateinit var tvDesc: TextView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dogs)


        if (intent != null) {
            breed = intent.extras?.getString("BREED_NAME").toString()
            subBreed = intent.extras?.getString("SUBBREED_NAME").toString()
            tagFROM = intent.extras?.getString("FROM").toString()
        }

        factory = DogsViewModelFactory(application, breed, subBreed)
        dogsViewModel = ViewModelProviders.of(this, factory).get(DogsViewModel::class.java)

        initialization()

        if (tagFROM.equals("Breed")) {
            dogsViewModel.getDogsByBreed(breed).observe(this, Observer { dogs ->
                mDogsAdapter.setDogs(dogs)
            })
        } else {
            dogsViewModel.getDogsByBreedAndSubBreed(breed, subBreed)
                .observe(this, Observer { dogs ->
                    mDogsAdapter.setDogs(dogs)
                })
        }

    }

    @SuppressLint("SetTextI18n")
    private fun initialization() {

        back_img = findViewById(R.id.img_back)
        mainImg = findViewById(R.id.mainImg)
        tvBreed = findViewById(R.id.tv_breed)
        tvDesc = findViewById(R.id.tv_desc)
        tvSubBreed = findViewById(R.id.tv_subBreed)
        mRecyclerView = findViewById(R.id.horizantalRecyclerView)

        mDogsAdapter = DogsAdapter()

        tvBreed.setText(breed)
        tvSubBreed.setText(subBreed)
        tvDesc.setText("Tap on list to view Image")

        val customFont =
            Typeface.createFromAsset(applicationContext.getAssets(), "Montserrat-Light.ttf")

        tvBreed.typeface = customFont
        tvSubBreed.typeface = customFont
        tvDesc.typeface = customFont

        mRecyclerView.setHasFixedSize(false)
        mRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mRecyclerView.adapter = mDogsAdapter

        back_img.setOnClickListener { finish() }
    }

    inner class DogsAdapter : RecyclerView.Adapter<DogsAdapter.ViewHolder>() {

        private var dogsList: List<DogsEntity> = emptyList()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val itemView =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.home_recycler_item, parent, false)
            return ViewHolder(itemView)

        }

        override fun getItemCount(): Int {
            return dogsList.size
        }

        @SuppressLint("CheckResult")
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            val requestOptions = RequestOptions()
            requestOptions.placeholder(R.drawable.def_home_image)
            requestOptions.error(R.drawable.def_home_image)
            requestOptions.centerInside()

            Glide.with(applicationContext)
                .load(dogsList[position].dogimage)
                .apply(requestOptions)
                .into(holder.imgDog)

            holder.itemView.setOnClickListener {
                setImage(dogsList[position].dogimage)
            }
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val imgDog: ImageView = itemView.findViewById(R.id.img_breed)
        }

        fun setDogs(dogs: List<DogsEntity>) {
            dogsList = dogs
            notifyDataSetChanged()
        }
    }

    @SuppressLint("CheckResult")
    private fun setImage(image: String) {
        val requestOptions = RequestOptions()
        requestOptions.placeholder(R.drawable.def_home_image)
        requestOptions.error(R.drawable.def_home_image)
        requestOptions.centerInside()

        Glide.with(applicationContext)
            .load(image)
            .apply(requestOptions)
            .into(mainImg)
    }
}