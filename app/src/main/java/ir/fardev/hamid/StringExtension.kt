package ir.fardev.hamid

import android.util.Log

private  const val TAG = "StringExtension"
fun String.convertFaNumToEn() :String
{
    Log.i(TAG, "convertPersianNumToEng: ")
    return this.replace("۱","1").
    replace("۲","2").
    replace("۳","3").
    replace("۰","0").
    replace("۴","4").
    replace("۵","5").
    replace("۶","6").
    replace("۷","7").
    replace("۸","8").
    replace("۹","9")
}