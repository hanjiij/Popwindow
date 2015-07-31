package com.hj.popwindow.activitys.appabout;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hj.popwindow.R;

public class App_User_Feedback
        extends ActionBarActivity {

    EditText editText_content;
    Button submit_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app__user__feedback);

        init();

        setListener();
    }

    /**
     * 初始化
     */
    private void init() {
        editText_content = (EditText) findViewById(R.id.user_feedback_edittext);
        submit_button = (Button) findViewById(R.id.user_feedback_button);
    }

    /**
     * 事件响应
     */
    private void setListener() {

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = editText_content.getText().toString();

                if (content.equals("")) {
                    Toast.makeText(App_User_Feedback.this, "请填写反馈建议再提交", Toast.LENGTH_SHORT).show();
                }else {
                    editText_content.setText("");

                    Toast.makeText(App_User_Feedback.this, "提交成功，谢谢您的建议", Toast.LENGTH_SHORT).show();

                    App_User_Feedback.this.finish();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_app__user__feedback, menu);
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
