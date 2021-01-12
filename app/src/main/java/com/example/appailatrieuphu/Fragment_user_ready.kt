package com.example.appailatrieuphu

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.appailatrieuphu.databinding.LayoutLoadReadyBinding

class Fragment_user_ready : Fragment() {
     private lateinit var binding  : LayoutLoadReadyBinding
     private var checkShowFragment = true
        fun setCheckShow(b : Boolean){
            this.checkShowFragment = b
        }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =  LayoutLoadReadyBinding.inflate(inflater, container ,  false)
        val handle  =  Handler()
        handle.postDelayed(Runnable {
            if(checkShowFragment){
                (activity as MainActivity).fragment_money_questions(1,false,false,false,false)
            }
        },6000)
        return  binding.root
    }
}