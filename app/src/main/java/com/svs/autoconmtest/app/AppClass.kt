package com.svs.autoconmtest.app

import android.app.Application
import com.google.gson.Gson

class AppClass : Application() {

    companion object {
        val gson = Gson()
    }

}