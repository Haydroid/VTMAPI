package com.bonc.vtm.hardware.port;

/**
 * @Author : YangWei
 * @Date : 2018/6/30
 * @Description : 串口实例
 */

public class SerialPortItem {
    private String _name;
    private String _fullPath;

    protected SerialPortItem(String name, String fullPath) {
        _name = name;
        _fullPath = fullPath;
    }

    public String getName() {
        return _name;
    }

    public String getFullPath() {
        return _fullPath;
    }

    @Override
    public String toString() {
        return _name;
    }
}
