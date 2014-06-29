package fr.poulpi.pegasus.ratp;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import fr.poulpi.pegasus.R;

/**
 * Created by pokito on 29/06/2014.
 */
public class LineStyle {

    private int mBgColor;
    private boolean mIsFilled;
    private String mText;
    private int mTextColor;

    public static final int METRO_MODE = 0;
    public static final int BUS_MODE = 1;

    /*--- Lines IDs ---*/
    public final static int BLANK = 0;
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


    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    private int mode;

    public int getmTextColor() {
        return mTextColor;
    }

    public void setmTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
    }

    public int getmBgColor() {
        return mBgColor;
    }

    public void setmBgColor(int mBgColor) {
        this.mBgColor = mBgColor;
    }

    public boolean ismIsFilled() {
        return mIsFilled;
    }

    public void setmIsFilled(boolean mIsFilled) {
        this.mIsFilled = mIsFilled;
    }

    public String getmText() {
        return mText;
    }

    public void setmText(String mText) {
        this.mText = mText;
    }

    public LineStyle(Context context, int mode, int lineNb, String label) {

        if (mode == METRO_MODE) {
            switch (lineNb) {
            /* METRO */
                case M1:
                    mIsFilled = true;
                    mBgColor = context.getResources().getColor(R.color.boutondor);
                    mTextColor = context.getResources().getColor(R.color.black);
                    mText = "1";
                    break;
                case M2:
                    mIsFilled = true;
                    mBgColor = context.getResources().getColor(R.color.azur);
                    mTextColor = context.getResources().getColor(R.color.white);
                    mText = "2";
                    break;
                case M3:
                    mIsFilled = true;
                    mBgColor = context.getResources().getColor(R.color.olive);
                    mTextColor = context.getResources().getColor(R.color.white);
                    mText = "3";
                    break;
                case M4:
                    mIsFilled = true;
                    mBgColor = context.getResources().getColor(R.color.parme);
                    mTextColor = context.getResources().getColor(R.color.white);
                    mText = "4";
                    break;
                case M5:
                    mIsFilled = true;
                    mBgColor = context.getResources().getColor(R.color.orange);
                    mTextColor = context.getResources().getColor(R.color.black);
                    mText = "5";
                    break;
                case M6:
                    mIsFilled = true;
                    mBgColor = context.getResources().getColor(R.color.menthe);
                    mTextColor = context.getResources().getColor(R.color.black);
                    mText = "6";
                    break;
                case M7:
                    mIsFilled = true;
                    mBgColor = context.getResources().getColor(R.color.rose);
                    mTextColor = context.getResources().getColor(R.color.black);
                    mText = "7";
                    break;
                case M8:
                    mIsFilled = true;
                    mBgColor = context.getResources().getColor(R.color.lilas);
                    mTextColor = context.getResources().getColor(R.color.black);
                    mText = "8";
                    break;
                case M9:
                    mIsFilled = true;
                    mBgColor = context.getResources().getColor(R.color.acacia);
                    mTextColor = context.getResources().getColor(R.color.black);
                    mText = "9";
                    break;
                case M10:
                    mIsFilled = true;
                    mBgColor = context.getResources().getColor(R.color.ocre);
                    mTextColor = context.getResources().getColor(R.color.black);
                    mText = "10";
                    break;
                case M11:
                    mIsFilled = true;
                    mBgColor = context.getResources().getColor(R.color.marron);
                    mTextColor = context.getResources().getColor(R.color.white);
                    mText = "11";
                    break;
                case M12:
                    mIsFilled = true;
                    mBgColor = context.getResources().getColor(R.color.sapin);
                    mTextColor = context.getResources().getColor(R.color.white);
                    mText = "12";
                    break;
                case M13:
                    mIsFilled = true;
                    mBgColor = context.getResources().getColor(R.color.pervenche);
                    mTextColor = context.getResources().getColor(R.color.black);
                    mText = "13";
                    break;
                case M14:
                    mIsFilled = true;
                    mBgColor = context.getResources().getColor(R.color.iris);
                    mTextColor = context.getResources().getColor(R.color.white);
                    mText = "14";
                    break;

            /* TRAMWAY */
                case T1:
                    mIsFilled = false;
                    mBgColor = context.getResources().getColor(R.color.azur);
                    mTextColor = context.getResources().getColor(R.color.azur);
                    mText = "1";
                    break;
                case T2:
                    mIsFilled = false;
                    mBgColor = context.getResources().getColor(R.color.parme);
                    mTextColor = context.getResources().getColor(R.color.parme);
                    mText = "2";
                    break;
                case T3:
                    mIsFilled = false;
                    mBgColor = context.getResources().getColor(R.color.orange);
                    mTextColor = context.getResources().getColor(R.color.orange);
                    mText = "3";
                    break;

            /* RER */
                case RA:
                    mIsFilled = false;
                    mBgColor = context.getResources().getColor(R.color.rera);
                    mTextColor = context.getResources().getColor(R.color.rera);
                    mText = "A";
                    break;
                case RB:
                    mIsFilled = false;
                    mBgColor = context.getResources().getColor(R.color.rerb);
                    mTextColor = context.getResources().getColor(R.color.rerb);
                    mText = "B";
                    break;
                case RC:
                    mIsFilled = false;
                    mBgColor = context.getResources().getColor(R.color.rerc);
                    mTextColor = context.getResources().getColor(R.color.rerc);
                    mText = "C";
                    break;
                case RD:
                    mIsFilled = false;
                    mBgColor = context.getResources().getColor(R.color.rerd);
                    mTextColor = context.getResources().getColor(R.color.rerd);
                    mText = "D";
                    break;
                case RE:
                    mIsFilled = false;
                    mBgColor = context.getResources().getColor(R.color.rere);
                    mTextColor = context.getResources().getColor(R.color.rere);
                    mText = "E";
                    break;
                case RJ:
                    mIsFilled = false;
                    mBgColor = context.getResources().getColor(R.color.rerj);
                    mTextColor = context.getResources().getColor(R.color.rerj);
                    mText = "J";
                    break;
                case RK:
                    mIsFilled = false;
                    mBgColor = context.getResources().getColor(R.color.rerk);
                    mTextColor = context.getResources().getColor(R.color.rerk);
                    mText = "K";
                    break;
                case RL:
                    mIsFilled = false;
                    mBgColor = context.getResources().getColor(R.color.rerl);
                    mTextColor = context.getResources().getColor(R.color.rerl);
                    mText = "L";
                    break;
                case RN:
                    mIsFilled = false;
                    mBgColor = context.getResources().getColor(R.color.rern);
                    mTextColor = context.getResources().getColor(R.color.rern);
                    mText = "N";
                    break;
                case RP:
                    mIsFilled = false;
                    mBgColor = context.getResources().getColor(R.color.rerp);
                    mTextColor = context.getResources().getColor(R.color.rerp);
                    mText = "P";
                    break;
                case RR:
                    mIsFilled = false;
                    mBgColor = context.getResources().getColor(R.color.rerr);
                    mTextColor = context.getResources().getColor(R.color.rerr);
                    mText = "R";
                    break;
                case RU:
                    mIsFilled = false;
                    mBgColor = context.getResources().getColor(R.color.reru);
                    mTextColor = context.getResources().getColor(R.color.reru);
                    mText = "U";
                    break;

            /* SIGNS */
                case RER:
                    mIsFilled = false;
                    mBgColor = context.getResources().getColor(R.color.ratp_blue);
                    mTextColor = context.getResources().getColor(R.color.ratp_blue);
                    mText = "RER";

                    break;
                case METRO:
                    mIsFilled = false;
                    mBgColor = context.getResources().getColor(R.color.ratp_blue);
                    mTextColor = context.getResources().getColor(R.color.ratp_blue);
                    mText = "M";
                    break;
                case TRAMWAY:
                    mIsFilled = false;
                    mBgColor = context.getResources().getColor(R.color.ratp_blue);
                    mTextColor = context.getResources().getColor(R.color.ratp_blue);
                    mText = "T";
                    break;

                case BLANK:
                    mIsFilled = false;
                    mBgColor = context.getResources().getColor(R.color.white);
                    mTextColor = context.getResources().getColor(R.color.white);
                    break;
            }

        } else if(mode==BUS_MODE) {
            mTextColor = context.getResources().getColor(R.color.black);
            mBgColor = context.getResources().getColor(R.color.divider_gray);
            if (label != null) mText = label;
            mIsFilled = true;
        }
    }
}
