package ir.fardev.hamid.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import ir.fardev.hamid.R
import ir.fardev.hamid.databinding.ActivityMainBinding
import ir.fardev.hamid.databinding.ActivitySplashBinding

private val TAG = MainActivity::class.java.simpleName
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }
    fun initView()
    {
        Log.i(TAG, "initView: ")
        binding.loginBtnMainActivity.setOnClickListener{
            Log.i(TAG, "initView: => login btn clicked")
            startActivity(Intent(this,LoginActivity::class.java))
        }
        binding.networkPerformanceBtnMainActivity.setOnClickListener{
            Log.i(TAG, "initView: => network performance btn clicked")
            startActivity(Intent(this,NetworkPerformanceActivity::class.java))
        }
    }
}