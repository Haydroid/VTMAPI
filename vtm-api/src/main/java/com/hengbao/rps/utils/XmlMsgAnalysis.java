package com.hengbao.rps.utils;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.StringReader;
import java.util.List;

/**
 * @Author : YangWei
 * @Date : 2018/6/29
 * @Description : XML处理工具类
 */

public class XmlMsgAnalysis {
    public XmlMsgAnalysis() {
    }

    public static Element parseXml(String xmlText) throws JDOMException {
        try {
            SAXBuilder sax = new SAXBuilder();
            Document doc = sax.build(new StringReader(xmlText));
            Element root = doc.getRootElement();
            return root;
        } catch (Exception var4) {
            return null;
        }
    }

    public String isNullString(String str) {
        return null == str ? "" : str;
    }

    public String putPersonInfo(String key, Element personalData) {
        String value;
        if (personalData.getChild(key) != null) {
            value = personalData.getChild(key).getValue().trim();
        } else {
            value = "";
        }

        return value;
    }

    public StringBuilder putPersonForArray(List<Element> elementList, Element personalData, StringBuilder sb) {
        String separator = ";";
        if (elementList.size() > 0) {
            for (int i = 0; i < elementList.size(); i++) {
                if (i == elementList.size() - 1) {
                    separator = ",";
                }

                Element e = (Element) elementList.get(i);
                String elementValue = e.getValue().trim();
                sb.append(elementValue + separator);
            }
        }

        return sb;
    }

    public String getPersonalInfo(Element personalData) {
        String separator = ",";
        StringBuilder sb = new StringBuilder();
        sb.append(this.putPersonInfo("ICCID", personalData) + separator);
        sb.append(this.putPersonInfo("IMSI", personalData) + separator);
        sb.append(this.putPersonInfo("UIMID", personalData) + separator);
        sb.append(this.putPersonInfo("SID", personalData) + separator);
        sb.append(this.putPersonInfo("ACCOLC", personalData) + separator);
        sb.append(this.putPersonInfo("NID", personalData) + separator);
        sb.append(this.putPersonInfo("AKEY", personalData) + separator);
        sb.append(this.putPersonInfo("PIN1", personalData) + separator);
        sb.append(this.putPersonInfo("PIN2", personalData) + separator);
        sb.append(this.putPersonInfo("PUK1", personalData) + separator);
        sb.append(this.putPersonInfo("PUK2", personalData) + separator);
        sb.append(this.putPersonInfo("ADM", personalData) + separator);
        sb.append(this.putPersonInfo("HRPDUPP", personalData) + separator);
        sb.append(this.putPersonInfo("HRPDSS", personalData) + separator);
        List<Element> childList = personalData.getChildren("SIPUPP");
        sb = this.putPersonForArray(childList, personalData, sb);
        childList = personalData.getChildren("SIPSS");
        sb = this.putPersonForArray(childList, personalData, sb);
        childList = personalData.getChildren("MIPUPP");
        sb = this.putPersonForArray(childList, personalData, sb);
        childList = personalData.getChildren("MNHASS");
        sb = this.putPersonForArray(childList, personalData, sb);
        childList = personalData.getChildren("MNAAASS");
        sb = this.putPersonForArray(childList, personalData, sb);
        sb.append(this.putPersonInfo("IMSIG", personalData) + separator);
        sb.append(this.putPersonInfo("ACC", personalData) + separator);
        sb.append(this.putPersonInfo("KI", personalData) + separator);
        sb.append(this.putPersonInfo("OPC_G", personalData) + separator);
        sb.append(this.putPersonInfo("SMSP", personalData) + separator);
        sb.append(this.putPersonInfo("IMSI_LTE", personalData) + separator);
        sb.append(this.putPersonInfo("ACC_LTE", personalData) + separator);
        sb.append(this.putPersonInfo("KI_LTE", personalData) + separator);
        sb.append(this.putPersonInfo("OPC_LTE", personalData));
        return sb.toString();
    }

    public void test() {
        String resXmlInfo = "<?xml version='1.0' encoding='UTF-8'?><ContractRoot><TcpCont><ActionCode>1</ActionCode><TransactionID>6090010001201708220021388478</TransactionID><RspTime>20170822184506</RspTime><Response><RspType>0</RspType><RspCode>0000</RspCode><RspDesc>成功</RspDesc></Response></TcpCont><SvcCont><ResultCode>00000000</ResultCode><ResultMessage>成功</ResultMessage><CryptIndex>11</CryptIndex><PersonalData><ICCID>89860315760107141657</ICCID><IMSI>460036210992509</IMSI><UIMID>80FFA5C9</UIMID><SID>3600</SID><ACCOLC>9</ACCOLC><NID>FFFF</NID><AKEY>F395AF9E8A2BE95D</AKEY><PIN1>1234</PIN1><PIN2>52778318</PIN2><PUK1>13374350</PUK1><PUK2>36377277</PUK2><ADM>818E4B9E</ADM><HRPDUPP>460036210992509@mycdma.cn</HRPDUPP><HRPDSS>4F94406F7F9AAD7D</HRPDSS><SIPUPP><SIPUPPData>23200F6374776170406D7963646D612E636E210F63746E6574406D7963646D612E636E20</SIPUPPData></SIPUPP><SIPSS><SIPSSData>EA93A629B00A30D116D7A16E32700565</SIPSSData></SIPSS><MIPUPP><MIPUPPData>460036210992509@mycdma.cn</MIPUPPData></MIPUPP><MNHASS><MNHASSData>59C9D1F25E62FD90</MNHASSData></MNHASS><MNHASS><MNHASSData>9F0544060CA34CDF</MNHASSData></MNHASS><MNHASS><MNHASSData>FF39B4F85DED9B3A</MNHASSData></MNHASS><MNHASS><MNHASSData>614FB39D1F28F624</MNHASSData></MNHASS><MNHASS><MNHASSData>AAC9A58EA51930A7</MNHASSData></MNHASS><MNHASS><MNHASSData>47173CB047EBFD20</MNHASSData></MNHASS><MNHASS><MNHASSData>A79AFD7156822800</MNHASSData></MNHASS><MNHASS><MNHASSData>351920007763A047</MNHASSData></MNHASS><MNHASS><MNHASSData>00721EFC205E3B7C</MNHASSData></MNHASS><MNHASS><MNHASSData>2CAAC085C383597E</MNHASSData></MNHASS><MNHASS><MNHASSData>845499FF9D42C52D</MNHASSData></MNHASS><MNHASS><MNHASSData>7D4401FF24CC1731</MNHASSData></MNHASS><MNHASS><MNHASSData>80657D1623449C14</MNHASSData></MNHASS><MNHASS><MNHASSData>93DD0157AF1C4756</MNHASSData></MNHASS><MNHASS><MNHASSData>71CFE5C4C80354ED</MNHASSData></MNHASS><MNAAASS><MNAAASSData>91A03C6194BD2002</MNAAASSData></MNAAASS><MNAAASS><MNAAASSData>C2E9DDE55C5F4015</MNAAASSData></MNAAASS><MNAAASS><MNAAASSData>CD24F5575FAFE61E</MNAAASSData></MNAAASS><MNAAASS><MNAAASSData>DF366CA27A30A05B</MNAAASSData></MNAAASS><MNAAASS><MNAAASSData>54B80268BB1274DC</MNAAASSData></MNAAASS><MNAAASS><MNAAASSData>B43DC809F4968881</MNAAASSData></MNAAASS><MNAAASS><MNAAASSData>5AE3AAE02BEB8A50</MNAAASSData></MNAAASS><MNAAASS><MNAAASSData>A3241FBED39E3530</MNAAASSData></MNAAASS><MNAAASS><MNAAASSData>68E7DE9BD33E92CD</MNAAASSData></MNAAASS><MNAAASS><MNAAASSData>88B5D80D0F0DADDE</MNAAASSData></MNAAASS><MNAAASS><MNAAASSData>2C31857F3BA0C8E3</MNAAASSData></MNAAASS><MNAAASS><MNAAASSData>B50381C6F0FDCF23</MNAAASSData></MNAAASS><MNAAASS><MNAAASSData>638BE1CD98881053</MNAAASSData></MNAAASS><MNAAASS><MNAAASSData>958A22CEDE040A60</MNAAASSData></MNAAASS><MNAAASS><MNAAASSData>98A74D7F4E6A496B</MNAAASSData></MNAAASS><IMSIG>204046504746726</IMSIG><ACC>6</ACC><KI>75FCFA0B27A5AA2DB2AD187D8DDDC802</KI><SMSP>+316540942002</SMSP><OPC_G>5C15F3D7E9599E2210467515FB8A74FA</OPC_G><IMSI_LTE>460110014832297</IMSI_LTE><ACC_LTE>7</ACC_LTE><KI_LTE>735F314E3D964627113D4E83E7D86BD9</KI_LTE><OPC_LTE>4F7BE02242A9FFB037475A100D43C247</OPC_LTE></PersonalData></SvcCont></ContractRoot>";
        XmlMsgAnalysis xml = new XmlMsgAnalysis();
        Element root = null;

        try {
            root = parseXml(resXmlInfo);
        } catch (Exception var11) {
            var11.printStackTrace();
        }

        Element resSvcCont = root.getChild("SvcCont");
        Element personalData = resSvcCont.getChild("PersonalData");
//        xml.getPersonalInfo(personalData);
        String strWriteCardData = xml.getPersonalInfo(personalData);
//        xml.putPersonInfo("ICCID", personalData);
        String ICCID = xml.putPersonInfo("ICCID", personalData);
//        xml.putPersonInfo("IMSI", personalData);
        String CIMSI = xml.putPersonInfo("IMSI", personalData);
//        xml.putPersonInfo("IMSIG", personalData);
        String GIMSI = xml.putPersonInfo("IMSIG", personalData);
//        xml.putPersonInfo("IMSI_LTE", personalData);
        String USIMIMSI = xml.putPersonInfo("IMSI_LTE", personalData);
    }

    public static void main(String[] args) {
        String resXmlInfo = "<?xml version='1.0' encoding='UTF-8'?><ContractRoot><TcpCont><ActionCode>1</ActionCode><TransactionID>6090010001201708220021388478</TransactionID><RspTime>20170822184506</RspTime><Response><RspType>0</RspType><RspCode>0000</RspCode><RspDesc>成功</RspDesc></Response></TcpCont><SvcCont><ResultCode>00000000</ResultCode><ResultMessage>成功</ResultMessage><CryptIndex>11</CryptIndex><PersonalData><ICCID>89860315760107141657</ICCID><IMSI>460036210992509</IMSI><UIMID>80FFA5C9</UIMID><SID>3600</SID><ACCOLC>9</ACCOLC><NID>FFFF</NID><AKEY>F395AF9E8A2BE95D</AKEY><PIN1>1234</PIN1><PIN2>52778318</PIN2><PUK1>13374350</PUK1><PUK2>36377277</PUK2><ADM>818E4B9E</ADM><HRPDUPP>460036210992509@mycdma.cn</HRPDUPP><HRPDSS>4F94406F7F9AAD7D</HRPDSS><SIPUPP><SIPUPPData>23200F6374776170406D7963646D612E636E210F63746E6574406D7963646D612E636E20</SIPUPPData></SIPUPP><SIPSS><SIPSSData>EA93A629B00A30D116D7A16E32700565</SIPSSData></SIPSS><MIPUPP><MIPUPPData>460036210992509@mycdma.cn</MIPUPPData></MIPUPP><MNHASS><MNHASSData>59C9D1F25E62FD90</MNHASSData></MNHASS><MNHASS><MNHASSData>9F0544060CA34CDF</MNHASSData></MNHASS><MNHASS><MNHASSData>FF39B4F85DED9B3A</MNHASSData></MNHASS><MNHASS><MNHASSData>614FB39D1F28F624</MNHASSData></MNHASS><MNHASS><MNHASSData>AAC9A58EA51930A7</MNHASSData></MNHASS><MNHASS><MNHASSData>47173CB047EBFD20</MNHASSData></MNHASS><MNHASS><MNHASSData>A79AFD7156822800</MNHASSData></MNHASS><MNHASS><MNHASSData>351920007763A047</MNHASSData></MNHASS><MNHASS><MNHASSData>00721EFC205E3B7C</MNHASSData></MNHASS><MNHASS><MNHASSData>2CAAC085C383597E</MNHASSData></MNHASS><MNHASS><MNHASSData>845499FF9D42C52D</MNHASSData></MNHASS><MNHASS><MNHASSData>7D4401FF24CC1731</MNHASSData></MNHASS><MNHASS><MNHASSData>80657D1623449C14</MNHASSData></MNHASS><MNHASS><MNHASSData>93DD0157AF1C4756</MNHASSData></MNHASS><MNHASS><MNHASSData>71CFE5C4C80354ED</MNHASSData></MNHASS><MNAAASS><MNAAASSData>91A03C6194BD2002</MNAAASSData></MNAAASS><MNAAASS><MNAAASSData>C2E9DDE55C5F4015</MNAAASSData></MNAAASS><MNAAASS><MNAAASSData>CD24F5575FAFE61E</MNAAASSData></MNAAASS><MNAAASS><MNAAASSData>DF366CA27A30A05B</MNAAASSData></MNAAASS><MNAAASS><MNAAASSData>54B80268BB1274DC</MNAAASSData></MNAAASS><MNAAASS><MNAAASSData>B43DC809F4968881</MNAAASSData></MNAAASS><MNAAASS><MNAAASSData>5AE3AAE02BEB8A50</MNAAASSData></MNAAASS><MNAAASS><MNAAASSData>A3241FBED39E3530</MNAAASSData></MNAAASS><MNAAASS><MNAAASSData>68E7DE9BD33E92CD</MNAAASSData></MNAAASS><MNAAASS><MNAAASSData>88B5D80D0F0DADDE</MNAAASSData></MNAAASS><MNAAASS><MNAAASSData>2C31857F3BA0C8E3</MNAAASSData></MNAAASS><MNAAASS><MNAAASSData>B50381C6F0FDCF23</MNAAASSData></MNAAASS><MNAAASS><MNAAASSData>638BE1CD98881053</MNAAASSData></MNAAASS><MNAAASS><MNAAASSData>958A22CEDE040A60</MNAAASSData></MNAAASS><MNAAASS><MNAAASSData>98A74D7F4E6A496B</MNAAASSData></MNAAASS><IMSIG>204046504746726</IMSIG><ACC>6</ACC><KI>75FCFA0B27A5AA2DB2AD187D8DDDC802</KI><SMSP>+316540942002</SMSP><OPC_G>5C15F3D7E9599E2210467515FB8A74FA</OPC_G><IMSI_LTE>460110014832297</IMSI_LTE><ACC_LTE>7</ACC_LTE><KI_LTE>735F314E3D964627113D4E83E7D86BD9</KI_LTE><OPC_LTE>4F7BE02242A9FFB037475A100D43C247</OPC_LTE></PersonalData></SvcCont></ContractRoot>";
        XmlMsgAnalysis xml = new XmlMsgAnalysis();
        Element root = null;

        try {
            root = parseXml(resXmlInfo);
        } catch (Exception var12) {
            var12.printStackTrace();
        }

        Element resSvcCont = root.getChild("SvcCont");
        Element personalData = resSvcCont.getChild("PersonalData");
//        xml.getPersonalInfo(personalData);
        String strWriteCardData = xml.getPersonalInfo(personalData);
//        xml.putPersonInfo("ICCID", personalData);
        String ICCID = xml.putPersonInfo("ICCID", personalData);
//        xml.putPersonInfo("IMSI", personalData);
        String CIMSI = xml.putPersonInfo("IMSI", personalData);
//        xml.putPersonInfo("IMSIG", personalData);
        String GIMSI = xml.putPersonInfo("IMSIG", personalData);
//        xml.putPersonInfo("IMSI_LTE", personalData);
        String USIMIMSI = xml.putPersonInfo("IMSI_LTE", personalData);
//        String resenc = Base64Util.getBase64Encode(resXmlInfo);
//        System.out.println(resenc);
        System.out.println(ICCID);
    }
}
