package com.bonc.vtm;

/**
 * @Author : YangWei
 * @Date : 2018/6/30
 * @Description : VtmEquipment
 */

public interface VtmEquipment {

    /**
     * 设备厂商
     */
    interface Supplier {
        String ZW = "VTM-ZW";//兆维
        String ZR = "VTM-ZR";//卓睿
    }

    /**
     * 外部设备接口（U口和串口）
     */
    interface Port {
        String ttyS0 = "/dev/ttyS0";
        String ttyS1 = "/dev/ttyS1";
        String ttyS3 = "/dev/ttyS3";
        String ttyS4 = "/dev/ttyS4";

        String ttyGS0 = "/dev/ttyGS0";
        String ttyGS1 = "/dev/ttyGS1";
        String ttyGS2 = "/dev/ttyGS2";
        String ttyGS3 = "/dev/ttyGS3";

        String ttysWK0 = "/dev/ttysWK0";
        String ttysWK1 = "/dev/ttysWK1";
        String ttysWK2 = "/dev/ttysWK2";
        String ttysWK3 = "/dev/ttysWK3";

        String ttyFIQ0 = "/dev/ttyFIQ0";
    }

    /**
     * 波特率
     */
    interface BaudRate {
        int B9600 = 9600;
        int B19200 = 19200;
        int B38400 = 38400;
        int B115200 = 115200;
    }

    /**
     * 指示灯
     */
    interface Lamp {
        int CL = 0;//常亮
        int SS = 1;//闪烁
        int KS = 2;//快闪
        int MS = 3;//慢闪
    }

}
