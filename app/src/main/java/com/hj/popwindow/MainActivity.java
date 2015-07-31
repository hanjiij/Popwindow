package com.hj.popwindow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.hj.popwindow.activitys.About_App_aty;
import com.hj.popwindow.activitys.My_Theme_aty;
import com.hj.popwindow.activitys.Set_App_aty;
import com.hj.popwindow.activitys.Theme_aty;


public class MainActivity
        extends ActionBarActivity {


    private Button theme_set_bt, app_set_bt, my_theme_set_bt, about_app_bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        setListener();

    }

    private void setListener() {

        theme_set_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Theme_aty.class));
            }
        });

        my_theme_set_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, My_Theme_aty.class));
            }
        });

        app_set_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, Set_App_aty.class));
            }
        });


        about_app_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, About_App_aty.class));
            }
        });
    }

    private void init() {

        theme_set_bt = (Button) findViewById(R.id.theme_set_bt);
        app_set_bt = (Button) findViewById(R.id.app_set_bt);
        my_theme_set_bt = (Button) findViewById(R.id.My_theme_set_bt);
        about_app_bt = (Button) findViewById(R.id.about_app);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
