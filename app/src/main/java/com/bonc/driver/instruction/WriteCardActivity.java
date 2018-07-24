package com.bonc.driver.instruction;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bonc.R;
import com.bonc.vtm.VtmEquipment;
import com.bonc.vtm.bean.WriteCardParamsBean;
import com.bonc.vtm.hardware.sim.CardFactory;
import com.bonc.vtm.hardware.sim.CardFactoryImp;
import com.google.gson.Gson;
import com.hengbao.rps.writecard.impl.WriteCardImpl;

import java.util.HashMap;
import java.util.Map;

/*

打开串口 --> 复位 --> 发卡 --> 上电 --> APDU 交互 --> 下电 --> 吐卡 --> 关闭串口

    返回错误代码
        0	正常、调用成功
        1	打开串口失败
        4	串口没有打开
        5	读取数据失败
        13	参数错误
        15	超时
        18	CRC校验错误
        19	命令执行失败
        20	日志文件打开失败
        21	接收ACK 失败
*/

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

public class WriteCardActivity extends AppCompatActivity implements View.OnClickListener {

    TextView deciphering_tv;//密文解密之后的报文

    TextView card_Info_tv;//卡信息

    TextView write_card_tv;//写卡结果

    //    private String IMEI = EquipmentInfoBean.IMEI;
    private String IMEI = "6661234567890";


    protected void initView() {

        deciphering_tv = findViewById(R.id.deciphering_tv);
        card_Info_tv = findViewById(R.id.card_Info_tv);
        write_card_tv = findViewById(R.id.write_card_tv);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hb_write_card);
        initView();
        loadData();
    }

    protected void loadData() {
        //读卡
//        readCard();

        //卡状态
//        cardStatus();

        //30秒轮询卡状态并回收
//        reIntake();

        //获取写卡信息
//        getWriteCardInfo();

        String resXmlInfo = "<?xml version='1.0' encoding='UTF-8'?><ContractRoot><TcpCont><ActionCode>1</ActionCode><TransactionID>6090010001201708220021388478</TransactionID><RspTime>20170822184506</RspTime><Response><RspType>0</RspType><RspCode>0000</RspCode><RspDesc>成功</RspDesc></Response></TcpCont><SvcCont><ResultCode>00000000</ResultCode><ResultMessage>成功</ResultMessage><CryptIndex>11</CryptIndex><PersonalData><ICCID>89860315760107141657</ICCID><IMSI>460036210992509</IMSI><UIMID>80FFA5C9</UIMID><SID>3600</SID><ACCOLC>9</ACCOLC><NID>FFFF</NID><AKEY>F395AF9E8A2BE95D</AKEY><PIN1>1234</PIN1><PIN2>52778318</PIN2><PUK1>13374350</PUK1><PUK2>36377277</PUK2><ADM>818E4B9E</ADM><HRPDUPP>460036210992509@mycdma.cn</HRPDUPP><HRPDSS>4F94406F7F9AAD7D</HRPDSS><SIPUPP><SIPUPPData>23200F6374776170406D7963646D612E636E210F63746E6574406D7963646D612E636E20</SIPUPPData></SIPUPP><SIPSS><SIPSSData>EA93A629B00A30D116D7A16E32700565</SIPSSData></SIPSS><MIPUPP><MIPUPPData>460036210992509@mycdma.cn</MIPUPPData></MIPUPP><MNHASS><MNHASSData>59C9D1F25E62FD90</MNHASSData></MNHASS><MNHASS><MNHASSData>9F0544060CA34CDF</MNHASSData></MNHASS><MNHASS><MNHASSData>FF39B4F85DED9B3A</MNHASSData></MNHASS><MNHASS><MNHASSData>614FB39D1F28F624</MNHASSData></MNHASS><MNHASS><MNHASSData>AAC9A58EA51930A7</MNHASSData></MNHASS><MNHASS><MNHASSData>47173CB047EBFD20</MNHASSData></MNHASS><MNHASS><MNHASSData>A79AFD7156822800</MNHASSData></MNHASS><MNHASS><MNHASSData>351920007763A047</MNHASSData></MNHASS><MNHASS><MNHASSData>00721EFC205E3B7C</MNHASSData></MNHASS><MNHASS><MNHASSData>2CAAC085C383597E</MNHASSData></MNHASS><MNHASS><MNHASSData>845499FF9D42C52D</MNHASSData></MNHASS><MNHASS><MNHASSData>7D4401FF24CC1731</MNHASSData></MNHASS><MNHASS><MNHASSData>80657D1623449C14</MNHASSData></MNHASS><MNHASS><MNHASSData>93DD0157AF1C4756</MNHASSData></MNHASS><MNHASS><MNHASSData>71CFE5C4C80354ED</MNHASSData></MNHASS><MNAAASS><MNAAASSData>91A03C6194BD2002</MNAAASSData></MNAAASS><MNAAASS><MNAAASSData>C2E9DDE55C5F4015</MNAAASSData></MNAAASS><MNAAASS><MNAAASSData>CD24F5575FAFE61E</MNAAASSData></MNAAASS><MNAAASS><MNAAASSData>DF366CA27A30A05B</MNAAASSData></MNAAASS><MNAAASS><MNAAASSData>54B80268BB1274DC</MNAAASSData></MNAAASS><MNAAASS><MNAAASSData>B43DC809F4968881</MNAAASSData></MNAAASS><MNAAASS><MNAAASSData>5AE3AAE02BEB8A50</MNAAASSData></MNAAASS><MNAAASS><MNAAASSData>A3241FBED39E3530</MNAAASSData></MNAAASS><MNAAASS><MNAAASSData>68E7DE9BD33E92CD</MNAAASSData></MNAAASS><MNAAASS><MNAAASSData>88B5D80D0F0DADDE</MNAAASSData></MNAAASS><MNAAASS><MNAAASSData>2C31857F3BA0C8E3</MNAAASSData></MNAAASS><MNAAASS><MNAAASSData>B50381C6F0FDCF23</MNAAASSData></MNAAASS><MNAAASS><MNAAASSData>638BE1CD98881053</MNAAASSData></MNAAASS><MNAAASS><MNAAASSData>958A22CEDE040A60</MNAAASSData></MNAAASS><MNAAASS><MNAAASSData>98A74D7F4E6A496B</MNAAASSData></MNAAASS><IMSIG>204046504746726</IMSIG><ACC>6</ACC><KI>75FCFA0B27A5AA2DB2AD187D8DDDC802</KI><SMSP>+316540942002</SMSP><OPC_G>5C15F3D7E9599E2210467515FB8A74FA</OPC_G><IMSI_LTE>460110014832297</IMSI_LTE><ACC_LTE>7</ACC_LTE><KI_LTE>735F314E3D964627113D4E83E7D86BD9</KI_LTE><OPC_LTE>4F7BE02242A9FFB037475A100D43C247</OPC_LTE></PersonalData></SvcCont></ContractRoot>";
        WriteCard(resXmlInfo);

    }

    private void getWriteCardInfo() {
        Map<String, String> params = new HashMap<>();
        params.put("serialNumber", "2222");
        params.put("meid", IMEI);
//        params.put("BusCode", "BUS80001");
//        params.put("ServiceCode", "SVC25001");
//        params.put("ServiceContractVer", "SVC2500120131101");
//        params.put("ActionCode", "0");
//        params.put("TransactionID", "6090010001201005280000001220");
//        params.put("ServiceLevel", "1");
//        params.put("SrcOrgID", "609001");
//        params.put("SrcSysID", "6090010001");
//        params.put("SrcSysSign", "crm4g609001000101015");
//        params.put("DstOrgID", "100000");
//        params.put("DstSysID", "1000000200");
//        params.put("ReqTime", "20170802154222");
        params.put("MDN", "17301347904");//17301348014  //18911012321  //17301347904
//        params.put("CardNO", "00319");
//        params.put("FromAreaNum", "021");
//        params.put("ToAreanum", "010");
//        params.put("ToLanid", "8110100");
        params.put("StaffID", "10001");
//        params.put("staffCode", "A00882");
        params.put("StaffName", "张三");
        params.put("ChannelID", "ABC10001");
        params.put("ChannelName", "鼓楼营业厅");
        String json = new Gson().toJson(params);
        Log.d("Log", ">>>>>>>>>>>>>>>loadData mwCardParamsJson:" + json);

/*
        addSubscription(
                factory.getServiceData()
                        .getWriteCard(json)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new ProgressSubscriber<BaseResultBean>(this) {
                                       @Override
                                       protected void msgEncoding(BaseResultBean baseResultBean) {
                                           if ("0000".equals(baseResultBean.getStatus())) {
                                               String resultDataStr = baseResultBean.getData() == null ? "" : baseResultBean.getData().toString().trim();
                                               Log.d("Log", ">>>>>>>>>>>>>>>loadData resultDataStr:" + resultDataStr);
                                               ReqWriteCardInfoBean reqWriteCardInfoBean = new Gson().fromJson(resultDataStr, ReqWriteCardInfoBean.class);

                                               String ciphertext = reqWriteCardInfoBean.getData() == null ? "" : reqWriteCardInfoBean.getData().toString().trim();
                                               if ("".equals(ciphertext)) {
                                                   ciphertext = reqWriteCardInfoBean.getOrgData() == null ? "" : reqWriteCardInfoBean.getOrgData().toString().trim();
                                               }

                                               deciphering_tv.setText(ciphertext);
                                               Log.d("Log", ">>>>>>>>>>>>>>> 密文：" + ciphertext);

                                               //解密
                                               getDecipheringText(ciphertext);

                                               //写卡
//                                               WriteCard(ciphertext);

                                           } else {
                                               showToast("请求失败");
                                           }
                                       }
                                   }
                        )
        );
*/

    }

    private void getDecipheringText(String ciphertext) {
//MDM解密
    /*
        EnvelopeDecObj envelopeDecObj = new EnvelopeDecObj();
        envelopeDecObj.setAccount(IMEI);
        envelopeDecObj.setAppKey(Constants.MOKEY_ENGINE_APP_KEY);
        envelopeDecObj.setPin(Constants.MOKEY_ENGINE_PIN);
        envelopeDecObj.setCipherData(ciphertext);
        byte[] ret = new MoKeyEngine(this).doEnvelopeDec(envelopeDecObj);
        String deciphering = new String(ret);
     */

//恒宝解密
//        String deciphering = new DecryptMsg().decryptMsgBusiness(ciphertext, IMEI, "app1709128285087707-1", "VTM-2017", this);

//        deciphering_tv.setText(deciphering);

//        Log.d("Log", ">>>>>>>>>>>>>>>getDecipheringText deciphering:" + deciphering);
    }

    private String readCard() {
        CardFactory cardFactory = new CardFactoryImp();
        String iccid = cardFactory.readCard();
        Log.d("Log", ">>>>>>>>>>>>>>> readCard ICCID:" + iccid);
        return iccid;
    }

    private String cardStatus() {
        CardFactory cardFactory = new CardFactoryImp();
        String cardStatus = cardFactory.getDevStatus();
        Log.d("Log", ">>>>>>>>>>>>>>> cardStatus:" + cardStatus);
        return cardStatus;
    }

    private int reIntake() {
        CardFactory cardFactory = new CardFactoryImp();
        int reIntake = cardFactory.reIntake();
        Log.d("Log", ">>>>>>>>>>>>>>> reIntake:" + reIntake);
        return reIntake;
    }

    private void WriteCard(String ciphertext) {

        WriteCardImpl wci = new WriteCardImpl(VtmEquipment.Supplier.ZW);
/*
        String randNum = wci.getRandNum();//随机数Json
        Log.d("Log", ">>>>>>>>>>>>>>> randNum:" + randNum);//{"randNum":"1C0D9DF1E787A497","retCode":"C0000","retMsg":"操作成功"}
//随机数Json转换JavaBean
        RandomBean mdmWriteCardBean = new Gson().fromJson(randNum, RandomBean.class);
        String randNumStr = mdmWriteCardBean.getRandNum();
        Log.d("Log", ">>>>>>>>>>>>>>> randNumStr:" + randNumStr);
//3DES-ECB加密随机数
        String key = "12345678123456781234567812345678";
        String encodeKey = DES.des3(randNumStr, key, 0);
        Log.d("Log", ">>>>>>>>>>>>>>> encodeKey:" + encodeKey);//54E28F5C6498B35D
//鉴权
        Map<String, String> authParamMap = new HashMap<>();
        authParamMap.put("authCode", encodeKey);
        String encodeKeyJson = new Gson().toJson(authParamMap);
        String authentication = wci.authentication(encodeKeyJson);//鉴权
        Log.d("Log", ">>>>>>>>>>>>>>> authentication:" + authentication);//{"retCode":"C0000","retMsg":"操作成功"}
*/

//写卡
        WriteCardParamsBean writeCardParamsBean = new WriteCardParamsBean();
        writeCardParamsBean.setCardData(ciphertext);//写卡数据密文
        writeCardParamsBean.setVendorCode(VtmEquipment.Supplier.ZW);//设备型号编码 ZW01 兆维 ZD01 中鼎 ZR01 卓睿
        writeCardParamsBean.setAccount(IMEI);//设备meid
        writeCardParamsBean.setAppkey("app1709128285087707-1");//Mdm提供的授权KEYMdm提供的授权KEY，应用app对应账号的密码
        writeCardParamsBean.setPin("VTM-2017");//Pin密码
        String wcpJson = new Gson().toJson(writeCardParamsBean);
        String writeCardResult = wci.writeCardBusiness(wcpJson, this);//写卡
        write_card_tv.setText(writeCardResult);//{"retCode":"C0004","retMsg":"报文参数错误"}
        Log.d("Log", ">>>>>>>>>>>>>>> writeCardResult:" + writeCardResult);//{"CIMSI":"460036210992350","GIMSI":"204046504746703","ICCID":"89860315760107141426","ICCSERIAL":"0117000319004060061B","USIMIMSI":"460110014832274","retCode":"C0000","retMsg":"操作成功"}
//读取卡信息
        String cardInfo = wci.readCardApduBusiness(VtmEquipment.Supplier.ZW);//读卡,VTM-ZW01 兆维 VTM-ZD01 中鼎 VTM-ZR01 卓睿
        card_Info_tv.setText(cardInfo);
        Log.d("Log", ">>>>>>>>>>>>>>> cardInfo:" + cardInfo);//{"ICCID":"89860315760107141426","retCode":"C0000","retMsg":"操作成功"}

    }

    @Override
    public void onClick(View v) {

    }


}
