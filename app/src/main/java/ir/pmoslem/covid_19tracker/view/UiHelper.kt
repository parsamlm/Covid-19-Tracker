package ir.pmoslem.covid_19tracker.view

import android.content.SharedPreferences
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.showSnackBar(text: String) {
    Snackbar.make(this.requireView(), text, Snackbar.LENGTH_SHORT).show()
}

fun SharedPreferences.getStringDataFromSharedPreferences(key: String, defValue: String): String? {
    return getString(key, defValue)
}




