package ir.fardev.hamid.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import ir.fardev.hamid.databinding.ActivitySplashBinding
import ir.fardev.hamid.repository.ConfigRep

private  val TAG = SplashActivity::class.java.simpleName
class SplashActivity : ComponentActivity()
{
    lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?)
    {
        Log.i(TAG, "onCreate: ")
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        gotoMainActivity()
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart: ")

        val configResp = ConfigRep()
        configResp.getConfig(
            success = {resp,_->
                Log.i(TAG, "onStart: configResp => success")
            },
            error = {err,_->
                Log.e(TAG, "onStart: configResp => error")
            }
        )

    }

    fun gotoMainActivity()
    {
        Log.i(TAG, "gotoMainActivity: ")
        val intent = Intent(this,LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        window.decorView.postDelayed({},300)
        window.decorView.postDelayed({ startActivity(Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        }) }, 500)
    }

}