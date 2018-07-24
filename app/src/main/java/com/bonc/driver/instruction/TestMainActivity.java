package com.bonc.driver.instruction;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bonc.R;
import com.bonc.f6test.MainActivity;

/**
 * @Author : YangWei
 * @Date : 2018/6/30
 * @Description : TestMainActivity
 */

public class TestMainActivity extends AppCompatActivity implements View.OnClickListener {

    Button test_main_button_1;
    Button test_main_button_2;
    Button test_main_button_3;
    Button test_main_button_4;
    Button test_main_button_5;
    Button test_main_button_6;
    Button test_main_button_7;
    TextView request_result_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);
        initView();
    }

    private void initView() {
        test_main_button_1 = findViewById(R.id.test_main_button_1);
        test_main_button_1.setOnClickListener(this);
        test_main_button_2 = findViewById(R.id.test_main_button_2);
        test_main_button_2.setOnClickListener(this);
        test_main_button_3 = findViewById(R.id.test_main_button_3);
        test_main_button_3.setOnClickListener(this);
        test_main_button_4 = findViewById(R.id.test_main_button_4);
        test_main_button_4.setOnClickListener(this);
        test_main_button_5 = findViewById(R.id.test_main_button_5);
        test_main_button_5.setOnClickListener(this);
        test_main_button_6 = findViewById(R.id.test_main_button_6);
        test_main_button_6.setOnClickListener(this);
        test_main_button_7 = findViewById(R.id.test_main_button_7);
        test_main_button_7.setOnClickListener(this);
        request_result_tv = findViewById(R.id.request_result_tv);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.test_main_button_1:
                Intent intent1 = new Intent(TestMainActivity.this, CardInstructionActivity.class);
                startActivity(intent1);
                break;

            case R.id.test_main_button_2:
                Intent intent2 = new Intent(TestMainActivity.this, ZwInstructionActivity.class);
                startActivity(intent2);
                break;

            case R.id.test_main_button_3:
                Intent intent3 = new Intent(TestMainActivity.this, IndicatorLampActivity.class);
                startActivity(intent3);
                break;

            case R.id.test_main_button_4:
                Intent intent4 = new Intent(TestMainActivity.this, MainActivity.class);
                startActivity(intent4);
                break;

            case R.id.test_main_button_5:
                Intent intent5 = new Intent(TestMainActivity.this, WriteCardActivity.class);
                startActivity(intent5);
                break;

            case R.id.test_main_button_6:
                Intent intent6 = new Intent(TestMainActivity.this, WriteCardActivity.class);
                startActivity(intent6);
                break;

            case R.id.test_main_button_7:
                Intent intent7 = new Intent(TestMainActivity.this, WriteCardActivity.class);
                startActivity(intent7);
                break;

            default:
                break;
        }
    }
}
