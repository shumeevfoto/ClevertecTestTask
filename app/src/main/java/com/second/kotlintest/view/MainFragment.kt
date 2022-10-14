package com.second.kotlintest.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.second.kotlintest.R
import com.second.kotlintest.adapter.RecyclerViewAdapter
import com.second.kotlintest.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return (binding.root)
    }

    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel(binding.imageView)
        initRecyclerView()
        binding.buttonSend.setOnClickListener {
            val input = recyclerViewAdapter.getData()
            mainActivityViewModel.sendPost(listOf(input[0], input[1], input[2]))

        }
    }



    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewAdapter = RecyclerViewAdapter()
        binding.recyclerView.adapter = recyclerViewAdapter
    }

    private fun initViewModel(imageView: ImageView) {
        mainActivityViewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]

        mainActivityViewModel.liveDataList.observe(viewLifecycleOwner) {
            if (it != null) {
                recyclerViewAdapter.setUpdatedData(it.fields)
                loadImage(it.image, imageView)
                activity?.title = it.title

            } else {
                Toast.makeText(requireContext(), getString(R.string.error_data), Toast.LENGTH_SHORT).show()
            }
        }


        mainActivityViewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is MainActivityViewModel.State.Loaded -> {
                    binding.progressBar.visibility = ProgressBar.INVISIBLE
                    binding.statusImage.visibility = ImageView.INVISIBLE
                }
                is MainActivityViewModel.State.Loading -> {
                    binding.progressBar.visibility = ProgressBar.VISIBLE
                    binding.statusImage.visibility = ImageView.INVISIBLE

                }
                is MainActivityViewModel.State.Error -> {
                    binding.progressBar.visibility = ProgressBar.INVISIBLE
                    binding.statusImage.visibility = ImageView.VISIBLE
                }
            }
        }

        mainActivityViewModel.liveDataResult.observe(viewLifecycleOwner) {
            showFinalScoreDialog(it)
        }
        mainActivityViewModel.makeApiCall()


    }


    private fun showFinalScoreDialog(res: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.result_dialog_title))
            .setMessage(res)
            .setCancelable(false)
            .setPositiveButton(getString(R.string.button_title_dialog)) { _, _ -> }
            .show()
    }

    private fun loadImage(image: String, imageView: ImageView) {
        Glide
            .with(this)
            .load(image)
            .into(imageView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

