package com.hengbao.rps.utils;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.Character.UnicodeBlock;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.SecureRandom;

/**
 * @Author : YangWei
 * @Date : 2018/6/29
 * @Description : PubUtils
 */

public class PubUtils {
    static File filePath;
    public static String p;

    public PubUtils() {
    }

    public static String ByteArrayToHex(byte[] b) {
        String ret = "";

        for (int i = 0; i < b.length; i++) {
//            String hex = Integer.toHexString(b[i] & 255);
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }

            ret = ret + hex.toUpperCase();
        }

        return ret.toUpperCase();
    }

    public static String StringToHex(String strSrc) {
        byte[] tmp = strSrc.getBytes();
        return ByteArrayToHex(tmp);
    }

    public static String StringToHexString(String strPart) {
        String hexString = "";

        for (int i = 0; i < strPart.length(); i++) {
            int ch = strPart.charAt(i);
            String strHex = Integer.toHexString(ch);
            hexString = hexString + strHex;
        }

        return hexString;
    }

    public static String HexToString(String s) {
        if (s.length() % 2 != 0) {
            return null;
        } else {
            byte[] baKeyword = new byte[s.length() / 2];

            for (int i = 0; i < baKeyword.length; i++) {
                try {
//                    int n = 255 & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16);
                    int n = 0xFF & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16);
                    baKeyword[i] = (byte) n;
                } catch (Exception var5) {
                    var5.printStackTrace();
                }
            }

            try {
                s = new String(baKeyword);
            } catch (Exception var4) {
                var4.printStackTrace();
            }

            return s;
        }
    }

    public static byte[] StrToHex(String str) {
        if (str.length() % 2 != 0) {
            str = str + "F";
        }

        byte[] ret = new byte[str.length() / 2];
        StringBuffer buf = new StringBuffer(2);
        int j = 0;

        for (int i = 0; i < str.length(); j++) {
            buf.insert(0, str.charAt(i));
            buf.insert(1, str.charAt(i + 1));
            int t = Integer.parseInt(buf.toString(), 16);
            ret[j] = (byte) t;
            i++;
            buf.delete(0, 2);
            i++;
        }

        return ret;
    }

    public static String HexTostr(byte[] hex) {
        String str = "";
        if (hex.length == 0) {
            return str;
        } else {
            for (int i = 0; i < hex.length; i++) {
//                String buf = Integer.toString(hex[i] & 255, 16);
                String buf = Integer.toString(hex[i] & 0xFF, 16);
                if (buf.length() != 2) {
                    str = str + "0" + buf;
                } else {
                    str = str + buf;
                }
            }

            return str;
        }
    }

    public static String HexTostr(byte[] hex, int length) {
        String str = "";
        if (hex.length != 0 && length != 0) {
            for (int i = 0; i < length; i++) {
//                String buf = Integer.toString(hex[i] & 255, 16);
                String buf = Integer.toString(hex[i] & 0xFF, 16);
                if (buf.length() != 2) {
                    str = str + "0" + buf;
                } else {
                    str = str + buf;
                }
            }

            return str;
        } else {
            return str;
        }
    }

    public static byte[] AscciToHex(byte[] ascci, int length) {
        if (ascci.length != 0 && length != 0) {
            int len = Math.min(ascci.length, length);
            byte[] buf = new byte[len];
            System.arraycopy(ascci, 0, buf, 0, len);

            String strTmp;
            try {
                strTmp = new String(buf, 0, len, "utf-8");
            } catch (UnsupportedEncodingException var6) {
                var6.printStackTrace();
                return null;
            }

            return StrToHex(strTmp);
        } else {
            return null;
        }
    }

    public static byte[] HexToAscci(byte[] hex, int length) {
        if (hex.length != 0 && length != 0) {
            int len = Math.min(hex.length, length);
            byte[] buf = new byte[len];
            System.arraycopy(hex, 0, buf, 0, len);
            String strTmp = HexTostr(buf);
            return strTmp.getBytes();
        } else {
            return null;
        }
    }

    public static String PaddingHexintToString(int src, int totalsize) {
        String target = Integer.toString(src, 16);
        if (target.length() > totalsize) {
            return null;
        } else {
            while (target.length() < totalsize) {
                target = "0" + target;
            }

            return target;
        }
    }

    public static byte[] int2byte(int res) {
        byte[] targets = new byte[]{(byte) (res & 0xFF), (byte) (res >> 8 & 0xFF), (byte) (res >> 16 & 0xFF), (byte) (res >>> 24)};
        return targets;
    }

    public static int byte2int(byte[] res) {
        int targets = res[0] & 0xFF | res[1] << 8 & 0xFF00 | res[2] << 24 >>> 8 | res[3] << 24;
        return targets;
    }

    public static void Memcpy(byte[] output, byte[] input, int outpos, int inpos, int len) {
        for (int i = 0; i < len; i++) {
            output[outpos + i] = input[inpos + i];
        }

    }

    public static String FcopVersion() {
        return System.getProperty("microedition.io.file.FileConnection.version");
    }

    public static byte[] showFile(String fileName) {
        File file = new File(fileName);

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            StringBuffer sb = new StringBuffer();

/*
            String line = br.readLine();
            while (line != null)
            {
                sb.append(line);
                line = br.readLine();
            }
*/

            for (String line = br.readLine(); line != null; line = br.readLine()) {
                sb.append(line);
            }

            br.close();
            return sb.toString().getBytes();
        } catch (IOException var5) {
            var5.printStackTrace();
            return null;
        }
    }

    public static void modifyFile(String path, byte[] newData) {
        byte[] buf = showFile(path);
        int len = buf.length + newData.length;
        byte[] Data = new byte[len];
        Memcpy(Data, buf, 0, 0, buf.length);
        Memcpy(Data, newData, buf.length, 0, newData.length);
        DeleteFile(path);
        WriteFile(path, Data);
    }

    public static void DeleteFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            System.out.println("File Not Exist");
        } else {
            if (!file.delete()) {
                System.out.println("File Delete Fail");
            }

        }
    }

    public static void WriteFile(String path, byte[] fileData) {
        File file = new File(path);
        if (file.exists()) {
            DeleteFile(path);

            try {
                file.createNewFile();
            } catch (IOException var5) {
                var5.printStackTrace();
            }
        }

        try {
            OutputStream os = new FileOutputStream(file);
            os.write(fileData);
            os.close();
        } catch (IOException var4) {
            var4.printStackTrace();
        }

    }

    public static void Writelog(String str) {
        String a = str;
        OutputStream os = null;
        if (!filePath.exists()) {
            filePath.mkdir();
        }

        File file = new File(filePath, p);

        try {
            os = new FileOutputStream(file, true);
            os.write("\n".getBytes());
            os.write("\n".getBytes());
            os.write(a.getBytes());
            os.close();
        } catch (Exception var5) {
            var5.printStackTrace();
        }

    }

    public static void Writelog(byte[] data) {
        OutputStream os = null;
        if (!filePath.exists()) {
            filePath.mkdir();
        }

        File file = new File(filePath, p);

        try {
            os = new FileOutputStream(file, true);
            os.write("\n".getBytes());
            os.write("\n".getBytes());
            os.write(data);
            os.close();
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    public static void Modifylog(String str) {
        String path = "file:///e:/log/log.txt";
        byte[] buf = str.getBytes();
        modifyFile(path, buf);
    }

    public static void reverse(byte[] pbData) {
        int l = pbData.length / 2;
        int b = 0;

        for (int c = pbData.length - 1; b < l; c--) {
            byte temp = pbData[b];
            pbData[b] = pbData[c];
            pbData[c] = temp;
            b++;
        }

    }

/*
    public static String GetApduResponse(byte[] bData, int nOff, int nDatalen) {
        String strRetData = "";
        if (bData == null) {
            return strRetData;
        } else if (nOff >= 0 && nDatalen >= 0 && nOff + nDatalen <= bData.length) {
            int nRet = false;

            for(int i = 0; i < nDatalen; i++) {
                int nRet;
                if (bData[nOff + i] < 0) {
                    nRet = 256 + bData[nOff + i];
                } else {
                    nRet = bData[nOff + i];
                }

                if (nRet < 16) {
                    strRetData = strRetData + "0";
                }

                strRetData = strRetData + Integer.toHexString(nRet) + " ";
                if (i % 8 == 7) {
                    strRetData = strRetData.concat("\n");
                }
            }

            return strRetData;
        } else {
            return strRetData;
        }
    }
*/

    public static String GetApduResponse(byte[] bData, int nOff, int nDatalen) {
        String strRetData = "";
        if (bData == null) {
            return strRetData;
        }
        if ((nOff < 0) || (nDatalen < 0) || (nOff + nDatalen > bData.length)) {
            return strRetData;
        }
        int nRet = 0;
        for (int i = 0; i < nDatalen; i++) {
            if (bData[(nOff + i)] < 0) {
                nRet = 256 + bData[(nOff + i)];
            } else {
                nRet = bData[(nOff + i)];
            }
            if (nRet < 16) {
                strRetData = strRetData + "0";
            }
            strRetData = strRetData + Integer.toHexString(nRet) + " ";
            if (i % 8 == 7) {
                strRetData = strRetData.concat("\n");
            }
        }
        return strRetData;
    }


    public static int GetDeviceStateResponse(byte[] retData, int nretDatalen) {
        byte[] b = new byte[2];
        Memcpy(b, retData, 0, nretDatalen - 2, 2);
        String buf = ByteArrayToHex(b);
        if (nretDatalen == 128) {
            WriteLogHex("b", b);
        }

        int n = Integer.parseInt(buf, 16);
        return n;
    }

    public static String GetApudReturnValue(byte[] retData, int nOff, int nretDatalen) {
        byte[] b = new byte[nretDatalen - 2];
        Memcpy(b, retData, 0, nOff, nretDatalen - 2);
        String buf = ByteArrayToHex(b);
        return buf;
    }

    public static String TrimSpace(String strMsg) {
        String buf = strMsg.trim();
        String ret = "";

        for (int i = 0; i < buf.length(); i++) {
            if (buf.charAt(i) != ' ') {
                ret = ret + buf.charAt(i);
            }
        }

        return ret;
    }

    public static void saveFile(String path, byte[] fileData) {
        OutputStream os = null;
        File file = new File(path);
        if (file.exists()) {
            int size = (int) file.length();
            size += 20;
        } else {
            try {
                file.createNewFile();
            } catch (IOException var6) {
                var6.printStackTrace();
            }
        }

        try {
            os = new FileOutputStream(file);
            os.write(fileData);
            os.close();
        } catch (IOException var5) {
            var5.printStackTrace();
        }

    }

    public static void WriteLog(String strTag, byte[] fileData) {
        OutputStream os = null;
        if (!filePath.exists()) {
            filePath.mkdir();
        }

        File file = new File(filePath, p);
        String var4 = ByteArrayToHex(fileData);

        try {
            os = new FileOutputStream(file, true);
            os.write("\n".getBytes());
            os.write(strTag.getBytes());
            os.write(" ".getBytes());
            os.write(fileData);
            os.close();
        } catch (Exception var6) {
            var6.printStackTrace();
        }

    }

    public static void WriteLog(String strTag, String fileData) {
        OutputStream os = null;
        if (!filePath.exists()) {
            filePath.mkdir();
        }

        File file = new File(filePath, p);

        try {
            os = new FileOutputStream(file, true);
            os.write("\n".getBytes());
            os.write(strTag.getBytes());
            os.write(" ".getBytes());
            os.write(fileData.getBytes());
            os.close();
        } catch (Exception var5) {
            var5.printStackTrace();
        }

    }

/*
    public static String BinToHex(String Src, int SrcLen) {
        int result = 0;
        int k = 1;
        int j = false;
        String Des = "";
        SrcLen -= SrcLen % 4;

        for(int i = SrcLen - 1; i >= 0; --i) {
            if (Src.charAt(i) == '1') {
                result += 1 << k - 1;
            }

            if (k == 4 || i == 0) {
                switch(result) {
                    case 10:
                        Des = Des + "A";
                        break;
                    case 11:
                        Des = Des + "B";
                        break;
                    case 12:
                        Des = Des + "C";
                        break;
                    case 13:
                        Des = Des + "D";
                        break;
                    case 14:
                        Des = Des + "E";
                        break;
                    case 15:
                        Des = Des + "F";
                        break;
                    default:
                        Des = Des + (char)(result + 48);
                }

                result = 0;
                k = 0;
            }

            ++k;
        }

        return Des;
    }
*/

    public static String BinToHex(String Src, int SrcLen) {
        int result = 0;
        int k = 1;
        int j = 0;
        String Des = "";
        SrcLen -= SrcLen % 4;
        for (int i = SrcLen - 1; i >= 0; i--) {
            if (Src.charAt(i) == '1') {
                result += (1 << k - 1);
            }
            if ((k == 4) || (i == 0)) {
                switch (result) {
                    case 10:
                        Des = Des + "A";
                        break;
                    case 11:
                        Des = Des + "B";
                        break;
                    case 12:
                        Des = Des + "C";
                        break;
                    case 13:
                        Des = Des + "D";
                        break;
                    case 14:
                        Des = Des + "E";
                        break;
                    case 15:
                        Des = Des + "F";
                        break;
                    default:
                        Des = Des + (char) (result + 48);
                }
                result = 0;
                k = 0;
            }
            k++;
        }
        return Des;
    }


    public static void WriteLogHex(String fileName, byte[] fileData) {
        String a = ByteArrayToHex(fileData);
        OutputStream os = null;
        if (!filePath.exists()) {
            filePath.mkdir();
        }

        File file = new File(filePath, p);

        try {
            os = new FileOutputStream(file, true);
            os.write("\n".getBytes());
            os.write(fileName.getBytes());
            os.write("\n".getBytes());
            os.write(a.getBytes());
            os.close();
        } catch (Exception var6) {
            var6.printStackTrace();
        }

    }

    public static String tlvEncode(String tag, String value) {
        String sums = tag + HexTostr(Integer.toHexString(value.length() / 2)) + value;
        return sums;
    }

    public static String HexTostr(String buf) {
        String sb = "";
        if (buf.length() != 2) {
            sb = "0" + buf;
        } else {
            sb = buf;
        }

        return sb;
    }

    public static String tlvDecode(String msg, String tag) {
        String s = msg.substring(msg.indexOf(tag) + tag.length(), msg.indexOf(tag) + tag.length() + 2);
        int i = Integer.parseInt(s, 16);
        String zh = msg.substring(msg.indexOf(tag) + tag.length() + 2, msg.indexOf(tag) + tag.length() + 2 + i * 2);
        return zh;
    }

    public static byte[] createRandom(int len) {
        byte[] ret = new byte[len];
        SecureRandom sr = new SecureRandom();
        sr.nextBytes(ret);
        return ret;
    }

    public static byte[] short2byteBigendian(int res) {
        byte[] targets = new byte[]{(byte) (res >> 8 & 0xFF), (byte) (res & 0xFF)};
        return targets;
    }

    public static byte[] getBytes(char[] chars) {
        Charset cs = Charset.forName("UTF-8");
        CharBuffer cb = CharBuffer.allocate(chars.length);
        cb.put(chars);
        cb.flip();
        ByteBuffer bb = cs.encode(cb);
        return bb.array();
    }

    public static String utf82gbk(String utf) {
        String l_temp = utf8ToUnicode(utf);
        l_temp = Unicode2GBK(l_temp);
        return l_temp;
    }

    public static String utf8ToUnicode(String inStr) {
        char[] myBuffer = inStr.toCharArray();
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < inStr.length(); i++) {
            UnicodeBlock ub = UnicodeBlock.of(myBuffer[i]);
            if (ub == UnicodeBlock.BASIC_LATIN) {
                sb.append(myBuffer[i]);
            } else if (ub == UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
//                int j = myBuffer[i] - 'ﻠ';
                int j = myBuffer[i] - 65248;
                sb.append((char) j);
            } else {
                short s = (short) myBuffer[i];
                String hexS = Integer.toHexString(s);
                String unicode = "\\u" + hexS;
                sb.append(unicode.toLowerCase());
            }
        }

        return sb.toString();
    }

    public static String Unicode2GBK(String dataStr) {
        int index = 0;
        StringBuffer buffer = new StringBuffer();
        int li_len = dataStr.length();

        while (true) {
            while (index < li_len) {
                if (index < li_len - 1 && "\\u".equals(dataStr.substring(index, index + 2))) {
                    String charStr = "";
                    charStr = dataStr.substring(index + 2, index + 6);
                    char letter = (char) Integer.parseInt(charStr, 16);
                    buffer.append(letter);
                    index += 6;
                } else {
                    buffer.append(dataStr.charAt(index));
                    index++;
                }
            }

            return buffer.toString();
        }
    }

    public static String readFileByLines(String fileName, String sFindStr) {
        File file = new File(fileName);
        BufferedReader reader = null;
        String deString = "";

        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;

            int line = 1;
            while ((tempString = reader.readLine()) != null) {
                if (tempString.indexOf(sFindStr) != -1) {
                    int i = tempString.indexOf("=");
                    deString = tempString.substring(i + 1, tempString.length());
                    break;
                }
                line++;
            }

/*
            for(int var6 = 1; (tempString = reader.readLine()) != null; ++var6) {
                if (tempString.indexOf(sFindStr) != -1) {
                    int i = tempString.indexOf("=");
                    deString = tempString.substring(i + 1, tempString.length());
                    break;
                }
            }
*/

            reader.close();
        } catch (IOException var16) {
            var16.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException var15) {
                    var15.printStackTrace();
                }
            }

        }

        return deString;
    }

    public static boolean writeFileByLines(String fileName, String sFindStr, String sTeshu, String desStr) {
        File file = new File(fileName);
        BufferedReader reader = null;
        String deString = "";
        String SumString = "";

        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int var9 = 1;
            int line = 1;
            while ((tempString = reader.readLine()) != null) {
                if (tempString.indexOf(sFindStr) != -1) {
                    int i = tempString.indexOf(sTeshu);
                    deString = tempString.substring(i + 1, tempString.length());
                    String desString = tempString.replace(deString, desStr);
                    SumString = SumString + desString + "\n";
                } else {
                    SumString = SumString + tempString + "\n";
                    ++var9;
                    line++;
                }
            }

            reader.close();
            FileWriter desWriter = new FileWriter(file);
            desWriter.write(SumString);
            desWriter.close();
        } catch (IOException var20) {
            var20.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException var19) {
                    var19.printStackTrace();
                }
            }

        }

        return true;
    }

    public static String HL_Exchange(String strData) {
        byte[] temp = StrToHex(strData.toUpperCase());

        for (int i = 0; i < temp.length; i++) {
            temp[i] = HL_reverse(temp[i]);
        }

        String strTemp = ByteArrayToHex(temp);
        return strTemp;
    }

    public static byte HL_reverse(byte pbData) {
/*
        int bh = pbData << 4 & 240;
        int bl = pbData >> 4 & 15;
*/

        int bh = pbData << 4 & 0xF0;
        int bl = pbData >> 4 & 0xF;

        byte temp = (byte) (bh + bl);
        return temp;
    }

    public static String getFile(String fileName) {
        String res = "";

        try {
            FileInputStream in = new FileInputStream(fileName);
            int length = in.available();
            byte[] buffer = new byte[length];
            in.read(buffer);
            res = new String(buffer, "UTF-8");
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return res;
    }

    static {
        filePath = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "log");
        p = "android.txt";
    }
}
