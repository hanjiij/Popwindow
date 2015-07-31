package com.hj.popwindow.function;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

import java.lang.reflect.Method;

/**
 * Created by HJ on 2015/7/15.
 * 个人热点开关类
 */
public class SetHotSpot {

    private WifiManager wifiManager;
    private boolean flag = false;
    private Context context;
    private SuccessEnable successEnable;
    private FailEnable failEnable;
    private Method method;
    private boolean afterwifienable=false;

    public SetHotSpot(Context context, SuccessEnable successEnable, FailEnable failEnable) {

        this.context = context;
        this.successEnable = successEnable;
        this.failEnable = failEnable;

        //获取wifimanager对象
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);


        //通过反射调用设置热点
        try {
            method = wifiManager.getClass().getMethod(
                    "setWifiApEnabled", WifiConfiguration.class, Boolean.TYPE);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置热点开关
     */
    public void setHot() {
        flag = !flag;
        if (setWifiApEnabled(flag)) {

            if (flag) {
                successEnable.onSuccess(1);
            }else {
                successEnable.onSuccess(0);

                if (afterwifienable) {
                    wifiManager.setWifiEnabled(true);
                    afterwifienable=!afterwifienable;
                    System.out.println("======"+"恢复wifi之前状态");
                }
            }

        } else {
            failEnable.onFail(0);
            flag = !flag;
        }
    }

    /**
     * 获取热点状态
     *
     * @return 10---正在关闭；11---已关闭；12---正在开启；13---已开启   -1---获取失败
     */
    public int getWifiApState() {

        int i = -1;

        try {
            i = (Integer) method.invoke(wifiManager);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return i;
    }

    private boolean setWifiApEnabled(boolean wifiApEnabled) {

        if (wifiManager.isWifiEnabled()) { // 判断wifi开启状态
            wifiManager.setWifiEnabled(false);
            afterwifienable=true;
            System.out.println("======"+"记录wifi之前状态");
        }

        try {
//            //热点的配置类
//            WifiConfiguration apConfig = new WifiConfiguration();
//            //配置热点的名称(可以在名字后面加点随机数什么的)
//            apConfig.SSID = "哈哈哈";
//            //配置热点的密码
//            apConfig.preSharedKey = "1234567890";
//            apConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);


            //返回热点打开状态

            return (Boolean) method.invoke(wifiManager, null, wifiApEnabled);

        } catch (Exception e) {
            return false;
        }
    }

    // 接口返回开关的各种状态信息
    public interface SuccessEnable {
        void onSuccess(int state);
    }

    public interface FailEnable {
        void onFail(int err);
    }
}
