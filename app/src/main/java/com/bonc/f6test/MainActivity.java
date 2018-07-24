package com.bonc.f6test;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bonc.R;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    FragmentTabHost mTabHost = null;
    int iditem;

    Button openPort;
    Button closePort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Application.mainView = this;
        Application.CTX = this;
        initTabHost();
        setStatus(false);
        openPort = findViewById(R.id.openPort);
        closePort = findViewById(R.id.closePort);
        openPort.setOnClickListener(this);
        closePort.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        iditem = item.getItemId();
        int lRet;
        String strMsg = new String();
        switch (iditem) {
            case R.id.connect:
                ConnDialog dlg = new ConnDialog(this);
                dlg.setCloseListener(new ConnDialog.OnCloseListener() {
                    @Override
                    public void onClose(boolean isOk) {
                        if (isOk)
                            setStatus(true);
                    }
                });
                dlg.show();
                break;
            case R.id.disconnect:
                try {
                    if (Application.testThread != null) {
                        if (Application.testThread.isAlive()) {
                            Application.showError("正在测试中。请停止测试，然后再断开连接。", MainActivity.this);
                            return super.onOptionsItemSelected(item);
                        }
                    }
                    Application.cr.disconnect();
                    strMsg = "连接断开成功!!";
                    setStatus(false);
                    Toast.makeText(getApplication(), strMsg,
                            Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    Application.showError(e.getMessage(), MainActivity.this);
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initTabHost() {
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        Resources resources = getResources();
        addTab(mTabHost, resources.getString(R.string.base), BaseFragment.class);
        addTab(mTabHost, resources.getString(R.string.cpu), CpuFragment.class);
        addTab(mTabHost, resources.getString(R.string.mag), MagFragment.class);
        addTab(mTabHost, resources.getString(R.string.test), TestFragment.class);
    }

    private void addTab(FragmentTabHost tabHost, String tag, Class<?> clss) {
        FragmentTabHost.TabSpec tabSpec = tabHost.newTabSpec(tag);
        tabSpec.setIndicator(tag);
        tabHost.addTab(tabSpec, clss, null);
    }

    private void setStatus(boolean isConnected) {
        String title = getString(R.string.app_name);
        String status = "";
        if (isConnected) {
            if (iditem == R.id.connect) status = "当前连接方式：串口";
            title += "[" + status + "]";
        }
        setTitle(title);
    }

    @Override
    public void finish() {
        if (Application.testThread != null) {
            if (Application.testThread.isAlive()) {
                Application.testThread.interrupt();
                Application.testThread = null;
            }
        }

        try {
            Application.cr.disconnect();
        } catch (Exception ex) {

        }

        super.finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.openPort:
                ConnDialog dlg = new ConnDialog(this);
                dlg.setCloseListener(new ConnDialog.OnCloseListener() {
                    @Override
                    public void onClose(boolean isOk) {
                        if (isOk)
                            setStatus(true);
                    }
                });
                dlg.show();
                break;
            case R.id.closePort:
                String strMsg;
                try {
                    if (Application.testThread != null) {
                        if (Application.testThread.isAlive()) {
                            Application.showError("正在测试中。请停止测试，然后再断开连接。", MainActivity.this);
                        }
                    }
                    Application.cr.disconnect();
                    strMsg = "连接断开成功!!";
                    setStatus(false);
                    Toast.makeText(getApplication(), strMsg,
                            Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    Application.showError(e.getMessage(), MainActivity.this);
                }
                break;
        }
    }
}
