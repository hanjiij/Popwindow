package com.hj.popwindow.activitys;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.hj.popwindow.R;
import com.hj.popwindow.adapter.MyThemeAdapter;
import com.hj.popwindow.datatype.ThemeInfo;

import java.util.ArrayList;
import java.util.List;

public class My_Theme_aty
        extends ActionBarActivity {

    private ListView theme_listview;
    private MyThemeAdapter themeAdapter;
    private List<ThemeInfo> themeInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__theme_aty);


        init();

        setListener();

    }

    private void init() {

        theme_listview = (ListView) findViewById(R.id.my_theme_listveiw);

        themeInfos = new ArrayList<>();

        setThemeInfosData();

        themeAdapter = new MyThemeAdapter(My_Theme_aty.this, themeInfos);


        theme_listview.setAdapter(themeAdapter);

    }


    private void setThemeInfosData() {

        for (int i = 0; i < 20; i++) {
            ThemeInfo themeInfo = new ThemeInfo(getResources().getDrawable(R.drawable.ic_launcher),
                    i + ":" + "主题的标题为" + i, "主题的内容为" + i);

            themeInfos.add(themeInfo);
        }
    }

    private void setListener() {



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my__theme_aty, menu);
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
