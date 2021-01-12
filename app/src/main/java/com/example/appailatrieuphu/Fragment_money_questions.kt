package com.example.appailatrieuphu

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.appailatrieuphu.databinding.LayoutAfterClickPlayBinding
import com.example.appailatrieuphu.databinding.LayoutMoneyQuestionsBinding

class Fragment_money_questions : Fragment() {
    private lateinit var binding: LayoutMoneyQuestionsBinding
    private var mediaPlayer: MediaPlayer? = null
    private var nextQuestion = true
    private var restart = false
    private var percent50 = false
    private var help = false
    private var call = false
    fun setNextQuestion(b: Boolean) {
        this.nextQuestion = b
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // data help
        restart = arguments!!.getBoolean("restart")
        percent50 = arguments!!.getBoolean("percent50")
        help = arguments!!.getBoolean("help")
        call = arguments!!.getBoolean("call")
        val number_question = arguments!!.getInt("number_question")
        binding = LayoutMoneyQuestionsBinding.inflate(inflater, container, false)
        //media
        //
        when (number_question) {
            1 -> {
                mediaPlayer = MediaPlayer.create(binding.root.context, R.raw.ques1)
                mediaPlayer?.start()
                mediaPlayer?.setOnCompletionListener {
                    MediaPlayer.OnCompletionListener {
                        mediaPlayer?.release()
                        Log.d("anser", "mediaplay complete ")
                    }
                }
                binding.textLevel1.setBackgroundResource(R.drawable.bg_money)
            }
            2 -> {
                mediaPlayer = MediaPlayer.create(binding.root.context, R.raw.ques2_b)
                mediaPlayer?.start()
                mediaPlayer?.setOnCompletionListener {
                    MediaPlayer.OnCompletionListener {
                        mediaPlayer?.release()
                        Log.d("anser", "mediaplay complete ")
                    }
                }
                binding.textLevel2.setBackgroundResource(R.drawable.bg_money)
            }
            3 -> {
                mediaPlayer = MediaPlayer.create(binding.root.context, R.raw.ques3_b)
                mediaPlayer?.start()
                mediaPlayer?.setOnCompletionListener {
                    MediaPlayer.OnCompletionListener {
                        mediaPlayer?.release()
                        Log.d("anser", "mediaplay complete ")
                    }
                }
                binding.textLevel3.setBackgroundResource(R.drawable.bg_money)
            }
            4 -> {
                mediaPlayer = MediaPlayer.create(binding.root.context, R.raw.ques4_b)
                mediaPlayer?.start()
                mediaPlayer?.setOnCompletionListener {
                    MediaPlayer.OnCompletionListener {
                        mediaPlayer?.release()
                        Log.d("anser", "mediaplay complete ")
                    }
                }
                binding.textLevel4.setBackgroundResource(R.drawable.bg_money)
            }
            5 -> {
                mediaPlayer = MediaPlayer.create(binding.root.context, R.raw.ques5_b)
                mediaPlayer?.start()
                mediaPlayer?.setOnCompletionListener {
                    MediaPlayer.OnCompletionListener {
                        mediaPlayer?.release()
                        Log.d("anser", "mediaplay complete ")
                    }
                }
                binding.textLevel5.setBackgroundResource(R.drawable.bg_money)
            }
            6 -> {
                Log.d("anser", "bg media question 6")
                mediaPlayer = MediaPlayer.create(binding.root.context, R.raw.ques6)
                mediaPlayer?.start()
                mediaPlayer?.setOnCompletionListener {
                    MediaPlayer.OnCompletionListener {
                        mediaPlayer?.release()
                        Log.d("anser", "mediaplay complete ")
                    }
                }
                binding.textLevel6.setBackgroundResource(R.drawable.bg_money)
            }
            7 -> {
                mediaPlayer = MediaPlayer.create(binding.root.context, R.raw.ques7_b)
                mediaPlayer?.start()
                mediaPlayer?.setOnCompletionListener {
                    MediaPlayer.OnCompletionListener {
                        mediaPlayer?.release()
                        Log.d("anser", "mediaplay complete ")
                    }
                }
                binding.textLevel7.setBackgroundResource(R.drawable.bg_money)
            }
            8 -> {
                mediaPlayer = MediaPlayer.create(binding.root.context, R.raw.ques8_b)
                mediaPlayer?.start()
                mediaPlayer?.setOnCompletionListener {
                    MediaPlayer.OnCompletionListener {
                        mediaPlayer?.release()
                        Log.d("anser", "mediaplay complete ")
                    }
                }
                binding.textLevel8.setBackgroundResource(R.drawable.bg_money)
            }
            9 -> {
                mediaPlayer = MediaPlayer.create(binding.root.context, R.raw.ques9_b)
                mediaPlayer?.start()
                mediaPlayer?.setOnCompletionListener {
                    MediaPlayer.OnCompletionListener {
                        mediaPlayer?.release()
                        Log.d("anser", "mediaplay complete ")
                    }
                }
                binding.textLevel9.setBackgroundResource(R.drawable.bg_money)
            }
            10 -> {
                mediaPlayer = MediaPlayer.create(binding.root.context, R.raw.ques10)
                mediaPlayer?.start()
                mediaPlayer?.setOnCompletionListener {
                    MediaPlayer.OnCompletionListener {
                        mediaPlayer?.release()
                        Log.d("anser", "mediaplay complete ")
                    }
                }
                binding.textLevel10.setBackgroundResource(R.drawable.bg_money)
            }
            11 -> {
                Log.d("anser", "bg media question 11")
                mediaPlayer = MediaPlayer.create(binding.root.context, R.raw.ques11)
                mediaPlayer?.start()
                mediaPlayer?.setOnCompletionListener {
                    MediaPlayer.OnCompletionListener {
                        mediaPlayer?.release()
                        Log.d("anser", "mediaplay complete ")
                    }
                }
                binding.textLevel11.setBackgroundResource(R.drawable.bg_money)
            }
            12 -> {
                mediaPlayer = MediaPlayer.create(binding.root.context, R.raw.ques12)
                mediaPlayer?.start()
                mediaPlayer?.setOnCompletionListener {
                    MediaPlayer.OnCompletionListener {
                        mediaPlayer?.release()
                        Log.d("anser", "mediaplay complete ")
                    }
                }
                binding.textLevel12.setBackgroundResource(R.drawable.bg_money)
            }
            13 -> {
                mediaPlayer = MediaPlayer.create(binding.root.context, R.raw.ques13)
                mediaPlayer?.start()
                mediaPlayer?.setOnErrorListener { p0, p1, p2 ->
                    Log.d("anser", "Error$p1 $p2")
                    true
                }
                mediaPlayer?.setOnCompletionListener {
                    MediaPlayer.OnCompletionListener {
                        Log.d("anser", "mediaplay complete ")
                    }
                }
                binding.textLevel13.setBackgroundResource(R.drawable.bg_money)
            }
            14 -> {
                mediaPlayer = MediaPlayer.create(binding.root.context, R.raw.ques14)
                mediaPlayer?.start()
                mediaPlayer?.setOnCompletionListener {
                    MediaPlayer.OnCompletionListener {
                        mediaPlayer?.release()
                        Log.d("anser", "mediaplay complete ")
                    }
                }
                binding.textLevel14.setBackgroundResource(R.drawable.bg_money)
            }
            15 -> {
                mediaPlayer = MediaPlayer.create(binding.root.context, R.raw.ques15)
                mediaPlayer?.start()
                mediaPlayer?.setOnCompletionListener {
                    MediaPlayer.OnCompletionListener {
                        mediaPlayer?.release()
                        Log.d("anser", "mediaplay complete ")
                    }
                }
                binding.textLevel15.setBackgroundResource(R.drawable.bg_money)
            }
        }
        if (number_question == 15) {
            val handle = Handler()
            handle.postDelayed({
                mediaPlayer?.stop()
                mediaPlayer?.release()
                mediaPlayer = null
                if (nextQuestion) {
                    (activity as MainActivity).fragment_show_question(number_question,restart,percent50,help,call)
                }
            }, 4000)
        } else {
            val handle = Handler()
            handle.postDelayed({
                mediaPlayer?.stop()
                mediaPlayer?.release()
                mediaPlayer = null
                if (nextQuestion) {
                    (activity as MainActivity).fragment_show_question(number_question,restart,percent50,help,call)
                }
            }, 2000)
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        mediaPlayer?.start()
    }

    override fun onDestroyView() {
        mediaPlayer?.release()
        super.onDestroyView()
    }
}