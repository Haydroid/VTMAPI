package com.hengbao.rps;

import com.hengbao.rps.entity.CommonRspInfo;
import com.hengbao.rps.exception.ErrorsEnum;
import com.hengbao.rps.utils.StringUtil;

/**
 * @Author : YangWei
 * @Date : 2018/6/29
 * @Description : BaseBusiness
 */

public abstract class BaseBusiness {
    public BaseBusiness() {
    }

    protected void packRspInfo(CommonRspInfo rsp, String code, String msg) {
        if (StringUtil.isBlank(code)) {
            rsp.setRetCode(ErrorsEnum.C0000.getCode());
            rsp.setRetMsg(ErrorsEnum.C0000.getMessage());
        } else {
            rsp.setRetCode(code);
            rsp.setRetMsg(msg);
        }

    }
}
