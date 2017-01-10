package com.android.jtknife.core.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.graphics.Paint.Style;

import com.android.jtknife.core.R;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/1/11
 */
public class StrokeTextViewBigo extends AppCompatTextView {

    private int textColor;
    private int strokeColor;
    private float strokeSize;

    public StrokeTextViewBigo(Context context) {
        super(context);
        z(context, null);
    }

    public StrokeTextViewBigo(Context context, AttributeSet attrs) {
        super(context, attrs);
        z(context, attrs);
    }

    public StrokeTextViewBigo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        z(context, attrs);
    }

    public void z(Context context, AttributeSet attributeSet) {
        this.strokeColor = 0;
        this.strokeSize = 0.0f;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.StrokeTextViewV2);
            this.textColor = obtainStyledAttributes.getColor(R.styleable.StrokeTextViewV2_s_text_color, ViewCompat.MEASURED_STATE_MASK);
            this.strokeColor = obtainStyledAttributes.getColor(R.styleable.StrokeTextViewV2_s_stroke_color, 0);
            this.strokeSize = obtainStyledAttributes.getDimension(R.styleable.StrokeTextViewV2_s_size, 0.0f);
            obtainStyledAttributes.recycle();
        }
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        String charSequence = getText().toString();
        Paint textPaint = new TextPaint(1);
        Paint textPaint2 = new TextPaint(1);
        textPaint.setStyle(Style.STROKE);
        textPaint2.setTextSize(getTextSize());
        textPaint2.setColor(this.textColor);
        textPaint.setColor(this.strokeColor);
        textPaint.setTextSize(getTextSize());
        textPaint.setStrokeWidth(this.strokeSize);
        float measuredHeight = (((float) getMeasuredHeight()) - textPaint.descent()) - ((float) getPaddingBottom());
        canvas.drawText(charSequence, 0.0f, measuredHeight, textPaint);
        canvas.drawText(charSequence, 0.0f, measuredHeight, textPaint2);
    }

    public void setTextColor(int i) {
        this.textColor = i;
        super.setTextColor(0);
    }
}
