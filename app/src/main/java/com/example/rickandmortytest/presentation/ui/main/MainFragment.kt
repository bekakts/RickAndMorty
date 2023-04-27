package com.example.rickandmortytest.presentation.ui.main

import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.rickandmortytest.R
import com.example.rickandmortytest.databinding.FragmentMainBinding
import com.example.rickandmortytest.presentation.base.BaseFragment
import com.example.rickandmortytest.presentation.ui.characters.CharactersFragment
import com.example.rickandmortytest.presentation.ui.episode.EpisodeFragment
import com.example.rickandmortytest.presentation.ui.location.LocationFragment
import com.google.android.material.tabs.TabLayoutMediator


class MainFragment : BaseFragment(R.layout.fragment_main) {

    private val binding by viewBinding(FragmentMainBinding::bind)
    private val tabNames = arrayListOf(
        "Character",
        "Location",
        "Episode"
    )
    private val fragmentList = arrayListOf(
        CharactersFragment(),
        LocationFragment(),
        EpisodeFragment()
    )

    override fun initialize() {
        super.initialize()
        initAdapter()
        TabLayoutMediator(binding.mainTabLayout, binding.mainPager) { tab, position ->
            tab.text = tabNames[position]
        }.attach()
    }

    private fun initAdapter() {
        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )
        binding.mainPager.adapter = adapter
    }

}