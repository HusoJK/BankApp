package com.olmez.bankapp.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.olmez.bankapp.R
import com.olmez.bankapp.adapter.BankBranchAdapter
import com.olmez.bankapp.databinding.FragmentBankListBinding
import com.olmez.bankapp.model.Branch
import com.olmez.bankapp.viewmodel.BankListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class BankListFragment : Fragment() {


    lateinit var viewModel: BankListViewModel
    private val branchAdapter = BankBranchAdapter(arrayListOf())
    private var _binding: FragmentBankListBinding? = null
    private val binding get() = _binding!!

    private var searchDataList = ArrayList<Branch>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBankListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[BankListViewModel::class.java]
        viewModel.getData()

        binding.bankListRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.bankListRecyclerView.adapter = branchAdapter

        searchButtonEvents()
        observeLiveData()

    }

    /**
     * Bu methodda şehir ismine göre arama işlemi yapılmıştır
     */
    private fun filterData(query: String) {
        val filteredList = ArrayList<Branch>()
        for (i in searchDataList) {
            if (i.city.lowercase(Locale.ROOT).contains(query)) {
                filteredList.add(i)
            }
        }
        if (!filteredList.isEmpty()) {
            branchAdapter.updateCountryList(filteredList)
        }
    }

    /**
     * Bu methodda ViewModel içindeki Live Data`ların gözlemlenip UI tarafına aktarma işlemi yapılmıştır
     */
    private fun observeLiveData() {
        viewModel.branches.observe(viewLifecycleOwner, Observer { branches ->
            branches?.let {

                binding.bankListRecyclerView.visibility = View.VISIBLE
                branchAdapter.updateCountryList(branches)

                for (item in branches) {
                    searchDataList.add(item)
                }
            }
        })
        viewModel.loading.observe(viewLifecycleOwner, Observer { loading ->
            if (loading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            if (error) {
                binding.errorTextView.visibility = View.VISIBLE
            } else {
                binding.errorTextView.visibility = View.GONE
            }
        })


        viewModel.isNetworkConnected.observe(viewLifecycleOwner, Observer { connection ->
            if (connection == false){
                binding.progressBar.visibility = View.GONE
                binding.textView.visibility = View.VISIBLE
                binding.errorTextView.visibility = View.GONE
            }else{
                binding.progressBar.visibility = View.VISIBLE
                binding.textView.visibility = View.GONE
            }
        })

    }

    /**
     * Bu methodda search buttonunun görevleri tanımlanmıştır
     */
    private fun searchButtonEvents() {
        binding.searchEditText.setOnClickListener {
            it.isFocusableInTouchMode = true
            it.requestFocus()
        }

        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterData(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}