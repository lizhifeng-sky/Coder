package lzf.coder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/10/13 0013.
 */
public class FlipBoardView extends View {
    private Bitmap bitmap;
    private Matrix matrix;
    private Paint paint;
    private static final int NUM_OF_POINT = 8;
    /***
     * 原图每块的宽度
     */
    private int mFlodWidth;
    /**
     * 折叠时，每块的宽度
     */
    private int mTranslateDisPerFlod;

    /**
     * 图片的折叠后的总宽度
     */
    private int mTranslateDis;

    /**
     * 折叠后的总宽度与原图宽度的比例
     */
    private float mFactor = 0.8f;
    /**
     * 折叠块的个数
     */
    private int mNumOfFolds = 8;

    private Matrix[] mMatrices = new Matrix[mNumOfFolds];

    public FlipBoardView(Context context) {
        this(context, null);
    }

    public FlipBoardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlipBoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);


        //折叠后的总宽度
        mTranslateDis = (int) (bitmap.getWidth() * mFactor);
        //原图每块的宽度
        mFlodWidth = bitmap.getWidth() / mNumOfFolds;
        //折叠时，每块的宽度
        mTranslateDisPerFlod = mTranslateDis / mNumOfFolds;
        //初始化matrix
        for (int i = 0; i < mNumOfFolds; i++) {
            mMatrices[i] = new Matrix();
        }
        //纵轴减小的那个高度，用勾股定理计算下
        int depth = (int) Math.sqrt(mFlodWidth * mFlodWidth
                - mTranslateDisPerFlod * mTranslateDisPerFlod) / 2;
        //转换点
        float[] src = new float[NUM_OF_POINT];
        float[] dst = new float[NUM_OF_POINT];

        for (int i = 0; i < NUM_OF_POINT; i++) {
            src[0] = i * mFlodWidth;
            src[1] = 0;
            src[2] = src[0] + mFlodWidth;
            src[3] = 0;
            src[4] = src[2];
            src[5] = bitmap.getHeight();
            src[6] = src[0];
            src[7] = src[5];

            boolean isEven = i % 2 == 0;

            dst[0] = i * mTranslateDisPerFlod;
            dst[1] = isEven ? 0 : depth;
            dst[2] = dst[0] + mTranslateDisPerFlod;
            dst[3] = isEven ? depth : 0;
            dst[4] = dst[2];
            dst[5] = isEven ? bitmap.getHeight() - depth : bitmap
                    .getHeight();
            dst[6] = dst[0];
            dst[7] = isEven ? bitmap.getHeight() : bitmap.getHeight()
                    - depth;
            mMatrices[i].setPolyToPoly(src, 0, dst, 0, src.length >> 1);
        }

        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < NUM_OF_POINT; i++) {
            canvas.save();
            canvas.concat(mMatrices[i]);
            //控制显示的大小
            canvas.clipRect(mFlodWidth * i, 0, mFlodWidth * i + mFlodWidth,
                    bitmap.getHeight());
            //绘制图片
            canvas.drawBitmap(bitmap, 0, 0, null);
            canvas.restore();
        }
    }
}
