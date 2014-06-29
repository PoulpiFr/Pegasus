package fr.poulpi.pegasus.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import fr.poulpi.pegasus.R;

public class RATPLineView extends View {

    public static final int FULL = 0;
    public static final int DASHED = 1;

    private Paint mLinePaint;
    private Paint mDashedLinePaint;

    private float lineWidth;
    private int mode;
    private Paint mStartEndPaint;

    public RATPLineView(Context context) {
        super(context);
        init(null, 0);
    }

    public RATPLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public RATPLineView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.RATPLineView, defStyle, 0);

        lineWidth = a.getDimension(R.styleable.RATPLineView_lineWidth, 0);

        if(a.getString(R.styleable.RATPLineView_mode) != null) {
            if (a.getString(R.styleable.RATPLineView_mode).equals(getContext().getString(R.string.RATPLineView_dashed))) {
                mode = DASHED;
            } else if (a.getString(R.styleable.RATPLineView_mode).equals(getContext().getString(R.string.RATPLineView_full))) {
                mode = FULL;
            }
        } else {
            mode = FULL;
        }

        a.recycle();

        mStartEndPaint = new Paint();
        mStartEndPaint.setColor(getResources().getColor(R.color.acacia));
        mStartEndPaint.setStyle(Paint.Style.FILL);

        // Set up a default TextPaint object
        mLinePaint = new Paint();
        mLinePaint.setStrokeWidth(lineWidth);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setColor(getResources().getColor(R.color.acacia));

        mDashedLinePaint = new Paint();
        mDashedLinePaint.setStrokeWidth(lineWidth);
        mDashedLinePaint.setColor(getResources().getColor(R.color.cobalt));
        mDashedLinePaint.setStyle(Paint.Style.STROKE);

        mDashedLinePaint.setPathEffect(new DashPathEffect(new float[] {0 , 2*lineWidth}, 0));

        mDashedLinePaint.setStrokeJoin(Paint.Join.ROUND);    // set the join to round you want
        mDashedLinePaint.setStrokeCap(Paint.Cap.ROUND);

        // Update TextPaint and text measurements from attributes
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(mode == FULL) {
            // Draw the first circle
        canvas.drawCircle(getWidth() / 2, getWidth() / 2, getWidth() / 2, mStartEndPaint);

        // Draw the second circle
        canvas.drawCircle(getWidth()/2, getBottom() - getWidth()/2, getWidth()/2, mStartEndPaint);

        // Draw the line
            canvas.drawLine((getWidth()) / 2, getTop() + lineWidth, (getWidth()) / 2, getBottom() - lineWidth, mLinePaint);
        } else if(mode == DASHED) {
            canvas.drawLine((getWidth()) / 2, getTop() + lineWidth, (getWidth()) / 2, getBottom() - lineWidth, mDashedLinePaint);
        }
    }

}
