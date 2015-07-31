package com.hj.popwindow.function;

import android.content.Context;
import android.media.AudioManager;

/**
 * Created by HJ on 2015/7/15.
 * 设置静音开关
 */
public class SetSilent {

    /**
     * 获取当前情景模式
     * int RINGER_MODE_SILENT = 0; 静音
     * int RINGER_MODE_VIBRATE = 1; 震动
     * int RINGER_MODE_NORMAL = 2; 普通
     *
     * @return
     */
    public static int getRingMode(Context context) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        return audioManager.getRingerMode();
    }

    /**
     * 设置静音开关
     */
    public static void setRingMode(Context context) {

        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        if (getRingMode(context) == AudioManager.RINGER_MODE_NORMAL) {
            audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        } else {
            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            audioManager.setStreamVolume(AudioManager.RINGER_MODE_NORMAL, 4, 0);
        }
    }
}