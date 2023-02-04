package ru.androidschool.intensiv

import android.app.Application
import android.content.Context
import ru.androidschool.intensiv.data.database.MovieDatabase
import timber.log.Timber

class MovieFinderApp : Application() {
    private lateinit var db: MovieDatabase
    override fun onCreate() {
        super.onCreate()
        instance = this
        initDebugTools()
createDatabase(this)

    }
    fun createDatabase(context: Context) {
        db = MovieDatabase.get(context)
    }
    private fun initDebugTools() {
        if (BuildConfig.DEBUG) {
            initTimber()
        }
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }


    companion object {
        var instance: MovieFinderApp? = null
            private set
    }
}
