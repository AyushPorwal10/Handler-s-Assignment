package ayush.practice

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class AppApplicationClass : Application(){
    override fun onCreate() {
        super.onCreate()
    }
}