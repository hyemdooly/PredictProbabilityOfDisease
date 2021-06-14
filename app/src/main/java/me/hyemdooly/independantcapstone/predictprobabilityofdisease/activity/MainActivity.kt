package me.hyemdooly.independantcapstone.predictprobabilityofdisease.activity

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.database.sqlite.transaction
import asMultipart
import kotlinx.android.synthetic.main.activity_main.*
import me.hyemdooly.independantcapstone.predictprobabilityofdisease.R
import me.hyemdooly.independantcapstone.predictprobabilityofdisease.ResultDTO
import me.hyemdooly.independantcapstone.predictprobabilityofdisease.RetrofitAPI
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.InputStreamReader
import java.util.concurrent.TimeUnit


private const val OPEN_DIRECTORY_REQUEST_CODE = 1000

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_open_file.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "text/*"
            }

            startActivityForResult(intent, OPEN_DIRECTORY_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == OPEN_DIRECTORY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val directoryUri = data?.data ?: return
            layout_wait.visibility = View.VISIBLE
            val client = OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder().baseUrl("http://158.247.218.97:5000").client(client).addConverterFactory(GsonConverterFactory.create()).build()
            val server = retrofit.create(RetrofitAPI::class.java)
            val data = directoryUri.asMultipart("file", contentResolver)
            server.postUpload(data!!).enqueue(object: Callback<ResultDTO> {
                override fun onFailure(call: Call<ResultDTO>, t: Throwable) {
                    Log.d("response", t.message.toString())
                    Toast.makeText(applicationContext, "서버 통신에 문제가 발생했습니다.", Toast.LENGTH_LONG).show()
                    layout_wait.visibility = View.INVISIBLE
                }

                override fun onResponse(call: Call<ResultDTO>, response: Response<ResultDTO>) {
                    layout_wait.visibility = View.INVISIBLE
                    if(response.code() == 200) {
                        val intent = Intent(applicationContext, ResultActivity::class.java)
                        response.body()!!.data.forEach {
                            val geno = it.geno
                            intent.putExtra("${geno}_count", it.total)
                            intent.putExtra("${geno}_max_pval", it.max_p_val)
                            intent.putExtra("${geno}_min_pval", it.min_p_val)
                        }
                        startActivity(intent)
                    } else {
                        Toast.makeText(applicationContext, "Request Code : ${response.code()}", Toast.LENGTH_LONG).show()
                    }
                }

            })

        }
    }

}