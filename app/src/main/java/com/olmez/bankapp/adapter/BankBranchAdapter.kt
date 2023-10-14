package com.olmez.bankapp.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.olmez.bankapp.databinding.BankBranchItemBinding
import com.olmez.bankapp.model.Branch
import com.olmez.bankapp.view.BankListFragmentDirections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BankBranchAdapter(val branchList: ArrayList<Branch>) :
    RecyclerView.Adapter<BankBranchAdapter.ViewHolder>() {
    private val firebaseAnalytics = Firebase.analytics

    class ViewHolder(val binding: BankBranchItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            BankBranchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val branch = branchList[position]
        holder.binding.brunchTextview.text =
            branch.city + " " + "-" + " " + branch.district + " " + "(${branch.bankCode})"

        holder.binding.brunchItem.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, branch.id.toString())
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "branch")
            bundle.putString("regional_coordinator", branch.regionalCoordinator)
            bundle.putString("city", branch.city)
            bundle.putString("district", branch.district)
            bundle.putString("address_name", branch.addressName)
            bundle.putString("bank_type", branch.bankType)
            bundle.putString("bank_code", branch.bankCode)
            bundle.putString("address", branch.address)



            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SHARE, bundle)

            val action =
                BankListFragmentDirections.actionBankListFragmentToBankDetailFragment(branch.id)
            Navigation.findNavController(it).navigate(action)

        }
        /* holder.binding.city.text = branch.city
         holder.binding.branchType.text = branch.bankType
         holder.binding.branchName.text = branch.bankBranch

         holder.binding.branchItem.setOnClickListener {


         }*/
    }

    override fun getItemCount(): Int {
        return branchList.size
    }


    fun updateCountryList(newBranchesList: List<Branch>) {
        branchList.clear()
        branchList.addAll(newBranchesList)
        notifyDataSetChanged()

    }

    fun setData(newData: List<Branch>) {
        branchList.addAll(newData)
        notifyDataSetChanged() // RecyclerView'ı güncelleyin
    }

    /*override fun onBranchClicked(v: View) {
       /* val uuid = v.countryUuidText.text.toString().toInt()
        val action =
        //action.countryUuid
        Navigation.findNavController(v).navigate(action)*/
        val action = BankListFragmentDirections.actionBankListFragmentToBankDetailFragment()
    }*/


}