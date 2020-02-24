package com.ro.itrack.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.ro.itrack.R;


public class DrawView extends View {

    private Paint paint1;
    private Paint paint2;

    private float xPosition;
    private float yPosition;

    public DrawView(Context context) {
        super(context);
        initValue();
    }

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initValue();
    }

    public DrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initValue();
    }

    private void initValue() {
        paint1 = new Paint();
        paint2 = new Paint();
        paint1.setColor(Color.BLACK);
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setStrokeWidth(20);
        paint2.setColor(Color.RED);
    }

    public void setUserPosition(PointF pointF){
        this.xPosition = pointF.x;
        this.yPosition = pointF.y;
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(0, 0, getWidth(), 0, paint1);
        canvas.drawLine(0, 0, 0, getHeight(), paint1);
        canvas.drawLine(getWidth(), 0, getWidth(), getHeight(), paint1);
        canvas.drawLine(0, getHeight(), getWidth(), getHeight(), paint1);
        //canvas.drawBitmap(bitmap, 0, 0, paint1);
        canvas.drawCircle(getHeight() -((xPosition * getHeight()) / 3.32F), (yPosition * getWidth()) / 10.81F, 15, paint2); //X-axis mirror
       // invalidate(0, 0, getWidth(), getHeight());
    }

}