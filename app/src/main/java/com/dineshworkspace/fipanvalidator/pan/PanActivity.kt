package com.dineshworkspace.fipanvalidator.pan

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.viewModelScope
import com.dineshworkspace.fipanvalidator.R
import com.dineshworkspace.panvalidator.helpers.InputFilterMinMax
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_pan.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PanActivity : AppCompatActivity() {

    private val panViewModel: PanViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pan)

        et_birth_date.filters = arrayOf(InputFilterMinMax(1, 31))
        et_birth_month.filters = arrayOf(InputFilterMinMax(1, 12))

        et_birth_month.addTextChangedListener {
            validateInputs()
        }

        et_birth_date.addTextChangedListener {
            validateInputs()
        }

        et_pan.addTextChangedListener {
            if (it?.toString()!!.isNotEmpty()) {
                panViewModel.validatePanCard(it.toString())
            }
        }

        et_birth_year.addTextChangedListener {
            if (it?.toString()!!.isNotEmpty()) {
                panViewModel.validateBirthYear(it.toString())
            }
        }

        panViewModel.isValidPan.observe(this) {
            updatePanUi(it)
        }

        panViewModel.isValidYear.observe(this) {
            updateYearUi(it)
        }

        btn_next.setOnClickListener {
            Toast.makeText(this@PanActivity, getString(R.string.submit_msg), Toast.LENGTH_LONG)
                .show()
            //Delay is added so Toast will be displayed clearly
            panViewModel.viewModelScope.launch {
                delay(1000)
                finish()
            }
        }

        tv_dont_have_pan.setOnClickListener {
            finish()
        }
    }

    private fun updatePanUi(it: Boolean?) {
        when (it) {
            true -> updateEditTextUi(et_pan, R.color.blue)
            false -> updateEditTextUi(et_pan, R.color.grey)
            else -> {}
        }
        validateInputs()
    }

    private fun updateYearUi(it: Boolean?) {
        when (it) {
            true -> updateEditTextUi(et_birth_year, R.color.purple_700)
            false -> updateEditTextUi(et_birth_year, R.color.grey)
            else -> {}
        }
        validateInputs()
    }


    private fun updateEditTextUi(editText: EditText, color: Int) {
        val grad = editText.background as GradientDrawable
        grad.setStroke(4, resources.getColor(color, this.theme))
    }

    private fun validateInputs() {
        btn_next.isEnabled = panViewModel.isValidPan.value!! && panViewModel.isValidYear.value!! &&
                (et_birth_date.length() == 1 || et_birth_date.length() == 2) &&
                (et_birth_month.length() == 1 || et_birth_month.length() == 2)
    }
}