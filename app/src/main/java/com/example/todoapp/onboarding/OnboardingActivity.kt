package com.example.todoapp.onboarding

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.todoapp.R
import com.example.todoapp.View.LoginActivity
import com.example.todoapp.utils.Shared_pref_constant

class OnboardingActivity : AppCompatActivity(),OnBoardingOneFragment.OnNextClick,OnBoardingTwoFragment.OnOptionsOne,OnBoardingThreeFragment.OnOptionsTwo {
    lateinit var viewPager: ViewPager
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor:SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        bindView()
        setUpSharedPreferences()
    }

    private fun setUpSharedPreferences() {
        sharedPreferences=getSharedPreferences(Shared_pref_constant.SHARED_PREF, Context.MODE_PRIVATE)
    }

    private fun bindView() {
      viewPager=findViewById(R.id.viewPager)
        val adapter=FragmentAdaper(supportFragmentManager)
        viewPager.adapter=adapter
    }

    override fun onClick() {
        viewPager.currentItem=1
    }

    override fun onClickOneNext() {
        viewPager.currentItem=2
    }

    override fun onClickOnePrev() {
        viewPager.currentItem=0
    }

    override fun onClickTwoNext() {
        editor=sharedPreferences.edit()
        editor.putBoolean(Shared_pref_constant.ON_BOARDED_SUCCESSFULLY,true)
        editor.apply()
        val intent= Intent(this@OnboardingActivity,LoginActivity::class.java)
        startActivity(intent)
    }

    override fun onClickTwoPrev() {
        viewPager.currentItem=1
    }
}
