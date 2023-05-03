package com.example.rickandmortytest.presentation.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.rickandmortytest.presentation.ui.characters.CharactersFragment
import com.example.rickandmortytest.presentation.ui.episode.EpisodeFragment
import com.example.rickandmortytest.presentation.ui.location.LocationFragment

class ViewPagerAdapter(
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CharactersFragment()
            1 -> LocationFragment()
            else -> EpisodeFragment()
        }
    }

    override fun getItemCount(): Int {
        return 3
    }
}
