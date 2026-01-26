package com.msnegi.websearchretrofit.tools;


import android.os.Environment;
import android.util.Log;

import com.msnegi.websearchretrofit.BuildConfig;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LogManager {

    public static String FILE_PROJECT_DIRECTORY = Environment.getExternalStorageDirectory().getAbsolutePath().toString() + "/" + "MyAllSamples/";
    public static boolean isDebugToBeLogged = BuildConfig.DEBUG;

    private LogManager() {
        super();
    }

    public static void d(String tag, String message) {
        if (isDebugToBeLogged == true) {
            if (message == null) {
                return;
            }
            Log.d(tag, message);
        } else {
            // do nothing
        }
    }

    public static void e(String tag, String message) {
        if (isDebugToBeLogged == true) {
            Log.e(tag, message);
        } else {
            // do nothing
        }
    }

    public static void v(String tag, String message) {
        if (isDebugToBeLogged == true) {
            Log.v(tag, message);
        } else {
            // do nothing
        }
    }

    public static void i(String tag, String message) {
        if (isDebugToBeLogged == true) {
            Log.i(tag, message);
        } else {
            // do nothing
        }
    }

    public static void w( String tag, String message) {
        if (isDebugToBeLogged == true) {
            Log.w(tag, message);
        } else {
            // do nothing
        }
    }

    /*
     * used to print stacktrace
	 */
    public static void printStackTrace(Exception e) {
        e.printStackTrace();
    }


    public static void logInToFile(String message) {
        try {
            File file = new File(FILE_PROJECT_DIRECTORY + "token.txt");

            // If file does not exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(message);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Method to print the logs in a local file in sd card.
     * @param tag :  String tag for the log. It can be a module name.
     * @param message : Message to print
     */
    public static void logToFile(String tag, String message) {
        if (tag == null) {
            tag = "null";
        }
        if (message == null) {
            message = "null";
        }
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

        String date = new Date(System.currentTimeMillis()).toString();
        String calDate = df.format(Calendar.getInstance().getTime());

        String data = tag + ": " + message + "\n @" + calDate + "";

        //writeToFile(date + "_log.txt", data);
    }

    //
    // /data/wifi/ or to the /data/misc/wifi/ directory, depending on the
    // device.
    // In /data/wifi/, look for and open a file named bcm_supp.conf. In
    // /data/misc/wifi/ look for and open a file named wpa_suppliciant.conf.

    private static final String FOLDER = "/.myallsamples_logs";

    public static void writeToFile(String fileName, String data) {
        File backUpFolder = new File(Environment.getExternalStorageDirectory()
                + FOLDER);
        if (!backUpFolder.exists()) {
            backUpFolder.mkdir();
        }

        File backUpFile = new File(Environment.getExternalStorageDirectory()
                + FOLDER + "/" + fileName);
        if (!backUpFile.exists()) {
            try {
                backUpFile.createNewFile();
            } catch (IOException e) {

            }
        }
        try {
            FileOutputStream out = new FileOutputStream(backUpFile, true);

            OutputStreamWriter osw = new OutputStreamWriter(out);

            osw.append(data + "\n");
            osw.flush();
            osw.close();

            out.flush();
            out.close();

        } catch (FileNotFoundException e) {
            //UtilityMethods.printStackTrace(e);
        } catch (IOException e) {
            //UtilityMethods.printStackTrace(e);
        }
    }
}
