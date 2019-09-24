package com.p413dev.dogs

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.p413dev.dogs.Room.Entity.BreedEntity
import com.p413dev.dogs.ViewModel.HomeViewModel


class HomeActivity : AppCompatActivity() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mBreedsAdapter: BreedsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        mBreedsAdapter = BreedsAdapter()
        mRecyclerView = findViewById(R.id.recyclerView)

        mRecyclerView.layoutManager = GridLayoutManager(this, 2)
        mRecyclerView.setHasFixedSize(false)
        mRecyclerView.adapter = mBreedsAdapter

        homeViewModel.getAllBreeds().observe(this, Observer { breeds ->
            mBreedsAdapter.setBreeds(breeds)
        })
    }

    inner class BreedsAdapter : RecyclerView.Adapter<BreedsAdapter.ViewHolder>() {
        private var breedsList: List<BreedEntity> = emptyList()
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val itemView =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.home_recycler_item, parent, false)
            return ViewHolder(itemView)
        }

        override fun getItemCount(): Int {
            Log.v("data", "size " + breedsList.size)
            return breedsList.size
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.tvBreed.text = breedsList[position].breedname
            holder.itemView.setOnClickListener {
                checkForSubBreedInfo(breedsList[position].breedname)
            }
        }

        fun setBreeds(breeds: List<BreedEntity>) {
            breedsList = breeds
            notifyDataSetChanged()
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val tvBreed: TextView = itemView.findViewById(R.id.tv_item_breed)
        }
    }

    private fun checkForSubBreedInfo(breedname: String) {
        if (homeViewModel.checkForSubBreeds(breedname) > 0) {
            // subBreeds present
            val intent = Intent(this, SubBreedsActivity::class.java)
            intent.putExtra("BREED_NAME", breedname)
            startActivity(intent)
        } else {
            // show breed images
            val intent = Intent(this, DogsActivity::class.java)
            intent.putExtra("FROM", "Breed")
            intent.putExtra("BREED_NAME", breedname)
            startActivity(intent)
        }
    }
}