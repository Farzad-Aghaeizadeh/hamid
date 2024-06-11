package ir.fardev.splash_login

import android.app.Application
import android.util.Log
import com.facebook.stetho.Stetho

private val TAG = Application::class.java.simpleName
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "onCreate: ")
        Stetho.defaultInspectorModulesProvider(this)
    }
}