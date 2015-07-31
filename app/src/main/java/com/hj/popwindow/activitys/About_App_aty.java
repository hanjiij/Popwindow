package com.hj.popwindow.activitys;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hj.popwindow.R;
import com.hj.popwindow.activitys.appabout.App_Introduction;
import com.hj.popwindow.activitys.appabout.App_User_Feedback;

public class About_App_aty
        extends ActionBarActivity {

    private ListView about_listview;
    private String[] about_list;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about__app_aty);

        init();
        setListener();
    }

    /**
     * 初始化
     */
    private void init() {

        about_listview = (ListView) findViewById(R.id.about_app_listview);
        about_list = getResources().getStringArray(R.array.about_values);
        myAdapter = new MyAdapter();

        about_listview.setAdapter(myAdapter);
    }

    /**
     * 设置监听
     */
    private void setListener() {

//        about_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(About_App_aty.this, position+"", Toast.LENGTH_SHORT).show();
//            }
//        });
    }


    private class MyAdapter
            extends BaseAdapter {

        @Override
        public int getCount() {
            return about_list.length;
        }

        @Override
        public String getItem(int position) {
            return about_list[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;

            if (convertView == null) {

                convertView = getLayoutInflater().inflate(R.layout.about_app_item_layout, null);

                viewHolder = new ViewHolder();

                viewHolder.content =
                        (TextView) convertView.findViewById(R.id.about_app_item_textview);
                viewHolder.checkBox =
                        (CheckBox) convertView.findViewById(R.id.about_app_item_checkbox);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.content.setText(getItem(position));
            viewHolder.content.setTag(position);


            if (about_list[position].equals("悬浮开关")) {

                viewHolder.checkBox.setVisibility(View.VISIBLE);
                viewHolder.checkBox
                        .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView,
                                                         boolean isChecked) {
                                Toast.makeText(About_App_aty.this, "状态" + isChecked,
                                        Toast.LENGTH_SHORT)
                                        .show();
                            }
                        });
            } else {
                viewHolder.content.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch ((int) ((TextView) v).getTag()) {
                            case 0:

                                startActivity(
                                        new Intent(About_App_aty.this, App_Introduction.class));
                                break;
                            case 1:
                                if (getVersionName().equals("1.0")) {
                                    Toast.makeText(About_App_aty.this, "已是最新版本", Toast.LENGTH_SHORT)
                                            .show();
                                } else {
                                    Toast.makeText(About_App_aty.this, "有新版本，下载更新",
                                            Toast.LENGTH_SHORT)
                                            .show();
                                }
                                break;
                            case 2:
                                break;
                            case 3:
                                startActivity(
                                        new Intent(About_App_aty.this, App_User_Feedback.class));
                                break;
                        }
                    }
                });

                viewHolder.checkBox.setVisibility(View.INVISIBLE);
            }
            return convertView;
        }

        public final class ViewHolder {
            public TextView content;
            public CheckBox checkBox;
        }
    }


    /**
     * 获取版本信息
     *
     * @return 版本信息
     */
    private String getVersionName() {

        PackageManager packageManager = getPackageManager();

        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);

            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {

            return "";
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about__app_aty, menu);
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
