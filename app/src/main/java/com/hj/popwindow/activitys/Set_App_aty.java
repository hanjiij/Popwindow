package com.hj.popwindow.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.hj.popwindow.R;
import com.hj.popwindow.activitys.appset.Set_App_Style_aty;
import com.hj.popwindow.activitys.appset.Set_Usual_App_aty;
import com.hj.popwindow.activitys.appset.Set_Usual_Function_aty;
import com.hj.popwindow.database.data.MAPP;

public class Set_App_aty
        extends ActionBarActivity {


    private Button set_app_style_bt, set_usual_app_bt, set_usual_function_bt, about_app_bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_set_app_aty);

        MAPP.setStyleInfo(Set_App_aty.this, 1);

        init();

        setListener();
    }

    private void setListener() {

        set_app_style_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Set_App_aty.this, Set_App_Style_aty.class));
            }
        });

        set_usual_app_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Set_App_aty.this, Set_Usual_App_aty.class));
            }
        });

        set_usual_function_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Set_App_aty.this, Set_Usual_Function_aty.class));
            }
        });

        about_app_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Set_App_aty.this, About_App_aty.class));
            }
        });
    }

    private void init() {

        set_usual_app_bt = (Button) findViewById(R.id.set_usual_app_bt);
        about_app_bt = (Button) findViewById(R.id.about_app);
        set_app_style_bt = (Button) findViewById(R.id.set_app_style);
        set_usual_function_bt = (Button) findViewById(R.id.set_usual_function);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_set__app_aty, menu);
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
