package com.example.rickandmortytest.presentation.ui.main

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.rickandmortytest.presentation.base.BaseFragment

class ViewPagerAdapter(
    list: ArrayList<BaseFragment>,
    fm: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fm, lifecycle) {

    private val pagerList = list

    override fun getItemCount() = pagerList.size

    override fun createFragment(position: Int) = pagerList[position]


}