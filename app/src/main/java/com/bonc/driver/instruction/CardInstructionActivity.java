package com.bonc.driver.instruction;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bonc.R;
import com.bonc.f6test.APDUCmdItem;
import com.bonc.f6test.Application;
import com.bonc.f6test.ConnDialog;
import com.bonc.f6test.Utils;
import com.bonc.vtm.hardware.card.CardDriver;
import com.bonc.vtm.utils.HexUtil;

import java.util.Vector;

/**
 * @Author : YangWei
 * @Date : 2018/6/30
 * @Description : CardInstructionActivity
 */

public class CardInstructionActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnOpenPort;//打开串口
    private Button btnClosePort;//关闭串口
    private Button btnCardBoxStatus;//卡箱状态

    private Button btnResetNoAction;//重置-不移动SIM卡
    private Button btnResetFrontOut;//重置-吐卡
    private Button btnResetReturnBox;//重置-回收

    private Button btnEnableInsertion;//读卡口进卡，使能进卡
    private Button btnUnableInsertion;//
    private Button btnCardCapture;//回收

    private Button btnMoveCardRead;//射频位
    private Button btnMoveIc;//移动到IC卡位
    private Button btnMoveCardOut;//移动卡到前端持卡位
    private Button btnMoveCardEject;//从前端弹卡

    private Button btnPowerOn;//上电
    private Spinner spinnerAPDUCmd;
    private Button btnExecAPDU;//执行APDU指令
    private Button btnPowerOff;//下电

    private Button btnCardStatus;//设备与卡片状态
    private Button btnGetVersion;//获取版本号

    private TextView receiveDataTv;//接收数据
//    private TextView receiveHexDataTv;//接收数据16HexData
//    private TextView execResultTv;//执行结果

    public static final String bytesToHexString(byte[] bArray, int iDataLen) {
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
        setContentView(R.layout.activity_card_instruction);
        Application.mainView = this;
        Application.CTX = this;
        initView();
    }

    protected void initView() {

        btnOpenPort = findViewById(R.id.btnOpenPort);//打开串口
        btnOpenPort.setOnClickListener(this);
        btnClosePort = findViewById(R.id.btnClosePort);//关闭串口
        btnClosePort.setOnClickListener(this);
        btnCardBoxStatus = (Button) findViewById(R.id.btnCardBoxStatus);//卡箱状态
        btnCardBoxStatus.setOnClickListener(this);

        btnResetNoAction = (Button) findViewById(R.id.btnResetNoAction);//重置-不移动SIM卡
        btnResetNoAction.setOnClickListener(this);
        btnResetFrontOut = (Button) findViewById(R.id.btnResetFrontOut);//重置-吐卡
        btnResetFrontOut.setOnClickListener(this);
        btnResetReturnBox = (Button) findViewById(R.id.btnResetReturnBox);//重置-回收
        btnResetReturnBox.setOnClickListener(this);

        btnEnableInsertion = (Button) findViewById(R.id.btnEnableInsertion);//读卡口进卡，使能进卡
        btnEnableInsertion.setOnClickListener(this);
        btnUnableInsertion = (Button) findViewById(R.id.btnUnableInsertion);//
        btnUnableInsertion.setOnClickListener(this);
        btnCardCapture = (Button) findViewById(R.id.btnCardCapture);//回收
        btnCardCapture.setOnClickListener(this);

        btnMoveCardRead = (Button) findViewById(R.id.btnMoveCardRead);//射频位
        btnMoveCardRead.setOnClickListener(this);
        btnMoveIc = (Button) findViewById(R.id.btnMoveIc);//移动到IC卡位
        btnMoveIc.setOnClickListener(this);
        btnMoveCardOut = (Button) findViewById(R.id.btnMoveCardOut);//吐卡
        btnMoveCardOut.setOnClickListener(this);
        btnMoveCardEject = (Button) findViewById(R.id.btnMoveCardEject);//
        btnMoveCardEject.setOnClickListener(this);

        btnPowerOn = (Button) findViewById(R.id.btnPowerOn);//上电
        btnPowerOn.setOnClickListener(this);

        spinnerAPDUCmd = (Spinner) findViewById(R.id.spinnerAPDUCmd);
        Vector<APDUCmdItem> vec = Application.getPredCmdItems();
        ArrayAdapter<APDUCmdItem> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, vec);
        spinnerAPDUCmd.setAdapter(adapter);

        btnExecAPDU = (Button) findViewById(R.id.btnExecAPDU);//执行APDU指令
        btnExecAPDU.setOnClickListener(this);
        btnPowerOff = (Button) findViewById(R.id.btnPowerOff);//下电
        btnPowerOff.setOnClickListener(this);

        btnCardStatus = (Button) findViewById(R.id.btnCardStatus);//设备与卡片状态
        btnCardStatus.setOnClickListener(this);
        btnGetVersion = (Button) findViewById(R.id.btnGetVersion);//获取版本号
        btnGetVersion.setOnClickListener(this);

        receiveDataTv = (TextView) findViewById(R.id.receiveDataTv);//接收数据
//        receiveHexDataTv = (TextView) findViewById(R.id.receiveHexDataTv);//接收数据16HexData
//        execResultTv = (TextView) findViewById(R.id.execResultTv);//执行结果

    }

    private void showToast(String msg) {
        Toast.makeText(getApplication(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
        try {
            receiveDataTv.setText("-=-=-");
            switch (view.getId()) {
                case R.id.btnOpenPort://打开串口
                    new ConnDialog(this).show();
                    break;
                case R.id.btnClosePort://关闭串口
                    Application.cr.disconnect();
                    showToast("连接断开成功!");
                    break;
                case R.id.btnCardBoxStatus://查询卡机状态
                    String spos = "";
                    CardDriver.CardStatus val;
                    val = Application.cr.getCardPosition();
                    if (val.cardPosition == 0x30) spos += "机内无卡\r\n";
                    else if (val.cardPosition == 0x31) spos += "卡在出卡口\r\n";
                    else if (val.cardPosition == 0x32) spos += "机内有卡\r\n";

                    if (val.cardBoxStatus == 0x30) spos += "卡箱空\r\n";
                    else if (val.cardBoxStatus == 0x31) spos += "卡箱卡少\r\n";
                    else if (val.cardBoxStatus == 0x32) spos += "卡箱卡足\r\n";

                    if (val.isCaptureBoxFull) spos += "回收箱满\r\n";
                    else spos += "回收箱未满\r\n";

                    Application.showInfo(spos, "卡片位置", CardInstructionActivity.this);

                    break;
                case R.id.btnResetNoAction://复位/重置-不移动SIM卡
                    String result1 = Application.cr.reset((byte) Application.instructions.getCardResetNoAction());
                    receiveDataTv.setText(result1);
                    break;
                case R.id.btnResetFrontOut://复位移动持卡位
                    String result2 = Application.cr.reset((byte) Application.instructions.getCardResetFrontOut());
                    receiveDataTv.setText(result2);
                    break;
                case R.id.btnResetReturnBox://复位回收
                    String result3 = Application.cr.reset((byte) Application.instructions.getCardResetReturnBox());
                    receiveDataTv.setText(result3);
                    break;
                case R.id.btnEnableInsertion://允许前端进卡
                    String result4 = Application.cr.controlInsertion((byte) Application.instructions.getInsertionYes());
                    receiveDataTv.setText(result4);
                    break;
                case R.id.btnUnableInsertion://禁止进卡
                    String result5 = Application.cr.controlInsertion((byte) Application.instructions.getInsertionNo());
                    receiveDataTv.setText(result5);
                    break;
                case R.id.btnCardCapture://回收
                    String result6 = Application.cr.moveCard((byte) Application.instructions.getCardMoveBox());
                    receiveDataTv.setText(result6);
                    break;
                case R.id.btnMoveCardRead://射频位
                    String result7 = Application.cr.moveCard((byte) Application.instructions.getCardMoveRead());
                    receiveDataTv.setText(result7);
                    break;
                case R.id.btnMoveIc://移动到IC卡位
                    String result8 = Application.cr.moveCard((byte) Application.instructions.getCardMoveIc());
                    receiveDataTv.setText(result8);
                    break;
                case R.id.btnMoveCardOut://移动卡到前端持卡位
                    String result9 = Application.cr.moveCard((byte) Application.instructions.getCardMoveOut());
                    receiveDataTv.setText(result9);
                    break;
                case R.id.btnMoveCardEject://从前端弹卡
                    String result10 = Application.cr.moveCard((byte) Application.instructions.getCardMoveEject());
                    receiveDataTv.setText(result10);
                    break;
                case R.id.btnPowerOn://上电
                    byte[] atr = Application.cr.chipPowerOn();
                    receiveDataTv.setText(HexUtil.bytesToHexString(atr));
                    break;
                case R.id.btnExecAPDU://执行APDU指令
                    APDUCmdItem cmd = (APDUCmdItem) spinnerAPDUCmd.getSelectedItem();
                    byte[] receivedData = Application.cr.chipIo(cmd.getCmdData());
                    receiveDataTv.setText(HexUtil.bytesToHexString(receivedData));
                    break;
                case R.id.btnPowerOff://下电
                    String result11 = Application.cr.chipPowerOff();
                    receiveDataTv.setText(result11);
                    break;
                case R.id.btnCardStatus://设备与卡片位置状态
                    String result12 = Application.cr.chipCardStatus();
                    receiveDataTv.setText(result12);
                    break;
                case R.id.btnGetVersion://获取版本号
                    String result13 = Application.cr.getVersion();
                    receiveDataTv.setText(result13);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            Application.showError(e.getMessage(), CardInstructionActivity.this);
        }

    }

}
