package com.ha.app.helpers;

import com.ha.app.constants.DateConstants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;

public class DateConverter {
    public static String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DateConstants.DD_MM_YYYY);
        return dateFormat.format(date);
    }
}
