package com.mertinam.storeapp.view.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.huawei.hms.ads.AdListener
import com.huawei.hms.ads.AdParam
import com.huawei.hms.ads.BannerAdSize
import com.huawei.hms.ads.InterstitialAd
import com.huawei.hms.ads.banner.BannerView
import com.mertinam.storeapp.R
import com.mertinam.storeapp.adapter.ProductAdapter
import com.mertinam.storeapp.databinding.FragmentHomeBinding
import com.mertinam.storeapp.util.ApplicationViewModelFactory
import com.mertinam.storeapp.viewmodel.MainViewModel


class HomeFragment : Fragment() {

  //  private lateinit var viewModel : MainViewModel
    private val viewModel : MainViewModel by viewModels {
        ApplicationViewModelFactory(requireActivity().application)
  }

    lateinit var bannerView: BannerView
    private val tag = "HUAWEI_AD"
    companion object{
        var interstitialAd: InterstitialAd? = null
    }


    private lateinit var binding : FragmentHomeBinding
   /* private var productAdapter = ProductAdapter(arrayListOf()) { position ->
      //  findNavController().navigate(R.id.action_homeFragment_to_detailFragment)
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment())
    }
    */


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false)
       // viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        loadBannerAd()
        loadInterstitialAd()

        binding.productRV.layoutManager = LinearLayoutManager(requireContext())
        viewModel.getDataFromAPI()
        setObservers()
        // Inflate the layout for this fragment
        return binding.root
    }


    private fun setObservers()
    {
        viewModel.productData.observe(viewLifecycleOwner) { list ->
             val productAdapter = ProductAdapter(arrayListOf()) { position ->
                //  findNavController().navigate(R.id.action_homeFragment_to_detailFragment)
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(list[position]))
            }
            binding.productRV.adapter = productAdapter
            productAdapter.updateList(list)
            viewModel.insertAll(list)

        }
        viewModel.productLoad.observe(viewLifecycleOwner) { loading ->
            if(loading)
                binding.loadingPB.visibility= View.VISIBLE
            else
                binding.loadingPB.visibility = View.GONE
        }
        viewModel.productError.observe(viewLifecycleOwner){ error ->
            if(error)
                binding.errorTV.visibility = View.VISIBLE
            else
                binding.errorTV.visibility = View.GONE
        }
    }

    private fun loadBannerAd(){
        // Obtain BannerView.
        bannerView = binding.hwBannerView
        bannerView.adListener = getAdListener()
        // Set the ad unit ID and ad dimensions. "testw6vs28auh3" is a dedicated test ad unit ID.
        bannerView.adId = "testw6vs28auh3"
        bannerView.bannerAdSize = BannerAdSize.BANNER_SIZE_360_57
        // Set the refresh interval to 60 seconds.
        bannerView.setBannerRefresh(60)
        // Create an ad request to load an ad.
        val adParam = AdParam.Builder().build()
        bannerView.loadAd(adParam)
    }

    private fun loadInterstitialAd(){
        interstitialAd = InterstitialAd(this.context);
        interstitialAd!!.adListener = getAdListener()
        // "testb4znbuh3n2" is a dedicated test ad unit ID. Before releasing your app, replace the test ad unit ID with the formal one.
        interstitialAd!!.setAdId("testb4znbuh3n2");
        // Load an interstitial ad.
        val adParam = AdParam.Builder().build()
        interstitialAd!!.loadAd(adParam)
    }

    private fun getAdListener(): AdListener{
         val adListener: AdListener = object : AdListener() {
            override fun onAdLoaded() {
                // Called when an ad is loaded successfully.
                Log.i(tag, "onAdLoaded")
            }
            override fun onAdFailed(errorCode: Int) {
                // Called when an ad fails to be loaded.
                Log.i(tag, "onAdFailed")
                Log.i(tag, errorCode.toString())
            }
            override fun onAdOpened() {
                // Called when an ad is opened.
                Log.i(tag, "onAdOpened")
            }
            override fun onAdClicked() {
                // Called when an ad is clicked.
                Log.i(tag, "onAdClicked")

            }
            override fun onAdLeave() {
                // Called when an ad leaves an app.
                Log.i(tag, "onAdLeaved")
            }
            override fun onAdClosed() {
                // Called when an ad is closed.
                Log.i(tag, "onAdClosed")
            }
        }
        return adListener
    }

    override fun onDestroy() {
        super.onDestroy()
        bannerView.destroy()
    }

}