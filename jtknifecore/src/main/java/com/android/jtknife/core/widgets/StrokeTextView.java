package com.android.jtknife.core.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

import com.android.jtknife.core.R;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/11/9
 */
public class StrokeTextView extends TextView {
    private static final int GRAVITY_CENTER = 0;
    private static final int GRAVITY_LEFT = -1;
    private static final String TAG = "StrokeTextView";
    private int mGravity;
    private boolean mHasLine;
    private Paint mLinePaint;
    private int mSpaceHeightForLine;
    private int mStrokeColor;
    private TextPaint mStrokePaint;
    private float mStrokeWidth;
    private TextPaint mWordsPaint;

    public StrokeTextView(Context context) {
        this(context, null);
    }

    public StrokeTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842884);
    }

    public StrokeTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mStrokePaint = new TextPaint();
        this.mWordsPaint = new TextPaint();
        this.mStrokeWidth = 0.0f;
        this.mStrokeColor = GRAVITY_CENTER;
        this.mGravity = GRAVITY_LEFT;
        a(context, attributeSet);
    }

    public void setTextColor(@ColorInt int i) {
        this.mWordsPaint.setColor(i);
    }

    public void setStrokeColor(@ColorInt int i) {
        this.mStrokePaint.setColor(i);
    }

    public void setBottomLine(boolean z) {
        this.mHasLine = z;
        if (this.mHasLine && this.mLinePaint == null) {
            this.mLinePaint = new Paint();
            this.mLinePaint.setStrokeWidth(this.mStrokeWidth);
            this.mLinePaint.setColor(this.mWordsPaint.getColor());
            FontMetricsInt fontMetricsInt = getPaint().getFontMetricsInt();
            this.mSpaceHeightForLine = Math.abs((fontMetricsInt.bottom - fontMetricsInt.ascent) - getLineHeight()) - (fontMetricsInt.bottom - fontMetricsInt.descent);
        }
    }

    private void a(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.StrokeTextView);
            this.mStrokeWidth = obtainStyledAttributes.getDimension(GRAVITY_CENTER, 0.0f);
            this.mStrokeColor = obtainStyledAttributes.getColor(R.styleable.StrokeTextView_s_color, GRAVITY_CENTER);
            this.mGravity = obtainStyledAttributes.getInt(R.styleable.StrokeTextView_s_gravity, GRAVITY_LEFT);
            obtainStyledAttributes.recycle();
        }
        this.mStrokePaint.setColor(this.mStrokeColor);
        this.mStrokePaint.setTypeface(Typeface.DEFAULT_BOLD);
        this.mStrokePaint.setStyle(Style.STROKE);
        this.mStrokePaint.setStrokeWidth(this.mStrokeWidth);
        this.mStrokePaint.setAntiAlias(true);
        this.mStrokePaint.setSubpixelText(true);
        this.mStrokePaint.setTextScaleX(getTextScaleX());
        this.mWordsPaint.setTypeface(Typeface.DEFAULT_BOLD);
        this.mWordsPaint.setAntiAlias(true);
        this.mWordsPaint.setSubpixelText(true);
        this.mWordsPaint.setTextScaleX(getTextScaleX());
        this.mWordsPaint.setColor(getCurrentTextColor());
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        setMeasuredDimension((int) (((float) getMeasuredWidth()) + this.mStrokeWidth), getMeasuredHeight());
    }

    @SuppressLint({"DrawAllocation"})
    protected void onDraw(@NonNull Canvas canvas) {
        canvas.save();
        this.mStrokePaint.setTextSize(getTextSize());
        this.mWordsPaint.setTextSize(getTextSize());
        Alignment alignment = this.mGravity == 0 ? Alignment.ALIGN_CENTER : Alignment.ALIGN_NORMAL;
        StaticLayout staticLayout = new StaticLayout(getText(), this.mStrokePaint, canvas.getWidth(), alignment, 1.0f, 0.0f, true);
        StaticLayout staticLayout2 = new StaticLayout(getText(), this.mWordsPaint, canvas.getWidth(), alignment, 1.0f, 0.0f, true);
        canvas.translate(this.mStrokeWidth / 2.0f, (float) (this.mGravity == 0 ? (getHeight() - staticLayout.getHeight()) / 2 : GRAVITY_CENTER));
        staticLayout.draw(canvas);
        staticLayout2.draw(canvas);
        if (this.mHasLine) {
            int lineCount = getLineCount();
            Layout layout = getLayout();
            int paddingTop = getPaddingTop();
            int paddingLeft = getPaddingLeft();
            for (int i = GRAVITY_CENTER; i < lineCount; i++) {
                int lineBottom = (layout.getLineBottom(i) + paddingTop) - this.mSpaceHeightForLine;
                canvas.drawLine(((float) paddingLeft) + layout.getLineLeft(i), (float) lineBottom, layout.getLineRight(i) + ((float) paddingLeft), (float) lineBottom, this.mLinePaint);
            }
        }
        canvas.restore();
    }
}

