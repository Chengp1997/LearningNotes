package edu.vt.cs5254.dreamcatcher

import android.app.Application

class DreamCatcherApplication: Application() {
    //when app is first loaded, create, and repository will be initialized, repository
    // will live longer than activity
    override fun onCreate() {
        super.onCreate()
        DreamRepository.initialize(this)
    }
}