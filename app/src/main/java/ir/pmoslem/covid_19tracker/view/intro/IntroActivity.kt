package ir.pmoslem.covid_19tracker.view.intro

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import ir.pmoslem.covid_19tracker.R
import ir.pmoslem.covid_19tracker.databinding.IntroActivityBinding
import ir.pmoslem.covid_19tracker.model.UserData
import ir.pmoslem.covid_19tracker.view.main.MainActivity
import javax.inject.Inject

@AndroidEntryPoint
class IntroActivity : AppCompatActivity() {

    lateinit var binding: IntroActivityBinding

    @Inject
    lateinit var userData: UserData

    var isDoneButtonActive = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = IntroActivityBinding.inflate(layoutInflater)
        val root = binding.root
        setContentView(root)

        if (userData.getUserName()?.length!! > 3) {
            startMainActivity()
        }

        binding.tietName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s == null) {
                    return
                }

                isDoneButtonActive = if (s.length >= 3) {
                    binding.doneButton.setBackgroundColor(Color.WHITE)
                    true
                } else {
                    binding.doneButton.setBackgroundColor(
                        ContextCompat.getColor(this@IntroActivity, R.color.gray_150)
                    )
                    false
                }

            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.doneButton.setOnClickListener {
            if (isDoneButtonActive) {
                userData.saveUserName(binding.tietName.text.toString().trim())
                startMainActivity()
            }
        }

    }

    private fun startMainActivity() {
        val intent = Intent(this@IntroActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}