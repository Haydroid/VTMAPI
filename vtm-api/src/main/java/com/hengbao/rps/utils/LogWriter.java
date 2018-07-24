package com.hengbao.rps.utils;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author : YangWei
 * @Date : 2018/6/29
 * @Description : 日志控制
 */

public class LogWriter {
    private static LogWriter mLogWriter;
    private static SimpleDateFormat datef = new SimpleDateFormat("yy-MM-dd");
    private static Writer mWriter;
    private static SimpleDateFormat df;
    private static boolean isPrint = true;
    public final String TAG = "hb_plugin";
    private static boolean print2LogCat = true;
    private static boolean print2File = true;

    public LogWriter() {
    }

    public static LogWriter getInstance() {
        if (mLogWriter == null) {
            mLogWriter = new LogWriter();
        }

        return mLogWriter;
    }

    public static LogWriter open() throws IOException {
        getInstance();
        String mPath = Environment.getExternalStorageDirectory() + File.separator + "hb_" + datef.format(new Date()) + ".txt";
//        new File(mPath);
        File mFile = new File(mPath);
        mWriter = new BufferedWriter(new FileWriter(mPath, true), 2048);
        df = new SimpleDateFormat("[yy-MM-dd HH:mm:ss]:");
        return mLogWriter;
    }

    public void close() throws IOException {
        mWriter.close();
    }

    public void print(String log) {
        if (isPrint) {
            if (!TextUtils.isEmpty(log)) {
                if (print2File) {
                    String var2 = "11111111111111111111111111111111";

                    try {
                        Date date = new Date();
                        String log1 = "\n" + df.format(date) + "\n" + log.toString();
                        mWriter.append(log1);
                        mWriter.flush();
                    } catch (IOException var5) {
                        var5.printStackTrace();
                    }
                }

                if (print2LogCat) {
                    Log.i("hb_plugin", log.toString());
                }

            }
        }
    }

    public void printE(String tag, String log) {
        if (isPrint) {
            if (!TextUtils.isEmpty(log)) {
                if (print2File) {
                    try {
                        mWriter.append("\n" + df.format(new Date()));
                        mWriter.append("\n");
                        mWriter.flush();
                    } catch (IOException var5) {
                        var5.printStackTrace();
                    }

                    try {
                        mWriter.append(log.toString());
                    } catch (IOException var4) {
                        var4.printStackTrace();
                    }
                }

                if (print2LogCat) {
                    Log.e(tag, log.toString());
                }

            }
        }
    }

    public void print(Class<?> cls, String methodName, String log) throws Exception {
        if (isPrint) {
            if (!TextUtils.isEmpty(log)) {
                if (print2File) {
                    mWriter.append(df.format(new Date()) + "\n");
                    mWriter.append(cls.getSimpleName() + "." + methodName + ":" + "\n" + log + "\n");
                    mWriter.flush();
                }

                if (print2LogCat) {
                    Log.i("hb_plugin", log);
                }

            }
        }
    }
}
