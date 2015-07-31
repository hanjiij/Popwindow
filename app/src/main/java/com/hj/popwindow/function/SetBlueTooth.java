package com.hj.popwindow.function;

import android.bluetooth.BluetoothAdapter;

/**
 * Created by HJ on 2015/7/14.
 * 开关蓝牙类
 * 实例化函数，传递SuccessEnable，FailEnable。
 * 失败返回0代表未找到蓝牙驱动，关闭成功返回1，开启成功返回2
 */
public class SetBlueTooth {

    private BluetoothAdapter bluetoothAdapter;
    private SuccessEnable successEnable;
    private FailEnable failEnable;

    public SetBlueTooth(SuccessEnable successEnable, FailEnable failEnable) {

        this.successEnable = successEnable;
        this.failEnable = failEnable;

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    /**
     * 设置蓝牙开启关闭，自动判断，已开启则关闭，关闭则开启
     */
    public void setBlueToothCloseAOpen() {

        if (bluetoothAdapter == null) {
            failEnable.onFail(0);
        } else {

            if (bluetoothAdapter.isEnabled()) {
                bluetoothAdapter.disable();
                successEnable.onSuccess(1);
            } else {
                bluetoothAdapter.enable();
                successEnable.onSuccess(2);
            }
        }
    }

    /**判断盘呀是否开启
     * @return 0为未找到蓝牙驱动，1代表已开启，2代表已关闭
     */
    public int getBlueToothStatu(){
        if (bluetoothAdapter == null) {
            return 0;
        }else {
            if (bluetoothAdapter.isEnabled()) {
                return 1;
            } else {
                return 2;
            }
        }
    }

    public interface SuccessEnable {
        void onSuccess(int state);
    }

    public interface FailEnable {
        void onFail(int err);
    }
}
