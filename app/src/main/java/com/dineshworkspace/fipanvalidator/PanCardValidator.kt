package com.dineshworkspace.fipanvalidator

import com.dineshworkspace.panvalidator.helpers.AppConstants
import java.util.regex.Matcher
import java.util.regex.Pattern

class PanCardValidator {

    fun validatePanCard(panData: String): Boolean {
        val pattern: Pattern = Pattern.compile(AppConstants.PAN_REGEX)
        val matcher: Matcher = pattern.matcher(panData)
        if (matcher.matches()) {
            return true
        }
        return false
    }

}