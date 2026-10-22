package com.msnegi.websearchvolley.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.msnegi.websearchvolley.R;

public class RoundCornerImageView extends androidx.appcompat.widget.AppCompatImageView {

    public static float radius = 18.0f;
    Context context;

    public RoundCornerImageView(Context context) {
        super(context);
        this.context = context;
    }

    public RoundCornerImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public RoundCornerImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        radius = context.getResources().getDimension(com.intuit.sdp.R.dimen._10sdp);
        Path clipPath = new Path();
        RectF rect = new RectF(0, 0, this.getWidth(), this.getHeight());
        clipPath.addRoundRect(rect, radius, radius, Path.Direction.CW);
        canvas.clipPath(clipPath);
        super.onDraw(canvas);
    }
}
