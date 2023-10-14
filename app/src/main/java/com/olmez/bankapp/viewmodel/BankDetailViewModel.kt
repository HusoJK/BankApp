package com.olmez.bankapp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import com.olmez.bankapp.db.BranchDatabase
import com.olmez.bankapp.model.Branch
import kotlinx.coroutines.launch


class BankDetailViewModel(application: Application) : BaseViewModel(application) {

    val branchLiveData = MutableLiveData<Branch>()
    private val firebaseAnalytics = Firebase.analytics

    /**
     * Detay ekranında gösterilmek için veriler Database`den alınmıştır ve Firebase Analythics loglama işlemi yapılmıştır
     */
    fun getBranchFromRoom(branchID: Int) {
        launch {
            val dao = BranchDatabase(getApplication()).branchDao()
            val branch = dao.getBranch(branchID)
            branchLiveData.value = branch

            firebaseAnalytics.logEvent("branches") {
                branchLiveData.value?.let { param("id", it.id.toString()) }
                branchLiveData.value?.let { param("city", it.city) }
                branchLiveData.value?.let { param("address", it.district) }
                branchLiveData.value?.let { param("bankBranch", it.bankBranch) }
                branchLiveData.value?.let { param("bankType", it.bankType) }
                branchLiveData.value?.let { param("bankCode", it.bankCode) }
                branchLiveData.value?.let { param("addressName", it.addressName) }
                branchLiveData.value?.let { param("address", it.address) }
                branchLiveData.value?.let { param("postCode", it.postCode) }
                branchLiveData.value?.let { param("onOffLine", it.onOffLine) }
                branchLiveData.value?.let { param("onOffSite", it.onOffSite) }
                branchLiveData.value?.let { param("nearestAtm", it.nearestAtm) }


            }
        }
    }


}