package ir.fardev.hamid.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import ir.fardev.hamid.R
import ir.fardev.hamid.databinding.FragmentOtpBinding

/**
 * A simple [Fragment] subclass.
 * Use the [OTPFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OTPFragment : Fragment()
{
    private  val TAG = OTPFragment::class.java.simpleName
    lateinit var binding: FragmentOtpBinding
    val args: OTPFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate: ")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        Log.i(TAG, "onCreateView: ")
        binding = FragmentOtpBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.i(TAG, "onViewCreated: ")
        super.onViewCreated(view, savedInstanceState)
        binding.phoneTvOTPFragment.text = args.number
    }


}