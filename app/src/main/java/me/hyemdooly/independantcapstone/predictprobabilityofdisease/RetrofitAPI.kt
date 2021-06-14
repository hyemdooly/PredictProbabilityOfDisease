package me.hyemdooly.independantcapstone.predictprobabilityofdisease

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

data class ItemDTO(val geno: Int, val total: String, val min_p_val: String, val max_p_val: String)
data class ResultDTO(val data: MutableList<ItemDTO>)

interface RetrofitAPI {
    @Multipart
    @POST("/upload")
    fun postUpload(@Part file: MultipartBody.Part): Call<ResultDTO>

}