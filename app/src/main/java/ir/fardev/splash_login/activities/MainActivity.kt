package ir.fardev.splash_login.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import ir.fardev.splash_login.databinding.ActivityMainBinding

class MainActivity : ComponentActivity()
{
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        gotoSecondActivity()
    }

    fun gotoSecondActivity()
    {
        val intent = Intent(this,LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        window.decorView.postDelayed({},300)



        window.decorView.postDelayed({ startActivity(Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        }) }, 500)
    }

}