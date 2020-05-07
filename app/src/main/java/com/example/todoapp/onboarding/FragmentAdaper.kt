package com.example.todoapp.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class FragmentAdaper(fragmentManager:FragmentManager): FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return OnBoardingOneFragment()
            }
            1 -> {
                return OnBoardingTwoFragment()
            }

        }
        return OnBoardingTwoFragment()
    }

    override fun getCount(): Int {
       return 2
    }
}