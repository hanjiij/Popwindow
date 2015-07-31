package com.hj.popwindow.activitys.appset;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.hj.popwindow.Config;
import com.hj.popwindow.R;

public class Set_App_Style_aty
        extends ActionBarActivity {

    private ImageView app_style_two_1lv, app_style_two_2lv, app_style_nine_1lv, app_style_nine_2lv;
    private CheckBox app_style_two_1checkbox, app_style_two_2checkbox, app_style_nine_1checkbox,
            app_style_nine_2checkbox;

    private SharedPreferences sharedPreferences;  // 获取记录是否被点击
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set__app__style_aty);


        init();

        setListener();
    }

    private void init() {
        app_style_two_1lv = (ImageView) findViewById(R.id.app_style_two_1lv);
        app_style_two_2lv = (ImageView) findViewById(R.id.app_style_two_2lv);
        app_style_nine_1lv = (ImageView) findViewById(R.id.app_style_nine_1lv);
        app_style_nine_2lv = (ImageView) findViewById(R.id.app_style_nine_2lv);

        app_style_two_1checkbox = (CheckBox) findViewById(R.id.app_style_two_1checkbox);
        app_style_two_2checkbox = (CheckBox) findViewById(R.id.app_style_two_2checkbox);
        app_style_nine_1checkbox = (CheckBox) findViewById(R.id.app_style_nine_1checkbox);
        app_style_nine_2checkbox = (CheckBox) findViewById(R.id.app_style_nine_2checkbox);

        sharedPreferences = getSharedPreferences(Config.THEME_LIST_INFO,
                Activity.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        boolean is_style=sharedPreferences.getString(Config.IS_STYLE,"two").equals("two");  // 判断选择的主题
        boolean is_item_two=sharedPreferences.getBoolean(Config.IS_ITEM_TWO, false);   // 判断主题一是否勾选
        boolean is_item_nine=sharedPreferences.getBoolean(Config.IS_ITEM_NINE,false);



        app_style_two_1checkbox.setChecked(is_style);
        app_style_two_2checkbox.setChecked(is_item_two);
        app_style_two_2checkbox.setEnabled(is_style);

        app_style_nine_1checkbox.setChecked(!is_style);
        app_style_nine_2checkbox.setChecked(is_item_nine);
        app_style_nine_2checkbox.setEnabled(!is_style);


        if (is_style) {

            app_style_two_1lv.setAlpha(0);

            if (is_item_two) {
                app_style_two_2lv.setAlpha(0);
            }
        } else {

            app_style_nine_1lv.setAlpha(0);

            if (is_item_nine) {
                app_style_nine_2lv.setAlpha(0);
            }
        }

    }

    private void setListener() {

        //两行的checkbox1选择监听
        app_style_two_1checkbox.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if (isChecked) {

                            app_style_two_1lv.setAlpha(0);

                            if (sharedPreferences.getBoolean(Config.IS_ITEM_TWO, false)) {
                                app_style_two_2lv.setAlpha(0);
                            }

                            app_style_nine_1checkbox.setChecked(!isChecked);
                            app_style_two_2checkbox.setEnabled(true);

                        } else {

                            app_style_two_1lv.setAlpha(80);
                            app_style_two_2lv.setAlpha(80);

                            app_style_nine_1checkbox.setChecked(!isChecked);
                            app_style_two_2checkbox.setEnabled(false);
                        }

                        editor.putString(Config.IS_STYLE,"two");  // 写入样式选择
                        editor.commit();
                    }
                });

        //两行的checkbox2选择监听
        app_style_two_2checkbox.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if (isChecked) {
                            app_style_two_2lv.setAlpha(0);
                            editor.putBoolean(Config.IS_ITEM_TWO, true);  // 写入样式选择
                            editor.commit();
                        }else {
                            app_style_two_2lv.setAlpha(80);
                            editor.putBoolean(Config.IS_ITEM_TWO, false);  // 写入样式选择
                            editor.commit();
                        }
                    }
                });

        //九宫格checkbox1选择监听
        app_style_nine_1checkbox.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if (isChecked) {

                            app_style_nine_1lv.setAlpha(0);

                            if (sharedPreferences.getBoolean(Config.IS_ITEM_NINE, false)) {
                                app_style_nine_2lv.setAlpha(0);
                            }

                            app_style_two_1checkbox.setChecked(!isChecked);
                            app_style_nine_2checkbox.setEnabled(true);

                        } else {

                            app_style_nine_1lv.setAlpha(80);
                            app_style_nine_2lv.setAlpha(80);

                            app_style_two_1checkbox.setChecked(!isChecked);
                            app_style_nine_2checkbox.setEnabled(false);
                        }

                        editor.putString(Config.IS_STYLE,"nine");  // 写入样式选择
                        editor.commit();
                    }
                });

        //九宫格checkbox2选择监听
        app_style_nine_2checkbox.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                        if (isChecked) {
                            app_style_nine_2lv.setAlpha(0);
                            editor.putBoolean(Config.IS_ITEM_NINE, true);  // 写入样式选择
                            editor.commit();
                        }else {
                            app_style_nine_2lv.setAlpha(80);
                            editor.putBoolean(Config.IS_ITEM_NINE, false);  // 写入样式选择
                            editor.commit();
                        }
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_set__app__style_aty, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
