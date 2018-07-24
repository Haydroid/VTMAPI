package com.bonc.driver.instruction;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.bonc.R;
import com.cwtech.siu.SiuDri;

/**
 * @Author : YangWei
 * @Date : 2018/6/30
 * @Description : IndicatorLampActivity
 */

public class IndicatorLampActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText txtPortFile;//串口号
    private Spinner spBaudRate;//波特率
    private TextView tv_receive16;//接收数据16HexData
    private TextView tvReciveData;//接收数据
    private TextView tv_ExecResult;//执行结果
    private Button btnOpenPort;//打开串口
    private Button btnClosePort;//关闭串口
    private Button btnGetVersion;//获取版本号
    private Button btnBarCodeLight;//二维码指示灯
    private Button btnBarCodeLight0;//二/条关灯
    private Button btnReadCardLight;//读卡器指示灯
    private Button btnReadCardLight0;//读卡器关灯
    //    private Button btnIsssCardLight;//卡机
//    private Button btnIsssCardLight0;//卡机关灯
    private Button btnSimIssCardLight;//发卡指示灯
    private Button btnSimIssCardLight0;//发卡关灯
    private Button btnReceiptPrinterLight;//打印指示灯
    private Button btnReceiptPrinterLight0;//打印关灯
    private Button btnIDCardLight;//身份证指示灯
    private Button btnIDCardLight0;//身份证关灯
    private Button btnFingerLight;//指纹指示灯
    private Button btnFingerLight0;//指纹关灯
    private Button btnRFReadCardLight;//非接指示灯
    private Button btnRFReadCardLight0;//非接关灯
    private Button btnCheckClose;//接近检测
    private TextView tv_editLightData;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};
    public static SiuDri m_SiuDri = null;
    private String sPort = "/dev/ttyS0";
    private int iBaudRate = 115200;
    private int iLightMode = 2;

    public static void verifyStoragePermissions(Activity activity) {
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zw_indicator_lamp);
        verifyStoragePermissions(this);
        m_SiuDri = new SiuDri();
        txtPortFile = (EditText) findViewById(R.id.portFile);//串口号
        spBaudRate = (Spinner) findViewById(R.id.spBaudRate);//波特率
        tv_receive16 = (TextView) findViewById(R.id.tv_receive16);//接收数据16HexData
        tvReciveData = (TextView) findViewById(R.id.tv_receive);//接收数据
        tv_ExecResult = (TextView) findViewById(R.id.tv_ExecResult);//执行结果
        btnOpenPort = (Button) findViewById(R.id.btnOpenPort);//打开串口
        btnOpenPort.setOnClickListener(this);
        btnClosePort = (Button) findViewById(R.id.btnClosePort);//关闭串口
        btnClosePort.setOnClickListener(this);
        btnGetVersion = (Button) findViewById(R.id.btnGetVersion);//获取版本号
        btnGetVersion.setOnClickListener(this);
        btnBarCodeLight = (Button) findViewById(R.id.btnBarCodeLight);//二维码指示灯
        btnBarCodeLight.setOnClickListener(this);
        btnBarCodeLight0 = (Button) findViewById(R.id.btnBarCodeLight0);//二/条关灯
        btnBarCodeLight0.setOnClickListener(this);
        btnReadCardLight = (Button) findViewById(R.id.btnReadCardLight);//读卡器指示灯
        btnReadCardLight.setOnClickListener(this);
        btnReadCardLight0 = (Button) findViewById(R.id.btnReadCardLight0);//读卡器关灯
        btnReadCardLight0.setOnClickListener(this);
/*
        btnIsssCardLight = (Button) findViewById(R.id.btnIsssCardLight);//卡机
        btnIsssCardLight.setOnClickListener(this);
        btnIsssCardLight0 = (Button) findViewById(R.id.btnIsssCardLight0);//卡机关灯
        btnIsssCardLight0.setOnClickListener(this);
*/

        btnSimIssCardLight = (Button) findViewById(R.id.btnSimIssCardLight);//发卡指示灯
        btnSimIssCardLight.setOnClickListener(this);
        btnSimIssCardLight0 = (Button) findViewById(R.id.btnSimIssCardLight0);//发卡关灯
        btnSimIssCardLight0.setOnClickListener(this);
        btnReceiptPrinterLight = (Button) findViewById(R.id.btnReceiptPrinterLight);//打印指示灯
        btnReceiptPrinterLight.setOnClickListener(this);
        btnReceiptPrinterLight0 = (Button) findViewById(R.id.btnReceiptPrinterLight0);//打印关灯
        btnReceiptPrinterLight0.setOnClickListener(this);
        btnIDCardLight = (Button) findViewById(R.id.btnIDCardLight);//身份证指示灯
        btnIDCardLight.setOnClickListener(this);
        btnIDCardLight0 = (Button) findViewById(R.id.btnIDCardLight0);//身份证关灯
        btnIDCardLight0.setOnClickListener(this);
        btnFingerLight = (Button) findViewById(R.id.btnFingerLight);//指纹指示灯
        btnFingerLight.setOnClickListener(this);
        btnFingerLight0 = (Button) findViewById(R.id.btnFingerLight0);//指纹指示灯
        btnFingerLight0.setOnClickListener(this);
        btnRFReadCardLight = (Button) findViewById(R.id.btnRFReadCardLight);//非接指示灯
        btnRFReadCardLight.setOnClickListener(this);
        btnRFReadCardLight0 = (Button) findViewById(R.id.btnRFReadCardLight0);//非接指示灯
        btnRFReadCardLight0.setOnClickListener(this);
        btnCheckClose = (Button) findViewById(R.id.btnCheckClose);//接近检测
        btnCheckClose.setOnClickListener(this);
        tv_editLightData = (TextView) findViewById(R.id.editLightData);
    }

    @Override
    public void onClick(View v) {
//打开串口
        if (v.getId() == R.id.btnOpenPort) {
            sPort = txtPortFile.getText().toString();
            iBaudRate = Integer.parseInt(spBaudRate.getSelectedItem().toString());
            int iRet = m_SiuDri.SIU_OpenDevice(sPort, (long) iBaudRate);
            if (iRet == 0) {
                tvReciveData.setText("OPEN OK");
                tv_receive16.setText("");
                tv_ExecResult.setText("");
            } else {
                tvReciveData.setText("OPEN FAIL");
                tv_receive16.setText("");
                tv_ExecResult.setText("");
            }
        }
//关闭串口
        else if (v.getId() == R.id.btnClosePort) {
            int iRet = m_SiuDri.SIU_CloseDevice();
            if (iRet == 0) {
                tvReciveData.setText("CLOSE OK");
                tv_receive16.setText("");
                tv_ExecResult.setText("");
            } else {
                tvReciveData.setText("CLOSE FAIL");
                tv_receive16.setText("");
                tv_ExecResult.setText("");
            }
        }
//获取版本号
        else if (v.getId() == R.id.btnGetVersion) {
            byte[] bVersion = new byte[64];
            int[] iOutDataLen = new int[1];
            int iRet = m_SiuDri.SIU_GetVersion(bVersion, iOutDataLen);
            //Display_Result(iRet,iOutDataLen[0],bExeInfo);
            if (iRet == 0) {
                String sDataLen = Integer.toString(iOutDataLen[0]);
                String t = new String(bVersion);
                sDataLen += " ;" + t.trim();
                tvReciveData.setText(sDataLen);
                String sRet = Integer.toString(iRet);
                tv_ExecResult.setText("返回值:" + sRet);
            } else {
                String sRet = Integer.toString(iRet);
                byte[] bErrorInfo = new byte[64];
                //m_SiuDri.SIU_GetLastErrInfo(iRet,bErrorInfo);
                String sError = new String(bErrorInfo);
                tvReciveData.setText("");
                tv_receive16.setText("");
                tv_ExecResult.setText("返回值:" + sRet + " 错误描述：" + sError);
            }
        }
//二维码/条码
        else if (v.getId() == R.id.btnBarCodeLight) {
            iLightMode = Integer.parseInt(tv_editLightData.getText().toString());
            int iRet = m_SiuDri.SIU_SetBarCodeLight(1, iLightMode);
            if (iRet == 0) {
                tvReciveData.setText("灯亮");
                tv_receive16.setText("");
                tv_ExecResult.setText("");
            } else {
                tvReciveData.setText("FAIL");
                tv_receive16.setText("");
                tv_ExecResult.setText("");
            }
        }
//二/条关灯
        else if (v.getId() == R.id.btnBarCodeLight0) {
            iLightMode = Integer.parseInt(tv_editLightData.getText().toString());
            int iRet = m_SiuDri.SIU_SetBarCodeLight(0, iLightMode);
            if (iRet == 0) {
                tvReciveData.setText("灯亮");
                tv_receive16.setText("");
                tv_ExecResult.setText("");
            } else {
                tvReciveData.setText("FAIL");
                tv_receive16.setText("");
                tv_ExecResult.setText("");
            }
        }
//读卡器
        else if (v.getId() == R.id.btnReadCardLight) {
            iLightMode = Integer.parseInt(tv_editLightData.getText().toString());
            int iRet = m_SiuDri.SIU_SetReadCardLight(1, iLightMode);
            if (iRet == 0) {
                tvReciveData.setText("灯亮");
                tv_receive16.setText("");
                tv_ExecResult.setText("");
            } else {
                tvReciveData.setText("FAIL");
                tv_receive16.setText("");
                tv_ExecResult.setText("");
            }
        }
//读卡器关灯
        else if (v.getId() == R.id.btnReadCardLight0) {
            iLightMode = Integer.parseInt(tv_editLightData.getText().toString());
            int iRet = m_SiuDri.SIU_SetReadCardLight(0, iLightMode);
            if (iRet == 0) {
                tvReciveData.setText("灯亮");
                tv_receive16.setText("");
                tv_ExecResult.setText("");
            } else {
                tvReciveData.setText("FAIL");
                tv_receive16.setText("");
                tv_ExecResult.setText("");
            }
        }
//打印指示灯
        else if (v.getId() == R.id.btnReceiptPrinterLight) {
            iLightMode = Integer.parseInt(tv_editLightData.getText().toString());
            int iRet = m_SiuDri.SIU_SetReceiptPrinterLight(1, iLightMode);
            if (iRet == 0) {
                tvReciveData.setText("灯亮");
                tv_receive16.setText("");
                tv_ExecResult.setText("");
            } else {
                tvReciveData.setText("FAIL");
                tv_receive16.setText("");
                tv_ExecResult.setText("");
            }
        }
//打印关灯
        else if (v.getId() == R.id.btnReceiptPrinterLight0) {
            iLightMode = Integer.parseInt(tv_editLightData.getText().toString());
            int iRet = m_SiuDri.SIU_SetReceiptPrinterLight(0, iLightMode);
            if (iRet == 0) {
                tvReciveData.setText("灯亮");
                tv_receive16.setText("");
                tv_ExecResult.setText("");
            } else {
                tvReciveData.setText("FAIL");
                tv_receive16.setText("");
                tv_ExecResult.setText("");
            }
        }
//身份证
        else if (v.getId() == R.id.btnIDCardLight) {
            iLightMode = Integer.parseInt(tv_editLightData.getText().toString());
            int iRet = m_SiuDri.SIU_SetIDCardLight(1, iLightMode);
            if (iRet == 0) {
                tvReciveData.setText("灯亮");
                tv_receive16.setText("");
                tv_ExecResult.setText("");
            } else {
                tvReciveData.setText("FAIL");
                tv_receive16.setText("");
                tv_ExecResult.setText("");
            }
        }
//身份证关灯
        else if (v.getId() == R.id.btnIDCardLight0) {
            iLightMode = Integer.parseInt(tv_editLightData.getText().toString());
            int iRet = m_SiuDri.SIU_SetIDCardLight(0, iLightMode);
            if (iRet == 0) {
                tvReciveData.setText("灯亮");
                tv_receive16.setText("");
                tv_ExecResult.setText("");
            } else {
                tvReciveData.setText("FAIL");
                tv_receive16.setText("");
                tv_ExecResult.setText("");
            }
        }
//指纹
        else if (v.getId() == R.id.btnFingerLight) {
            iLightMode = Integer.parseInt(tv_editLightData.getText().toString());
            int iRet = m_SiuDri.SIU_SetFingerLight(1, iLightMode);
            if (iRet == 0) {
                tvReciveData.setText("灯亮");
                tv_receive16.setText("");
                tv_ExecResult.setText("");
            } else {
                tvReciveData.setText("FAIL");
                tv_receive16.setText("");
                tv_ExecResult.setText("");
            }
        }
//指纹关灯
        else if (v.getId() == R.id.btnFingerLight0) {
            iLightMode = Integer.parseInt(tv_editLightData.getText().toString());
            int iRet = m_SiuDri.SIU_SetFingerLight(0, iLightMode);
            if (iRet == 0) {
                tvReciveData.setText("灯亮");
                tv_receive16.setText("");
                tv_ExecResult.setText("");
            } else {
                tvReciveData.setText("FAIL");
                tv_receive16.setText("");
                tv_ExecResult.setText("");
            }
        }
//非接指示灯
        else if (v.getId() == R.id.btnRFReadCardLight) {
            iLightMode = Integer.parseInt(tv_editLightData.getText().toString());
            int iRet = m_SiuDri.SIU_SetRFReadCardLight(1, iLightMode);
            if (iRet == 0) {
                tvReciveData.setText("灯亮");
                tv_receive16.setText("");
                tv_ExecResult.setText("");
            } else {
                tvReciveData.setText("FAIL");
                tv_receive16.setText("");
                tv_ExecResult.setText("");
            }
        }
//非接关灯
        else if (v.getId() == R.id.btnRFReadCardLight0) {
            iLightMode = Integer.parseInt(tv_editLightData.getText().toString());
            int iRet = m_SiuDri.SIU_SetRFReadCardLight(0, iLightMode);
            if (iRet == 0) {
                tvReciveData.setText("灯亮");
                tv_receive16.setText("");
                tv_ExecResult.setText("");
            } else {
                tvReciveData.setText("FAIL");
                tv_receive16.setText("");
                tv_ExecResult.setText("");
            }
        }
/*
//卡机
        else if (v.getId() == R.id.btnIsssCardLight) {
            iLightMode = Integer.parseInt(tv_editLightData.getText().toString());
            int iRet = m_SiuDri.SIU_SetIsssCardLight(1, iLightMode);
            if (iRet == 0) {
                tvReciveData.setText("灯亮");
                tv_receive16.setText("");
                tv_ExecResult.setText("");
            } else {
                tvReciveData.setText("FAIL");
                tv_receive16.setText("");
                tv_ExecResult.setText("");
            }
        }
//卡机关灯
        else if (v.getId() == R.id.btnIsssCardLight0) {
            iLightMode = Integer.parseInt(tv_editLightData.getText().toString());
            int iRet = m_SiuDri.SIU_SetIsssCardLight(0, iLightMode);
            if (iRet == 0) {
                tvReciveData.setText("灯亮");
                tv_receive16.setText("");
                tv_ExecResult.setText("");
            } else {
                tvReciveData.setText("FAIL");
                tv_receive16.setText("");
                tv_ExecResult.setText("");
            }
        }
*/

//发卡指示灯
        else if (v.getId() == R.id.btnSimIssCardLight) {
            iLightMode = Integer.parseInt(tv_editLightData.getText().toString());
            int iRet = m_SiuDri.SIU_SetSimIssCardLight(1, iLightMode);
            if (iRet == 0) {
                tvReciveData.setText("灯亮");
                tv_receive16.setText("");
                tv_ExecResult.setText("");
            } else {
                tvReciveData.setText("FAIL");
                tv_receive16.setText("");
                tv_ExecResult.setText("");
            }
        }
//发卡关灯
        else if (v.getId() == R.id.btnSimIssCardLight0) {
            iLightMode = Integer.parseInt(tv_editLightData.getText().toString());
            int iRet = m_SiuDri.SIU_SetSimIssCardLight(0, iLightMode);
            if (iRet == 0) {
                tvReciveData.setText("灯亮");
                tv_receive16.setText("");
                tv_ExecResult.setText("");
            } else {
                tvReciveData.setText("FAIL");
                tv_receive16.setText("");
                tv_ExecResult.setText("");
            }
        }
//接近检测
        else if (v.getId() == R.id.btnCheckClose) {
            int iRet = m_SiuDri.SIU_CheckClose();
            if (iRet == 0) {
                tvReciveData.setText("没人");
                tv_receive16.setText("");
                tv_ExecResult.setText("");
            } else if (iRet == 1) {
                tvReciveData.setText("有人");
                tv_receive16.setText("");
                tv_ExecResult.setText("");
            } else {
                tvReciveData.setText("其他错误");
            }
        }

    }
}
