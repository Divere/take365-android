package org.take365.helpers

/**
 * Created by divere on 02/11/2016.
 */

object DateHelpers {

    private val monthNames = arrayOf("Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь")

    fun getMonthDate(num: Int): String {
        return monthNames[num]
    }
}
