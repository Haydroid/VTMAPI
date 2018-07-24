package com.bonc.vtm;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

import com.bonc.vtm.hardware.card.CardDriver;
import com.bonc.vtm.hardware.card.Instructions;
import com.bonc.vtm.hardware.port.SerialPortFinder;
import com.bonc.vtm.hardware.port.SerialPortItem;

import java.util.Vector;

/**
 * @Author : YangWei
 * @Date : 2018/7/5 下午10:06
 * @Description : VTM初始化
 */

public class VtmApi {

    public static Vector<SerialPortItem> serialPortItemVector;
    public static CardDriver cardDriver;
    public static Instructions instructions;

    public VtmApi() {
    }

    public static void init() {
        SerialPortFinder serialPortFinder = new SerialPortFinder();
        serialPortItemVector = serialPortFinder.getAllPorts();
        cardDriver = new CardDriver();
        instructions = Instructions.getInstance();
    }

    public static boolean isExistPort(String portName) {
        for (int i = 0; i < serialPortItemVector.size(); i++) {
            String name = serialPortItemVector.get(i).getName();
            if (name.equals(portName)) {
                return true;
            }
        }
        return false;
    }

    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return "";
        }
        return telephonyManager.getDeviceId();
    }

    public static String getPhoneNum(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return "";
        }
        return telephonyManager.getLine1Number();
    }

    public static String getVersionCode(Context context) {
        PackageManager pm = context.getPackageManager();
        PackageInfo info;
        try {
            info = pm.getPackageInfo(context.getPackageName(), 0);
            String versionCode = String.valueOf(info.versionCode);
            String versionName = String.valueOf(info.versionName);
            return versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

}
