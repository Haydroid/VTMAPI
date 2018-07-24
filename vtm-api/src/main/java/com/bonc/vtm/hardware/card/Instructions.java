package com.bonc.vtm.hardware.card;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.bonc.vtm.Constants.CARD_30;
import static com.bonc.vtm.Constants.CARD_31;
import static com.bonc.vtm.Constants.CARD_32;
import static com.bonc.vtm.Constants.CARD_33;
import static com.bonc.vtm.Constants.CARD_39;

/**
 * @Author : YangWei
 * @Date : 2018/6/30 下午7:35
 * @Description : F3-1300 指令集
 */

public class Instructions {

    private static Instructions instance;

    public Instructions(int cardMoveOut, int cardMoveIc, int cardMoveRead, int cardMoveBox, int cardMoveEject, int cardResetFrontOut, int cardResetReturnBox, int cardResetNoAction, int insertionYes, int insertionNo) {
        this.cardMoveOut = cardMoveOut;
        this.cardMoveIc = cardMoveIc;
        this.cardMoveRead = cardMoveRead;
        this.cardMoveBox = cardMoveBox;
        this.cardMoveEject = cardMoveEject;
        this.cardResetFrontOut = cardResetFrontOut;
        this.cardResetReturnBox = cardResetReturnBox;
        this.cardResetNoAction = cardResetNoAction;
        this.insertionYes = insertionYes;
        this.insertionNo = insertionNo;
    }

    public static Instructions getInstance() {
        if (instance == null) {
            instance = new Instructions(CARD_30, CARD_31, CARD_32, CARD_33, CARD_39, CARD_30, CARD_31, CARD_33, CARD_30, CARD_31);
        }
        return instance;
    }

    @IntDef({CARD_30, CARD_31, CARD_32, CARD_33, CARD_39})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LimitCmdType {
    }

    @LimitCmdType
    int cardMoveOut;//卡移动到前端持卡位

    @LimitCmdType
    int cardMoveIc;//移动到IC卡位

    @LimitCmdType
    int cardMoveRead;//移动到射频位(读卡位)

    @LimitCmdType
    int cardMoveBox;//卡回收

    @LimitCmdType
    int cardMoveEject;//前端弹出卡片

    @LimitCmdType
    int cardResetFrontOut;//复位移动持卡位

    @LimitCmdType
    int cardResetReturnBox;//复位回收

    @LimitCmdType
    int cardResetNoAction;//复位无动作

    @LimitCmdType
    int insertionYes;//允许出卡口进卡

    @LimitCmdType
    int insertionNo;//禁止出卡口进卡

    @LimitCmdType
    public int getCardMoveOut() {
        return cardMoveOut;
    }

    public void setCardMoveOut(@LimitCmdType int cardMoveOut) {
        this.cardMoveOut = cardMoveOut;
    }

    @LimitCmdType
    public int getCardMoveIc() {
        return cardMoveIc;
    }

    public void setCardMoveIc(@LimitCmdType int cardMoveIc) {
        this.cardMoveIc = cardMoveIc;
    }

    @LimitCmdType
    public int getCardMoveRead() {
        return cardMoveRead;
    }

    public void setCardMoveRead(@LimitCmdType int cardMoveRead) {
        this.cardMoveRead = cardMoveRead;
    }

    @LimitCmdType
    public int getCardMoveBox() {
        return cardMoveBox;
    }

    public void setCardMoveBox(@LimitCmdType int cardMoveBox) {
        this.cardMoveBox = cardMoveBox;
    }

    @LimitCmdType
    public int getCardMoveEject() {
        return cardMoveEject;
    }

    public void setCardMoveEject(@LimitCmdType int cardMoveEject) {
        this.cardMoveEject = cardMoveEject;
    }

    @LimitCmdType
    public int getCardResetFrontOut() {
        return cardResetFrontOut;
    }

    public void setCardResetFrontOut(@LimitCmdType int cardResetFrontOut) {
        this.cardResetFrontOut = cardResetFrontOut;
    }

    @LimitCmdType
    public int getCardResetReturnBox() {
        return cardResetReturnBox;
    }

    public void setCardResetReturnBox(@LimitCmdType int cardResetReturnBox) {
        this.cardResetReturnBox = cardResetReturnBox;
    }

    @LimitCmdType
    public int getCardResetNoAction() {
        return cardResetNoAction;
    }

    public void setCardResetNoAction(@LimitCmdType int cardResetNoAction) {
        this.cardResetNoAction = cardResetNoAction;
    }

    @LimitCmdType
    public int getInsertionYes() {
        return insertionYes;
    }

    public void setInsertionYes(@LimitCmdType int insertionYes) {
        this.insertionYes = insertionYes;
    }

    @LimitCmdType
    public int getInsertionNo() {
        return insertionNo;
    }

    public void setInsertionNo(@LimitCmdType int insertionNo) {
        this.insertionNo = insertionNo;
    }
}
