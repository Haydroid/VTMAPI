package com.bonc.vtm.hardware.lamp;

import android.content.Context;

import com.bonc.vtm.Constants;
import com.bonc.vtm.VtmEquipment;
import com.cwtech.siu.SiuDri;

/**
 * @Author : YangWei
 * @Date : 2018/6/30
 * @Description : LampImpl
 */

public class LampImpl implements LampInterface {
    private int openPortResult;
    private SiuDri siuDri;

    public LampImpl(Context context) {
        this.siuDri = new SiuDri();
        this.openPortResult = siuDri.SIU_OpenDevice(VtmEquipment.Port.ttysWK0, VtmEquipment.BaudRate.B115200);
    }

    @Override
    public int barCodeLight(int iMode) {
        if (openPortResult == 0) {
            return siuDri.SIU_SetBarCodeLight(iMode, VtmEquipment.Lamp.KS);
        } else {
            return Constants.EXCEPTION_FLAG;
        }
    }

    @Override
    public int readCardLight(int iMode) {
        if (openPortResult == 0) {
            return siuDri.SIU_SetReadCardLight(iMode, VtmEquipment.Lamp.KS);
        } else {
            return Constants.EXCEPTION_FLAG;
        }
    }

    @Override
    public int receiptPrinterLight(int iMode) {
        if (openPortResult == 0) {
            return siuDri.SIU_SetReceiptPrinterLight(iMode, VtmEquipment.Lamp.KS);
        } else {
            return Constants.EXCEPTION_FLAG;
        }

    }

    @Override
    public int iDCardLight(int iMode) {
        if (openPortResult == 0) {
            return siuDri.SIU_SetIDCardLight(iMode, VtmEquipment.Lamp.KS);
        } else {
            return Constants.EXCEPTION_FLAG;
        }
    }

    @Override
    public int fingerLight(int iMode) {
        if (openPortResult == 0) {
            return siuDri.SIU_SetFingerLight(iMode, VtmEquipment.Lamp.KS);
        } else {
            return Constants.EXCEPTION_FLAG;
        }
    }

    @Override
    public int rFReadCardLight(int iMode) {
        if (openPortResult == 0) {
            return siuDri.SIU_SetRFReadCardLight(iMode, VtmEquipment.Lamp.KS);
        } else {
            return Constants.EXCEPTION_FLAG;
        }

    }

    @Override
    public int simIssCardLight(int iMode) {
        if (openPortResult == 0) {
            return siuDri.SIU_SetSimIssCardLight(iMode, VtmEquipment.Lamp.KS);
        } else {
            return Constants.EXCEPTION_FLAG;
        }
    }

    @Override
    public int checkClose() {
        if (openPortResult == 0) {
            return siuDri.SIU_CheckClose();
        } else {
            return Constants.EXCEPTION_FLAG;
        }
    }

    @Override
    public int closeDevice() {
        if (openPortResult == 0) {
            return siuDri.SIU_CloseDevice();
        } else {
            return Constants.EXCEPTION_FLAG;
        }
    }
}
