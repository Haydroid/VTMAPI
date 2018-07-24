package com.bonc.vtm.utils;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

/**
 * @Author : YangWei
 * @Date : 2018/6/30
 * @Description : ObjStorage
 */

public class ObjStorage {
    private SharedPreferences m_sharedPreferences;

    public ObjStorage(SharedPreferences sharedPreferences) {
        m_sharedPreferences = sharedPreferences;
    }

    /**
     * 从数据库里保存数据，并加密
     *
     * @param key 关键字
     * @param obj 保护的内容
     */
    public void save(String key, Object obj) {
        if (obj == null || key == null)
            return;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos;
        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Editor editor = m_sharedPreferences.edit();
        editor.putString(key, Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT));
        editor.commit();
    }

    /**
     * 从数据库取出数据，并解密。
     *
     * @param key        关键词
     * @param defaultObj 如果为空，默认返回的数据。
     */
    public Object load(String key, Object defaultObj) {
        String wordBase64 = m_sharedPreferences.getString(key, null);
        if (wordBase64 == null)
            return defaultObj;
        byte[] base64Bytes = Base64.decode(wordBase64.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
        ObjectInputStream ois = null;
        Object ret = null;
        try {
            ois = new ObjectInputStream(bais);
            ret = ois.readObject();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return ret;
    }

    /**
     * 删除一行数据。
     *
     * @param key
     * @throws
     * @Title remove 移除某一行。
     */
    public void remove(String key) {
        Editor editor = m_sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }

    /**
     * 保存数组字符串，自定义的方法
     *
     * @param key  关键字
     * @param strs 数组字符串
     * @Title saveArrayString 保存数组字符串。
     */
    public void saveArrayString(String key, String[] strs) {
        Editor editor = m_sharedPreferences.edit();
        String str = "";
        for (String s : strs) {
            str += s + ";";
        }
        editor.putString(key, str);
        editor.commit();
    }

    /**
     * 取出以字符串形式保存到share的数组
     *
     * @param key      关键字
     * @param defValue 默认数组
     * @return 字符数组
     * @Title getString 获取字符数组
     */
    public String[] getStringArray(String key, String defValue) {
        String str = m_sharedPreferences.getString(key, defValue);
        String[] strs = str.split(";");
        return strs;
    }

    public String[] getStringArray(String key, String[] defValue) {
        String str = m_sharedPreferences.getString(key, "");
        if (str != null && !str.equals("")) {
            return str.split(";");
        } else {
            return defValue;
        }
    }
}
