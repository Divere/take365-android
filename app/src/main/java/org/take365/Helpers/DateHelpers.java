package org.take365.Helpers;

/**
 * Created by divere on 02/11/2016.
 */

public class DateHelpers {

    private static String[] monthNames = new String[]{
            "Январь",
            "Февраль",
            "Март",
            "Апрель",
            "Май",
            "Июнь",
            "Июль",
            "Август",
            "Сентябрь",
            "Октябрь",
            "Ноябрь",
            "Декабрь"
    };

    public static String getMonthDate(int num) {
        return monthNames[num];
    }

}
