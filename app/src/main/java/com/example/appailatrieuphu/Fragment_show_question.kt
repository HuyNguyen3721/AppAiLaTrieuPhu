package com.example.appailatrieuphu

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.media.MediaPlayer
import android.os.*
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.appailatrieuphu.databinding.DialogBarchartBinding
import com.example.appailatrieuphu.databinding.DialogCallBinding
import com.example.appailatrieuphu.databinding.LayoutDialogEndBinding
import com.example.appailatrieuphu.databinding.LayputShowQuestionBinding
import com.example.appailatrieuphu.db.DatabaseManager
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import java.util.*

class Fragment_show_question : Fragment(), View.OnClickListener {
    private lateinit var binding: LayputShowQuestionBinding
    private lateinit var bindingDialog: LayoutDialogEndBinding
    private var mediaPlay: MediaPlayer? = null
    private var bg_mediaPlay: MediaPlayer? = null
    private lateinit var showDialog: AlertDialog
    private var dialogcell: AlertDialog? = null
    private var dialogBarchart: AlertDialog? = null
    private var time = 30
    private var checkClickChoseAnser: Boolean = false
    private lateinit var data: Data_question
    private var number = 0
    private var choseLose = false
    private lateinit var countTime: CountTime
    private var breakCountTime = false
    var isUseRestart = false
    var isUseCall = false
    var isUseHelp = false
    var isUsePercent50 = false
    fun setBreakCountTime(b: Boolean) {
        this.breakCountTime = b
    }

    override fun onDestroyView() {
        setBreakCountTime(true)
        bg_mediaPlay?.release()
        mediaPlay?.release()
        super.onDestroyView()
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        data = arguments!!.getSerializable("data") as Data_question
        number = arguments!!.getInt("numberQuestion")
        Log.d("anser ", " !: " + data.answer)
        binding = LayputShowQuestionBinding.inflate(inflater, container, false)
        // music bg
        bg_mediaPlay = MediaPlayer.create(binding.root.context, R.raw.background_music)
        bg_mediaPlay?.isLooping = true
        bg_mediaPlay?.start()
        //check use help
        if (isUseRestart) {
            binding.btnRestart.isEnabled = false
            binding.ivUseRestart.visibility = View.VISIBLE
        }
        if (isUseCall) {
            binding.btnCall.isEnabled = false
            binding.ivUseCall.visibility = View.VISIBLE
        }
        if (isUseHelp) {
            binding.btnHelp.isEnabled = false
            binding.ivUseHelp.visibility = View.VISIBLE
        }
        if (isUsePercent50) {
            binding.btnPercent50.isEnabled = false
            binding.ivUsePercent50.visibility = View.VISIBLE
        }
        //
        binding.textTitleQuestion.text = "Câu $number"
        binding.data = data
        binding.textTime.text = "$time"
        binding.textAnsA.setOnClickListener(this)
        binding.textAnsB.setOnClickListener(this)
        binding.textAnsC.setOnClickListener(this)
        binding.textAnsD.setOnClickListener(this)
        binding.btnTerminate.setOnClickListener(this)
        binding.btnPercent50.setOnClickListener(this)
        binding.btnCall.setOnClickListener(this)
        binding.btnHelp.setOnClickListener(this)
        binding.btnRestart.setOnClickListener(this)
        countTime = CountTime()
        countTime.execute()
        return binding.root
    }

    @SuppressLint("StaticFieldLeak")
    inner class CountTime : AsyncTask<Void, Int, Void>() {
        override fun doInBackground(vararg p0: Void?): Void? {
            for (i in 30 downTo 0) {
                publishProgress(i)
                SystemClock.sleep(1000)
                if (countTime.isCancelled || breakCountTime) {
                    break
                }
            }
            return null
        }
        override fun onProgressUpdate(vararg values: Int?) {
            binding.textTime.text = values[0].toString()
            if (values[0] == 0 && !checkClickChoseAnser) {
                bg_mediaPlay?.stop()
                bg_mediaPlay?.release()
                bg_mediaPlay = null
                bg_mediaPlay = MediaPlayer.create(binding.root.context, R.raw.out_of_time)
                bg_mediaPlay?.start()
                dialogBarchart?.dismiss()
                dialogcell?.dismiss()
                showDiaLogEnd()
            }
        }
    }

    // dialog
    @SuppressLint("SetTextI18n")
    private fun showDiaLogEnd() {
        mediaPlay?.release()
        val dialog = AlertDialog.Builder(binding.root.context)
        bindingDialog = LayoutDialogEndBinding.inflate(layoutInflater, null, false)
        val num = arguments?.getInt("numberQuestion")?.minus(1)
        when (num) {
            0 -> bindingDialog.money.text = "0.000Đ"
            1 ->
                if (choseLose) {
                    bindingDialog.money.text = "0.000Đ"
                } else {
                    bindingDialog.money.text = "200.000Đ"
                }
            2 ->
                if (choseLose) {
                    bindingDialog.money.text = "0.000Đ"
                } else {
                    bindingDialog.money.text = "400.000Đ"
                }
            3 ->
                if (choseLose) {
                    bindingDialog.money.text = "0.000Đ"
                } else {
                    bindingDialog.money.text = "600.000Đ"
                }
            4 ->
                if (choseLose) {
                    bindingDialog.money.text = "0.000Đ"
                } else {
                    bindingDialog.money.text = "1.000.000Đ"
                }

            5 -> bindingDialog.money.text = "2.000.000Đ"
            6 ->
                if (choseLose) {
                    bindingDialog.money.text = "2.000.000Đ"
                } else {
                    bindingDialog.money.text = "3.000.000Đ"
                }

            7 ->
                if (choseLose) {
                    bindingDialog.money.text = "2.000.000Đ"
                } else {
                    bindingDialog.money.text = "6.000.000Đ"
                }
            8 ->
                if (choseLose) {
                    bindingDialog.money.text = "2.000.000Đ"
                } else {
                    bindingDialog.money.text = "10.000.000Đ"
                }
            9 ->
                if (choseLose) {
                    bindingDialog.money.text = "2.000.000Đ"
                } else {
                    bindingDialog.money.text = "14.000.000Đ"
                }

            10 -> bindingDialog.money.text = "22.000.000Đ"
            11 ->
                if (choseLose) {
                    bindingDialog.money.text = "22.000.000Đ"
                } else {
                    bindingDialog.money.text = "30.000.000Đ"
                }

            12 -> if (choseLose) {
                bindingDialog.money.text = "22.000.000Đ"
            } else {
                bindingDialog.money.text = "40.000.000Đ"
            }
            13 ->
                if (choseLose) {
                    bindingDialog.money.text = "22.000.000Đ"
                } else {
                    bindingDialog.money.text = "60.000.000Đ"
                }
            14 ->
                if (choseLose) {
                    bindingDialog.money.text = "22.000.000Đ"
                } else {
                    bindingDialog.money.text = "85.000.000Đ"
                }
        }
        if (number == 15 && !choseLose) {
            bindingDialog.money.text = "150.000.000Đ"
        }
        bindingDialog.save.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                Log.d("key", "text " + bindingDialog.edtName.text.trim())
                if (bindingDialog.edtName.text.toString().trim().isEmpty()) {
//                        Log.d("key" , "Show Toast OK")
                    Toast.makeText(
                        binding.root.context,
                        "Vui Lòng Điền Tên Để Lưu !",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val name = bindingDialog.edtName.text
                    val money = bindingDialog.money.text
                    val database = DatabaseManager(binding.root.context)
                    database.openDatabse()
                    database.queryData("INSERT INTO Hight_score VALUES (null,'$name','$money')")
                    database.closeDatabase()
                    Toast.makeText(binding.root.context, "Đã Lưu !", Toast.LENGTH_SHORT).show()
                    mediaPlay?.release()
                    mediaPlay = null
                    bg_mediaPlay?.release()
                    bg_mediaPlay = null
                    showDialog.dismiss()
                    (activity as MainActivity).fragment_startgame_relace()
                }
            }
        })
        bindingDialog.exit.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                bg_mediaPlay?.release()
                bg_mediaPlay = null
                mediaPlay?.release()
                mediaPlay = null
                showDialog.dismiss()
                (activity as MainActivity).fragment_startgame_relace()
            }
        })
        dialog.setView(bindingDialog.root)
        dialog.setCancelable(false)
        showDialog = dialog.create()
        showDialog.setCanceledOnTouchOutside(false)
        showDialog.show()
    }

    @SuppressLint("SetTextI18n")
    override fun onClick(p0: View) {
        mediaPlay?.release()
        when (p0.id) {
            /*-----------------------------------------------------------------*/
            R.id.textAnsA -> {
                if (!checkClickChoseAnser) {
                    //unable click help
                    unableCLickHelp()
                    // stop count time
                    countTime.cancel(true)
                    // sound chose
                    if (number == 1 || number == 2 || number == 3 || number == 4 || number == 5) {
                        Log.d("key", " Sound CHosse Ok a")
                        mediaPlay = MediaPlayer.create(binding.root.context, R.raw.ans_a)
                        mediaPlay?.start()
                    } else {
                        mediaPlay = MediaPlayer.create(binding.root.context, R.raw.ans_a2)
                        mediaPlay?.start()
                    }
                    // chosse
                    binding.textAnsA.setBackgroundResource(R.drawable.bg_choose)
                    (binding.textAnsA.background as AnimationDrawable).start()
                    checkClickChoseAnser = true
                    //check anwser
                    val handle = Handler()
                    handle.postDelayed(Runnable {
                        Log.d("key", "anser : " + data.answer)
                        if (data.answer == 1 && !breakCountTime) {
                            Log.d("key", "bg true A")
                            binding.textAnsA.setBackgroundResource(R.drawable.bg_ans)
                            (binding.textAnsA.background as AnimationDrawable).start()
                            if (number == 1 || number == 2 || number == 3 || number == 4 || number == 5) {
                                Log.d("key", " Sound CHosse True a")
                                removeMediaplayer()
                                mediaPlay = MediaPlayer.create(binding.root.context, R.raw.true_a)
                                mediaPlay?.start()
                                if (number == 5) {
                                    val handler = Handler()
                                    handler.postDelayed(Runnable {
                                        if (mediaPlay != null && !breakCountTime) {
                                            removeMediaplayer()
                                            mediaPlay = MediaPlayer.create(
                                                binding.root.context,
                                                R.raw.chuc_mung_vuot_moc_01_0
                                            )
                                            mediaPlay?.start()
                                            val handlernext = Handler()
                                            handlernext.postDelayed(Runnable {
                                                if (mediaPlay != null && !breakCountTime) {
                                                    removeMediaplayer()
                                                    winer()
                                                }
                                            }, 8000)
                                        }
                                    }, 3000)
                                } else {
                                    val handlernext = Handler()
                                    handlernext.postDelayed(Runnable {
                                        if (mediaPlay != null && !breakCountTime) {
                                            removeMediaplayer()
                                            winer()
                                        }
                                    }, 3000)
                                }
                            } else if (number == 6 || number == 7 || number == 8 || number == 9 || number == 10) {
                                removeMediaplayer()
                                mediaPlay = MediaPlayer.create(binding.root.context, R.raw.true_a2)
                                mediaPlay?.start()
                                if (number == 10) {
                                    val handler = Handler()
                                    handler.postDelayed(Runnable {
                                        if (mediaPlay != null && !breakCountTime) {
                                            removeMediaplayer()
                                            mediaPlay = MediaPlayer.create(
                                                binding.root.context,
                                                R.raw.chuc_mung_vuot_moc_02_0
                                            )
                                            mediaPlay?.start()
                                        }
                                        val handlernext = Handler()
                                        handlernext.postDelayed(Runnable {
                                            if (mediaPlay != null && !breakCountTime) {
                                                removeMediaplayer()
                                                winer()
                                            }
                                        }, 8000)
                                    }, 5100)
                                } else {
                                    val handlernext = Handler()
                                    handlernext.postDelayed(Runnable {
                                        if (mediaPlay != null && !breakCountTime) {
                                            removeMediaplayer()
                                            winer()
                                        }
                                    }, 4000)
                                }

                            } else {
                                removeMediaplayer()
                                mediaPlay = MediaPlayer.create(binding.root.context, R.raw.true_a3)
                                mediaPlay?.start()
                                val handlernext = Handler()
                                handlernext.postDelayed(Runnable {
                                    if (mediaPlay != null && !breakCountTime) {
                                        removeMediaplayer()
                                        winer()
                                    }
                                }, 5000)
                            }
                        } else {
                            choseLose = true
                            //false
                            binding.textAnsA.setBackgroundResource(R.drawable.bg_false)
                            (binding.textAnsA.background as AnimationDrawable).start()
                        }

                    }, 5900)
                }
            }
            /*-----------------------------------------------------------------*/
            R.id.textAnsB -> {
                if (!checkClickChoseAnser) {
                    //unable click help
                    unableCLickHelp()
                    // stop count time
                    countTime.cancel(true)
                    // sound chose
                    if (number == 1 || number == 2 || number == 3 || number == 4 || number == 5) {
                        Log.d("key", " Sound CHosse Ok b")
                        mediaPlay = MediaPlayer.create(binding.root.context, R.raw.ans_b)
                        mediaPlay?.start()
                    } else {
                        mediaPlay = MediaPlayer.create(binding.root.context, R.raw.ans_b2)
                        mediaPlay?.start()
                    }
                    // chosse
                    binding.textAnsB.setBackgroundResource(R.drawable.bg_choose)
                    (binding.textAnsB.background as AnimationDrawable).start()
                    checkClickChoseAnser = true
                    //check anwser
                    val handle = Handler()
                    handle.postDelayed(Runnable {
                        Log.d("key", "anser : " + data.answer)
                        if (data.answer == 2 && !breakCountTime) {
                            Log.d("key", "bg true B")
                            binding.textAnsB.setBackgroundResource(R.drawable.bg_ans)
                            (binding.textAnsB.background as AnimationDrawable).start()
                            if (number == 1 || number == 2 || number == 3 || number == 4 || number == 5) {
                                Log.d("key", "sound true B")
                                removeMediaplayer()
                                mediaPlay = MediaPlayer.create(binding.root.context, R.raw.true_b)
                                mediaPlay?.start()
                                if (number == 5) {
                                    val handler = Handler()
                                    handler.postDelayed(Runnable {
                                        if (mediaPlay != null && !breakCountTime) {
                                            removeMediaplayer()
                                            mediaPlay = MediaPlayer.create(
                                                binding.root.context,
                                                R.raw.chuc_mung_vuot_moc_01_0
                                            )
                                            mediaPlay?.start()
                                            val handlernext = Handler()
                                            handlernext.postDelayed(Runnable {
                                                if (mediaPlay != null && !breakCountTime) {
                                                    removeMediaplayer()
                                                    winer()
                                                }
                                            }, 8000)
                                        }
                                    }, 3000)
                                } else {
                                    val handlernext = Handler()
                                    handlernext.postDelayed(Runnable {
                                        if (mediaPlay != null && !breakCountTime) {
                                            removeMediaplayer()
                                            winer()
                                        }
                                    }, 3000)
                                }
                            } else if (number == 6 || number == 7 || number == 8 || number == 9 || number == 10) {
                                removeMediaplayer()
                                mediaPlay = MediaPlayer.create(binding.root.context, R.raw.true_b2)
                                mediaPlay?.start()
                                if (number == 10) {
                                    val handler = Handler()
                                    handler.postDelayed(Runnable {
                                        if (mediaPlay != null && !breakCountTime) {
                                            removeMediaplayer()
                                            mediaPlay = MediaPlayer.create(
                                                binding.root.context,
                                                R.raw.chuc_mung_vuot_moc_02_0
                                            )
                                            mediaPlay?.start()
                                            val handlernext = Handler()
                                            handlernext.postDelayed(Runnable {
                                                if (mediaPlay != null && !breakCountTime) {
                                                    removeMediaplayer()
                                                    winer()
                                                }
                                            }, 5100)
                                        }
                                    }, 3000)

                                } else {
                                    val handlernext = Handler()
                                    handlernext.postDelayed(Runnable {
                                        if (mediaPlay != null && !breakCountTime) {
                                            removeMediaplayer()
                                            winer()
                                        }
                                    }, 5100)
                                }
                            } else {
                                removeMediaplayer()
                                mediaPlay = MediaPlayer.create(binding.root.context, R.raw.true_b3)
                                mediaPlay?.start()
                                val handlernext = Handler()
                                handlernext.postDelayed(Runnable {
                                    if (mediaPlay != null && !breakCountTime) {
                                        removeMediaplayer()
                                        winer()
                                    }
                                }, 2000)
                            }
                        } else {
                            choseLose = true
                            //false
                            binding.textAnsB.setBackgroundResource(R.drawable.bg_false)
                            (binding.textAnsB.background as AnimationDrawable).start()
                        }
                    }, 5900)
                }
            }
            /*-----------------------------------------------------------------*/
            R.id.textAnsC -> {
                if (!checkClickChoseAnser) {
                    //unable click help
                    unableCLickHelp()
                    // stop count time
                    countTime.cancel(true)
                    // sound chose
                    if (number == 1 || number == 2 || number == 3 || number == 4 || number == 5) {
                        Log.d("key", " Sound CHosse Ok c")
                        mediaPlay = MediaPlayer.create(binding.root.context, R.raw.ans_c)
                        mediaPlay?.start()
                    } else {
                        mediaPlay = MediaPlayer.create(binding.root.context, R.raw.ans_c2)
                        mediaPlay?.start()
                    }
                    // chosse
                    binding.textAnsC.setBackgroundResource(R.drawable.bg_choose)
                    (binding.textAnsC.background as AnimationDrawable).start()
                    checkClickChoseAnser = true
                    //check anwser
                    val handle = Handler()
                    handle.postDelayed(Runnable {
                        Log.d("key", "anser : " + data.answer)
                        if (data.answer == 3 && !breakCountTime) {
                            Log.d("key", "bg true C")
                            binding.textAnsC.setBackgroundResource(R.drawable.bg_ans)
                            (binding.textAnsC.background as AnimationDrawable).start()

                            if (number == 1 || number == 2 || number == 3 || number == 4 || number == 5) {
                                Log.d("key", "sound true C")
                                removeMediaplayer()
                                mediaPlay = MediaPlayer.create(binding.root.context, R.raw.true_c)
                                mediaPlay?.start()
                                if (number == 5) {
                                    val handler = Handler()
                                    handler.postDelayed(Runnable {
                                        if (mediaPlay != null && !breakCountTime) {
                                            removeMediaplayer()
                                            mediaPlay = MediaPlayer.create(
                                                binding.root.context,
                                                R.raw.chuc_mung_vuot_moc_01_0
                                            )
                                            mediaPlay?.start()
                                            val handlernext = Handler()
                                            handlernext.postDelayed(Runnable {
                                                if (mediaPlay != null && !breakCountTime) {
                                                    removeMediaplayer()
                                                    winer()
                                                }
                                            }, 8000)
                                        }
                                    }, 3000)

                                } else {
                                    val handlernext = Handler()
                                    handlernext.postDelayed(Runnable {
                                        if (mediaPlay != null && !breakCountTime) {
                                            removeMediaplayer()
                                            winer()
                                        }
                                    }, 3000)
                                }
                            } else if (number == 6 || number == 7 || number == 8 || number == 9 || number == 10) {
                                removeMediaplayer()
                                mediaPlay = MediaPlayer.create(binding.root.context, R.raw.true_c2)
                                mediaPlay?.start()
                                if (number == 10) {
                                    val handler = Handler()
                                    handler.postDelayed(Runnable {
                                        if (mediaPlay != null && !breakCountTime) {
                                            removeMediaplayer()
                                            mediaPlay = MediaPlayer.create(
                                                binding.root.context,
                                                R.raw.chuc_mung_vuot_moc_02_0
                                            )
                                            mediaPlay?.start()
                                            val handlernext = Handler()
                                            handlernext.postDelayed(Runnable {
                                                if (mediaPlay != null && !breakCountTime) {
                                                    removeMediaplayer()
                                                    winer()
                                                }
                                            }, 5100)
                                        }
                                    }, 3000)

                                } else {
                                    val handlernext = Handler()
                                    handlernext.postDelayed(Runnable {
                                        if (mediaPlay != null && !breakCountTime) {
                                            removeMediaplayer()
                                            winer()
                                        }
                                    }, 5000)
                                }
                            } else {
                                removeMediaplayer()
                                mediaPlay = MediaPlayer.create(binding.root.context, R.raw.true_c3)
                                mediaPlay?.start()
                                val handlernext = Handler()
                                handlernext.postDelayed(Runnable {
                                    if (mediaPlay != null && !breakCountTime) {
                                        removeMediaplayer()
                                        winer()
                                    }
                                }, 3000)
                            }
                        } else {
                            choseLose = true
                            //false
                            binding.textAnsC.setBackgroundResource(R.drawable.bg_false)
                            (binding.textAnsC.background as AnimationDrawable).start()
                        }
                    }, 5900)
                }
            }
            /*-----------------------------------------------------------------*/
            R.id.textAnsD -> {
                if (!checkClickChoseAnser) {
                    //unable click help
                    unableCLickHelp()
                    // stop count time
                    countTime.cancel(true)
                    // sound chose
                    if (number == 1 || number == 2 || number == 3 || number == 4 || number == 5) {
                        Log.d("key", " Sound CHosse Ok d")
                        mediaPlay = MediaPlayer.create(binding.root.context, R.raw.ans_d)
                        mediaPlay?.start()
                    } else {
                        mediaPlay = MediaPlayer.create(binding.root.context, R.raw.ans_d2)
                        mediaPlay?.start()
                    }
                    // chosse
                    binding.textAnsD.setBackgroundResource(R.drawable.bg_choose)
                    (binding.textAnsD.background as AnimationDrawable).start()
                    checkClickChoseAnser = true
                    //check anwser
                    val handle = Handler()
                    handle.postDelayed(Runnable {
                        Log.d("key", "anser : " + data.answer)
                        if (data.answer == 4 && !breakCountTime) {
                            Log.d("key", "bg true D")
                            binding.textAnsD.setBackgroundResource(R.drawable.bg_ans)
                            (binding.textAnsD.background as AnimationDrawable).start()
                            if (number == 10 || number == 11 || number == 12 || number == 13 || number == 14 || number == 15) {
                                Log.d("key", "sound true D")
                                removeMediaplayer()
                                mediaPlay = MediaPlayer.create(binding.root.context, R.raw.true_d3)
                                mediaPlay?.start()
                                if (number == 10) {
                                    val handler = Handler()
                                    handler.postDelayed(Runnable {
                                        if (mediaPlay != null && !breakCountTime) {
                                            removeMediaplayer()
                                            mediaPlay = MediaPlayer.create(
                                                binding.root.context,
                                                R.raw.chuc_mung_vuot_moc_02_0
                                            )
                                            mediaPlay?.start()
                                            val handlernext = Handler()
                                            handlernext.postDelayed(Runnable {
                                                if (mediaPlay != null && !breakCountTime) {
                                                    removeMediaplayer()
                                                    winer()
                                                }
                                            }, 5100)
                                        }
                                    }, 3000)

                                } else {
                                    val handlernext = Handler()
                                    handlernext.postDelayed(Runnable {
                                        if (mediaPlay != null && !breakCountTime) {
                                            removeMediaplayer()
                                            winer()
                                        }
                                    }, 5000)
                                }
                            } else {
                                removeMediaplayer()
                                mediaPlay = MediaPlayer.create(binding.root.context, R.raw.true_d2)
                                mediaPlay?.start()
                                if (number == 5) {
                                    val handler = Handler()
                                    handler.postDelayed(Runnable {
                                        if (mediaPlay != null && !breakCountTime) {
                                            removeMediaplayer()
                                            mediaPlay = MediaPlayer.create(
                                                binding.root.context,
                                                R.raw.chuc_mung_vuot_moc_01_0
                                            )
                                            mediaPlay?.start()
                                            val handlernext = Handler()
                                            handlernext.postDelayed(Runnable {
                                                if (mediaPlay != null && !breakCountTime) {
                                                    removeMediaplayer()
                                                    winer()
                                                }
                                            }, 8000)
                                        }
                                    }, 3000)

                                } else {
                                    val handlernext = Handler()
                                    handlernext.postDelayed(Runnable {
                                        if (mediaPlay != null && !breakCountTime) {
                                            removeMediaplayer()
                                            winer()
                                        }
                                    }, 3000)
                                }
                            }
                        } else {
                            choseLose = true
                            //false
                            binding.textAnsD.setBackgroundResource(R.drawable.bg_false)
                            (binding.textAnsD.background as AnimationDrawable).start()
                        }
                    }, 5900)
                }
            }
            /*-----------------------------------------------------------------*/
            R.id.btnTerminate -> {
                // stop count time
                countTime.cancel(true)
                binding.ivUseTerminate.visibility = View.VISIBLE
                removeMediaplayer()
                if (!breakCountTime) {
                    mediaPlay = MediaPlayer.create(binding.root.context, R.raw.lose)
                    mediaPlay!!.start()
                    showDiaLogEnd()
                }
            }
            /*-----------------------------------------------------------------*/
            R.id.btnRestart -> {
                binding.ivUseRestart.visibility = View.VISIBLE
                binding.btnRestart.isEnabled = false
                isUseRestart = true
                val database = DatabaseManager(binding.root.context)
                database.openDatabse()
                val cursor =
                    database.getData("SELECT * from question  where level = $number order by random() LIMIT 1")
                cursor.moveToFirst() //cần  có  để chạy từ phần tử đầu tiên trong mảng , không có thì n chạy từ đâu đâu -> có thể bị lỗi cao
                val index_question = cursor.getColumnIndex("question")
                val index_a = cursor.getColumnIndex("casea")
                val index_b = cursor.getColumnIndex("caseb")
                val index_c = cursor.getColumnIndex("casec")
                val index_d = cursor.getColumnIndex("cased")
                val index_true = cursor.getColumnIndex("truecase")

                val question = cursor.getString(index_question)
                val a = cursor.getString(index_a)
                val b = cursor.getString(index_b)
                val c = cursor.getString(index_c)
                val d = cursor.getString(index_d)
                val truecase = cursor.getInt(index_true)
                binding.textQuestion.text = question
                binding.textAnsA.text = a
                binding.textAnsB.text = b
                binding.textAnsC.text = c
                binding.textAnsD.text = d
                data.answer = truecase
                cursor.close()
                database.closeDatabase()
            }
            /*-----------------------------------------------------------------*/
            R.id.btnPercent50 -> {
                removeMediaplayer()
                mediaPlay = MediaPlayer.create(binding.root.context, R.raw.sound5050)
                mediaPlay?.start()
                val handle = Handler()
                handle.postDelayed({
                    binding.ivUsePercent50.visibility = View.VISIBLE
                    binding.btnPercent50.isEnabled = false
                    isUsePercent50 = true
                    if (data.answer == 1 || data.answer == 3) {
                        binding.textAnsB.text = ""
                        binding.textAnsB.isEnabled = false
                        binding.textAnsD.text = ""
                        binding.textAnsD.isEnabled = false
                    } else {
                        binding.textAnsA.text = ""
                        binding.textAnsA.isEnabled = false
                        binding.textAnsC.text = ""
                        binding.textAnsC.isEnabled = false
                    }
                }, 2990)
            }
            /*---------------------------------------------------*/
            R.id.btnHelp -> {
                removeMediaplayer()
                mediaPlay = MediaPlayer.create(binding.root.context, R.raw.khan_gia)
                mediaPlay?.start()
                val handle = Handler()
                handle.postDelayed({
                    binding.ivUseHelp.visibility = View.VISIBLE
                    binding.btnHelp.isEnabled = false
                    isUseHelp = true
                    val binding_barchar = DialogBarchartBinding.inflate(
                        layoutInflater,
                        null,
                        false
                    )
                    val random = Random()
                    var A = random.nextInt(60).toFloat()
                    var B = random.nextInt(60).toFloat()
                    var C = random.nextInt(60).toFloat()
                    var D = random.nextInt(60).toFloat()
                    when (data.answer) {
                        1 -> A = random.nextInt(20) + 80F
                        2 -> B = random.nextInt(20) + 80F
                        3 -> C = random.nextInt(20) + 80F
                        4 -> D = random.nextInt(20) + 80F
                    }
                    val arrbarchart = ArrayList<BarEntry>()
                    arrbarchart.add(BarEntry(1F, A))
                    arrbarchart.add(BarEntry(2F, B))
                    arrbarchart.add(BarEntry(3F, C))
                    arrbarchart.add(BarEntry(4F, D))
                    val barDataSet = BarDataSet(arrbarchart, "%")
                    barDataSet.setColors(ColorTemplate.MATERIAL_COLORS[0])
                    barDataSet.valueTextColor = Color.BLACK
                    barDataSet.valueTextColor = 14
                    //
                    val barData = BarData(barDataSet)
                    binding_barchar.barchart.setFitBars(true)
                    binding_barchar.barchart.description.text = " "
                    binding_barchar.barchart.data = barData
                    binding_barchar.barchart.animateY(2000)
                    //dialog
                    val builder = AlertDialog.Builder(binding.root.context)
                    builder.setView(binding_barchar.root)
                    builder.setPositiveButton("Ok") { _: DialogInterface, i: Int ->
                        // dismiss dialog
                    }
                    dialogBarchart = builder.create()
                    if (!breakCountTime) {
                        dialogBarchart?.show()
                    }
                }, 5000)
            }
            R.id.btnCall -> {
                removeMediaplayer()
                mediaPlay = MediaPlayer.create(binding.root.context, R.raw.help_call)
                mediaPlay?.start()
                binding.ivUseCall.visibility = View.VISIBLE
                binding.btnCall.isEnabled = false
                isUseCall = true
                val bindingCalling = DialogCallBinding.inflate(layoutInflater, null, false)
                bindingCalling.calling.setOnClickListener {
                    val anser = data.answer
                    var anserCall = ""
                    when (anser) {
                        1 -> anserCall = "A"
                        2 -> anserCall = "B"
                        3 -> anserCall = "C"
                        4 -> anserCall = "D"
                    }
                    bindingCalling.textanser.text = "Đáp án là : $anserCall"
                }
                val buildercell = AlertDialog.Builder(binding.root.context)
                buildercell.setView(bindingCalling.root)
                buildercell.setPositiveButton("Ok") { _: DialogInterface, i: Int ->
                    // dismiss dialog
                }
                dialogcell = buildercell.create()
                if (!breakCountTime) {
                    dialogcell?.show()
                }
            }

        }
        val handle = Handler()
        handle.postDelayed(Runnable {
            if (choseLose && !breakCountTime) {
                when (data.answer) {
                    1 -> {
                        //sound lose
                        if (number == 1 || number == 2 || number == 3 || number == 4 || number == 5) {
                            Log.d("key", " Sound CHosse false a")
                            if (mediaPlay != null) {
                                removeMediaplayer()
                            }
                            mediaPlay = MediaPlayer.create(binding.root.context, R.raw.lose_a)
                            mediaPlay?.start()
                        } else {
                            if (mediaPlay != null) {
                                removeMediaplayer()
                            }
                            mediaPlay = MediaPlayer.create(binding.root.context, R.raw.lose_a2)
                            mediaPlay?.start()
                        }
                        // bg lose
                        binding.textAnsA.setBackgroundResource(R.drawable.bg_ans)
                        (binding.textAnsA.background as AnimationDrawable).start()
                        // end game
                        val handle = Handler()
                        handle.postDelayed(Runnable {
                            removeMediaplayer()
                            if (!breakCountTime) {
                                mediaPlay = MediaPlayer.create(binding.root.context, R.raw.lose)
                                mediaPlay!!.start()
                                showDiaLogEnd()
                            }
                        }, 5000)
                    }
                    2 -> {
                        // sound lose
                        if (number == 1 || number == 2 || number == 3 || number == 4 || number == 5) {
                            Log.d("key", " Sound CHosse false B")
                            removeMediaplayer()
                            mediaPlay = MediaPlayer.create(binding.root.context, R.raw.lose_b)
                            mediaPlay?.start()
                        } else {
                            removeMediaplayer()
                            mediaPlay = MediaPlayer.create(binding.root.context, R.raw.lose_b2)
                            mediaPlay?.start()
                        }
                        // bg lose
                        binding.textAnsB.setBackgroundResource(R.drawable.bg_ans)
                        (binding.textAnsB.background as AnimationDrawable).start()
                        // end game
                        val handle = Handler()
                        handle.postDelayed(Runnable {
                            removeMediaplayer()
                            if (!breakCountTime) {
                                mediaPlay = MediaPlayer.create(binding.root.context, R.raw.lose)
                                mediaPlay!!.start()
                                showDiaLogEnd()
                            }
                        }, 5000)
                    }
                    3 -> {
                        // sound
                        if (number == 1 || number == 2 || number == 3 || number == 4 || number == 5) {
                            Log.d("key", " Sound CHosse false B")
                            removeMediaplayer()
                            mediaPlay = MediaPlayer.create(binding.root.context, R.raw.lose_c)
                            mediaPlay?.start()
                        } else {
                            removeMediaplayer()
                            mediaPlay = MediaPlayer.create(binding.root.context, R.raw.lose_c2)
                            mediaPlay?.start()
                        }
                        //bg lose
                        binding.textAnsC.setBackgroundResource(R.drawable.bg_ans)
                        (binding.textAnsC.background as AnimationDrawable).start()
                        // end game
                        val handle = Handler()
                        handle.postDelayed(Runnable {
                            removeMediaplayer()
                            if (!breakCountTime) {
                                mediaPlay = MediaPlayer.create(binding.root.context, R.raw.lose)
                                mediaPlay!!.start()
                                showDiaLogEnd()
                            }
                        }, 5000)
                    }
                    4 -> {
                        //sound
                        if (number == 1 || number == 2 || number == 3 || number == 4 || number == 5) {
                            Log.d("key", " Sound CHosse false B")
                            removeMediaplayer()
                            mediaPlay = MediaPlayer.create(binding.root.context, R.raw.lose_d)
                            mediaPlay?.start()
                        } else {
                            removeMediaplayer()
                            mediaPlay = MediaPlayer.create(binding.root.context, R.raw.lose_d2)
                            mediaPlay?.start()
                        }
                        // bg lose
                        binding.textAnsD.setBackgroundResource(R.drawable.bg_ans)
                        (binding.textAnsD.background as AnimationDrawable).start()
                        // end game
                        val handle = Handler()
                        handle.postDelayed(Runnable {
                            removeMediaplayer()
                            if (!breakCountTime) {
                                mediaPlay = MediaPlayer.create(binding.root.context, R.raw.lose)
                                mediaPlay!!.start()
                                showDiaLogEnd()
                            }
                        }, 5000)
                    }
                }
            }
        }, 6100)
    }

    private fun winer() {
        if (number == 15) {
            mediaPlay = MediaPlayer.create(binding.root.context, R.raw.best_player)
            mediaPlay?.start()
            showDiaLogEnd()
        } else {
            Log.d("help", " $isUseRestart : $isUsePercent50 : $isUseHelp : $isUseCall")
            (activity as MainActivity).fragment_money_questions(
                number + 1,
                isUseRestart,
                isUsePercent50,
                isUseHelp,
                isUseCall
            )
        }
    }

    private fun unableCLickHelp() {
        // unable click help
        binding.btnTerminate.isEnabled = false
        binding.btnRestart.isEnabled = false
        binding.btnHelp.isEnabled = false
        binding.btnPercent50.isEnabled = false
        binding.btnCall.isEnabled = false
    }

    private fun removeMediaplayer() {
        mediaPlay?.stop()
        mediaPlay?.release()
        mediaPlay = null
    }

    override fun onStop() {
        Log.d("Key", "OnStop")
        super.onStop()
        bg_mediaPlay?.pause()
        mediaPlay?.pause()
    }

    override fun onStart() {
        Log.d("Key", "onStart")
        super.onStart()
        if (bg_mediaPlay != null && !bg_mediaPlay!!.isPlaying) {
            bg_mediaPlay?.start()
            mediaPlay?.start()
        }
    }

    override fun onResume() {
        Log.d("Key", "on Resume")
        super.onResume()
        if (bg_mediaPlay != null && !bg_mediaPlay!!.isPlaying) {
            bg_mediaPlay?.start()
            mediaPlay?.start()
        }
    }

}