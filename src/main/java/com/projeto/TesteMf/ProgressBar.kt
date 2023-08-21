package com.projeto.TesteMf

import kotlin.math.ceil

class ProgressBar(val name : String, val maxValue : Float) {

    private var progress : Float = 0.0F
    private val stepValue : Float = maxValue / 100

    fun present(text : String? = null)
    {
        print("|")
        var s = ceil((25 * progress) / 100).toInt()
        for(x in 0 until s)
        {
            print('=')
        }
        for(x in 0 until (25 - s))
        {
            print(" ")
        }
        print("| ${text ?: name} \r")
    }

    fun update(step : Float, tag : String?)
    {
        progress = step / stepValue
        present(tag)
    }

    fun conclude()
    {
        print("|          Done           | $name \n")
    }

}