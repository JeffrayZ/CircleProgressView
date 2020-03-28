package com.example.demo;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @ProjectName: CirclePercentView
 * @Package: com.example.circlepercentview
 * @ClassName: CirclePercentView
 * @Description: java类作用描述
 * @Author: Jeffray
 * @CreateDate: 2020/3/28 10:02
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/3/28 10:02
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class CircleProgressView1 extends View {
    // 下层圆环画笔
    private Paint normalPaint;
    // 上层进度画笔
    private Paint percentPaint;

    private int progress;
    private int lastPercent = 0;

    public CircleProgressView1(Context context) {
        super(context);
        init(context);
    }

    public CircleProgressView1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CircleProgressView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public CircleProgressView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec),measureHeight(heightMeasureSpec));
    }

    private int measureHeight(int heightMeasureSpec) {
        int height = 200;
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);
        if(specMode == MeasureSpec.EXACTLY){
            height = specSize;
        }
        return height;
    }

    private int measureWidth(int widthMeasureSpec) {
        int width = 200;
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        if(specMode == MeasureSpec.EXACTLY){
            width = specSize;
        }
        return width;
    }

    private void init(Context context) {
        normalPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        normalPaint.setColor(Color.GRAY);
        normalPaint.setStrokeWidth(8f);
        normalPaint.setStyle(Paint.Style.STROKE);
        // 圆角
        normalPaint.setStrokeJoin(Paint.Join.ROUND);
        // 圆角
        normalPaint.setStrokeCap(Paint.Cap.ROUND);


        percentPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        percentPaint.setColor(Color.RED);
        percentPaint.setStrokeWidth(8f);
        percentPaint.setStyle(Paint.Style.STROKE);
        // 圆角
        percentPaint.setStrokeJoin(Paint.Join.ROUND);
        // 圆角
        percentPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(getWidth()/2,getHeight()/2,getHeight()/2 - 4f,normalPaint);
        canvas.drawArc(4f,4f,getWidth()-4f,getHeight()-4f,-90, progress * 3.6f,false,percentPaint);
    }

    public void setPercent(int percent){

        // 创建 ObjectAnimator 对象
        ObjectAnimator animator = ObjectAnimator.ofInt(this, "progress", lastPercent, percent);
        // 执行动画
        animator.start();
        this.lastPercent = percent;
    }

    // 创建 setter 方法
    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }
}
