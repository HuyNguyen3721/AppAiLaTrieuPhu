package com.example.appailatrieuphu

import android.widget.TextView
import androidx.databinding.BindingAdapter


object  Untils{
        @JvmStatic
        @BindingAdapter("setText")
        fun  setText(tv : TextView ,  content : String){
            tv.text =  content
        }
}