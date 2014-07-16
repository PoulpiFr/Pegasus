package fr.poulpi.pegasus.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import fr.poulpi.pegasus.R;
import fr.poulpi.pegasus.ratp.LineStyle;

/**
 * Created by pokito on 29/05/2014.
 */
public class RATPLineSignView extends View {

    private static final int TEXT_SIZE = 50;
    private static final int RADIUS = 3;
    private static final float BUS_BOX_RATIO = (float) 0.8;
    private static final int BORDER = 25;

    private String mText;
    private float mBorder;
    private float mRadius;
    private int mTextColor;
    private int mBgColor;
    private boolean mIsFilled;
    private Paint mTextPaint;
    private Paint mBgPaint;
    private float mTextSize;

    private int mode;
    private Rect mBusRect;

    public RATPLineSignView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.RATPLineSignView,
                0, 0);

        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);

        float radius = (int)RADIUS * dm.density;
        float border = (int)BORDER * dm.density;
        float textSize = (int)TEXT_SIZE * dm.scaledDensity;

        try {
            mTextColor = a.getColor(R.styleable.RATPLineSignView_textColor, Color.BLACK);
            mBgColor = a.getColor(R.styleable.RATPLineSignView_bgColor, Color.BLACK);
            mIsFilled = a.getBoolean(R.styleable.RATPLineSignView_filled, false);
            mRadius = a.getDimension(R.styleable.RATPLineSignView_radius, radius);
            mBorder = a.getDimension(R.styleable.RATPLineSignView_border, border);
            mText = a.getString(R.styleable.RATPLineSignView_text);
            mTextSize = a.getDimension(R.styleable.RATPLineSignView_textSize, textSize);
        } finally {
            a.recycle();
        }

        init(isInEditMode());

    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float centerX = (float) (getWidth() / 2.);
        float centerY = (float) (getHeight() / 2.);

        switch (mode){
            case LineStyle.METRO_MODE:
                canvas.drawCircle(centerX, centerY, mRadius, mBgPaint);
                break;
            case LineStyle.BUS_MODE:
                canvas.drawRect(0, (1-BUS_BOX_RATIO)*getHeight(), getWidth(), BUS_BOX_RATIO*getHeight(), mBgPaint);
                break;
        }

        int xPos = (int) centerX;
        int yPos = (int) ((centerY) - ((mTextPaint.descent() + mTextPaint.ascent()) / 2));

        if (mText != null) {
            canvas.drawText(mText, xPos, yPos, mTextPaint);
        }

    }

    private void init(boolean isInEditMode) {

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        if (!isInEditMode){
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/ratp.otf");
            mTextPaint.setTypeface(tf);
        }

        mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgPaint.setColor(mBgColor);
        mBgPaint.setStrokeWidth(mBorder);

        if(mIsFilled){
            mBgPaint.setStyle(Paint.Style.FILL);
        } else {
            mBgPaint.setStyle(Paint.Style.STROKE);
        }

        int top = (int) (getTop() - getHeight()/6.);
        int bottom = (int) (getBottom() + getHeight()/6.);

    }

    public void setMetroLine(int line){

        mode = LineStyle.METRO_MODE;
        LineStyle ls = new LineStyle(getContext(), LineStyle.METRO_MODE, line, null);

        mIsFilled = ls.ismIsFilled();
        mBgColor = ls.getmBgColor();
        mTextColor = ls.getmTextColor();
        mText = ls.getmText();

        if(line == LineStyle.RER){
            DisplayMetrics dm = new DisplayMetrics();
            WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(dm);

            mTextSize = (int) getContext().getResources().getDimension(R.dimen.rer_sign_text_size);
        }

        init(false);
        invalidate();

    }

    public void setBusLine(String label) {

        mode = LineStyle.BUS_MODE;
        LineStyle ls = new LineStyle(getContext(), LineStyle.BUS_MODE, 0, label);

        mIsFilled = ls.ismIsFilled();
        mBgColor = ls.getmBgColor();
        mTextColor = ls.getmTextColor();
        mText = ls.getmText();

        init(false);
        invalidate();
    }

    public void setLine(String label) {

        try {
            if (label.equals("1")) {
                this.setMetroLine(LineStyle.M1);
            } else if (label.equals("2")) {
                this.setMetroLine(LineStyle.M2);
            } else if (label.equals("3")) {
                this.setMetroLine(LineStyle.M3);
            } else if (label.equals("4")) {
                this.setMetroLine(LineStyle.M4);
            } else if (label.equals("5")) {
                this.setMetroLine(LineStyle.M5);
            } else if (label.equals("6")) {
                this.setMetroLine(LineStyle.M6);
            } else if (label.equals("7")) {
                this.setMetroLine(LineStyle.M7);
            } else if (label.equals("8")) {
                this.setMetroLine(LineStyle.M8);
            } else if (label.equals("9")) {
                this.setMetroLine(LineStyle.M9);
            } else if (label.equals("10")) {
                this.setMetroLine(LineStyle.M10);
            } else if (label.equals("11")) {
                this.setMetroLine(LineStyle.M11);
            } else if (label.equals("12")) {
                this.setMetroLine(LineStyle.M12);
            } else if (label.equals("13")) {
                this.setMetroLine(LineStyle.M13);
            } else if (label.equals("14")) {
                this.setMetroLine(LineStyle.M14);
            } else if (label.equals("A")) {
                this.setMetroLine(LineStyle.RA);
            } else if (label.equals("B")) {
                this.setMetroLine(LineStyle.RB);
            } else if (label.equals("C")) {
                this.setMetroLine(LineStyle.RC);
            } else if (label.equals("D")) {
                this.setMetroLine(LineStyle.RD);
            } else if (label.equals("E")) {
                this.setMetroLine(LineStyle.RE);
            } else if (label.equals("J")) {
                this.setMetroLine(LineStyle.RJ);
            } else if (label.equals("K")) {
                this.setMetroLine(LineStyle.RK);
            } else if (label.equals("L")) {
                this.setMetroLine(LineStyle.RL);
            } else if (label.equals("N")) {
                this.setMetroLine(LineStyle.RN);
            } else if (label.equals("P")) {
                this.setMetroLine(LineStyle.RP);
            } else if (label.equals("R")) {
                this.setMetroLine(LineStyle.RR);
            } else if (label.equals("U")) {
                this.setMetroLine(LineStyle.RU);
            } else if (label.startsWith("N")){
                this.setBusLine(label);
            } else if (Integer.valueOf(label) >= 20) {
                this.setBusLine(label);
            }

        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
