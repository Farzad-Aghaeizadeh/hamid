package ir.fardev.splash_login.ui.theme

import android.util.Log
import kotlin.math.log

class PhoneValidator (private var number: String) {
    private val TAG = PhoneValidator::class.java.simpleName
    fun isValid() : Boolean {
        Log.i(TAG, "isValid: number => $number")
        removePlusPrefix()
        val isValidLength = isValidLength()
        Log.i(TAG, "isValid: result => $isValidLength")
        return isValidLength
    }
    private fun removePlusPrefix()
    {
        Log.i(TAG, "removePlusPrefix: ")
        if (number.first() == '+')
        {
            Log.d(TAG, "removePlusPrefix: first char is plus => $number")
           number =  number.removeRange( 0..2)
            Log.i(TAG, "removePlusPrefix: result after removed prefix => $number ")
        }
    }
    private fun isValidLength() : Boolean {
        Log.i(TAG, "isValidLength: ")
        number.length in (10..11)
        return number.length == 11 || number.length == 10
    }
}