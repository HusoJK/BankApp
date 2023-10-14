package com.olmez.bankapp.api

/**
 * API çağrısının yapıldığı class
 */
import com.olmez.bankapp.model.Branch
import com.olmez.bankapp.util.Util.NAME_SPACE
import retrofit2.Response
import retrofit2.http.GET

interface CallAPI {
    @GET(NAME_SPACE)
  suspend fun getData() : Response<List<Branch>>
}