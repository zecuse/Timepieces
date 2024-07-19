package com.zecuse.timepieces.model

class TimePoint(time: Long = 0L)
{
	val millis = (time / 10) % 100
	val secs = (time / 1000) % 60
	val mins = (time / 1000 / 60) % 60
	val hrs = (time / 1000 / 60 / 60) % 24

	override fun toString(): String
	{
		val milDigits = (if (millis < 10) "0" else "") + millis
		val secDigits = (if (secs < 10) "0" else "") + secs
		val minDigits = (if (mins < 10) "0" else "") + mins
		val hrDigits = (if (hrs < 10) "0" else "") + hrs
		return "$hrDigits:$minDigits:$secDigits.$milDigits"
	}
}