package cn.kairun.kairunuser.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import cn.kairun.kairunuser.R;

/**
 * Created by ZengRong on 2016/3/25.
 */
public class SleepCustomView extends View {
    /**
     * 第一圈的颜色
     */
    private int mFirstColor;
    /**
     * 第二圈的颜色
     */
    private int mSecondColor;
    /**
     * 圈的宽度
     */
    private int mCircleWidth;

    /**
     * 画笔
     */
    private Paint mPaint;
    /**
     * 当前进度
     */
    private int mProgress;
    /**
     * 速度
     */
    private int mSpeed ;

    /**
     * 是否应该开始下一个
     */
    private boolean isNext = false;

    /**
     * 睡眠小时
     */
    private int sleepHour;
    public SleepCustomView(Context context) {
        this(context, null);
    }

    public SleepCustomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    /**
     * 必要的初始化，获得一些自定义的值
     *
     * @param context
     * @param attrs
     * @param defStyle
     */
    public SleepCustomView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SleepProgressBar, defStyle, 0);
        int n = a.getIndexCount();

        for (int i = 0; i < n; i++)
        {
            int attr = a.getIndex(i);
            switch (attr)
            {
                case R.styleable.SleepProgressBar_firstColor:
                    mFirstColor = a.getColor(attr, Color.GREEN);
                    break;
                case R.styleable.SleepProgressBar_secondColor:
                    mSecondColor = a.getColor(attr, Color.RED);
                    break;
                case R.styleable.SleepProgressBar_circleWidth:
                    mCircleWidth = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.SleepProgressBar_speed:
                    mSpeed = a.getInt(attr, 20);// 默认20
                    break;
            }
        }
        a.recycle(); //回收资源
        mPaint = new Paint(); //初始化画笔
        // 绘图线程
        new Thread() {
            public void run()
            {
                while (true)
                {
                    mProgress++;
                    sleepHour++;
                    if (mProgress == 360)
                    {
                        mProgress = 0;
                        sleepHour = 0;
                        if (!isNext) {
                            isNext = true;
                        } else {
                            isNext = true;
                        }

                    }
                    postInvalidate(); //重绘圆
                    try
                    {
                        Thread.sleep(mSpeed); //绘制速度
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            };
        }.start();
   }
    @Override
    protected void onDraw(Canvas canvas) {
        /**
         * 内圆
         */
        int centre = getWidth() / 2; // 获取圆心的x坐标
        int radius = 300;// 半径
        mPaint.setStrokeWidth(mCircleWidth); // 设置圆环的宽度
        mPaint.setAntiAlias(true); // 消除锯齿
        mPaint.setStyle(Paint.Style.STROKE); // 设置空心
        RectF oval = new RectF(centre - radius, centre - radius, centre + radius, centre + radius); // 用于定义的圆弧的形状和大小的界限
        mPaint.setColor(mSecondColor); // 设置圆环的颜色
        canvas.drawCircle(centre, centre, radius, mPaint); // 画出圆环
        mPaint.setColor(mFirstColor); // 设置圆环的颜色
        canvas.drawArc(oval, -90, mProgress, false, mPaint); // 根据进度画圆弧


        mPaint = null;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(3);
        mPaint.setTextSize(80);
        String testString = "睡眠";
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.colortextcolor));
        mPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(testString, oval.centerX(), oval.centerX() - 85, mPaint);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(2);
        mPaint.setTextSize(80);
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorabroadcircular));
        mPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(String.valueOf(sleepHour)+"小时", oval.centerX(), oval.centerX() + 20, mPaint);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(2);
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.colortextcolor));
        canvas.drawLine(oval.centerX() - 200, oval.centerY() + 50, oval.centerX() + 200, oval.centerY() + 50, mPaint);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(2);
        mPaint.setTextSize(50);
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorBlue));
        mPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("睡眠质量", oval.centerX(), oval.centerX() + 120, mPaint);
    }

}
