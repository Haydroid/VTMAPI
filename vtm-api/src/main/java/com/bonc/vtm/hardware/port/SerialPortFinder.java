package com.bonc.vtm.hardware.port;

import android.util.Log;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Iterator;
import java.util.Vector;

/**
 * @Author : YangWei
 * @Date : 2018/6/30
 * @Description : 获取串口
 */

public class SerialPortFinder {

    private static final String TAG = "SerialPortFinder";
    private Vector<Driver> mDrivers = null;

    public class Driver {
        public Driver(String name, String root) {
            mDriverName = name;
            mDeviceRoot = root;
        }

        private String mDriverName;
        private String mDeviceRoot;
        Vector<File> mDevices = null;

        public Vector<File> getDevices() {
            if (mDevices == null) {
                mDevices = new Vector<>();
                File dev = new File("/dev");
                File[] files = dev.listFiles();
                int i;
                for (i = 0; i < files.length; i++) {
                    if (files[i].getAbsolutePath().startsWith(mDeviceRoot)) {

                        Log.i(TAG, "Device[" + i + "]:" + files[i]);

                        mDevices.add(files[i]);
                    }
                }
            }
            return mDevices;
        }

        public String getName() {
            return mDriverName;
        }
    }



    Vector<Driver> getDrivers() throws IOException {

        if (mDrivers == null) {
            mDrivers = new Vector<>();
            LineNumberReader r = new LineNumberReader(new FileReader("/proc/tty/drivers"));
            String l;
            while ((l = r.readLine()) != null) {
                String[] w = l.split(" +");
                if ((w.length == 5) && (w[4].equals("serial"))) {

                    Log.d(TAG, "Driver: " + w[1]);

                    mDrivers.add(new Driver(w[0], w[1]));
                }
            }
            r.close();
        }
        return mDrivers;
    }

    public Vector<SerialPortItem> getAllPorts() {
        Vector<SerialPortItem> ports = new Vector<>();
        Iterator<Driver> driverIterator;
        try {
            driverIterator = getDrivers().iterator();
            while (driverIterator.hasNext()) {
                Driver driver = driverIterator.next();
                Iterator<File> fileIterator = driver.getDevices().iterator();
                while (fileIterator.hasNext()) {
                    File file = fileIterator.next();
                    String name = String.format("%s (%s)", file.getName(), driver.getName());
                    String fullPath = file.getAbsolutePath();
                    ports.add(new SerialPortItem(name, fullPath));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ports;
    }
}
