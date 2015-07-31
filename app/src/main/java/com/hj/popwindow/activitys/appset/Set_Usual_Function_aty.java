package com.hj.popwindow.activitys.appset;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.hj.popwindow.Config;
import com.hj.popwindow.R;
import com.hj.popwindow.adapter.MyUsualFunctionAdapter;
import com.hj.popwindow.database.data.DateUtils;
import com.hj.popwindow.datatype.FunctionInfo;
import com.hj.popwindow.function.SetBlueTooth;
import com.hj.popwindow.function.SetFlashLight;
import com.hj.popwindow.function.SetFlightMode;
import com.hj.popwindow.function.SetGprs;
import com.hj.popwindow.function.SetHotSpot;
import com.hj.popwindow.function.SetScreenShot;
import com.hj.popwindow.function.SetSilent;
import com.hj.popwindow.function.SetWiFI;
import com.hj.popwindow.view.MyGridView;

import java.util.ArrayList;
import java.util.List;

public class Set_Usual_Function_aty
        extends ActionBarActivity {

    private Button wifi_bt, gprs_bt, lanya_bt, jieping_bt, shoudian_bt, xitong_bt, xuanfu_bt,
            redian_bt, feixing_bt, jinying_bt, gps_bt;
    private FunctionBroadCast functionBroadCast;

    //功能实体类
    private SetWiFI setWiFI;
    private SetGprs setGprs;
    private SetBlueTooth setBlueTooth;
    private SetScreenShot setScreenShot;
    private SetFlashLight setFlashLight;
    private SetHotSpot setHotSpot;
    private SetFlightMode setFlightMode;
    private SetSilent setSilent;


    private MyGridView usual_function_gridveiw;  // 常用功能选择gridview
    private List<FunctionInfo> functionInfoList;  // 常用功能链表数据
    private MyUsualFunctionAdapter myUsualFunctionAdapter;  //  常用功能适配器

    private SharedPreferences sharedPreferences;  // 获取记录是否被点击
    private SharedPreferences.Editor editor;

    private AlertDialog shortcut_select_dialog;  // 弹出框快捷方式选择
    private MyGridView shortcut_select_gridview;
    private MyUsualFunctionAdapter shortcut_select_adapter;
    private List<FunctionInfo> shortcut_select_list;
    private View shortcut_select_view;  // 选择功能弹出框视图
    private int selectShortCutPosition = 0;  // 判断点击的是第几个快捷键

    private String functionstr = "function_";

    private FunctionInfo default_functionInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set__usual__function_aty);

        init();

        setBtListener();

        default_functionInfo =
                new FunctionInfo(-1, "", getResources().getDrawable(R.drawable.add_item));

//        //初始化常用功能，当数据库中为空，则初始化四个功能进入面板
//        if (DateUtils.getFunctions(Set_Usual_Function_aty.this).size() == 0) {
//
//            for (int i = 0; i < 4; i++) {
//                DateUtils.setFunction(Set_Usual_Function_aty.this, i + 1, i);
//            }
//        }

        initShortCut();

        usual_function_gridveiw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                setDialog();
                selectShortCutPosition = position;
            }
        });

        shortcut_select_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                editor.putInt(functionstr + selectShortCutPosition,
//                        shortcut_select_list.get(position).getId());
//                editor.commit();

                DateUtils.setFunction(Set_Usual_Function_aty.this, selectShortCutPosition + 1,
                        shortcut_select_list.get(position).getId());

                setFunctionDatas();

                shortcut_select_dialog.cancel();
            }
        });
    }

    /**
     * 初始化快捷功能
     */
    private void initShortCut() {

        usual_function_gridveiw = (MyGridView) findViewById(R.id.usual_function_gridveiw);
        functionInfoList = new ArrayList<>();
        myUsualFunctionAdapter =
                new MyUsualFunctionAdapter(Set_Usual_Function_aty.this, functionInfoList,
                        new MyUsualFunctionAdapter.Position() {
                            @Override
                            public void onPosition(int position) {

                                Toast.makeText(Set_Usual_Function_aty.this, position + "",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }, false);

        sharedPreferences = getSharedPreferences(Config.SHORTCUT_CONFIG, Activity.MODE_PRIVATE);
        editor = sharedPreferences.edit();


        usual_function_gridveiw.setAdapter(myUsualFunctionAdapter);


        setFunctionDatas();   // 设置更新数据

        // 弹出框部分

        shortcut_select_dialog = new AlertDialog.Builder(Set_Usual_Function_aty.this).create();

        shortcut_select_view =
                getLayoutInflater().inflate(R.layout.dialogview_shortcut_layout, null);

        shortcut_select_gridview =
                (MyGridView) shortcut_select_view.findViewById(R.id.app_info_gridveiw_dialog);


        shortcut_select_list = new ArrayList<>();

        shortcut_select_adapter =
                new MyUsualFunctionAdapter(Set_Usual_Function_aty.this, shortcut_select_list, null,
                        true);

        shortcut_select_gridview.setAdapter(shortcut_select_adapter);
    }

    /**
     * 弹出选择常用功能对话框
     */
    private void setDialog() {

        setShortCutSelectDatas();

        shortcut_select_dialog.setView(shortcut_select_view);

        shortcut_select_dialog.setTitle(getResources().getString(R.string.please_select_shortcut));

        shortcut_select_dialog.show();
    }

    /**
     * 设置弹出框中常用快捷键数据
     */
    private void setShortCutSelectDatas() {

        if (shortcut_select_list.size() != 0) {
            shortcut_select_list.clear();
        }

//        String[] shortcut_values = getResources().getStringArray(R.array.shortcut_values);

        shortcut_select_list = DateUtils.getAllFuns(Set_Usual_Function_aty.this);


        for (int i = 0; i < shortcut_select_list.size(); i++) { // 过滤重复项

            for (int j = 0; j < functionInfoList.size(); j++) {
                if (shortcut_select_list.get(i).getId()==functionInfoList.get(j).getId()) {
                    shortcut_select_list.remove(i);
                }
            }
        }

//        for (int i = 1; i <= shortcut_values.length; i++) {
//
//            String name = shortcut_values[i - 1];
//
//            FunctionInfo functionInfo = new FunctionInfo(i, name);
//
//            //避免重复显示
//            int j;
//            for (j = 0; j < functionInfoList.size(); j++) {
//                if (functionInfoList.get(j).equals(functionInfo)) {
//                    break;
//                }
//            }
//
//            if (j == (functionInfoList.size())) {
//                shortcut_select_list.add(functionInfo);
//            }
//        }

        shortcut_select_adapter.NotifyDataSetChanged(shortcut_select_list);
    }

    /**
     * 为常用功能链表添加数据
     */
    private void setFunctionDatas() {

        if (functionInfoList.size() != 0) {
            functionInfoList.clear();
        }

//        String[] shortcut_values = getResources().getStringArray(R.array.shortcut_values);
//
//        for (int i = 1; i < 4; i++) {
//
//            int id = sharedPreferences.getInt(functionstr + i, i);
//            String name = shortcut_values[id - 1];
//
//            FunctionInfo functionInfo = new FunctionInfo(id, name);
//            functionInfoList.add(functionInfo);
//        }

        functionInfoList = DateUtils.getFunctions(Set_Usual_Function_aty.this);

        if (functionInfoList.size() < 4) {
            functionInfoList.add(default_functionInfo);
        }

        myUsualFunctionAdapter.NotifyDataSetChanged(functionInfoList);
    }


    /**
     * 初始化变量
     */
    private void init() {

        wifi_bt = (Button) findViewById(R.id.wifi_bt);
        gprs_bt = (Button) findViewById(R.id.gprs_bt);
        lanya_bt = (Button) findViewById(R.id.lanya_bt);
        jieping_bt = (Button) findViewById(R.id.jieping_bt);
        shoudian_bt = (Button) findViewById(R.id.shoudian_bt);
        xitong_bt = (Button) findViewById(R.id.xitong_bt);
        xuanfu_bt = (Button) findViewById(R.id.xuanfu_bt);
        redian_bt = (Button) findViewById(R.id.redian_bt);
        feixing_bt = (Button) findViewById(R.id.feixing_bt);
        jinying_bt = (Button) findViewById(R.id.jinying_bt);
        gps_bt = (Button) findViewById(R.id.gps_bt);


//        setWiFI = new SetWiFI(Set_Usual_Function_aty.this);  // wifi开关类
//        setGprs = new SetGprs(Set_Usual_Function_aty.this);  // 数据流量开关
//        setFlashLight = new SetFlashLight();   // 手电筒开关类
//        setSilent = new SetSilent(Set_Usual_Function_aty.this);
//
//
//        setBlueTooth = new SetBlueTooth(new SetBlueTooth.SuccessEnable() {
//            @Override
//            public void onSuccess(String state) {
//
//                Toast.makeText(Set_Usual_Function_aty.this, state, Toast.LENGTH_SHORT).show();
//            }
//        }, new SetBlueTooth.FailEnable() {
//            @Override
//            public void onFail(String err) {
//
//                Toast.makeText(Set_Usual_Function_aty.this, err, Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//        setScreenShot =
//                new SetScreenShot(Set_Usual_Function_aty.this, new SetScreenShot.SuccessEnable() {
//                    @Override
//                    public void onSuccess(final String state) {
//
//                        Toast.makeText(Set_Usual_Function_aty.this, state, Toast.LENGTH_SHORT)
//                                .show();
//                    }
//                }, new SetScreenShot.FailEnable() {
//                    @Override
//                    public void onFail(final String err) {
//
//                        Toast.makeText(Set_Usual_Function_aty.this, err, Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//
//        setHotSpot = new SetHotSpot(Set_Usual_Function_aty.this, new SetHotSpot.SuccessEnable() {
//            @Override
//            public void onSuccess(String state) {
//
//                Toast.makeText(Set_Usual_Function_aty.this, state, Toast.LENGTH_SHORT).show();
//            }
//        }, new SetHotSpot.FailEnable() {
//            @Override
//            public void onFail(String err) {
//
//                Toast.makeText(Set_Usual_Function_aty.this, err, Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        setFlightMode = new SetFlightMode(Set_Usual_Function_aty.this,
//                new SetFlightMode.SuccessEnable() {
//                    @Override
//                    public void onSuccess(final String state) {
//
//                        Toast.makeText(Set_Usual_Function_aty.this, state,
//                                Toast.LENGTH_SHORT).show();
//
//                    }
//                }, new SetFlightMode.FailEnable() {
//            @Override
//            public void onFail(final String err) {
//
//                Toast.makeText(Set_Usual_Function_aty.this, err, Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    /**
     * 设置监听
     */
    private void setBtListener() {


//        wifi_bt.setOnClickListener(new View.OnClickListener() {  // wifi
//            @Override
//            public void onClick(View v) {
//
//                setWiFI.setWifi();
//            }
//        });
//
//
//        gprs_bt.setOnClickListener(new View.OnClickListener() {  //数据流量
//            @Override
//            public void onClick(View v) {
//
//
//                boolean isopen = setGprs.gprsIsOpenMethod();
//
//                setGprs.Open_And_Close_Gprs(isopen);
//
//                if (isopen) {
//
//                    gprs_bt.setText(getString(R.string.open_gprs));
//                } else {
//
//                    gprs_bt.setText(getString(R.string.close_gprs));
//                }
//            }
//        });
//
//        lanya_bt.setOnClickListener(new View.OnClickListener() {   //蓝牙
//            @Override
//            public void onClick(View v) {
//
//                setBlueTooth.setBlueToothCloseAOpen();
//            }
//        });
//
//        jieping_bt.setOnClickListener(new View.OnClickListener() {  //截屏
//            @Override
//            public void onClick(View v) {
//
//                setScreenShot.setScreenShot("/sdcard/screenaaa.jpg");
//
//            }
//        });
//
//        shoudian_bt.setOnClickListener(new View.OnClickListener() {  //手电筒
//            @Override
//            public void onClick(View v) {
//
//                setFlashLight.setLight();
//
//            }
//        });
//
//        xitong_bt.setOnClickListener(new View.OnClickListener() {  //系统设置
//            @Override
//            public void onClick(View v) {
//
//                startActivity(new Intent(Settings.ACTION_SETTINGS));
//            }
//        });
//
//        xuanfu_bt.setOnClickListener(new View.OnClickListener() {  //悬浮窗设置
//            @Override
//            public void onClick(View v) {
//
//
//            }
//        });
//
//        redian_bt.setOnClickListener(new View.OnClickListener() {  //个人热点
//            @Override
//            public void onClick(View v) {
//
//                setHotSpot.setHot();
//            }
//        });
//
//        feixing_bt.setOnClickListener(new View.OnClickListener() {  //飞行模式
//            @Override
//            public void onClick(View v) {
//
//
//                setFlightMode.setAirplaneModeOn();
//            }
//        });
//        jinying_bt.setOnClickListener(new View.OnClickListener() {  //静音
//            @Override
//            public void onClick(View v) {
//
//                setSilent.setRingMode();
//            }
//        });
//
//        gps_bt.setOnClickListener(new View.OnClickListener() {  //gps设置
//            @Override
//            public void onClick(View v) {
//
//                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
//
//
//            }
//        });
    }

    /**
     * 广播内部类
     */
    public class FunctionBroadCast
            extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            System.out.println("广播：---->" + intent.getAction());

            if (WifiManager.WIFI_STATE_CHANGED_ACTION
                    .equals(intent.getAction())) {//这个监听wifi的打开与关闭，与wifi的连接无关
                int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
                System.out.println("WIFI状态" + "wifiState" + wifiState);
                switch (wifiState) {

                    case WifiManager.WIFI_STATE_DISABLED:
                        System.out.println("WIFI_STATE_DISABLED");
                        Toast.makeText(context, getResources().getString(R.string.closed_wifi),
                                Toast.LENGTH_SHORT).show();
                        wifi_bt.setText(getResources().getString(R.string.open_wifi));
                        break;

                    case WifiManager.WIFI_STATE_DISABLING:
                        System.out.println("WIFI_STATE_DISABLING");
                        Toast.makeText(context, getResources().getString(R.string.closeing_wifi),
                                Toast.LENGTH_SHORT).show();
                        break;

                    case WifiManager.WIFI_STATE_ENABLED:
                        System.out.println("WIFI_STATE_ENABLED");
                        Toast.makeText(context, getResources().getString(R.string.opened_wifi),
                                Toast.LENGTH_SHORT).show();
                        wifi_bt.setText(getResources().getString(R.string.close_wifi));
                        break;

                    case WifiManager.WIFI_STATE_ENABLING:
                        System.out.println("WIFI_STATE_ENABLING");
                        Toast.makeText(context, getResources().getString(R.string.opening_wifi),
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }
    }

    /**
     * 注册广播
     */
    private void regeistbroadcast() {
        // TODO Auto-generated method stub
        IntentFilter iFilter = new IntentFilter();
        iFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        functionBroadCast = new FunctionBroadCast();
        registerReceiver(functionBroadCast, iFilter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        regeistbroadcast();
    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(functionBroadCast);
    }
}