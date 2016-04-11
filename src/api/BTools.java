package api;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Кирилл on 14.12.2015.
 */
public class BTools {
    public BTools() {
    }

    public static void printLine(Object o) {
        System.out.print(o);
    }

    public static void printNewLine(Object o) {
        printLine(o);
        System.out.println("");
    }

    public static void printLnWithTime(Object o) {
        printLine(getCurrentTime() + " " + o);
        System.out.println("");
    }

    public static String getCurrentTime() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm:ss");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }
}
