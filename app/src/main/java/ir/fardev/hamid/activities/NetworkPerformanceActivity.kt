package ir.fardev.hamid.activities

import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import ir.fardev.hamid.data.response.ConfigurationRemoteResp
import ir.fardev.hamid.databinding.ActivityNetworkPerformanceBinding
import ir.fardev.hamid.repository.ConfigRep
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NetworkPerformanceActivity : AppCompatActivity() {
    private val TAG = NetworkPerformanceActivity::class.java.simpleName
    lateinit var binding: ActivityNetworkPerformanceBinding

    private val MAX_REQUEST = 2000

    private var currentNormalRequestFailed = 0
    private var currentNormalRequestSuccess = 0
    private var startTimeNormal: Long = 0

    private var currentRxRequestFailed = 0
    private var currentRxRequestSuccess = 0
    private var startTimeRx: Long = 0

    private var currentCoroutineRequestFailed = 0
    private var currentCoroutineRequestSuccess = 0
    private var startTimeCoroutine: Long = 0


    private var compositeDisposable = CompositeDisposable()
    val configRep by lazy { ConfigRep() }

    lateinit var job: Job


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNetworkPerformanceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()

    }

    private fun initView() {
        Log.i(TAG, "initView: ")
        binding.startAttackBtnNetworkActivity.setOnClickListener {
            Log.i(TAG, "initView: startAttackBtnNetworkActivity")
            normalWay()
        }


        binding.endAttackBtnNetworkActivity.setOnClickListener {
            Log.i(TAG, "initView: endAttackBtnNetworkActivity")
            configRep.cancelAllRequests()
        }

        binding.startAttackBtnNetworkActivityRx.setOnClickListener {
            Log.i(TAG, "initView: startAttackBtnNetworkActivityRx")
            rxWay()
        }

        binding.endAttackBtnNetworkActivityRx.setOnClickListener {
            Log.i(TAG, "initView: endAttackBtnNetworkActivity")
            compositeDisposable.dispose()
            compositeDisposable.clear()
        }

        binding.startAttackBtnNetworkActivityCoroutine.setOnClickListener {
            Log.i(TAG, "initView: startAttackBtnNetworkActivityCoroutine")
            coroutineWay()
        }

        binding.endAttackBtnNetworkActivityCoroutine.setOnClickListener {
            Log.i(TAG, "initView: endAttackBtnNetworkActivityCoroutine")
            job.cancel()

        }
    }

    private fun rxWay() {
        Log.i(TAG, "rxWay: ")
        binding.timeAttackNetworkActivityRx.text = ""
        startTimeRx = System.currentTimeMillis()
        currentRxRequestFailed = 0
        currentRxRequestSuccess = 0

        for (i in 1..MAX_REQUEST) {
            Log.i(TAG, "initView: rxWay in for => $i")
            compositeDisposable.add(configRep.getConfigRxKotlin().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({ result ->
                    Log.i(TAG, "initView => rxWay => success $i")
                    currentRxRequestSuccess++
                    updateRxProgressBarAndRequests()
                }, { error ->
                    Log.e(TAG, "initView => rxWay => error $error")
                    currentRxRequestFailed++
                    updateRxProgressBarAndRequests()
                }))
        }

    }


    private fun coroutineWay() {
        Log.i(TAG, "coroutineWay: ")
        binding.timeAttackNetworkActivityCoroutine.text = ""
        startTimeCoroutine = System.currentTimeMillis()
        currentCoroutineRequestFailed = 0
        currentCoroutineRequestSuccess = 0

        job = CoroutineScope(Dispatchers.IO).launch {
            val deferredResults = List(MAX_REQUEST) {
                async {
                    try {
                        val result = configRep.getConfigCoroutine()
                        if (result != null) {
                            currentCoroutineRequestFailed++
                            Log.i(TAG, "coroutineWay: success")
                        } else {
                            currentCoroutineRequestSuccess++
                            Log.e(TAG, "coroutineWay: failure")
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "coroutineWay: error", e)
                        currentCoroutineRequestFailed++
                    } finally {
                        withContext(Dispatchers.Main) {
                            updateCoroutineProgressBarAndRequests()
                        }
                    }
                }
            }
            deferredResults.awaitAll()
        }
    }
    fun normalWay() {
        binding.timeAttackNetworkActivity.text = ""
        startTimeNormal = System.currentTimeMillis()
        currentNormalRequestFailed = 0
        currentNormalRequestSuccess = 0

        val configRespSuccess: (ConfigurationRemoteResp, Int) -> Unit = { resp, counter ->
            Log.i(TAG, "initView: startAttackBtnNetworkActivity normal => success $counter")
            currentNormalRequestSuccess++
            updateNormalProgressBarAndRequests()
        }
        val configRespFailure: (String, Int) -> Unit = { err, counter ->
            Log.e(TAG, "initView: startAttackBtnNetworkActivity normal => error : $counter")
            currentNormalRequestFailed++
            updateNormalProgressBarAndRequests()
        }

        for (i in 1..MAX_REQUEST) {
            Log.i(TAG, "initView: in for => $i")
            val call = configRep.getConfig(success = configRespSuccess,
                error = configRespFailure,
                counter = i)
        }
    }

    fun updateNormalProgressBarAndRequests() {
        binding.attackProgressLPI.progress =
            (((currentNormalRequestSuccess.toFloat() + currentNormalRequestFailed.toFloat()) / MAX_REQUEST.toFloat()) * 100).toInt()
        binding.failedAttackNetworkActivity.text = currentNormalRequestFailed.toString()
        binding.successfulAttackNetworkActivity.text = currentNormalRequestSuccess.toString()
        binding.timeAttackNetworkActivity.text =
            ((System.currentTimeMillis() - startTimeNormal) / 1000).toString()
    }

    fun updateRxProgressBarAndRequests() {
        binding.attackProgressLPIRx.progress =
            (((currentRxRequestSuccess.toFloat() + currentRxRequestFailed.toFloat()) / MAX_REQUEST.toFloat()) * 100).toInt()
        binding.failedAttackNetworkActivityRx.text = currentRxRequestFailed.toString()
        binding.successfulAttackNetworkActivityRx.text = currentRxRequestSuccess.toString()
        binding.timeAttackNetworkActivityRx.text =
            ((System.currentTimeMillis() - startTimeRx) / 1000).toString()
    }
    fun updateCoroutineProgressBarAndRequests() {
            binding.attackProgressLPICoroutine.progress =
                (((currentCoroutineRequestSuccess.toFloat() + currentCoroutineRequestFailed.toFloat()) / MAX_REQUEST.toFloat()) * 100).toInt()
            binding.failedAttackNetworkActivityCoroutine.text = currentCoroutineRequestSuccess.toString()
            binding.successfulAttackNetworkActivityCoroutine.text = currentCoroutineRequestFailed.toString()
            binding.timeAttackNetworkActivityCoroutine.text = ((System.currentTimeMillis() - startTimeCoroutine) / 1000).toString()

    }
}