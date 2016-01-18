package com.githang.clipimageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;


/**
 * 带取景框的 surface view
 *
 * @author Geek_Soledad (msdx.android@qq.com)
 * @version 2015-12-28 3.2
 * @since 2015-12-28 3.2
 */
public class Viewfinder extends View {
    private final int mMaskColor;

    private final Paint mPaint;
    private final int mWidth;
    private final int mHeight;
    private final String mTipText;

    private Rect mBorder = new Rect();

    public Viewfinder(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.WHITE);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Viewfinder);
        mWidth = ta.getInteger(R.styleable.Viewfinder_pwWidth, 1);
        mHeight = ta.getInteger(R.styleable.Viewfinder_pwHeight, 1);
        mTipText = ta.getString(R.styleable.Viewfinder_pwTipText);
        final int textSize = ta.getDimensionPixelSize(R.styleable.Viewfinder_pwTipTextSize, 24);
        mPaint.setTextSize(textSize);
        ta.recycle();

        mPaint.setDither(true);
        mMaskColor = getResources().getColor(R.color.viewfinder_mask);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        final int width = getWidth();
        final int height = getHeight();
        final int borderHeight = width * mHeight / mWidth;
        mBorder.right = width;
        mBorder.top = (height - borderHeight) / 2;
        mBorder.bottom = mBorder.top + borderHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        final int width = getWidth();
        final int height = getHeight();

        mPaint.setColor(mMaskColor);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, width, mBorder.top, mPaint);
        canvas.drawRect(0, mBorder.bottom, width, height, mPaint);

        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(1);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(mBorder.left, mBorder.top, mBorder.right, mBorder.bottom, mPaint);

        if (mTipText != null) {
            final float textWidth = mPaint.measureText(mTipText);
            final float startX = (width - textWidth) / 2;
            final Paint.FontMetrics fm = mPaint.getFontMetrics();
            final float startY = mBorder.bottom + mBorder.top / 2 - (fm.descent - fm.ascent) / 2;
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawText(mTipText, startX, startY, mPaint);
        }
    }

    public Rect getBorder() {
        return mBorder;
    }
}