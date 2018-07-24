package com.bonc.vtm.encryption;

/**
 * @Author : YangWei
 * @Date : 2018/6/30
 * @Description : DesArray
 */

public class DesArray {

    private int[] dest;

    public DesArray(int length) {
        dest = new int[length * 4];
    }

    public int[] getDest() {
        return dest;
    }

    public void setDest(int[] dest) {
        this.dest = dest;
    }

    public void setValue(int i, int value) {
        this.dest[i] = value;
    }
}
