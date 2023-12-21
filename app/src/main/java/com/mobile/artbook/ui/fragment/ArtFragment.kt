package com.mobile.artbook.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.mobile.artbook.R
import com.mobile.artbook.databinding.FragmentArtsBinding
import com.mobile.artbook.view.ArtFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArtFragment : Fragment(R.layout.fragment_arts) {

    private var fragmentBinding : FragmentArtsBinding ?= null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentArtsBinding.bind(view)
        fragmentBinding = binding

        binding.fab.setOnClickListener {
            val action = ArtFragmentDirections.actionArtFragmentToArtDetailsFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}