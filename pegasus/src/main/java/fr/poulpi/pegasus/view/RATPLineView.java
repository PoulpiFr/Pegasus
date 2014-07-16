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
import fr.poulpi.pegasus.ratp.LineStyle;

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

    public void setLine(String label) {

        try {
            if (label.equals("1")) {
                this.setLineStyle(LineStyle.M1);
            } else if (label.equals("2")) {
                this.setLineStyle(LineStyle.M2);
            } else if (label.equals("3")) {
                this.setLineStyle(LineStyle.M3);
            } else if (label.equals("4")) {
                this.setLineStyle(LineStyle.M4);
            } else if (label.equals("5")) {
                this.setLineStyle(LineStyle.M5);
            } else if (label.equals("6")) {
                this.setLineStyle(LineStyle.M6);
            } else if (label.equals("7")) {
                this.setLineStyle(LineStyle.M7);
            } else if (label.equals("8")) {
                this.setLineStyle(LineStyle.M8);
            } else if (label.equals("9")) {
                this.setLineStyle(LineStyle.M9);
            } else if (label.equals("10")) {
                this.setLineStyle(LineStyle.M10);
            } else if (label.equals("11")) {
                this.setLineStyle(LineStyle.M11);
            } else if (label.equals("12")) {
                this.setLineStyle(LineStyle.M12);
            } else if (label.equals("13")) {
                this.setLineStyle(LineStyle.M13);
            } else if (label.equals("14")) {
                this.setLineStyle(LineStyle.M14);
            } else if (label.equals("A")) {
                this.setLineStyle(LineStyle.RA);
            } else if (label.equals("B")) {
                this.setLineStyle(LineStyle.RB);
            } else if (label.equals("C")) {
                this.setLineStyle(LineStyle.RC);
            } else if (label.equals("D")) {
                this.setLineStyle(LineStyle.RD);
            } else if (label.equals("E")) {
                this.setLineStyle(LineStyle.RE);
            } else if (label.equals("J")) {
                this.setLineStyle(LineStyle.RJ);
            } else if (label.equals("K")) {
                this.setLineStyle(LineStyle.RK);
            } else if (label.equals("L")) {
                this.setLineStyle(LineStyle.RL);
            } else if (label.equals("N")) {
                this.setLineStyle(LineStyle.RN);
            } else if (label.equals("P")) {
                this.setLineStyle(LineStyle.RP);
            } else if (label.equals("R")) {
                this.setLineStyle(LineStyle.RR);
            } else if (label.equals("U")) {
                this.setLineStyle(LineStyle.RU);
            } else if (label.startsWith("N")) {
                this.setLineStyle(LineStyle.BUS_MODE);
            } else if (Integer.valueOf(label) >= 20) {
                this.setLineStyle(LineStyle.BUS_MODE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setLineStyle(int lineStyle) {

        LineStyle ls = new LineStyle(getContext(), LineStyle.METRO_MODE, lineStyle, null);

        mLinePaint.setColor(ls.getmBgColor());
        mStartEndPaint.setColor(ls.getmBgColor());

        invalidate();

    }
}
