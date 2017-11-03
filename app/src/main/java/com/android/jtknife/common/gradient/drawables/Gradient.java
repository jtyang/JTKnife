package com.android.jtknife.common.gradient.drawables;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/11/3
 */
public class Gradient extends Drawable {

    private Type type = Type.LINEAR;
    private int[] colors = {Color.WHITE, Color.BLACK};
    private float[] offsets;
    private float angle = 0.0f;
    private Shader.TileMode tileMode = Shader.TileMode.CLAMP;
    private float radius = 0.0f;
    private int gradientAlpha = 255;

//    public Gradient(Type type, int[] colors, float[] offsets, float angle, Shader.TileMode tileMode, float radius, int gradientAlpha) {
//        this.type = type;
//        this.colors = colors;
//        this.offsets = offsets;
//        this.angle = angle;
//        this.tileMode = tileMode;
//        this.radius = radius;
//        this.gradientAlpha = gradientAlpha;
//    }

    public Gradient(Type type, float radius) {
        this.type = type;
        this.radius = radius;
    }

    public enum Type {
        LINEAR, RADIAL, SWEEP
    }

    private Matrix matrix = new Matrix();//private val matrix: Matrix = Matrix()
    private boolean rebuildGradient = true;//private var rebuildGradient: Boolean = true
    private Paint gradientPaint = new Paint();//private var gradientPaint: Paint = Paint()
    private boolean centerInit = false;//private var centerInit = false
    private float centerX = 0.0f;//private var centerX: Float = 0.0f
    private float centerY = 0.0f;//private var centerY: Float = 0.0f
    private float scaleX = 1.0F;//private var scaleX: Float = 1.0f
    private float scaleY = 1.0f;//private var scaleY: Float = 1.0f

    //    private int[] colors;
    public void setClolors(int[] colors) {
        this.colors = colors;
        rebuild();
    }

    //    var colors: IntArray = colors
//    set(colors)
//    {
//        field = colors
//        rebuild()
//    }
    public void setOffsets(float[] offsets) {
        this.offsets = offsets;
        rebuild();
    }

    //    var offsets: FloatArray? = offsets
//    set(offsets)
//    {
//        field = offsets
//        rebuild()
//    }
    public void setAngle(float angle) {
        this.angle = angle;
        rebuild();
    }

    //    var angle: Float = angle
//    set(angle)
//    {
//        field = angle
//        rebuild()
//    }
    public void setTileMode(Shader.TileMode tileMode) {
        this.tileMode = tileMode;
        rebuild();
    }

    //    var tileMode: Shader.TileMode = tileMode
//    set(tileMode)
//    {
//        field = tileMode
//        rebuild()
//    }
    public Shader shader;
//    var shader: Shader? = null

    public Paint getPaint(int width, int height, boolean forceRebuild) {
        if (rebuildGradient || forceRebuild) {
            if (!centerInit) {
                centerX = width / 2.0f;
                centerY = height / 2.0f;
                centerInit = true;
            }
            matrix.setScale(scaleX, scaleY, centerX, centerY);
            Matrix matrixWithoutRotate = new Matrix(matrix);
            matrix.postRotate(angle, centerX, centerY);
            if (type == Type.LINEAR) {
                double angleInRadian = Math.toRadians(Double.valueOf(angle));
                float w = (float) Math.cos(angleInRadian) * width / 2;
                float h = (float) Math.sin(angleInRadian) * height / 2;
                LinearGradient shader1 = new LinearGradient(centerX - w, centerY - h, centerX + w, centerY + h, colors, offsets, tileMode);
                shader1.setLocalMatrix(matrixWithoutRotate);
                shader = shader1;
            } else if (type == Type.RADIAL) {
                float r;
                if (radius != 0.0f) {
                    r = radius;
                } else {
                    r = Math.max(width, height) / 2f;
                }
                RadialGradient shader2 = new RadialGradient(centerX, centerY, r, colors, offsets, tileMode);
                shader2.setLocalMatrix(matrix);
                shader = shader2;
            } else if (type == Type.SWEEP) {
                SweepGradient shader3 = new SweepGradient(centerX, centerY, colors, offsets);
                shader3.setLocalMatrix(matrix);
                shader = shader3;
            }
            gradientPaint.reset();
            gradientPaint.setShader(shader);
            gradientPaint.setAlpha(gradientAlpha);
            rebuildGradient = false;
            return gradientPaint;
        }
        return gradientPaint;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        if (canvas != null)
            canvas.drawRect(0f, 0f, canvas.getClipBounds().width(), canvas.getClipBounds().height(), getPaint(canvas.getClipBounds().width(), canvas.getClipBounds().height(), false));
//        canvas.drawRect(0f, 0f, bounds.width().toFloat(), bounds.height().toFloat(), getPaint(bounds.width(), bounds.height()))
    }

    public void rebuild() {
        rebuildGradient = true;
        invalidateSelf();
    }


    @Override
    public void setAlpha(int alpha) {
        this.gradientAlpha = alpha;
        rebuild();
    }

    public void center(float x, float y) {
        centerX = x;
        centerY = y;
        centerInit = true;
        rebuild();
    }

    public void scale(float x, float y) {
        scaleX = x;
        scaleY = y;
        rebuild();
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
//        return when (gradientAlpha)
//        {
//            1 -> PixelFormat.OPAQUE
//            0 -> PixelFormat.TRANSPARENT
//			else -> PixelFormat.TRANSLUCENT
//        }
        if (gradientAlpha == 1) {
            return PixelFormat.OPAQUE;
        } else if (gradientAlpha == 0) {
            return PixelFormat.TRANSPARENT;
        } else {
            return PixelFormat.TRANSLUCENT;
        }
    }

}
