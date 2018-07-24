package com.hengbao.rps.writecard;

import android.content.Context;

/**
 * @Author : YangWei
 * @Date : 2018/6/30
 * @Description : 写卡接口
 */

public interface WriteCardInterface {
    String writeCardBusiness(String var1, Context var2);

    String readCardApduBusiness(String var1);
}
