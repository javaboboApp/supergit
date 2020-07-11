package com.theappexperts.supergit.utils

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

object Utitlites {
    fun runDelayForTesting(runDelay: Boolean, runWihDelayTesting: () -> Unit) {
        if (runDelay)
            GlobalScope.launch {
                delay(1000)
                runWihDelayTesting()
            }
        else
            runWihDelayTesting()
    }


    fun getDateAsHeaderId(milliseconds: Long): Long {
        val dateFormat = SimpleDateFormat("ddMMyyyy", Locale.getDefault())
        return dateFormat.format(Date(milliseconds)).toLong()
    }
}