package com.mertinam.storeapp.view.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.mertinam.storeapp.R
import com.mertinam.storeapp.databinding.FragmentDetailBinding
import com.mertinam.storeapp.util.ApplicationViewModelFactory
import com.mertinam.storeapp.util.downloadURL
import com.mertinam.storeapp.viewmodel.MainViewModel


class DetailFragment : Fragment() {

    val args : DetailFragmentArgs by navArgs()
    private lateinit var binding : FragmentDetailBinding
    private val viewModel : MainViewModel by viewModels {
        ApplicationViewModelFactory(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_detail,container,false)
        viewModel.findByName(args.product.title)
            initUI()
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun initUI(){
      //  val product = args.product
        viewModel.product.observe(viewLifecycleOwner){product ->
            with(binding){
                tvProductName.text = product.title
                tvCategory.text = product.category
                tvPrice.text = product.price.toString()
                tvDescription.text = product.description
                ivProduct.downloadURL(product.imageUrl)

            }
        }
    }

}