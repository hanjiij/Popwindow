package com.hj.popwindow.activitys.appset;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.hj.popwindow.Config;
import com.hj.popwindow.R;
import com.hj.popwindow.adapter.MyAppInfoAdapter;
import com.hj.popwindow.database.data.DateUtils;
import com.hj.popwindow.datatype.AppInfo;
import com.hj.popwindow.util.GetAppInfo;
import com.hj.popwindow.util.Util_MyDataBase;
import com.hj.popwindow.view.MyGridView;

import java.util.ArrayList;
import java.util.List;

public class Set_Usual_App_aty
        extends ActionBarActivity {

    private MyGridView usual_App_Info_GridView;  // 显示常用app面板gridView框
    private MyAppInfoAdapter usual_app_myAppInfoAdapter; // 常用app面板gridView适配器
    private List<AppInfo> usual_app_info_list; // 常用app面板链表数据


    private GetAppInfo getAppInfo;  //获取已安装程序信息类
    private GridView appsDialogGridView;  //弹出框GridView
    private MyAppInfoAdapter myAppInfoDialogAdapter;  //弹出框列表适配器
    private List<AppInfo> appInfoDialogList;  // 弹出框程序信息列表
    private AlertDialog app_GridView_Dialog;  //弹出框
    private View dialogView; //弹出框的视图

    private AppInfo default_app_info;   // 最后加号为默认程序信息

    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_usual_app_aty);

        init();

        setListener();
    }

    /**
     * 初始化数据变量
     */
    private void init() {

        usual_App_Info_GridView = (MyGridView) findViewById(R.id.usual_app_info_gridveiw);
        usual_app_info_list = new ArrayList<>();
        default_app_info = new AppInfo("", null, getResources().getDrawable(R.drawable.add_item));

        getAppInfo = new GetAppInfo(Set_Usual_App_aty.this);

        //初始化sharedPreferences，读取和写入数据
        SharedPreferences sharedPreferences = getSharedPreferences(Config.THEME_LIST_INFO,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        boolean is_first = sharedPreferences.getBoolean(Config.IS_FIRST_START, true);

        if (is_first) {  // 判断是否为第一次打开软件，若是则向表中添加最近使用的app
            List<AppInfo> recentlyListInfo = getAppInfo.Get_Recently_App_Info();

            for (int i = 0; i < (recentlyListInfo.size() > 8 ? 8 : recentlyListInfo.size()); i++) {
                AppInfo appInfo = recentlyListInfo.get(i);

//                Util_MyDataBase.InsertDataBase(Set_Usual_App_aty.this, appInfo);// 向数据库中插入数据

                DateUtils.insertApp(Set_Usual_App_aty.this, i + 1, appInfo);
            }

            editor.putBoolean(Config.IS_FIRST_START, false);
            editor.commit();
        }

        usual_app_info_list.add(default_app_info);  // 末尾加入加号供选择

        usual_app_myAppInfoAdapter = new MyAppInfoAdapter(Set_Usual_App_aty.this,
                usual_app_info_list, new MyAppInfoAdapter.Position() {
            @Override
            public void onposition(int position) {

                Confirm_delete(position);
            }
        }, false);

        usual_App_Info_GridView.setAdapter(usual_app_myAppInfoAdapter);


        //弹出框界面的初始化信息
        app_GridView_Dialog = new AlertDialog.Builder(Set_Usual_App_aty.this).create();
        dialogView = LayoutInflater.from(Set_Usual_App_aty.this).inflate(R.layout.dialogview_layout,
                null);
        appsDialogGridView = (GridView) dialogView.findViewById(R.id.app_info_gridveiw_dialog);
        appInfoDialogList = new ArrayList<>();
        myAppInfoDialogAdapter =
                new MyAppInfoAdapter(Set_Usual_App_aty.this, appInfoDialogList, null, true);
        appsDialogGridView.setAdapter(myAppInfoDialogAdapter);


        new Thread(new Runnable() {
            @Override
            public void run() {

                System.out.println("开始加载");
                appInfoDialogList = getAppInfo.getAllApps();
                System.out.println("加载结束");
            }
        }).start();
    }

    /**
     * 设置监听
     */
    private void setListener() {

        appsDialogGridView.setOnItemClickListener(  // 弹出框中gridView点击事件的监听
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position,
                                            long id) {
                        System.out.println(position);

                        DateUtils.insertApp(Set_Usual_App_aty.this, (index + 1),
                                appInfoDialogList.get(position));

                        appInfoDialogList.remove(position);  // 将弹出框中添加到常用的那一项删除，防止重复出现

                        if (index!=usual_app_info_list.size()) {
                            appInfoDialogList.add(usual_app_info_list.get(index));
                        }

                        setUsualAppListData();
                        app_GridView_Dialog.cancel();

//                        if (usual_app_info_list.size() != 0) {  // 移除最后一个默认appInfo
//                            usual_app_info_list.remove(usual_app_info_list.size() - 1);
//                        }
//
//                        usual_app_info_list.add(appInfoDialogList.get(position));
//
//                        Util_MyDataBase.InsertDataBase(Set_Usual_App_aty.this,
//                                appInfoDialogList.get(position));// 向数据库中插入数据
//
//                        if (usual_app_info_list.size() < 8) {  // 判断常用app链表大小，若小于8则添加默认appInfo
//
//                            usual_app_info_list.add(default_app_info);
//                        }
//
//                        appInfoDialogList.remove(position);  // 将弹出框中添加到常用的那一项删除，防止重复出现
//
//                        usual_app_myAppInfoAdapter
//                                .NotifyDataSetChanged(usual_app_info_list);// 通知更新常用app
//
//                        app_GridView_Dialog.cancel();
                    }
                });

        usual_App_Info_GridView
                .setOnItemClickListener(new AdapterView.OnItemClickListener() {  // 程序选择按钮监听
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position,
                                            long id) {

                        index = position;

                        setDialog();  // 判断最最后一个是否为默认app信息，默认appname为空

//                        if (position == (usual_app_info_list.size() - 1) &&
//                                usual_app_info_list.get(usual_app_info_list.size() - 1).getAppName()
//                                        .equals("")) {
//
//                            setDialog();  // 判断最最后一个是否为默认app信息，默认appname为空
//                        } /*else {
//                            startApp(usual_app_info_list.get(position).getPackageName());  // 判断不是最后的默认则点击启动此app
//                        }*/
                    }
                });

//        usual_App_Info_GridView.setOnItemLongClickListener(  // 长按删除此程序入口
//                new AdapterView.OnItemLongClickListener() {
//                    @Override
//                    public boolean onItemLongClick(AdapterView<?> parent, View view,
//                                                   final int position,
//                                                   long id) {
//
//                        Confirm_delete(position);
//
//                        return true;
//                    }
//                });

    }

    /**
     * 弹出确认删除快捷入口dialog
     *
     * @param position 删除的位置信息
     */
    private void Confirm_delete(final int position) {
        AlertDialog.Builder alert = new AlertDialog.Builder(Set_Usual_App_aty.this);

        alert.setMessage("是否要删除此app入口");

        alert.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                // 删除面板中的快捷入口，将删除的重新添加到弹出框列表中
                //  appInfoDialogList.add(select_AppInfoList.get(position));
                appInfoDialogList.add(Util_MyDataBase
                        .Get_Package_Name_To_AppInfo(Set_Usual_App_aty.this,
                                usual_app_info_list.get(position)
                                        .getPackageName()));

                Util_MyDataBase.DeleteDatabase(Set_Usual_App_aty.this,
                        usual_app_info_list.get(position).getPackageName());


                usual_app_info_list.remove(position);

                if (usual_app_info_list.size() == 7 &&
                        !usual_app_info_list.get(usual_app_info_list.size() - 1)
                                .getAppName()
                                .equals("")) {
                    usual_app_info_list.add(default_app_info);
                }

                usual_app_myAppInfoAdapter
                        .NotifyDataSetChanged(usual_app_info_list);
            }
        });

        alert.setNegativeButton("取消", null);

        if (!(position == (usual_app_info_list.size() - 1)) ||
                // 判断最后一个是否是默认，若是默认了则不弹出删除框
                !usual_app_info_list.get(usual_app_info_list.size() - 1)
                        .getAppName()
                        .equals("")) {
            alert.show();
        }
    }


    /**
     * 显示dialog
     */
    public void setDialog() {

        app_GridView_Dialog.setView(dialogView);

        if (appInfoDialogList.size() == 0) {  // 若数据为空，则获取数据
            setAppsListData();
        }

        myAppInfoDialogAdapter.NotifyDataSetChanged(appInfoDialogList);

        app_GridView_Dialog.setTitle("选择");

        app_GridView_Dialog.show();

        DisplayMetrics windowManager = getResources().getDisplayMetrics();

        int w = windowManager.widthPixels;
        int h = windowManager.heightPixels;

        WindowManager.LayoutParams params = app_GridView_Dialog.getWindow().getAttributes();
        params.width = (int) (w * (7.0 / 8));  // 800
        params.height = (int) (h * (2.0 / 3));  // 1280
        app_GridView_Dialog.getWindow().setAttributes(params);

    }

    /**
     * 设置常用app列表数据
     */
    private void setUsualAppListData() {

        if (usual_app_info_list.size() > 0) {

            usual_app_info_list.clear();
        }

//        Util_MyDataBase
//                .SetDatabaseToList(Set_Usual_App_aty.this, usual_app_info_list); // 从数据库中获得数据添加到列表中

        usual_app_info_list = DateUtils.getApps(Set_Usual_App_aty.this);

        if (usual_app_info_list.size() < 8) {

            usual_app_info_list.add(default_app_info);
        }

        usual_app_myAppInfoAdapter.NotifyDataSetChanged(usual_app_info_list);  // 更新数据到列表中
    }

    /**
     * 设置弹出框app列表信息的数据
     */
    private void setAppsListData() {

        appInfoDialogList.clear();

        appInfoDialogList = getAppInfo.getAllApps();
    }

    /**
     * 设置推荐app列表的数据
     */
    private void setAdAppListData() {

    }

    /**
     * 启动其他程序
     *
     * @param packageName 所要启动的程序的包名
     */
    private void startApp(String packageName) {
        try {
            Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(Set_Usual_App_aty.this, getResources().getString(R.string.start_err),
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        setUsualAppListData();
    }

    @Override
    protected void onPause() {
        super.onPause();

        Util_MyDataBase.CloseSqlDataBase();  // 关闭数据库
    }
}
