package com.example.demo;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import java.util.Objects;

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
public class CircleProgressView extends View {
    private float circleWidth;
    @ColorInt
    private int circleColor;
    @ColorInt
    private int percentCircleColor;

    // 下层圆环画笔
    private Paint normalPaint;

    // 上层进度画笔
    private Paint percentPaint;

    private int progress;
    private int lastPercent = 0;
    private Bitmap centerBitmap;
    private Matrix matrix;

    public CircleProgressView(Context context) {
        this(context,null);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.main_page_not_evaluate);
        centerBitmap = Bitmap.createScaledBitmap(centerBitmap,w,h,true);
        matrix = new Matrix();
        matrix.setScale(0.5f,0.5f);
        centerBitmap = Bitmap.createBitmap(centerBitmap,0,0,centerBitmap.getWidth(),centerBitmap.getHeight(),matrix,true);
        matrix.setTranslate((getWidth() - centerBitmap.getWidth())/2,(getHeight() - centerBitmap.getHeight())/2);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        // 以小的尺寸为标准
        if(width > height){
            width = height;
        } else {
            height = width;
        }
        setMeasuredDimension(width,height);
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

    private void init(Context context,@Nullable AttributeSet attrs) {
        TypedArray typedArray = null;
        try {
            typedArray = context.obtainStyledAttributes(attrs,R.styleable.CircleProgressView);
            circleWidth = typedArray.getDimension(R.styleable.CircleProgressView_circle_width,10f);
            circleColor = typedArray.getColor(R.styleable.CircleProgressView_circle_color,Color.GRAY);
            percentCircleColor = typedArray.getColor(R.styleable.CircleProgressView_percent_circle_color,Color.RED);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Objects.requireNonNull(typedArray).recycle();
        }

        normalPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        normalPaint.setColor(circleColor);
        normalPaint.setStrokeWidth(circleWidth);
        normalPaint.setStyle(Paint.Style.STROKE);
        // 圆角
        normalPaint.setStrokeJoin(Paint.Join.ROUND);
        // 圆角
        normalPaint.setStrokeCap(Paint.Cap.ROUND);


        percentPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        percentPaint.setColor(percentCircleColor);
        percentPaint.setStrokeWidth(circleWidth);
        percentPaint.setStyle(Paint.Style.STROKE);
        // 圆角
        percentPaint.setStrokeJoin(Paint.Join.ROUND);
        // 圆角
        percentPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(getWidth()/2,getHeight()/2,getHeight()/2 - circleWidth/2,normalPaint);
        canvas.drawArc(circleWidth/2,circleWidth/2,getWidth()-circleWidth/2,getHeight()-circleWidth/2,-90, progress * 3.6f,false,percentPaint);
        canvas.drawBitmap(centerBitmap,matrix,null);
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
