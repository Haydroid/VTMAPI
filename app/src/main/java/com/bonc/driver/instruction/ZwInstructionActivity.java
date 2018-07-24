package com.bonc.driver.instruction;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.bonc.R;
import com.bonc.vtm.VtmEquipment;
import com.cwtech.isssimcard.IssSimCardDri;
import com.hengbao.rps.utils.DataUtils;

/**
 * @Author : YangWei
 * @Date : 2018/6/30
 * @Description : ZwInstructionActivity
 */

public class ZwInstructionActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};

    private Button btnOpenPort;//打开串口
    private Button btnClosePort;//关闭串口
    private Button btnReset1;//重置-不移动SIM卡
    private Button btnReset2;//重置-回收
    private Button btnReset3;//重置-吐卡
    private Button btnDispense1;//发卡1，把卡发到写卡位置
    private Button btnDispense2;//发卡2，把卡发到写卡位置
    private Button btnDispense3;//发卡3，把卡发到写卡位置
    private Button btnEject;//吐卡，把卡从读写卡位置送到发卡器卡口
    private Button btnCapture1;//回收1，把卡从读写卡位置回收到回收盒
    private Button btnCapture2;//回收2，把卡从读写卡位置回收到回收盒
    private Button btnReinTake1;//卡口回收1，把卡从卡口位置回收到前回收箱1
    private Button btnReinTake2;//卡口回收2，把卡从卡口位置回收到前回收箱2
    private Button btnReinTake3;//卡口回收3，把卡从卡口位置回收到前回收箱3
    private Button btnPowerOn;//上电
    private Button btnExeAPDU;//EXEC_APDU，执行APDU指令
    private Button btnPowerOff;//下电
    private Button btnEnableICInput;//读卡口进卡，使能进卡
    private Button btnGetVersion;//获取版本号
    private Button btnDevStatus;//设备与卡片状态
    private Button btnCardBoxStatus;//卡箱状态
    private Button btnIccid;//读取ICCID
    private TextView tv_receive;//接收数据
    private TextView tv_receive16;//接收数据16HexData
    private TextView tv_ExecResult;//执行结果

    public static IssSimCardDri m_IssSimCard = new IssSimCardDri();

    public static void verifyStoragePermissions(Activity activity) {
        try {//检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity, "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {// 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final String bytesToHexString(byte[] bArray, int iDataLen) {
        //StringBuffer sb = new StringBuffer(bArray.length);
        StringBuffer sb = new StringBuffer(iDataLen);
        String sTemp;
        for (int i = 0; i < iDataLen; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zw_instruction);
        verifyStoragePermissions(this);
        initView();
    }

    protected void initView() {

        btnOpenPort = (Button) findViewById(R.id.btnOpenPort);//打开串口
        btnOpenPort.setOnClickListener(this);
        btnClosePort = (Button) findViewById(R.id.btnClosePort);//关闭串口
        btnClosePort.setOnClickListener(this);
        btnReset1 = (Button) findViewById(R.id.btnReset1);//重置-不移动SIM卡
        btnReset1.setOnClickListener(this);
        btnReset2 = (Button) findViewById(R.id.btnReset2);//重置-回收
        btnReset2.setOnClickListener(this);
        btnReset3 = (Button) findViewById(R.id.btnReset3);//重置-吐卡
        btnReset3.setOnClickListener(this);
        btnDispense1 = (Button) findViewById(R.id.btnDispense1);//发卡1，把卡发到写卡位置
        btnDispense1.setOnClickListener(this);
        btnDispense2 = (Button) findViewById(R.id.btnDispense2);//发卡2，把卡发到写卡位置
        btnDispense2.setOnClickListener(this);
        btnDispense3 = (Button) findViewById(R.id.btnDispense3);//发卡3，把卡发到写卡位置
        btnDispense3.setOnClickListener(this);
        btnEject = (Button) findViewById(R.id.btnEject);//吐卡，把卡从读写卡位置送到发卡器卡口
        btnEject.setOnClickListener(this);
        btnCapture1 = (Button) findViewById(R.id.btnCapture1);//回收1，把卡从读写卡位置回收到回收盒
        btnCapture1.setOnClickListener(this);
        btnCapture2 = (Button) findViewById(R.id.btnCapture2);//回收2，把卡从读写卡位置回收到回收盒
        btnCapture2.setOnClickListener(this);
        btnReinTake1 = (Button) findViewById(R.id.btnReinTake1);//卡口回收1，把卡从卡口位置回收到前回收箱1
        btnReinTake1.setOnClickListener(this);
        btnReinTake2 = (Button) findViewById(R.id.btnReinTake2);//卡口回收2，把卡从卡口位置回收到前回收箱2
        btnReinTake2.setOnClickListener(this);
        btnReinTake3 = (Button) findViewById(R.id.btnReinTake3);//卡口回收3，把卡从卡口位置回收到前回收箱3
        btnReinTake3.setOnClickListener(this);
        btnPowerOn = (Button) findViewById(R.id.btnPowerOn);//上电
        btnPowerOn.setOnClickListener(this);
        btnExeAPDU = (Button) findViewById(R.id.btnExeAPDU);//EXEC_APDU，执行APDU指令
        btnExeAPDU.setOnClickListener(this);
        btnPowerOff = (Button) findViewById(R.id.btnPowerOff);//下电
        btnPowerOff.setOnClickListener(this);
        btnEnableICInput = (Button) findViewById(R.id.btnEnableICInput);//读卡口进卡，使能进卡
        btnEnableICInput.setOnClickListener(this);
        btnGetVersion = (Button) findViewById(R.id.btnGetVersion);//获取版本号
        btnGetVersion.setOnClickListener(this);
        btnDevStatus = (Button) findViewById(R.id.btnDevStatus);//设备与卡片状态
        btnDevStatus.setOnClickListener(this);
        btnCardBoxStatus = (Button) findViewById(R.id.btnCardBoxStatus);//卡箱状态
        btnCardBoxStatus.setOnClickListener(this);
        btnIccid = (Button) findViewById(R.id.btnIccid);//读取ICCID
        btnIccid.setOnClickListener(this);

        tv_receive = (TextView) findViewById(R.id.tv_receive);//接收数据
        tv_receive16 = (TextView) findViewById(R.id.tv_receive16);//接收数据16HexData
        tv_ExecResult = (TextView) findViewById(R.id.tv_ExecResult);//执行结果

    }

    @Override
    public void onClick(View v) {

//打开串口
        if (v.getId() == R.id.btnOpenPort) {//打开串口
            int iRet = m_IssSimCard.Sim_OpenDevice("/dev/ttysWK0", VtmEquipment.BaudRate.B9600);//串口号，波特率
            if (iRet == 0) {//0，成功； !=0 失败
                tv_receive.setText("OPEN OK");
                tv_receive16.setText("");
                tv_ExecResult.setText("");
            } else {
                tv_receive.setText("OPEN FAIL");
                tv_receive16.setText("");
                tv_ExecResult.setText("");
            }
        }
//关闭串口
        else if (v.getId() == R.id.btnClosePort) {//关闭串口
            int iRet = m_IssSimCard.Sim_CloseDevice();//0，成功 !=0，失败
            if (iRet == 0) {
                tv_receive.setText("CLOSE OK");
                tv_receive16.setText("");
                tv_ExecResult.setText("");
            } else {
                tv_receive.setText("CLOSE FAIL");
                tv_receive16.setText("");
                tv_ExecResult.setText("");
            }
        }
//重置-不移动SIM卡
        else if (v.getId() == R.id.btnReset1) {
            // pStatus 0x30 0x30 读卡内部无卡   0x30 0x31 卡在卡口  0x30 0x32 读卡位置有卡
            byte[] bExeInfo = new byte[64];
            int iRet = m_IssSimCard.Sim_ResetDevice(1, bExeInfo);//1 不移动SIM卡，2回收，3吐卡；pExeInfor
            Display_Result(iRet, bExeInfo);
        }
//重置-回收
        else if (v.getId() == R.id.btnReset2) {
            // pStatus 0x30 0x30 读卡内部无卡   0x30 0x31 卡在卡口  0x30 0x32 读卡位置有卡
            byte[] bExeInfo = new byte[64];
            int iRet = m_IssSimCard.Sim_ResetDevice(2, bExeInfo);//1 不移动SIM卡，2回收，3吐卡；pExeInfor
            Display_Result(iRet, bExeInfo);
        }
//重置-吐卡
        else if (v.getId() == R.id.btnReset3) {
            // pStatus 0x30 0x30 读卡内部无卡   0x30 0x31 卡在卡口  0x30 0x32 读卡位置有卡
            byte[] bExeInfo = new byte[64];
            int iRet = m_IssSimCard.Sim_ResetDevice(3, bExeInfo);//1 不移动SIM卡，2回收，3吐卡；pExeInfor
            Display_Result(iRet, bExeInfo);
        }
//发卡箱1发卡，把卡发到写卡位置
        else if (v.getId() == R.id.btnDispense1) {
            byte[] bExeInfo = new byte[64];
            int iRet = m_IssSimCard.Sim_Dispense(1, bExeInfo);//卡箱编号(1);
            Display_Result(iRet, bExeInfo);
        }
//发卡2，把卡发到写卡位置
        else if (v.getId() == R.id.btnDispense2) {
            byte[] bExeInfo = new byte[64];
            int iRet = m_IssSimCard.Sim_Dispense(2, bExeInfo);//卡箱编号(2);
            Display_Result(iRet, bExeInfo);
        }
//发卡3，把卡发到写卡位置
        else if (v.getId() == R.id.btnDispense3) {
            byte[] bExeInfo = new byte[64];
            int iRet = m_IssSimCard.Sim_Dispense(3, bExeInfo);//卡箱编号(3);
            Display_Result(iRet, bExeInfo);
        }
//吐卡，把卡从读写卡位置送到发卡器卡口
        else if (v.getId() == R.id.btnEject) {
            byte[] bExeInfo = new byte[64];
            int iRet = m_IssSimCard.Sim_Eject(bExeInfo);
            Display_Result(iRet, bExeInfo);
        }
//回收1，把卡从读写卡位置回收到回收盒1（前面）
        else if (v.getId() == R.id.btnCapture1) {
            byte[] bExeInfo = new byte[64];
            int iRet = m_IssSimCard.Sim_Capture(1, bExeInfo);//iBoxIndex	回收到指定回收盒(1)
            Display_Result(iRet, bExeInfo);
        }
//回收2，把卡从读写卡位置回收到回收盒2（侧面）
        else if (v.getId() == R.id.btnCapture2) {
            byte[] bExeInfo = new byte[64];
            int iRet = m_IssSimCard.Sim_Capture(2, bExeInfo);//iBoxIndex	回收到指定回收盒(2)
            Display_Result(iRet, bExeInfo);
        }
//卡口回收1，把卡从卡口位置回收到前回收箱1
        else if (v.getId() == R.id.btnReinTake1) {
            byte[] bExeInfo = new byte[64];
            int iRet = m_IssSimCard.Sim_ReIntake(1, bExeInfo);
            Display_Result(iRet, bExeInfo);
        }
//卡口回收2，把卡从卡口位置回收到前回收箱2
        else if (v.getId() == R.id.btnReinTake2) {
            byte[] bExeInfo = new byte[64];
            int iRet = m_IssSimCard.Sim_ReIntake(2, bExeInfo);
            Display_Result(iRet, bExeInfo);
        }
//卡口回收3，把卡从卡口位置回收到前回收箱3
        else if (v.getId() == R.id.btnReinTake3) {
            byte[] bExeInfo = new byte[64];
            int iRet = m_IssSimCard.Sim_ReIntake(3, bExeInfo);
            Display_Result(iRet, bExeInfo);
        }
//上电
        else if (v.getId() == R.id.btnPowerOn) {
            byte[] bAtrData = new byte[128];//返回上电数据
            int[] bAtrDataLen = new int[1];//返回上电数据的长度
            byte[] bExeInfo = new byte[64];//出口参数
            int iRet = m_IssSimCard.Sim_PowerOn(bAtrData, bAtrDataLen, bExeInfo);
            resultInfo(iRet, bAtrData, bAtrDataLen, bExeInfo);
        }
//EXEC_APDU，执行APDU指令
        else if (v.getId() == R.id.btnExeAPDU) {//EXEC_APDU，执行APDU指令
            String sData = "00A40000023F00";
            byte[] bSendData = new byte[7];
            bSendData = DataUtils.HexToByteArr(sData);//APDU数据，APDU字符串转换为字节数组后传入
            int iSendDataLen = 0;
            iSendDataLen = sData.length() / 2;//APDU数据数据长度
            byte[] bRecvData = new byte[384];//返回APDU数据
            int[] bRecvDataLen = new int[1];//返回APDU数据的长度
            byte[] bExeInfo = new byte[64];//出口参数
            int iRet = m_IssSimCard.Sim_ApduExchange(bSendData, iSendDataLen, bRecvData, bRecvDataLen, bExeInfo);
            resultInfo(iRet, bRecvData, bRecvDataLen, bExeInfo);
        }
//下电
        else if (v.getId() == R.id.btnPowerOff) {//下电
            byte[] bExeInfo = new byte[64];
            int iRet = m_IssSimCard.Sim_PowerOff(bExeInfo);
            Display_Result(iRet, bExeInfo);
        }
//读卡口进卡，使能进卡
        else if (v.getId() == R.id.btnEnableICInput) {
            byte[] bExeInfo = new byte[64];
            int iRet = m_IssSimCard.Sim_EnableInput(bExeInfo);
            Display_Result(iRet, bExeInfo);
        }
//卡口禁止进卡
        else if (v.getId() == R.id.btnUnableICInput) {
            byte[] bExeInfo = new byte[64];
            int iRet = m_IssSimCard.Sim_DisableInsert(bExeInfo);
            Display_Result(iRet, bExeInfo);
        }
//获取版本号
        else if (v.getId() == R.id.btnGetVersion) {
            byte[] bVersion = new byte[64];//返回版本信息
            int[] iOutDataLen = new int[1];//返回版本信息长度
            byte[] bExeInfo = new byte[64];//出口参数
            int iRet = m_IssSimCard.Sim_GetVersion(bVersion, iOutDataLen, bExeInfo);
            resultInfo(iRet, bVersion, iOutDataLen, bExeInfo);
        }
//设备与卡片位置状态
        else if (v.getId() == R.id.btnDevStatus) {
            //pDevStatus 1 有卡 0 无卡
            //pDevStatus  1：卡口 2 机头 3 机头 4 机尾 5 闸门（1 关闭  0打开）6 IC触点(1接触  0释放  )
            byte[] bRecvData = new byte[6];//详细设备状态信息
            int[] iOutDataLen = new int[1];//返回状态信息长度
            byte[] bExeInfo = new byte[64];//出口参数
            int iRet = m_IssSimCard.Sim_GetDevStatus(bRecvData, iOutDataLen, bExeInfo);
            resultInfo(iRet, bRecvData, iOutDataLen, bExeInfo);
        }
//发卡箱状态
        else if (v.getId() == R.id.btnCardBoxStatus) {//卡箱状态
            //pCardBoxStatus 返回卡箱状态：@ 0x40 无卡箱， 0x30 无卡， 0x31 卡少， 0x32 卡足
            //pCardBoxStatus 六个字节分别是：卡箱1 回收箱1 卡箱2 回收箱2 卡箱3 回收箱3
            //发卡箱  0 无卡  1 卡少  2 卡足  10 卡箱未放置 @
            //回收箱  0 无卡  1 有卡  2卡满   10 卡箱未放置 @
            byte[] bRecvData = new byte[6];
            int[] iOutDataLen = new int[1];
            byte[] bExeInfo = new byte[64];
            int iRet = m_IssSimCard.Sim_GetCardBoxStatus(bRecvData, iOutDataLen, bExeInfo);//详细卡箱信息，返回状态信息长度
            resultInfo(iRet, bRecvData, iOutDataLen, bExeInfo);
        }

//读取磁道信息
        else if (v.getId() == R.id.btnIccid) {
            byte bit = 2;
            byte[] bRecvData = new byte[128];
            int[] iOutDataLen = new int[1];
            byte[] bExeInfo = new byte[64];
            int iRet = m_IssSimCard.Sim_ReadTrack(bit, bRecvData, iOutDataLen, bExeInfo);
            resultInfo(iRet, bRecvData, iOutDataLen, bExeInfo);
        }
//读取单磁道信息
        else if (v.getId() == R.id.btnIccid) {
            byte bit = 1;
            byte[] bRecvData = new byte[128];
            int[] iOutDataLen = new int[1];
            byte[] bExeInfo = new byte[64];
            int iRet = m_IssSimCard.Sim_ReadSingleTrack(bit, bRecvData, iOutDataLen, bExeInfo);
            resultInfo(iRet, bRecvData, iOutDataLen, bExeInfo);
        }

//读取ICCID
        else if (v.getId() == R.id.btnIccid) {
            byte[] bRecvData = new byte[22];//ICCID值  128
            int[] iOutDataLen = new int[1];//返回状态信息长度
            byte[] bExeInfo = new byte[64];//出口参数执行状态
            int iRet = m_IssSimCard.Sim_ReadICCID(bRecvData, iOutDataLen, bExeInfo);
            resultInfo(iRet, bRecvData, iOutDataLen, bExeInfo);
        }

    }

    public void resultInfo(int iRetCode, byte[] outDate, int[] outDataLen, byte[] bExeInfo) {//返回值 0，成功；!=0，失败
        String outDateString = new String(outDate);
        Log.d("Log",">>>>>>>>>>>>>>> outDateString:" + outDateString);
        char[] charArary = outDateString.toCharArray();
        for (int i = 0; i < charArary.length; i++) {
            Log.d("Log",">>>>>>>>>>>>>>> charArary[" + i + "]:" + charArary[i]);
        }
        String outDataLenString = String.valueOf(outDataLen[0]);
        String executedInfo = new String(bExeInfo);
        if (iRetCode == 0) {
            outDateString += "；" + outDataLenString;
            tv_receive.setText(outDataLenString);
            tv_receive16.setText(bytesToHexString(outDate, outDataLen[0]));
            tv_ExecResult.setText("执行成功！返回值：" + iRetCode + "；卡箱状态：" + outDateString + "；出口参数：" + executedInfo);
        } else {
            byte[] bErrorInfo = new byte[64];
            m_IssSimCard.GetLastErrInfo(iRetCode, bErrorInfo);//获取错误信息：错误返回码，错误说明
            String errorInfo = new String(bErrorInfo);
            tv_receive.setText("");
            tv_receive16.setText("");
            tv_ExecResult.setText("执行失败！返回值：" + iRetCode + "；出口参数：" + executedInfo + "；错误信息：" + errorInfo);
        }
    }

    public void Display_Result(int iRetCode, byte[] bExeInfo) {//指令执行结果编码(返回值 0，成功；!=0，失败)，出口参数
        String executeInfo = new String(bExeInfo);
        if (iRetCode == 0) {
            tv_receive.setText("");
            tv_receive16.setText("");
            tv_ExecResult.setText("执行成功！返回值：" + iRetCode + "；出口参数：" + executeInfo);
        } else {
            byte[] bErrorInfo = new byte[64];
            m_IssSimCard.GetLastErrInfo(iRetCode, bErrorInfo);//获取错误信息：错误返回码，错误说明
            String errorInfo = new String(bErrorInfo);
            tv_ExecResult.setText("执行失败！返回值：" + iRetCode + "；出口参数：" + executeInfo + "；错误信息：" + errorInfo);
        }
    }

/*
    出口参数（pExeInfor）
        000|描述
            第一位
                0 表示设备执行成功
            第二三位
                00 读写卡模块内部无卡
                01 卡在卡口
                02 卡在读写卡模块内部
        100|描述
            第一位
                1 表示设备执行失败
            第二三位
                00 设备执行的错误码，
            |后面
                错误描述
    返回值 0，成功； !=0，失败，通过行数GetLastError 获取错误描述
*/

}
