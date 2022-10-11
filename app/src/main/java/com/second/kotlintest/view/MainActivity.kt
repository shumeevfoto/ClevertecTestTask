package com.second.kotlintest.view

import android.os.Bundle
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.second.kotlintest.R
import com.second.kotlintest.adapter.RecyclerViewAdapter
import com.second.kotlintest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private lateinit var mainActivityViewModel: MainActivityViewModel
    private lateinit var binding:ActivityMainBinding


    private val recyclerView: RecyclerView
        get() = findViewById(R.id.recyclerView)

    private val progressBar:ProgressBar
        get() = findViewById(R.id.progressBar)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root
        )

        initViewModel(binding.imageView)
        initRecyclerView()
        binding.buttonSend.setOnClickListener {
            val input = recyclerViewAdapter.getData()
            mainActivityViewModel.sendPost(listOf(input[0], input[1], input[2]))

        }
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerViewAdapter = RecyclerViewAdapter()
        recyclerView.adapter = recyclerViewAdapter
    }

    private fun initViewModel(imageView: ImageView) {
        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        mainActivityViewModel.liveDataList.observe(this) {
            if (it != null) {
                recyclerViewAdapter.setUpdatedData(it.fields)
                loadImage(it.image, imageView)
                title = it.title

            } else {
                Toast.makeText(this, getString(R.string.error_data), Toast.LENGTH_SHORT).show()
            }
        }


        mainActivityViewModel.state.observe(this) {
            when (it) {
                is MainActivityViewModel.State.Loaded -> {
                    progressBar.visibility = ProgressBar.INVISIBLE
                }
                is MainActivityViewModel.State.Loading -> {
                    progressBar.visibility = ProgressBar.VISIBLE

                }
            }
        }

        mainActivityViewModel.liveDataResult.observe(this) {
            showFinalScoreDialog(it)
        }
        mainActivityViewModel.makeApiCall()


    }


    private fun showFinalScoreDialog(res: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.result_dialog_title))
            .setMessage(res)
            .setCancelable(false)
            .setPositiveButton(getString(R.string.button_title_dialog)) { _, _ -> }
            .show()
    }

    private fun loadImage(image: String, imageView: ImageView) {
        Glide
            .with(this@MainActivity)
            .load(image)
            .into(imageView)
    }

}