package com.p413dev.dogs

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.p413dev.dogs.Room.Entity.BreedEntity
import com.p413dev.dogs.Room.Entity.SubBreedEntity
import com.p413dev.dogs.ViewModel.HomeViewModel
import com.p413dev.dogs.ViewModel.SubBreedViewModel

class SubBreedsActivity : AppCompatActivity() {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var subBreedViewModel: SubBreedViewModel
    private lateinit var mBreedsAdapter: BreedsAdapter
    var breed: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        subBreedViewModel = ViewModelProviders.of(this).get(SubBreedViewModel::class.java)

        if (intent != null) {
            breed = intent.extras?.getString("BREED_NAME").toString()
        }
        mRecyclerView = findViewById(R.id.recyclerView)
        mRecyclerView.layoutManager = GridLayoutManager(this, 2)
        mRecyclerView.setHasFixedSize(false)
        mRecyclerView.adapter = mBreedsAdapter

        subBreedViewModel.getAllSubBreeds(breed).observe(this, Observer { breeds ->
            mBreedsAdapter.setBreeds(breeds)
        })

    }

    inner class BreedsAdapter : RecyclerView.Adapter<BreedsAdapter.ViewHolder>() {
        private var subBreedsList: List<SubBreedEntity> = emptyList()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val itemView =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.home_recycler_item, parent, false)
            return ViewHolder(itemView)
        }

        override fun getItemCount(): Int {
            return subBreedsList.size
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.tvBreed.text = subBreedsList[position].subbreedname
            holder.itemView.setOnClickListener {
                navigate(subBreedsList[position].subbreedname)
            }
        }

        fun setBreeds(breeds: List<SubBreedEntity>) {
            subBreedsList = breeds
            notifyDataSetChanged()
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val tvBreed: TextView = itemView.findViewById(R.id.tv_item_breed)
        }
    }

    private fun navigate(subbreedname: String) {
        val intent = Intent(this, DogsActivity::class.java)
        intent.putExtra("FROM", "subBreed")
        intent.putExtra("BREED_NAME", breed)
        intent.putExtra("SUBBREED_NAME", subbreedname)
        startActivity(intent)
    }


}
