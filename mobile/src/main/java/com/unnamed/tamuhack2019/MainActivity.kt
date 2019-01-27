package com.unnamed.tamuhack2019

import retrofit2.Callback
import retrofit2.Call
import android.annotation.SuppressLint
import android.app.ActionBar
import android.app.DownloadManager
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.content_main.*
import okhttp3.ResponseBody
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

private val TAG: String = MainActivity::class.java.simpleName

val employeeAPI by lazy {
    EmployeeAPIService.create()
}

val modelEmployee = Model.User(8, "Chase McDermott", false)

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        done.setOnClickListener {
//
//        }
//
//        help.setOnClickListener {
//
//        }
        progressBar.max = 100
        progressBar.min = 0
        progressBar.progress = progressBar.min
        //progressBar.scaleY = 3f
        progressBar.scaleX = 1.25F

        setTasks(modelEmployee.id)
        startTimer()

//        done.setOnClickListener {
//            employeeAPI.userDone(1)
//        }
//
//        help.setOnClickListener {
//            employeeAPI.helpUser(1)
//        }

    }
//
//    val queue = InterWebs.getInstance(this.applicationContext).requestQueue
//    val url = "https://20190127t031706-dot-tamuhack2019-todo.appspot.com/users/" + modelEmployee.id
//    val jsonObjReq = JsonObjectRequest(Request.Method.GET, url, null,
//            Response.Listener { response ->
//
//            }
//        )

    private fun setTasks(userId: Int) {
        val call: Call<User> = employeeAPI.getTasks(userId)
        var user: User? = User()




        call.enqueue(object: Callback<User>  {
            override fun onResponse(c: Call<User>, response: Response<User> ) {
                if (response.isSuccessful) {
                    Log.d(TAG, response.body().toString())
                    user = response.body() as User

                    if (user?.user?.id != 0) {
                        val tasks = user?.user?.tasks

                        if (tasks != null) {
                            for (task in tasks) {
                                val checky = CheckBox(applicationContext)
                                checky.text = task.name
                                val lparams = ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT)
                                checky.setOnCheckedChangeListener { buttonView, isChecked ->
                                    if (isChecked) {
                                        val checked = employeeAPI.completedTask(task.id)

                                        checked.enqueue(object: Callback<ResponseBody>  {
                                            override fun onResponse(c: Call<ResponseBody>, response: Response<ResponseBody> ) {
                                                if (response.isSuccessful) {
                                                    Log.d(TAG, "Everything should be good!")
                                                } else {
                                                    Log.d(TAG, response.code().toString())
                                                }

                                            }

                                            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                                                Log.d(TAG, "Something drastic happened!")
                                                t.printStackTrace()
                                            }
                                        })
                                    }
                                }
                                linearBoxes.addView(checky, lparams)
                            }
                        }
                    }
                } else {
                    Log.d(TAG, response.code().toString())
                }

            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d(TAG, "Something messed up!")
                t.printStackTrace()
            }
        })

    }



    private fun startTimer() {
        object: CountDownTimer(330000, 1000) {
            val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            override fun onFinish() {
                timer.text = "00:00"
                vibrate(5000)
            }

            @SuppressLint("SetTextI18n")
            override fun onTick(p0: Long) {
                val millis = p0
                val minutes = (millis / 1000) / 60
                val seconds = ((millis / 1000) % 60)
                if (seconds < 10) {
                    timer.text = minutes.toString() + ":0" + seconds.toString()
                } else {
                    timer.text = minutes.toString() + ":" + seconds.toString()
                }

                if (millis in 299500L..300500L) {
                    vibrate(500)
                    Log.d(TAG, "Made it!")
                } else if (millis in 119000L..121000L) {
                    vibrate(1000)
                } else if (millis in 59000L..61000L) {
                    vibrate(1500)
                } else {
                    Log.d(TAG, "Didn't make it :(")
                }

            }
        }.start()
    }

    private fun vibrate(time : Long) {
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(VibrationEffect.createOneShot(time, VibrationEffect.DEFAULT_AMPLITUDE))
    }


}
