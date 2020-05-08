package com.example.todoapp.onboarding

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.example.todoapp.R


class OnBoardingThreeFragment : Fragment() {
    lateinit var textViewNext: TextView
    lateinit var textViewBack: TextView
    lateinit var onOptions: OnOptionsTwo

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_on_boarding_three, container, false)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        onOptions=context as OnOptionsTwo
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textViewNext=view.findViewById(R.id.TextViewFragment3_Next)
        textViewBack=view.findViewById(R.id.TextViewFragment3_Prev)
        clickListeners()
    }


    private fun clickListeners() {
        textViewNext.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                onOptions.onClickTwoNext()
            }

        })
        textViewBack.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                onOptions.onClickTwoPrev()
            }

        })
    }
    interface OnOptionsTwo{
        fun onClickTwoNext()
        fun onClickTwoPrev()
    }

}
