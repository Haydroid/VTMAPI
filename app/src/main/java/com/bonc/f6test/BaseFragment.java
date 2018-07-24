package com.bonc.f6test;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.bonc.R;
import com.bonc.vtm.hardware.card.CardDriver;

import java.util.ArrayList;
import java.util.List;

public class BaseFragment extends Fragment {

    View mView;
    //LED
    private List<String> Ledlist = new ArrayList<String>();
    private ArrayAdapter<String> Ledadapter;
    private Spinner LedControl;
    //Rest
    private List<String> Restlist = new ArrayList<String>();
    private ArrayAdapter<String> Restadapter;
    private Spinner spRest;

    Context mContext;

    public BaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_base, null);
        //LedControl =  (Spinner) mView.findViewById(R.id.LEDType);
        spRest = (Spinner) mView.findViewById(R.id.RestType);
        initiSpinner();

        //复位
        final Button btnResetEject = (Button) mView.findViewById(R.id.btnResetEject);
        btnResetEject.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    int i = spRest.getSelectedItemPosition();
                    String fwver;
                    switch (i) {
                        case 0:
                            fwver = Application.cr.reset((byte) Application.instructions.getCardResetNoAction());
                            Application.showInfo(fwver, "复位无动作", mView.getContext());
                            break;
                        case 1:
                            fwver = Application.cr.reset((byte) Application.instructions.getCardResetFrontOut());
                            Application.showInfo(fwver, "复位移动持卡位 ：", mView.getContext());
                            break;
                        case 2:
                            fwver = Application.cr.reset((byte) Application.instructions.getCardResetReturnBox());
                            Application.showInfo(fwver, "复位回收 ：", mView.getContext());
                            break;
                    }
                } catch (Exception e) {
                    Application.showError(e.getMessage(), mView.getContext());
                }
            }
        });

        //查询卡机状态
        final Button btnCardPos = (Button) mView.findViewById(R.id.btnCardPos);
        btnCardPos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
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
                    /*
                     */
                    Application.showInfo(spos, "卡片位置", mView.getContext());
                } catch (Exception e) {
                    Application.showError(e.getMessage(), mView.getContext());
                }
            }
        });

        //允许前端进卡
        final Button btnPermitIns = (Button) mView.findViewById(R.id.btnPermitIns);
        btnPermitIns.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    byte reData[] = new byte[32];
                    String nStatus = "";
                    Application.cr.controlInsertion((byte) Application.instructions.getInsertionYes());
                    Application.showInfo(nStatus, "允许前端进卡", mView.getContext());
                } catch (Exception e) {
                    Application.showError(e.getMessage(), mView.getContext());
                }
            }
        });
        //回收卡
        final Button btnPermitInsmag = (Button) mView.findViewById(R.id.btnpermitin);
        btnPermitInsmag.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Application.cr.moveCard((byte) Application.instructions.getCardMoveBox());
                    Application.showInfo("操作成功!!", "回收", mView.getContext());
                } catch (Exception e) {
                    Application.showError(e.getMessage(), mView.getContext());
                }
            }
        });

        //禁止进卡
        final Button btnDenieIns = (Button) mView.findViewById(R.id.btnDenieIns);
        btnDenieIns.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Application.cr.controlInsertion((byte) Application.instructions.getInsertionNo());
//                    Application.cr.prohibitInsertion();
                    Application.showInfo("操作成功!!", "禁止进卡", mView.getContext());
                } catch (Exception e) {
                    Application.showError(e.getMessage(), mView.getContext());
                }
            }
        });

        //移动到读卡位
        final Button btnMoveToReadPos = (Button) mView.findViewById(R.id.btnMoveToReadPos);
        btnMoveToReadPos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Application.cr.moveCard((byte) Application.instructions.getCardMoveRead());
                    Application.showInfo("操作成功!!", "移动到射频位", mView.getContext());
                } catch (Exception e) {
                    Application.showError(e.getMessage(), mView.getContext());
                }
            }
        });

        //移动到IC卡位
        final Button btnMoveToICCPos = (Button) mView.findViewById(R.id.btnMoveToIC);
        btnMoveToICCPos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Application.cr.moveCard((byte) Application.instructions.getCardMoveIc());
                    Application.showInfo("操作成功!!", "移动到IC卡位", mView.getContext());
                } catch (Exception e) {
                    Application.showError(e.getMessage(), mView.getContext());
                }
            }
        });


        //从前端弹卡
        final Button btnOpenKey = (Button) mView.findViewById(R.id.btnCardOut);
        btnOpenKey.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Application.cr.moveCard((byte) Application.instructions.getCardMoveEject());
                    Application.showInfo("操作成功!!", "从前端弹卡", mView.getContext());
                } catch (Exception e) {
                    Application.showError(e.getMessage(), mView.getContext());
                }
            }
        });

        //移动卡到前端持卡位
        final Button btnCloseKey = (Button) mView.findViewById(R.id.btnMoveToFront);
        btnCloseKey.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Application.cr.moveCard((byte) Application.instructions.getCardMoveOut());
                    Application.showInfo("操作成功!!", "移动卡到前端持卡位", mView.getContext());
                } catch (Exception e) {
                    Application.showError(e.getMessage(), mView.getContext());
                }
                btnOpenKey.setEnabled(true);
            }
        });

        return mView;
    }


    public void initiSpinner() {
        Restlist.clear();
        Restlist.add("复位，不移动卡");
        Restlist.add("复位，移动到持卡位");
        Restlist.add("复位，回收");
        Restadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1, Restlist);
        Restadapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        spRest.setAdapter(Restadapter);
    }

}
