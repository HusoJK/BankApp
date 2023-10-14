package com.olmez.bankapp.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.olmez.bankapp.api.CallAPI
import com.olmez.bankapp.db.BranchDatabase
import com.olmez.bankapp.model.Branch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BankListViewModel @Inject constructor(
    private val apiCallAPI: CallAPI,
    application: Application
) : BaseViewModel(application) {

    val branches = MutableLiveData<List<Branch>>()
    val error = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()
    val isNetworkConnected = MutableLiveData<Boolean>()

    /**
     * API çağrısını tetikleyip verilerin işlendiği method
     */
    fun getData() {
        loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val isNetworkAvailable = isNetworkAvailable()

                if (isNetworkAvailable) {
                    val response = apiCallAPI.getData()
                    if (response.isSuccessful) {
                        val branchList: List<Branch>? = response.body()
                        error.postValue(false)

                        if (branchList != null && branchList.isNotEmpty()) {
                            branches.postValue(branches.value.orEmpty() + branchList)

                        }

                        if (branchList != null) {
                            storeInSQLite(branchList) // Detay ekranında kullanabilmek için veriler Room DB`ye eklendi
                        }
                    } else {
                        error.postValue(true)
                    }
                } else {
                    error.postValue(true)
                }


                isNetworkConnected.postValue(isNetworkAvailable)
            } catch (e: Exception) {
                error.postValue(true)

                isNetworkConnected.postValue(false)
            } finally {
                loading.postValue(false)
            }
        }

    }

    /**
     * Room DB`ye ekleme yapan scope bu methodda bulunmaktadır.
     */
    private fun storeInSQLite(list: List<Branch>) {
        launch {
            val dao = BranchDatabase(getApplication()).branchDao()
            dao.deleteAllBranches()
            var listLong = dao.insertBranches(*list.toTypedArray())
            var i = 0
            while (i < list.size) {
                list[i].id = listLong[i].toInt()
                i = i + 1
            }
        }
    }

    /**
     * İnternet bağlantısı kontrolü bu methodda yapılmıştır
     */
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }


}