package ir.fardev.splash_login.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ir.fardev.splash_login.convertFaNumToEn
import ir.fardev.splash_login.databinding.FragmentLoginBinding
import ir.fardev.splash_login.ui.theme.PhoneValidator

private val TAG = LoginFragment::class.simpleName

class LoginFragment : Fragment() {
    lateinit var binding: FragmentLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        Log.i(TAG, "initView: ")
        binding.reqBtnOTPFragment.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                Log.i(TAG, "initView -> onClick: ")
                val phoneNumber = binding.phoneEtOTPFragment.text.toString().convertFaNumToEn()
                val isValidNumber = PhoneValidator(phoneNumber).isValid()
                if (isValidNumber)
                {
                    requestAndNavigate(phoneNumber)
                }
                else
                {
                    Toast.makeText(requireContext(),"شماره اشتباه است",Toast.LENGTH_SHORT).show()
                }

            }

        })

    }

    private fun requestAndNavigate(phoneNumber: String) {
        requestOTP(phoneNumber) { otpResp ->
            Log.d(TAG, "requestAndNavigate: isSuccess => $otpResp")
            if (otpResp) {
                navigateToOTPFragment(phoneNumber)
            } else {
                // TODO: show snakbar
            }

        }
    }

    private fun navigateToOTPFragment(phoneNumber: String) {
        Log.i(TAG, "navigateToOTPFragment: ")
        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToOTPFragment(number = phoneNumber))
    }

    private fun requestOTP(phoneNumber: String, onOTPResponseCallBack: (isSuccess: Boolean) -> Unit) {

        Log.i(TAG, "requestOTP: ")
        // TODO: Request API CALL HERE

        // TODO : ADD THIS IF API RESULT IS SUCCESSFUL
        onOTPResponseCallBack.invoke(true)

        // TODO : ADD THIS IF API RESULT IS NOT SUCCESSFUL
        // onOTPResponseCallBack.invoke(false)

    }
}