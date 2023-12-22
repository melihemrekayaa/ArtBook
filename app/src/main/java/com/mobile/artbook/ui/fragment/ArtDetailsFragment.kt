package com.mobile.artbook.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.mobile.artbook.R
import com.mobile.artbook.databinding.FragmentArtDetailsBinding
import com.mobile.artbook.ui.viewmodel.ArtViewModel
import com.mobile.artbook.util.Status
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ArtDetailsFragment @Inject constructor (
    val glide : RequestManager
        ) : Fragment(R.layout.fragment_art_details) {

    private var fragmentBinding : FragmentArtDetailsBinding ?= null
    lateinit var viewModel: ArtViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)



        val binding = FragmentArtDetailsBinding.bind(view)
        fragmentBinding = binding

        subsribeToObservers()

        binding.artImageView.setOnClickListener {
            findNavController().navigate(ArtDetailsFragmentDirections.actionArtDetailsFragmentToImageApiFragment())
        }

        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)

        binding.saveBtn.setOnClickListener {
            viewModel.makeArt(binding.nameText.text.toString(),
                binding.artistText.text.toString(),
                binding.yearText.text.toString())
        }

    }

    private fun subsribeToObservers(){
        viewModel.selectedImageUrl.observe(viewLifecycleOwner, Observer { url->
            fragmentBinding?.let {
                glide.load(url).into(it.artImageView)
            }
        })

        viewModel.insertArtMessage.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS -> {
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_LONG).show()
                    findNavController().popBackStack()
                    viewModel.resetInsertArtMsg()
                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG).show()
                }

                Status.LOADING ->{

                }


            }
        })
    }
}