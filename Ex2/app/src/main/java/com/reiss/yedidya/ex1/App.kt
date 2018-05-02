package com.reiss.yedidya.ex1

import android.app.Application
import android.content.Context

/**
 * Created by yedidya on 10/04/2018.
 */

class App : Application() {

    companion object {
        lateinit var context : Context
    }


    override fun onCreate() {
        super.onCreate()
        App.context = applicationContext
    }
}