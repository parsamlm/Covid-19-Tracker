package ir.pmoslem.covid_19tracker.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import ir.pmoslem.covid_19tracker.R
import ir.pmoslem.covid_19tracker.databinding.FragmentHomeBinding
import ir.pmoslem.covid_19tracker.model.*
import ir.pmoslem.covid_19tracker.view.showSnackBar
import ir.pmoslem.covid_19tracker.viewmodel.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var countriesName: List<String>
    private lateinit var dataBinding: FragmentHomeBinding
    private lateinit var userCurrentCountry: String
    private lateinit var arrayAdapter: ArrayAdapter<String>

    @Inject
    lateinit var userData: UserData


    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Defining
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        val root = dataBinding.root


        dataBinding.viewmodel = homeViewModel

        dataBinding.btnNewsFragmentHome.setOnClickListener { v ->
            if (v != null) {
                Navigation.findNavController(v)
                    .navigate(R.id.action_navigation_home_to_navigation_news)
            }
        }

        userCurrentCountry = homeViewModel.userPreferredCountry.toString()
        val userCountryLiveData: MutableLiveData<String> = MutableLiveData<String>()
        userCountryLiveData.value = userCurrentCountry

        var checkedItem = 0

        // Get countries list for change country feature
        homeViewModel.getCountriesName().observe(requireActivity(), {
            countriesName = it
            arrayAdapter = ArrayAdapter(
                root.context,
                android.R.layout.simple_list_item_single_choice,
                countriesName
            )
            checkedItem = getItemPositionInArrayAdapter(arrayAdapter)
            setCountryDataInViews(userCurrentCountry)
        })


        // Change country listener logic
        dataBinding.btnChangeCountryHome.setOnClickListener {
            MaterialAlertDialogBuilder(root.context)
                .setTitle(resources.getString(R.string.country_change_dialog_title))
                .setPositiveButton(resources.getString(R.string.done)) { _, _ ->
                    showSnackBar("Country changed successfully!", R.color.greenColorSnackBar)
                }

                .setSingleChoiceItems(arrayAdapter, checkedItem) { _, which ->
                    userCurrentCountry = arrayAdapter.getItem(which).toString()
                    userData.savePreferredCountry(userCurrentCountry)
                    userCountryLiveData.value = userCurrentCountry
                    checkedItem = getItemPositionInArrayAdapter(arrayAdapter)
                }
                .show()
        }

        userCountryLiveData.observe(requireActivity(),
            { countryName ->
                setCountryDataInViews(countryName)
            })


        dataBinding.animationHome.setOnClickListener {
            val animation: LottieAnimationView = dataBinding.animationHome
            if (!animation.isAnimating) {
                animation.playAnimation()
            }
        }


        homeViewModel.getProgressBarStatus().observe(requireActivity(),
            { visibility ->
                if (visibility!!) {
                    dataBinding.pbLoadHome.visibility = View.VISIBLE
                    dataBinding.cvStatsHome.visibility = View.GONE
                    dataBinding.cvCheckoutHome.visibility = View.GONE
                } else {
                    dataBinding.pbLoadHome.visibility = View.GONE
                    dataBinding.cvStatsHome.visibility = View.VISIBLE
                    dataBinding.cvCheckoutHome.visibility = View.VISIBLE
                }
            })


        homeViewModel.getErrorStatus().observe(requireActivity(),
            { isErrorOccurred ->
                if (isErrorOccurred == true) {
                    showSnackBar("Couldn't connect to the server", R.color.redColorSnackBar)
                }
            })


        return root
    }


    private fun setCountryDataInViews(countryName: String) {
        GlobalScope.launch(Dispatchers.Main) {
            val country: Country? = homeViewModel.getCountryDataByName(countryName)
            country?.let {
                dataBinding.tvCountryNameHome.text = country.countryName
                dataBinding.tvCasesValueHome.text = country.totalCases.toString()
                dataBinding.tvRecoveredValueHome.text = country.totalRecovered.toString()
                dataBinding.tvDeathsValueHome.text = country.totalDeaths.toString()
            }
        }
    }

    private fun getItemPositionInArrayAdapter(arrayAdapter: ArrayAdapter<String>): Int {
        return arrayAdapter.getPosition(userCurrentCountry)
    }

}