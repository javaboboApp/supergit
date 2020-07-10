package com.theappexperts.supergit.utils

import com.theappexperts.supergit.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
}