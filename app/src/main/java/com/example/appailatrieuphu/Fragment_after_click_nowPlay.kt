package com.example.appailatrieuphu

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.appailatrieuphu.databinding.LayoutAfterClickPlayBinding

class Fragment_after_click_nowPlay : Fragment() {
    private lateinit var binding: LayoutAfterClickPlayBinding
    private lateinit var soundPool: SoundPool
    private var fragShow: Boolean = true
    fun  setfragShow( b : Boolean){
        this.fragShow = b
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val check =  arguments?.getBoolean("FragShow")
        binding = LayoutAfterClickPlayBinding.inflate(inflater, container, false)
        // sound music
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val audioAttrs = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()

            soundPool = SoundPool.Builder()
                .setMaxStreams(6)
                .setAudioAttributes(audioAttrs)
                .build()
        } else {
            soundPool = SoundPool(6, AudioManager.STREAM_MUSIC, 0)
        }
        soundPool.setOnLoadCompleteListener { soundPool: SoundPool, i: Int, i1: Int ->
            soundPool.play(i, 1F, 1F, 0, 0, 1F)
        }
        //create dialog
        val builder = AlertDialog.Builder(binding.btnContinue.context)
        builder.setTitle("Bạn đã sẵn sàng chưa ?")
        builder.setNegativeButton("Sẵn Sàng") { _: DialogInterface, _: Int ->
            (activity as MainActivity).fragment_user_ready()
        }
        builder.setPositiveButton("Hủy Bỏ") { _: DialogInterface, _: Int ->
            (activity as MainActivity).fragment_startgame_relace()
        }
        builder.setCancelable(false)
        val dialog = builder.create()
        dialog.setCanceledOnTouchOutside(false)
        // handle
        val handler = Handler()
        handler.postDelayed({
            binding.textLevel5.setBackgroundColor(Color.parseColor("#FF1636D5"))
        }, 4970)
        handler.postDelayed({
            binding.textLevel5.setBackgroundColor(android.R.drawable.btn_default)
            binding.textLevel10.setBackgroundColor(Color.parseColor("#FF1636D5"))
        }, 5170)
        handler.postDelayed({
            binding.textLevel10.setBackgroundColor(android.R.drawable.btn_default)
            binding.textLevel15.setBackgroundColor(Color.parseColor("#FF1636D5"))
        }, 5700)
        handler.postDelayed({
            binding.textLevel15.setBackgroundColor(android.R.drawable.btn_default)
            if (!dialog.isShowing && fragShow) {
                dialog.show()
            }
        }, 11000)
//        Log.d("key", "Fragmnet show "+ this.isResumed )
        binding.btnContinue.isSoundEffectsEnabled = false // off sound btn
        binding.btnContinue.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                soundPool.load(binding.btnContinue.context, R.raw.touch_sound, 1)
                dialog.show()
            }
        })
        return binding.root
    }
}