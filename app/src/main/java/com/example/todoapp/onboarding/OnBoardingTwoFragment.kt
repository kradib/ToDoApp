package com.example.todoapp.onboarding

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.example.todoapp.R


class OnBoardingTwoFragment : Fragment() {

    lateinit var textViewNext: TextView
    lateinit var textViewBack: TextView
    lateinit var onOptions: OnOptions
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_onboraing_two, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onOptions=context as OnOptions
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textViewNext=view.findViewById(R.id.TextViewFragment2_Next)
        textViewBack=view.findViewById(R.id.TextViewFragment2_Prev)
        clickListeners()
    }

    private fun clickListeners() {
        textViewNext.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
               onOptions.onClickNext()
            }

        })
        textViewBack.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                onOptions.onClickPrev()
            }

        })
    }
    interface OnOptions{
        fun onClickNext()
        fun onClickPrev()
    }
}
