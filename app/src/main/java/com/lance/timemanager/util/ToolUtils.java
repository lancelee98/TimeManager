package com.lance.timemanager.util;

import android.text.format.DateUtils;

public class ToolUtils {
    public  static String parseDate(long datetime)
    {
        String date=DateUtils.formatElapsedTime(datetime);
        String [] str=date.split(":");
        if(str.length==3)
        {
            return str[0]+"小时"+str[1]+"分钟";
        }
        else if(str.length==2)
            return str[0]+"分钟";
        else return null;
    }
}
