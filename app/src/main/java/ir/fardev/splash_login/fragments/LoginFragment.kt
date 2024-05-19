package ir.fardev.splash_login.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.fardev.splash_login.databinding.FragmentLoginBinding

private val TAG = LoginFragment::class.simpleName
class LoginFragment : Fragment()
{
    lateinit var binding: FragmentLoginBinding
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }
    private fun initView()
    {
        Log.i(TAG, "initView: ")
        binding.reqBtnOTPFragment.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?)
            {
                Log.i(TAG, "initView -> onClick: ")
                val phoneNumber = binding.phoneEtOTPFragment.text.toString()
                requestAndNavigate(phoneNumber)
            }

        })

    }
    private fun requestAndNavigate(phoneNumber : String)
    {
        requestOTP(phoneNumber){ otpResp ->
            Log.d(TAG, "requestAndNavigate: isSuccess => $otpResp")
        }
        navigateToOTPFragment()
    }

    private fun navigateToOTPFragment()
    {
        Log.i(TAG, "navigateToOTPFragment: ")
    }

    private fun requestOTP(phoneNumber : String, onOTPResponseCallBack : (isSuccess : Boolean)-> Unit )
    {
        Log.i(TAG, "requestOTP: ")
        onOTPResponseCallBack.invoke(false)
    }
}