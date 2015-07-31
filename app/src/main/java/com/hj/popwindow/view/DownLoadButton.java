package com.hj.popwindow.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.Button;

import com.hj.popwindow.R;

/**
 * Created by HJ on 2015/7/22.
 * 自定义button，用于下载按钮的进度效果
 */
public class DownLoadButton
        extends Button {

    private int w=0;

    public DownLoadButton(Context context) {
        super(context);
    }

    public DownLoadButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DownLoadButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Paint paint=new Paint();

        paint.setColor(getResources().getColor(R.color.use));

        RectF rectF=new RectF(0,0,w,this.getHeight());

        canvas.drawRect(rectF,paint);

        super.onDraw(canvas);
    }

    /**设置进度
     * @param progress 进度值
     */
    public void setCanvasProgress(int progress){

        this.w= (int) ((this.getWidth()*1.0)*(progress/100.0));
        invalidate();
    }
}
