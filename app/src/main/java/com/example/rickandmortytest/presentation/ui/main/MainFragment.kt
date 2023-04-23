package com.example.rickandmortytest.presentation.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.rickandmortytest.R
import com.example.rickandmortytest.databinding.FragmentMainBinding
import com.example.rickandmortytest.presentation.ui.characters.CharactersFragment
import com.example.rickandmortytest.presentation.ui.episode.EpisodeFragment
import com.example.rickandmortytest.presentation.ui.location.LocationFragment
import com.google.android.material.tabs.TabLayoutMediator


class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val tabNames = arrayListOf(
        "Character",
        "Location",
        "Episode"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClicker()

        val fragmentList = arrayListOf(
            CharactersFragment(),
            LocationFragment(),
            EpisodeFragment()
        )
        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )
        binding.mainPager.adapter = adapter

        TabLayoutMediator(binding.mainTabLayout, binding.mainPager) { tab, position ->
            tab.text = tabNames[position]
        }.attach()

    }

    private fun initClicker() {
        with(binding){

        }
    }

}