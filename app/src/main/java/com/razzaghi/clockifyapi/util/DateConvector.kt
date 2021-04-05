package com.razzaghi.clockifyapi.util

import java.util.*

object DateConvector {

    fun GregorianCalendarToJalaliCalendar(year:Int,month:Int,day:Int,):JalaliCalendar{

        var jalaliCalendar =
            JalaliCalendar(GregorianCalendar(year, month, day))

        return jalaliCalendar
    }

}