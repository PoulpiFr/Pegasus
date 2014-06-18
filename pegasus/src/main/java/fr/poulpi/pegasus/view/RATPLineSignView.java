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

/**
 * Created by pokito on 29/05/2014.
 */
public class RATPLineSignView extends View {

    private static final int TEXT_SIZE = 50;
    private static final int RADIUS = 3;
    private static final float BUS_BOX_RATIO = (float) 0.8;
    private static final int BORDER = 25;
    private static final int METRO_MODE = 0;
    private static final int BUS_MODE = 1;

    private String mText;
    private float mBorder;
    private float mRadius;
    private int mTextColor;
    private int mBgColor;
    private boolean mIsFilled;
    private Paint mTextPaint;
    private Paint mBgPaint;
    private float mTextSize;

    /*--- Lines IDs ---*/
    public static final int BLANK = 0;
    public final static int METRO = -1;
    public final static int RER = -2;
    public final static int TRAMWAY = -3;

    public final static int M1 = 1;
    public final static int M2 = 2;
    public final static int M3 = 3;
    public final static int M4 = 4;
    public final static int M5 = 5;
    public final static int M6 = 6;
    public final static int M7 = 7;
    public final static int M8 = 8;
    public final static int M9 = 9;
    public final static int M10 = 10;
    public final static int M11 = 11;
    public final static int M12 = 12;
    public final static int M13 = 13;
    public final static int M14 = 14;

    public final static int T1 = 21;
    public final static int T2 = 22;
    public final static int T3 = 23;

    public final static int RA = 41;
    public final static int RB = 42;
    public final static int RC = 43;
    public final static int RD = 44;
    public final static int RE = 45;
    public final static int RJ = 46;
    public final static int RK = 47;
    public final static int RL = 48;
    public final static int RN = 49;
    public final static int RP = 50;
    public final static int RR = 51;
    public final static int RU = 52;


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
            case METRO_MODE:
                canvas.drawCircle(centerX, centerY, mRadius, mBgPaint);
                break;
            case BUS_MODE:
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

        mode = METRO_MODE;

        switch (line){
            /* METRO */
            case M1:
                mIsFilled = true;
                mBgColor = getContext().getResources().getColor(R.color.boutondor);
                mTextColor = getContext().getResources().getColor(R.color.black);
                mText = "1";
                break;
            case M2:
                mIsFilled = true;
                mBgColor = getContext().getResources().getColor(R.color.azur);
                mTextColor = getContext().getResources().getColor(R.color.white);
                mText = "2";
                break;
            case M3:
                mIsFilled = true;
                mBgColor = getContext().getResources().getColor(R.color.olive);
                mTextColor = getContext().getResources().getColor(R.color.white);
                mText = "3";
                break;
            case M4:
                mIsFilled = true;
                mBgColor = getContext().getResources().getColor(R.color.parme);
                mTextColor = getContext().getResources().getColor(R.color.white);
                mText = "4";
                break;
            case M5:
                mIsFilled = true;
                mBgColor = getContext().getResources().getColor(R.color.orange);
                mTextColor = getContext().getResources().getColor(R.color.black);
                mText = "5";
                break;
            case M6:
                mIsFilled = true;
                mBgColor = getContext().getResources().getColor(R.color.menthe);
                mTextColor = getContext().getResources().getColor(R.color.black);
                mText = "6";
                break;
            case M7:
                mIsFilled = true;
                mBgColor = getContext().getResources().getColor(R.color.rose);
                mTextColor = getContext().getResources().getColor(R.color.black);
                mText = "7";
                break;
            case M8:
                mIsFilled = true;
                mBgColor = getContext().getResources().getColor(R.color.lilas);
                mTextColor = getContext().getResources().getColor(R.color.black);
                mText = "8";
                break;
            case M9:
                mIsFilled = true;
                mBgColor = getContext().getResources().getColor(R.color.acacia);
                mTextColor = getContext().getResources().getColor(R.color.black);
                mText = "9";
                break;
            case M10:
                mIsFilled = true;
                mBgColor = getContext().getResources().getColor(R.color.ocre);
                mTextColor = getContext().getResources().getColor(R.color.black);
                mText = "10";
                break;
            case M11:
                mIsFilled = true;
                mBgColor = getContext().getResources().getColor(R.color.marron);
                mTextColor = getContext().getResources().getColor(R.color.white);
                mText = "11";
                break;
            case M12:
                mIsFilled = true;
                mBgColor = getContext().getResources().getColor(R.color.sapin);
                mTextColor = getContext().getResources().getColor(R.color.white);
                mText = "12";
                break;
            case M13:
                mIsFilled = true;
                mBgColor = getContext().getResources().getColor(R.color.pervenche);
                mTextColor = getContext().getResources().getColor(R.color.black);
                mText = "13";
                break;
            case M14:
                mIsFilled = true;
                mBgColor = getContext().getResources().getColor(R.color.iris);
                mTextColor = getContext().getResources().getColor(R.color.white);
                mText = "14";
                break;

            /* TRAMWAY */
            case T1:
                mIsFilled = false;
                mBgColor = getContext().getResources().getColor(R.color.azur);
                mTextColor = getContext().getResources().getColor(R.color.azur);
                mText = "1";
                break;
            case T2:
                mIsFilled = false;
                mBgColor = getContext().getResources().getColor(R.color.parme);
                mTextColor = getContext().getResources().getColor(R.color.parme);
                mText = "2";
                break;
            case T3:
                mIsFilled = false;
                mBgColor = getContext().getResources().getColor(R.color.orange);
                mTextColor = getContext().getResources().getColor(R.color.orange);
                mText = "3";
                break;

            /* RER */
            case RA:
                mIsFilled = false;
                mBgColor = getContext().getResources().getColor(R.color.rera);
                mTextColor = getContext().getResources().getColor(R.color.rera);
                mText = "A";
                break;
            case RB:
                mIsFilled = false;
                mBgColor = getContext().getResources().getColor(R.color.rerb);
                mTextColor = getContext().getResources().getColor(R.color.rerb);
                mText = "B";
                break;
            case RC:
                mIsFilled = false;
                mBgColor = getContext().getResources().getColor(R.color.rerc);
                mTextColor = getContext().getResources().getColor(R.color.rerc);
                mText = "C";
                break;
            case RD:
                mIsFilled = false;
                mBgColor = getContext().getResources().getColor(R.color.rerd);
                mTextColor = getContext().getResources().getColor(R.color.rerd);
                mText = "D";
                break;
            case RE:
                mIsFilled = false;
                mBgColor = getContext().getResources().getColor(R.color.rere);
                mTextColor = getContext().getResources().getColor(R.color.rere);
                mText = "E";
                break;
            case RJ:
                mIsFilled = false;
                mBgColor = getContext().getResources().getColor(R.color.rerj);
                mTextColor = getContext().getResources().getColor(R.color.rerj);
                mText = "J";
                break;
            case RK:
                mIsFilled = false;
                mBgColor = getContext().getResources().getColor(R.color.rerk);
                mTextColor = getContext().getResources().getColor(R.color.rerk);
                mText = "K";
                break;
            case RL:
                mIsFilled = false;
                mBgColor = getContext().getResources().getColor(R.color.rerl);
                mTextColor = getContext().getResources().getColor(R.color.rerl);
                mText = "L";
                break;
            case RN:
                mIsFilled = false;
                mBgColor = getContext().getResources().getColor(R.color.rern);
                mTextColor = getContext().getResources().getColor(R.color.rern);
                mText = "N";
                break;
            case RP:
                mIsFilled = false;
                mBgColor = getContext().getResources().getColor(R.color.rerp);
                mTextColor = getContext().getResources().getColor(R.color.rerp);
                mText = "P";
                break;
            case RR:
                mIsFilled = false;
                mBgColor = getContext().getResources().getColor(R.color.rerr);
                mTextColor = getContext().getResources().getColor(R.color.rerr);
                mText = "R";
                break;
            case RU:
                mIsFilled = false;
                mBgColor = getContext().getResources().getColor(R.color.reru);
                mTextColor = getContext().getResources().getColor(R.color.reru);
                mText = "U";
                break;

            /* SIGNS */
            case RER:
                mIsFilled = false;
                mBgColor = getContext().getResources().getColor(R.color.ratp_blue);
                mTextColor = getContext().getResources().getColor(R.color.ratp_blue);
                mText = "RER";

                DisplayMetrics dm = new DisplayMetrics();
                WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
                wm.getDefaultDisplay().getMetrics(dm);

                mTextSize = (int)getContext().getResources().getDimension(R.dimen.rer_sign_text_size);

                break;
            case METRO:
                mIsFilled = false;
                mBgColor = getContext().getResources().getColor(R.color.ratp_blue);
                mTextColor = getContext().getResources().getColor(R.color.ratp_blue);
                mText = "M";
                break;
            case TRAMWAY:
                mIsFilled = false;
                mBgColor = getContext().getResources().getColor(R.color.ratp_blue);
                mTextColor = getContext().getResources().getColor(R.color.ratp_blue);
                mText = "T";
                break;

            case BLANK:
                mIsFilled = false;
                mBgColor = getContext().getResources().getColor(R.color.white);
                mTextColor = getContext().getResources().getColor(R.color.white);
                break;
        }

        init(false);
        invalidate();

    }

    public void setBusLine(String label) {

        mode = BUS_MODE;

        mTextColor = getContext().getResources().getColor(R.color.black);
        mBgColor = getContext().getResources().getColor(R.color.divider_gray);
        mText = label;
        mIsFilled = true;

        init(false);
        invalidate();
    }
}
