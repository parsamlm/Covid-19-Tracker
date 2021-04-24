package ir.pmoslem.covid_19tracker.view

import android.content.SharedPreferences
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import ir.pmoslem.covid_19tracker.R
import ir.pmoslem.covid_19tracker.view.main.MainActivity

fun Fragment.showSnackBar(text: String, colorIdResource: Int) {
    if (this.isVisible){
        Snackbar.make(requireView(), text, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(ContextCompat.getColor(activity!!, colorIdResource))
            .show()
    }
}

fun SharedPreferences.getStringDataFromSharedPreferences(key: String, defValue: String): String? {
    return getString(key, defValue)
}





