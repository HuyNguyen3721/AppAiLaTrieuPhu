package com.example.appailatrieuphu

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.appailatrieuphu.databinding.ActivityMainBinding
import com.example.appailatrieuphu.db.DatabaseManager

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var arrDataquestion : MutableList<Data_question> =  mutableListOf()
    private var mediaPlayer: MediaPlayer? = null
    private var userReady = false
    private var questionShow = false
    private var nextQuestion =  false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mediaPlayer = MediaPlayer.create(this, R.raw.bgmusic)
        mediaPlayer?.isLooping = true
        mediaPlayer?.start()
        fragment_startgame()
    }
    private  fun getDataQuestion(){
        arrDataquestion.clear()
        val database  =  DatabaseManager(this)
        database.openDatabse()
        val cursor = database.getData("SELECT* from (SELECT * from question  order by random()) GROUP by level ORDER by level ASC")
        cursor.moveToFirst() //cần  có  để chạy từ phần tử đầu tiên trong mảng , không có thì n chạy từ đâu đâu -> có thể bị lỗi cao
        val index_question =  cursor.getColumnIndex("question")
        val index_a =  cursor.getColumnIndex("casea")
        val index_b =  cursor.getColumnIndex("caseb")
        val index_c =  cursor.getColumnIndex("casec")
        val index_d =  cursor.getColumnIndex("cased")
        val index_true =  cursor.getColumnIndex("truecase")
        while(!cursor.isAfterLast){
            val question   =  cursor.getString(index_question)
            val a   =  cursor.getString(index_a)
            val b   =  cursor.getString(index_b)
            val c   =  cursor.getString(index_c)
            val d   =  cursor.getString(index_d)
            val truecase =  cursor.getInt(index_true)
            arrDataquestion.add(Data_question(question,a,b,c,d,truecase))
            cursor.moveToNext()
        }
        cursor.close()
        database.closeDatabase()
    }
    private fun fragment_startgame() {
        getDataQuestion()
        val tran = supportFragmentManager.beginTransaction()
        val frag = Fragment_StartGame()
        tran.add(R.id.FragmentMain, frag,"FRAGMENT_START")
        tran.addToBackStack(null).commit()
    }
     fun fragment_startgame_relace() {
         getDataQuestion()
         val frag_luatchoi = supportFragmentManager.findFragmentByTag("LUAT_CHOI") as Fragment_after_click_nowPlay
         frag_luatchoi.setfragShow(false)
        if (mediaPlayer != null || mediaPlayer!!.isPlaying) {
            mediaPlayer!!.stop()
            mediaPlayer!!.release()
            mediaPlayer =  null
        }
        mediaPlayer = MediaPlayer.create(this, R.raw.bgmusic)
        mediaPlayer?.isLooping =  true
        mediaPlayer?.start()
        val tran = supportFragmentManager.beginTransaction()
        val frag = Fragment_StartGame()
        tran.replace(R.id.FragmentMain, frag,"FRAGMENT_START")
        tran.addToBackStack(null).commit()
    }

    fun fragment_after_click_nowPlay() {
        if (mediaPlayer != null || mediaPlayer!!.isPlaying) {
            mediaPlayer!!.stop()
            mediaPlayer!!.release()
            mediaPlayer =  null
        }
        mediaPlayer = MediaPlayer.create(this, R.raw.luatchoi)
        mediaPlayer?.start()
        val tran = supportFragmentManager.beginTransaction()
        val frag = Fragment_after_click_nowPlay()
        tran.setCustomAnimations(R.anim.slide_in, R.anim.slide_out)
        tran.replace(R.id.FragmentMain, frag, "LUAT_CHOI")
        tran.addToBackStack(null).commit()
    }
    fun fragment_user_ready(){
        val frag_luatchoi = supportFragmentManager.findFragmentByTag("LUAT_CHOI") as Fragment_after_click_nowPlay
        frag_luatchoi.setfragShow(false)
        if (mediaPlayer != null || mediaPlayer!!.isPlaying) {
            mediaPlayer!!.stop()
            mediaPlayer!!.release()
            mediaPlayer =  null
        }
        userReady = true
        mediaPlayer = MediaPlayer.create(this, R.raw.user_ready)
        mediaPlayer?.start()
        val tran  = supportFragmentManager.beginTransaction()
        val frag  =  Fragment_user_ready()
        tran.replace(R.id.FragmentMain ,  frag,"USER_READY")
        tran.addToBackStack(null).commit()
    }
    fun fragment_money_questions(number : Int , restart : Boolean ,percent50 : Boolean , help : Boolean,  call : Boolean){
        nextQuestion = true
        if (mediaPlayer!!.isPlaying) {
            mediaPlayer!!.stop()
        }
        val tran = supportFragmentManager.beginTransaction()
        val frag =  Fragment_money_questions()
        val bundle  = Bundle()
        bundle.putInt("number_question",number)
        bundle.putBoolean("restart",restart)
        bundle.putBoolean("percent50",percent50)
        bundle.putBoolean("help",help)
        bundle.putBoolean("call",call)
        frag.arguments =  bundle
        tran.replace(R.id.FragmentMain , frag,"MONEY_QUESTION")
        tran.addToBackStack(null).commit()
    }
    fun fragment_show_question(number : Int, restart: Boolean,percent50: Boolean,help: Boolean,call: Boolean){
        questionShow = true
        val tran = supportFragmentManager.beginTransaction()
        val frag =  Fragment_show_question()
        frag.isUseRestart = restart
        frag.isUsePercent50 = percent50
        frag.isUseHelp =  help
        frag.isUseCall = call
        val bundle   = Bundle()
        bundle.putSerializable("data",arrDataquestion[number-1])
        bundle.putInt("numberQuestion",number)
        frag.arguments =  bundle
        tran.replace(R.id.FragmentMain , frag,"SHOW_QUESTIONS")
        tran.addToBackStack(null).commit()
    }
    override fun onStop() {
        Log.d("Key", "OnStop")
        super.onStop()
        mediaPlayer?.pause()
    }

    override fun onPause() {
        Log.d("Key", "onPause")
        super.onPause()
        mediaPlayer?.pause()
    }

    override fun onStart() {
        Log.d("Key", "onStart")
        super.onStart()
        if (mediaPlayer != null && !mediaPlayer!!.isPlaying) {
            mediaPlayer?.start()
        }
    }

    override fun onResume() {
        Log.d("Key", "onResume")
        super.onResume()
        if (mediaPlayer != null && !mediaPlayer!!.isPlaying) {
            mediaPlayer?.start()
        }

    }
    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount > 0 && !supportFragmentManager.findFragmentByTag("FRAGMENT_START")!!.isVisible) {
            fragment_startgame_relace()
           val frag_luatchoi = supportFragmentManager.findFragmentByTag("LUAT_CHOI") as Fragment_after_click_nowPlay
            frag_luatchoi.setfragShow(false)
           if(questionShow){
               val frag_show_question  =  supportFragmentManager.findFragmentByTag("SHOW_QUESTIONS") as Fragment_show_question
               frag_show_question.setBreakCountTime(true)
               questionShow = false
           }
            if(userReady){
                val frag_user_ready = supportFragmentManager.findFragmentByTag("USER_READY") as Fragment_user_ready
                frag_user_ready.setCheckShow(false)
                userReady = false
            }
            if(nextQuestion){
                val frag_money = supportFragmentManager.findFragmentByTag("MONEY_QUESTION") as Fragment_money_questions
                frag_money.setNextQuestion(false)
                nextQuestion =  false
            }
        }else {
            this.finish()
        }
    }
}