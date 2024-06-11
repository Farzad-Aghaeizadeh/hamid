package ir.fardev.hamid.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ir.fardev.hamid.convertFaNumToEn
import ir.fardev.hamid.databinding.FragmentLoginBinding
import ir.fardev.hamid.repository.AuthRep
import ir.fardev.hamid.ui.theme.PhoneValidator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay

private val TAG = LoginFragment::class.simpleName

class LoginFragment : Fragment() {
    private val authRep by lazy {AuthRep()}
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
                val validNumber = PhoneValidator(phoneNumber).validateOrNull()
                if (!validNumber.isNullOrEmpty())
                {

                        CoroutineScope(Dispatchers.IO).launch {
                            for( i in 1..100) {
                                kotlinx.coroutines.delay(2000)
                                requestAndNavigate(validNumber)
                        }

                    }
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
//                navigateToOTPFragment(phoneNumber)
            } else {
                Toast.makeText(requireContext(),"خطا در برقراری ارتباط",Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun navigateToOTPFragment(phoneNumber: String) {
        Log.i(TAG, "navigateToOTPFragment: ")
        requireActivity().runOnUiThread {

            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToOTPFragment(number = phoneNumber))
        }
    }

    private fun requestOTP(phoneNumber: String, onOTPResponseCallBack: (isSuccess: Boolean) -> Unit) {

        Log.i(TAG, "requestOTP: ")
        authRep.getOtp(
            phoneNumber = phoneNumber,
            success = {onOTPResponseCallBack.invoke(true)},
            error = {onOTPResponseCallBack.invoke(false)})
    }
}