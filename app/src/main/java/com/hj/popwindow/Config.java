package com.hj.popwindow;

/**
 * Created by HJ on 2015/7/10.
 *
 * 变量存储，工具类
 */
public class Config {

    public static String THEME_LIST_INFO="theme_list_info";
    public static String THEME_LIST_USED_ITEM="theme_list_used_item";
    public static String IS_FIRST_START="is_first_start";

    public static String SHORTCUT_CONFIG="shortcut_config";

    public static String ID = "id";
    public static String APP_NAME = "app_name";
    public static String APP_PACKAGE_NAME = "app_package_name";
    public static String APP_ICO = "app_ico";
    public static String IS_SYSTEM_APP = "is_system_app";
    public static String APP_TABLE_NAME = "AppInfo";
    public static String DEFAULT_APP_NAME = "default_app_name";
    public static String DEFAULT_APP_ICO = "default_app_ico";

    public static String IS_STYLE="is_style";
    public static String IS_ITEM_TWO="is_item_two";
    public static String IS_ITEM_NINE="is_item_nine";


    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i< c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }if (c[i]> 65280&& c[i]< 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }
}
