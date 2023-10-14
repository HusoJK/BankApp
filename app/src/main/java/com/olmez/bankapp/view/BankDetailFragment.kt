package com.olmez.bankapp.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.olmez.bankapp.R
import com.olmez.bankapp.databinding.FragmentBankDetailBinding
import com.olmez.bankapp.databinding.FragmentBankListBinding
import com.olmez.bankapp.model.Branch
import com.olmez.bankapp.viewmodel.BankDetailViewModel
import com.olmez.bankapp.viewmodel.BankListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BankDetailFragment : Fragment() {

    lateinit var viewModel: BankDetailViewModel
    private var branchID: Int = 0
    private var _binding: FragmentBankDetailBinding? = null
    private val binding get() = _binding!!
    var address = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBankDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * Sayfa geçişinde Navigation Component ile gönderilen id bilgisi
         */
        arguments?.let {
            branchID = BankDetailFragmentArgs.fromBundle(it).branchID
        }

        viewModel = ViewModelProvider(this)[BankDetailViewModel::class.java]
        viewModel.getBranchFromRoom(branchID)

            observeLiveData()

        binding.openGoogleMapsButton.setOnClickListener {
            openMaps(address)
        }


    }

    /**
     * Bu methodda ViewModel içindeki Live Data`ların gözlemlenip UI tarafına aktarma işlemi yapılmıştır
     */
    private fun observeLiveData() {
        viewModel.branchLiveData.observe(viewLifecycleOwner, Observer { branch ->
            branch?.let {
                binding.regionalCoordinatorTextView.text = branch.regionalCoordinator
                binding.cityTextView.text = branch.city
                binding.districtTextView.text = branch.district
                binding.addressNameTextView.text = branch.addressName
                binding.bankTypeTextView.text = branch.bankType
                binding.codeTextView.text = branch.bankCode
                binding.addressTextView.text = branch.address

                binding.detailToolBar.text = branch.city + " / " + branch.district

                address = branch.address

            }

        })
    }

    /**
     * Bu methodda haritaya yönlendirme yapılmıştır
     */
    fun openMaps(address:String){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=$address"))
        intent.setPackage("com.google.android.apps.maps")
        if (intent.resolveActivity(requireActivity().packageManager) != null && !address.equals("")) {
            startActivity(intent)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}