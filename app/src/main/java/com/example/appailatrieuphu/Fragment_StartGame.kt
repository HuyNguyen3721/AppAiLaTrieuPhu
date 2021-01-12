package com.example.appailatrieuphu

import android.app.AlertDialog
import android.content.DialogInterface
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appailatrieuphu.databinding.LayoutDialogEndBinding
import com.example.appailatrieuphu.databinding.LayoutHightScoreBinding
import com.example.appailatrieuphu.databinding.StartGameBinding
import com.example.appailatrieuphu.db.DatabaseManager

class Fragment_StartGame : Fragment() {
    private  lateinit var  binding : StartGameBinding
    private lateinit var soundPool : SoundPool
    private  var arrDatahightScore : MutableList<Data_hightscore> =  mutableListOf()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding =  StartGameBinding.inflate(inflater, container , false)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            val audioAttrs =  AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()

            soundPool =  SoundPool.Builder()
                .setMaxStreams(6)
                .setAudioAttributes(audioAttrs)
                .build()
        }else{
            soundPool = SoundPool(6,AudioManager.STREAM_MUSIC,0)
        }
        soundPool.setOnLoadCompleteListener { soundPool: SoundPool, i: Int, i1: Int ->
            soundPool.play(i, 1F, 1F, 0, 0, 1F)
        }
        binding.btnHighScore.isSoundEffectsEnabled = false
        binding.btnHighScore.setOnClickListener(object  : View.OnClickListener{
            override fun onClick(p0: View?) {
               soundPool.load(binding.btnHighScore.context,R.raw.touch_sound ,1)
                // get data hight score
                getDataHightScore()
                // set relView
                val bindingHightscore = LayoutHightScoreBinding.inflate(layoutInflater, null, false)
                    bindingHightscore.rclHightscore.layoutManager =  LinearLayoutManager(binding.root.context)
                    val adapter  =  AdapterRecycleView(arrDatahightScore)
                    bindingHightscore.rclHightscore.adapter = adapter
                val alertDialog =  AlertDialog.Builder(binding.btnHighScore.context)
                alertDialog.setView(bindingHightscore.root)
                alertDialog.setCancelable(false)
                alertDialog.setNegativeButton("Ok") { _: DialogInterface, _: Int ->
                }.create()
                alertDialog.show()
            }
        })
        binding.btnPlayNow.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                soundPool.load(binding.btnHighScore.context,R.raw.touch_sound ,1)
                (activity as MainActivity).fragment_after_click_nowPlay()
            }
        })
        return binding.root
    }
    fun getDataHightScore(){
        arrDatahightScore.clear()
        val database  =  DatabaseManager(binding.root.context)
        database.openDatabse()
        val cursor = database.getData("SELECT * from Hight_score ORDER by money DESC")
        cursor.moveToFirst() //cần  có  để chạy từ phần tử đầu tiên trong mảng , không có thì n chạy từ đâu đâu -> có thể bị lỗi cao
        val index_name =  cursor.getColumnIndex("name")
        val index_money =  cursor.getColumnIndex("money")
        while(!cursor.isAfterLast){
            val name   =  cursor.getString(index_name)
            val money   =  cursor.getString(index_money)
            arrDatahightScore.add(Data_hightscore(name,money))
            cursor.moveToNext()
        }
        cursor.close()
        database.closeDatabase()
    }
}